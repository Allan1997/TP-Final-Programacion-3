
package app;

import java.io.Console;
import java.io.IOException;
import java.sql.Date;
import java.util.Scanner;

import clases.Administrador;
import clases.Cliente;
import clases.Habitacion;
import clases.Hash;
import clases.Hotel;
import clases.Login;
import clases.Menu;
import clases.Pasajero;
import clases.Recepcionista;
import clases.Usuario;
import excepciones.NoHayHabitacionesException;
import excepciones.NoHayPasajerosException;
import excepciones.NoHayReservasException;
import excepciones.NoSePudoReservarException;
import excepciones.PasajeroYaExisteException;
import jdk.management.resource.internal.inst.SocketOutputStreamRMHooks;

// USER: admin PASS: 1234
// USER: allan pass 12345
// user: recep pass: 1234
public class Main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Hotel hotel = new Hotel("Hotel UTN", "Avenida Siempreviva 742");
		// leemos todos los datos que se tienen en archivos
		try {
			hotel.leerArchivos();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Login login = new Login();
		// leemos todos los datos que se tienen en archivos
		try {
			login.leerArchivo();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		hotel.pasarReservasAHabitaciones(); // reservamos las habitaciones en base a las reservas que tenemos en archivos
		Usuario us = login.Loguear();
		int opcion = 0;
		Menu menu1 = new Menu();

		/////////////// ADMINISTRADOR
		if (us instanceof Administrador) {
			boolean menu = true;
			while (menu == true) {
				menu1.menuAdmin();
				menu1.ingresaOpcion();
				int opcionadmin1 = 0;
				opcionadmin1 = scanner.nextInt();
				switch (opcionadmin1) {

				case 1: {
					menu1.menuUsuario();
					menu1.ingresaOpcion();
					int opcion4 = 0;
					opcion4 = scanner.nextInt();
					switch (opcion4) {
					case 1: { // Crear administrador
						login.nuevoAdministrador();
					}
						break;
					case 2: {// Crear cliente
						login.nuevoCliente();
					}
						break;
					case 3: { // crear recepcionista
						login.nuevoRecepcionista();
					}
						break;
					case 4: {// borrar Usuario
						System.out.println("Ingrese el usuario a eliminar: ");
						String nombreUsuario = scanner.next();
						login.eliminarUsuario(nombreUsuario);
					}
						break;
					case 5: {// modificar usuario
						System.out.println("-------- MODIFICACION DE USUARIO ---------");
						login.modificarUsuario();
					}
						break;
					case 6:{ // listar usuarios
						login.listarUsuarios();
					}break;
					}
				}break;

				case 2: {
					menu1.menuHotel();
					menu1.ingresaOpcion();
					int opcionadmin2 = 0;
					opcionadmin2 = scanner.nextInt();
					switch (opcionadmin2) {
					case 1: {// Informacion del hotel
						hotel.mostrarHotel();
					}
						break;
					case 2: {// Listar todas las habitaciones
						hotel.listarHabitaciones();
					}
						break;
					case 3: {// Mostrar habitaciones disponibles
						System.out.println("--------- DISPONIBLES --------");
						hotel.mostrarDisponible();
					}
						break;
					case 4: {// Mostrar habitaciones ocupadas
						System.out.println("------------ OCUPADAS ----------");
						hotel.mostrarOcupadas();
					}break;
					case 5: {// Reservar una habitacion
						try {
							hotel.nuevaReserva();
						} catch (NoHayHabitacionesException e) {
							System.out.println(e.getMessage());
						} catch (NoSePudoReservarException ex) {
							System.out.println(ex.getMessage());
						}
					}
						break;

					case 6: {// Check in
						System.out.println("-------------- CHECK IN --------------");
						System.out.println("Ingrese numero reserva");
						int numeroReserva = scanner.nextInt();
						hotel.checkIn(numeroReserva);
					}
						break;

					case 7: { // check out
						System.out.println("-------------- CHECK OUT ---------------");
						System.out.println("Ingrese numero de reserva: ");
						int numeroReserva = scanner.nextInt();
						double costo = hotel.checkOut(numeroReserva);
						System.out.println("El costo de la estad�a es: " + costo);
					}
						break;
					case 8: { // agregar habitacion
						System.out.println("------------- AGREGAR HABITACION ------------");
						System.out.println("Ingrese numero de la habitacion: ");
						int numero = scanner.nextInt();
						System.out.println("Ingrese capacidad de la habitacion: ");
						int capacidad = scanner.nextInt();
						System.out.println("Ingrese tarifa de la habitacion: ");
						double tarifa = scanner.nextDouble();
						System.out.println("Ingrese Detalle de la habitacion: ");
						String detalle = scanner.next();
						Habitacion habitacion = new Habitacion(numero, capacidad, tarifa, true, detalle);
						hotel.agregarHabitacion(habitacion);
					}
						break;
					case 9: { // eliminar habitacion
						System.out.println("--------------- ELIMINAR HABITACION -------------");
						System.out.println("Ingrese el numero de habitacion a eliminar:");
						int numeroHabitacion = scanner.nextInt();
						hotel.eliminarHabitacion(numeroHabitacion);
					}break;
					case 10:{ // listar Reservas
							try {
								hotel.listarReservas();
							} catch (NoHayReservasException e) {
								System.out.println(e.getMessage());
							}
					}break;
					case 11:{ // consultar reservas
						System.out.println("--------- CONSULTAR RESERVAS -----------");
						System.out.println("Ingrese DNI para consultar sus reservas");
						int dni = scanner.nextInt();
						System.out.println("------- Reservas --------");
						hotel.consultarReservas(dni);
						
					}break;
					case 12: { // eliminar reserva
						System.out.println("--------------- ELIMINAR RESERVA  -------------");
						System.out.println("Ingrese el numero de reserva a eliminar:");
						int numeroReserva = scanner.nextInt();
						hotel.eliminarReserva(numeroReserva);
					}break;
					case 13: {// Menu de pasajeros
						menu1.menuPasajero();
						menu1.ingresaOpcion();
						int opcionadmin3 = 0;
						opcionadmin3 = scanner.nextInt();
						switch (opcionadmin3) {
						case 1: {// Agregar nuevo pasajero
							try {
								hotel.registrarPasajero();
							} catch (PasajeroYaExisteException e) {
								System.out.println(e.getMessage());
							}
						}
							break;
						case 2: {// eliminar pasajero
							System.out.println("--------- ELIMINACION DE PASAJERO --------");
							System.out.println("Ingrese el dni del pasajero a eliminar");
							int dni = scanner.nextInt();
							hotel.eliminarPasajero(dni);
						}
							break;

						case 3: {// Listar Pasajeros
							try {
								hotel.listarPasajeros();
							} catch (NoHayPasajerosException e) {
								System.out.println(e.getMessage());
							}
						}
							break;
						case 4: { // salir

						}
							break;

						}
					}
					}

				}
					break;
				default: { // salir
					menu = false;
				}
					break;
				}
			}
		}

		///////////// RECEPCIONISTA
		if (us instanceof Recepcionista) {
			boolean menu = true;
			while (menu == true) {
				menu1.menuHotel();
				menu1.ingresaOpcion();
				int opcionadmin2 = 0;
				opcionadmin2 = scanner.nextInt();
				switch (opcionadmin2) {
				case 1: {// Informacion del hotel
					hotel.mostrarHotel();
				}
					break;
				case 2: {// Listar todas las habitaciones
					hotel.listarHabitaciones();
				}
					break;
				case 3: {// Mostrar habitaciones disponibles
					System.out.println("--------- DISPONIBLES --------");
					hotel.mostrarDisponible();
				}
					break;
				case 4: {// Mostrar habitaciones ocupadas
					System.out.println("------------ OCUPADAS ----------");
					hotel.mostrarOcupadas();
				}break;
				case 5: {// Reservar una habitacion
					try {
						hotel.nuevaReserva();
					} catch (NoHayHabitacionesException e) {
						System.out.println(e.getMessage());
					} catch (NoSePudoReservarException ex) {
						System.out.println(ex.getMessage());
					}
				}
					break;

				case 6: {// Check in
					System.out.println("-------------- CHECK IN --------------");
					System.out.println("Ingrese numero reserva");
					int numeroReserva = scanner.nextInt();
					hotel.checkIn(numeroReserva);
				}
					break;

				case 7: { // check out
					System.out.println("-------------- CHECK OUT ---------------");
					System.out.println("Ingrese numero de reserva: ");
					int numeroReserva = scanner.nextInt();
					double costo = hotel.checkOut(numeroReserva);
					System.out.println("El costo de la estad�a es: " + costo);
				}
					break;
				case 8: { // agregar habitacion
					System.out.println("------------- AGREGAR HABITACION ------------");
					System.out.println("Ingrese numero de la habitacion: ");
					int numero = scanner.nextInt();
					System.out.println("Ingrese capacidad de la habitacion: ");
					int capacidad = scanner.nextInt();
					System.out.println("Ingrese tarifa de la habitacion: ");
					double tarifa = scanner.nextDouble();
					System.out.println("Ingrese Detalle de la habitacion: ");
					String detalle = scanner.next();
					Habitacion habitacion = new Habitacion(numero, capacidad, tarifa, true, detalle);
					hotel.agregarHabitacion(habitacion);
				}
					break;
				case 9: { // eliminar habitacion
					System.out.println("--------------- ELIMINAR HABITACION -------------");
					System.out.println("Ingrese el numero de habitacion a eliminar:");
					int numeroHabitacion = scanner.nextInt();
					hotel.eliminarHabitacion(numeroHabitacion);
				}break;
				case 10: {// listar Reservas
					try {
						hotel.listarReservas();
					} catch (NoHayReservasException e) {
						System.out.println(e.getMessage());
					}	
				}break;
				case 11:{ // consultar reservas
					System.out.println("--------- CONSULTAR RESERVAS -----------");
					System.out.println("Ingrese DNI para consultar sus reservas");
					int dni = scanner.nextInt();
					System.out.println("------- Reservas --------");
					hotel.consultarReservas(dni);
					
				}break;
				case 12: { // eliminar reserva
					System.out.println("--------------- ELIMINAR RESERVA  -------------");
					System.out.println("Ingrese el numero de reserva a eliminar:");
					int numeroReserva = scanner.nextInt();
					hotel.eliminarReserva(numeroReserva);
				}break;
				case 13: {// Menu de pasajeros
					menu1.menuPasajero();
					menu1.ingresaOpcion();
					int opcionadmin3 = 0;
					opcionadmin3 = scanner.nextInt();
					switch (opcionadmin3) {
					case 1: {// Agregar nuevo pasajero
						try {
							hotel.registrarPasajero();
						} catch (PasajeroYaExisteException e) {
							System.out.println(e.getMessage());
						}
					}
						break;
					case 2: {// eliminar pasajero
						System.out.println("--------- ELIMINACION DE PASAJERO --------");
						System.out.println("Ingrese el dni del pasajero a eliminar");
						int dni = scanner.nextInt();
						hotel.eliminarPasajero(dni);
					}
						break;

					case 3: {// Listar Pasajeros
						try {
							hotel.listarPasajeros();
						} catch (NoHayPasajerosException e) {
							System.out.println(e.getMessage());
						}
					}
						break;
					default: { // salir
					}
						break;

					}
				}
					break;
				default: { // salir
					menu = false;
				}break;
				}
			}
		}
		///////// CLIENTES
		if (us instanceof Cliente) {
			boolean menu = true;
			while (menu == true) {
				menu1.menuCliente();
				menu1.ingresaOpcion();
				int opcioncliente1 = 0;

				opcioncliente1 = scanner.nextInt();
				switch (opcioncliente1) {
				case 1: { // nueva reserva
					try {
						hotel.nuevaReserva();
					} catch (NoHayHabitacionesException e) {
						System.out.println(e.getMessage());
					} catch (NoSePudoReservarException ex) {
						System.out.println(ex.getMessage());
					}
				}
					break;
				case 2: {// ver habitaciones disponibles
					System.out.println("--------- DISPONIBLES --------");
					hotel.mostrarDisponible();
				}
					break;
					default: {
						menu = false;
					}break;
				}
			}
		}

		System.out.println("GRACIAS VUELVA PRONTOSS");
	}

}

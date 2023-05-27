package org.ieslosremedios.daw1.prog.ut8.actividad92;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class metodosSQL {
    // Se usará PrepareStatement, ya que nos permitirá la creación de consultas parametrizadas
    public static void mostrarMenu(){
        System.out.println("----- MENÚ DE OPCIONES -----");
        System.out.println("1. Dar de alta un equipo");
        System.out.println("2. Dar de alta un jugador");
        System.out.println("3. Dar de baja un equipo");
        System.out.println("4. Dar de baja un jugador");
        System.out.println("5. Consultar datos de un equipo");
        System.out.println("6. Consultar datos de un jugador");
        System.out.println("7. Modificar datos de un equipo");
        System.out.println("8. Modificar datos de un jugador");
        System.out.println("9. Exportar metadatos");
        System.out.println("10. Salir");
        System.out.print("Selecciona una opción: ");
    }
    public static void darAltaEquipo(Scanner scan, Connection con){
        System.out.println("----- DAR DE ALTA UN EQUIPO -----");
        System.out.print("Introduce el nombre del equipo: ");
        String nombre = scan.nextLine();
        System.out.print("Introduce el estadio del equipo: ");
        String estadio = scan.nextLine();
        System.out.print("Introduce la ciudad del equipo: ");
        String ciudad = scan.nextLine();
        System.out.print("Introduce el número de socios del equipo: ");
        int numSocios =scan.nextInt();
        scan.nextLine();

        // Consulta asociada al metodo
        String query = "INSERT INTO equipos (nombre, estadio, ciudad, num_socios) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setString(1, nombre);
            statement.setString(2, estadio);
            statement.setString(3, ciudad);
            statement.setInt(4, numSocios);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Equipo dado de alta correctamente.");
            } else {
                System.out.println("Error al dar de alta el equipo.");
            }
        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
        }
    }

    public static void darAltaJugador(Scanner scan,Connection con){
        System.out.println("----- DAR DE ALTA A UN JUGADOR -----");
        System.out.print("Introduce el nombre del jugador: ");
        String nombre = scan.nextLine();
        System.out.print("Introduce los apellidos del jugador: ");
        String apellidos = scan.nextLine();
        System.out.print("Introduce la fecha de nacimiento del jugador (yyyy-MM-dd): ");
        String fechaNacimiento = scan.nextLine();
        System.out.print("Introduce el ID del equipo en el cual juega el jugador: ");
        int equipoId = scan.nextInt();
        scan.nextLine();

        // Consulta asociada al método
        String query = "INSERT INTO jugadores (nombre, apellidos, fechaNacimiento, equipo_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setString(1, nombre);
            statement.setString(2, apellidos);
            statement.setString(3, fechaNacimiento);
            statement.setInt(4, equipoId);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Equipo dado de alta correctamente.");
            } else {
                System.out.println("Error al dar de alta el equipo.");
            }
        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
        }
    }
    public static void darBajaEquipo(Scanner scan, Connection con){
        System.out.println("----- DAR DE BAJA A UN EQUIPO -----");
        System.out.print("Introduce el nombre del equipo a dar de baja: ");
        String nombreEquipo = scan.nextLine();

        // Consulta asociada al método
        String query = "DELETE FROM equipos WHERE nombre = ?";
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setString(1, nombreEquipo);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Equipo dado de baja correctamente.");
            } else {
                System.out.println("No se encontró un equipo con ese nombre.");
            }
        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
        }
    }
    public static void darBajaJugador(Scanner scan, Connection con) {
        System.out.println("----- DAR DE BAJA A UN JUGADOR -----");
        System.out.print("Introduce el nombre del jugador a dar de baja: ");
        String nombreJugador = scan.nextLine();

        // Consulta asociada al método
        String query = "DELETE FROM jugadores WHERE nombre = ?";
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setString(1, nombreJugador);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Jugador dado de baja correctamente.");
            } else {
                System.out.println("No se encontró un jugador con ese nombre.");
            }
        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
        }
    }
    public static void consultarEquipo(Scanner scan, Connection con) {
        System.out.println("----- CONSULTAR INFORMACIÓN DE UN EQUIPO -----");
        System.out.print("Introduce el nombre del equipo: ");
        String nombreEquipo = scan.nextLine();

        // Consulta asociada al método
        String query = "SELECT * FROM equipos WHERE nombre = ?";
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setString(1, nombreEquipo);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int codigoEquipo = resultSet.getInt("id");
                String estadio = resultSet.getString("estadio");
                String ciudad = resultSet.getString("ciudad");
                int numSocios = resultSet.getInt("num_socios");

                System.out.println("Id del equipo: " + codigoEquipo);
                System.out.println("Estadio: " + estadio);
                System.out.println("Ciudad: " + ciudad);
                System.out.println("Número de socios: " + numSocios);
            } else {
                System.out.println("No se encontró un equipo con ese nombre.");
            }
        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
        }
    }
    public static void consultarJugador(Scanner scan, Connection con) {
        System.out.println("----- CONSULTAR INFORMACIÓN DE UN JUGADOR -----");
        System.out.print("Introduce el nombre del jugador: ");
        String nombreJugador = scan.nextLine();

        // Consulta asociada al método
        String query = "SELECT * FROM jugadores WHERE nombre = ?";
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setString(1, nombreJugador);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int codigoJugador = resultSet.getInt("id");
                String nombre=resultSet.getString("nombre");
                String apellidos = resultSet.getString("apellidos");
                String fechaNacimiento = resultSet.getString("fechaNacimiento");
                int equipoId = resultSet.getInt("equipo_id");

                System.out.println("Código del jugador: " + codigoJugador);
                System.out.println("Nombre del jugador: "+nombre);
                System.out.println("Apellidos: " + apellidos);
                System.out.println("Fecha de nacimiento: " + fechaNacimiento);
                System.out.println("ID del equipo: " + equipoId);
            } else {
                System.out.println("No se encontró un jugador con ese nombre.");
            }
        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
        }
    }
}
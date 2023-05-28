package org.ieslosremedios.daw1.prog.ut8.actividad92;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
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
                System.out.println("Jugador dado de alta correctamente.");
            } else {
                System.out.println("Error al dar de alta al jugador.");
            }
        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
        }
    }
    public static void darBajaEquipo(Scanner scan, Connection con) {
        System.out.println("----- DAR DE BAJA A UN EQUIPO -----");
        System.out.print("Introduce el nombre del equipo a dar de baja: ");
        String nombreEquipo = scan.nextLine();

        // Borrar jugadores asociados al equipo
        String queryBorrarJugadores = "DELETE FROM jugadores WHERE equipo_id = (SELECT id FROM equipos WHERE nombre=?)";
        try (PreparedStatement statementBorrarJugadores = con.prepareStatement(queryBorrarJugadores)) {
            statementBorrarJugadores.setString(1, nombreEquipo);

            int jugadoresBorrados = statementBorrarJugadores.executeUpdate();
            System.out.println("Se han borrado " + jugadoresBorrados + " jugadores asociados al equipo.");
        } catch (SQLException e) {
            System.out.println("Error al borrar los jugadores: " + e.getMessage());
        }

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

    public static void modificarEquipo(Scanner scan, Connection con) {
        System.out.println("----- MODIFICAR INFORMACIÓN DE UN EQUIPO -----");
        System.out.print("Introduce el nombre del equipo a modificar: ");
        String nombreEquipo = scan.nextLine();

        System.out.print("¿Deseas modificar el nombre del equipo? (s/n): ");
        String opcionNombre = scan.nextLine();

        String nuevoNombreEquipo = null;
        if (opcionNombre.equalsIgnoreCase("s")) {
            System.out.print("Introduce el nuevo nombre del equipo: ");
            nuevoNombreEquipo = scan.nextLine();
        }

        System.out.print("¿Deseas modificar el estadio del equipo? (s/n): ");
        String opcionEstadio = scan.nextLine();

        String nuevoEstadio = null;
        if (opcionEstadio.equalsIgnoreCase("s")) {
            System.out.print("Introduce el nuevo estadio del equipo: ");
            nuevoEstadio = scan.nextLine();
        }

        System.out.print("¿Deseas modificar la ciudad del equipo? (s/n): ");
        String opcionCiudad = scan.nextLine();

        String nuevaCiudad = null;
        if (opcionCiudad.equalsIgnoreCase("s")) {
            System.out.print("Introduce la nueva ciudad del equipo: ");
            nuevaCiudad = scan.nextLine();
        }

        System.out.print("¿Deseas modificar el número de socios del equipo? (s/n): ");
        String opcionSocios = scan.nextLine();

        int nuevoNumSocios = 0;
        if (opcionSocios.equalsIgnoreCase("s")) {
            System.out.print("Introduce el nuevo número de socios del equipo: ");
            nuevoNumSocios = scan.nextInt();
            scan.nextLine();
        }

        // Consulta asociada al método
        StringBuilder queryBuilder = new StringBuilder("UPDATE equipos SET");
        boolean setClauseAdded = false;

        if (nuevoNombreEquipo != null) {
            queryBuilder.append(" nombre = ?");
            setClauseAdded = true;
        }

        if (nuevoEstadio != null) {
            if (setClauseAdded) {
                queryBuilder.append(",");
            }
            queryBuilder.append(" estadio = ?");
            setClauseAdded = true;
        }

        if (nuevaCiudad != null) {
            if (setClauseAdded) {
                queryBuilder.append(",");
            }
            queryBuilder.append(" ciudad = ?");
            setClauseAdded = true;
        }

        if (nuevoNumSocios != 0) {
            if (setClauseAdded) {
                queryBuilder.append(",");
            }
            queryBuilder.append(" num_socios = ?");
        }

        queryBuilder.append(" WHERE nombre = ?");

        try (PreparedStatement statement = con.prepareStatement(queryBuilder.toString())) {
            int parameterIndex = 1;

            if (nuevoNombreEquipo != null) {
                statement.setString(parameterIndex++, nuevoNombreEquipo);
            }

            if (nuevoEstadio != null) {
                statement.setString(parameterIndex++, nuevoEstadio);
            }

            if (nuevaCiudad != null) {
                statement.setString(parameterIndex++, nuevaCiudad);
            }

            if (nuevoNumSocios != 0) {
                statement.setInt(parameterIndex++, nuevoNumSocios);
            }

            statement.setString(parameterIndex, nombreEquipo);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Información del equipo modificada correctamente.");
            } else {
                System.out.println("No se encontró un equipo con ese nombre.");
            }
        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
        }
    }
    public static void modificarJugador(Scanner scan, Connection con) {
        System.out.println("----- MODIFICAR INFORMACIÓN DE UN JUGADOR -----");
        System.out.print("Introduce el nombre del jugador a modificar: ");
        String nombreJugador = scan.nextLine();

        System.out.print("¿Deseas modificar el nombre del jugador? (s/n): ");
        String opcionNombre = scan.nextLine();

        String nuevoNombre = null;
        if (opcionNombre.equalsIgnoreCase("s")) {
            System.out.print("Introduce el nuevo nombre del jugador: ");
            nuevoNombre = scan.nextLine();
        }

        System.out.print("¿Deseas modificar los apellidos del jugador? (s/n): ");
        String opcionApellidos = scan.nextLine();

        String nuevosApellidos = null;
        if (opcionApellidos.equalsIgnoreCase("s")) {
            System.out.print("Introduce los nuevos apellidos del jugador: ");
            nuevosApellidos = scan.nextLine();
        }

        System.out.print("¿Deseas modificar la fecha de nacimiento del jugador? (s/n): ");
        String opcionFechaNacimiento = scan.nextLine();

        String nuevaFechaNacimiento = null;
        if (opcionFechaNacimiento.equalsIgnoreCase("s")) {
            System.out.print("Introduce la nueva fecha de nacimiento del jugador (YYYY-MM-DD): ");
            nuevaFechaNacimiento = scan.nextLine();
        }

        System.out.print("¿Deseas modificar el equipo en el cual juega el jugador? (s/n): ");
        String opcionEquipo = scan.nextLine();

        int nuevoEquipo = 0;
        if (opcionEquipo.equalsIgnoreCase("s")) {
            System.out.print("Introduce el nuevo equipo en el cual juega el jugador (código de equipo): ");
            nuevoEquipo = scan.nextInt();
            scan.nextLine();
        }

        // Consulta asociada al método
        StringBuilder queryBuilder = new StringBuilder("UPDATE jugadores SET");
        boolean setClauseAdded = false;

        if (nuevoNombre != null) {
            queryBuilder.append(" nombre = ?");
            setClauseAdded = true;
        }

        if (nuevosApellidos != null) {
            if (setClauseAdded) {
                queryBuilder.append(",");
            }
            queryBuilder.append(" apellidos = ?");
            setClauseAdded = true;
        }

        if (nuevaFechaNacimiento != null) {
            if (setClauseAdded) {
                queryBuilder.append(",");
            }
            queryBuilder.append(" fechaNacimiento = ?");
            setClauseAdded = true;
        }

        if (nuevoEquipo != 0) {
            if (setClauseAdded) {
                queryBuilder.append(",");
            }
            queryBuilder.append(" equipo_id = ?");
        }

        queryBuilder.append(" WHERE nombre = ?");

        try (PreparedStatement statement = con.prepareStatement(queryBuilder.toString())) {
            int parameterIndex = 1;

            if (nuevoNombre != null) {
                statement.setString(parameterIndex++, nuevoNombre);
            }

            if (nuevosApellidos != null) {
                statement.setString(parameterIndex++, nuevosApellidos);
            }

            if (nuevaFechaNacimiento != null) {
                statement.setString(parameterIndex++, nuevaFechaNacimiento);
            }

            if (nuevoEquipo != 0) {
                statement.setInt(parameterIndex++, nuevoEquipo);
            }

            statement.setString(parameterIndex, nombreJugador);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Información del jugador modificada correctamente.");
            } else {
                System.out.println("No se encontró un jugador con ese nombre.");
            }
        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
        }
    }
    public static void exportarMetadatos(Scanner scan, Connection con) {
        FileWriter fileWriter = null;

        try {
            // Obtiene los metadatos de la base de datos
            DatabaseMetaData metaDatos = con.getMetaData();

            // Solicita al usuario el nombre de la tabla a consultar
            System.out.print("Introduce el nombre de la tabla a consultar: ");
            String tabla = scan.nextLine();

            // Crea el archivo de texto para exportar los metadatos
            System.out.print("Introduce la ruta del archivo de salida: ");
            String ruta = scan.nextLine();
            fileWriter = new FileWriter(ruta);

            // Obtiene los metadatos de la tabla especificada
            ResultSet columnas = metaDatos.getColumns(null, null, tabla, null);

            // Verifica si la tabla existe
            if (columnas.next()) {
                fileWriter.write("Tabla: " + tabla + "\n");

                // Recorre los metadatos de las columnas de la tabla
                while (columnas.next()) {
                    String columnName = columnas.getString("COLUMN_NAME");
                    String columnType = columnas.getString("TYPE_NAME");
                    fileWriter.write("    Campo: " + columnName + ", Tipo: " + columnType + "\n");
                }

                System.out.println("Los metadatos se han exportado correctamente.");
            } else {
                System.out.println("No se encontró la tabla especificada en la base de datos.");
            }
        } catch (SQLException | IOException e) {
            System.err.println("Los metadatos no se han podido exportar correctamente " + e.getMessage());
        } finally {
            // Cierra el archivo
            try {
                if (fileWriter != null)
                    fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
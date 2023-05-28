package org.ieslosremedios.daw1.prog.ut8.actividad92;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Actividad82Main extends metodosSQL{
    private static final String URL = "jdbc:mariadb://localhost:3306/campeonato";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            Boolean salir=false;
            int opciones;
            while (!salir) {
                mostrarMenu();
                opciones = scan.nextInt();
                scan.nextLine();
                switch (opciones) {
                    case 1:
                        darAltaEquipo(scan, con);
                        break;
                    case 2:
                        darAltaJugador(scan, con);
                        break;
                    case 3:
                        darBajaEquipo(scan, con);
                        break;
                    case 4:
                        darBajaJugador(scan, con);
                        break;
                    case 5:
                        consultarEquipo(scan, con);
                        break;
                    case 6:
                        consultarJugador(scan, con);
                        break;
                    case 7:
                        modificarEquipo(scan, con);
                        break;
                    case 8:
                        modificarJugador(scan, con);
                        break;
                    case 9:
                        exportarMetadatos(scan,con);
                        break;
                    case 10:
                        System.out.println("Saliendo del programa...");
                        salir=true;
                        scan.close();
                        con.close();
                        break;
                    default:
                        System.out.println("Opción no válida. Introduce un número del 1 al 10.");
                        break;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        scan.close();
    }
}

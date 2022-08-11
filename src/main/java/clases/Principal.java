package clases;

import java.sql.*;
import java.util.Scanner;

import static clases.Inits.scanner;
import static clases.Inits.conn;

public class Principal {

    /**
     * Clases
     */

    static Productos pd = new Productos();
    static Empleado em = new Empleado();
    static Tienda td = new Tienda();

    //--------------


    public static void main(String[] args) {
        initConnection();
        menuTablas();
    }

    public static void menuTablas(){
        scanner = new Scanner(System.in);
        int opt = 0;
        do {

            clearScreen();
            System.out.println("\n\t------ [ MENU ] ------ \n");
            System.out.println("\n1. EMPLEADOS. \n2. TIENDAS. \n3. PRODUCTOS. \n100. Salir. ");
            System.out.print("Digite la tabla que desea mostrar: ");
            opt = scanner.nextInt();

            System.out.println("\n\n");

            switch (opt){
                case 1: em.menuEmpleados(); break;
                case 2: td.menuTiendas(); break;
                case 3: pd.menuProductos(); break;
                default:
                    System.out.println("Opcion no disponible...");
            }
            pause();
            clearScreen();
        }while(opt != 100);
    }

    private static void initConnection() {
        String dbURL = "jdbc:mysql://localhost:3306/cadena_tiendas";
        String username = "root";
        String password = "feferrefe2020";

        try {
            conn = DriverManager.getConnection(dbURL, username, password);

            if (conn != null) {
                System.out.println("Connected");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void pause(){
        System.out.println("Press Any Key To Continue...");
        new java.util.Scanner(System.in).nextLine();
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}

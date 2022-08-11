package clases;

import java.util.Scanner;

import static clases.Empleado.showEmpleado;
import static clases.Principal.*;
import static clases.Inits.scanner;
import static clases.Inits.conn;
import static clases.Inits.statement;
import static clases.Inits.result;
import static clases.Productos.showProductos;
import static clases.Productos.verTodosProductos;

public class Tienda {
    public static void menuTiendas(){
        scanner = new Scanner(System.in);
        int opt = 0;
        do {
            clearScreen();
            System.out.println("\n------- [ TIENDAS ] -------\n");
            System.out.println("\n1. MOSTRAR TIENDAS. \n2. INSERTAR TIENDAS. \n3. ACTUALIZAR TIENDAS. \n4. BORRAR TIENDAS. \n5. VER TODOS LOS PRODUCTOS \n6. VER PRODUCTOS PERDIDOS. \n7. VER EMPLEADOS TIENDA. \n100. Salir. ");
            System.out.print("DIGITE LA OPCION: ");
            opt = scanner.nextInt();

            System.out.println("\n\n");

            switch (opt){
                case 1:
                    String value = showQueryTiendas();
                    showTiendas(value);
                    break;
                case 2: insertarTienda(); break;
                case 3: actualizarTienda(); break;
                case 4: borrarTienda(); break;
                case 5: verTodosProductos(); break;
                case 6:
                    value = verProductosPerdidos();
                    showProductos(value);
                    break;
                case 7:
                    value = verEmpleadosEnTienda();
                    showEmpleado(value);
                    break;
                default:
                    System.out.println("Opcion no disponible...");
            }
            pause();
            clearScreen();
        }while(opt != 100);
    }

    private static String verEmpleadosEnTienda() {
        scanner = new Scanner(System.in);
        int codigo_tienda_empleados;
        System.out.print("\nDigite el codigo de la tienda para mostrar a los empleados: ");
        codigo_tienda_empleados = scanner.nextInt();
        String value = "SELECT * FROM empleados where codigo_tienda = " + codigo_tienda_empleados;
        return value;
    }
    public static void showTiendas(String sql){
        try {
            statement = conn.createStatement();
            result = statement.executeQuery(sql);

            System.out.println("\n----- [ TIENDAS ] -----\n");
            while (result.next()){
                int codigo_tienda = result.getInt(1);
                String nombre_tienda = result.getString(2);
                String direccion_tienda = result.getString(3);
                String estado = result.getString(4);
                int cantidad_empleado = result.getInt(5);

                String output = "[*] - Tienda  %s - %s - %s - %s - %s";
                System.out.println(String.format
                        (output, String.valueOf(codigo_tienda), nombre_tienda, direccion_tienda, estado, cantidad_empleado));
            }
        } catch (Exception e) {
            System.out.println("EXCEPTION: " + e);
            e.getMessage();
        }
    }

    private static void borrarTienda() {
        scanner = new Scanner(System.in);
        try {
            String nombreTienda;

            System.out.print("Que tienda desea eliminar: ");
            nombreTienda = scanner.nextLine();

            String SQL = "DELETE FROM tienda WHERE nombre_tienda = '"+nombreTienda+"' ";


            statement = conn.createStatement();
            statement.executeUpdate(SQL);

            System.out.println("Tienda borrada con exito: " + nombreTienda);
        } catch (Exception e) {
            System.out.println("EXCEPTION: " + e);
            e.getMessage();
        }
    }

    private static void actualizarTienda() {
        scanner = new Scanner(System.in);
        try {
            String nombreTienda, nombreTienda_new;

            System.out.print("Que tienda desea actualizar: ");
            nombreTienda = scanner.nextLine();

            System.out.print("\nComo desea llamarla ahora: ");
            nombreTienda_new = scanner.nextLine();

            String SQL = "UPDATE tienda set nombre_tienda = '"+nombreTienda_new+"' where nombre_tienda = '" + nombreTienda+ "' ";

            statement = conn.createStatement();
            statement.executeUpdate(SQL);

            System.out.println("Tienda actualizada con exito: " + nombreTienda + " a " + nombreTienda_new);
        } catch (Exception e) {
            System.out.println("EXCEPTION: " + e);
            e.getMessage();
        }
    }

    private static void insertarTienda() {
    }

    private static String showQueryTiendas() {
        return "SELECT * FROM tienda";
    }

    private static String verProductosPerdidos() {
        scanner = new Scanner(System.in);
        int codigo_tienda_input;
        System.out.print("\nDigite el codigo de la tienda para mostrar los productos: ");
        codigo_tienda_input = scanner.nextInt();

        String value = "SELECT * FROM productos where codigo_tienda = " + codigo_tienda_input + " and cantidad_productos like " + 0;
        return value;
    }
}

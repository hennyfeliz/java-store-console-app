package clases;

import java.util.Scanner;

import static clases.Principal.*;
import static clases.Inits.scanner;
import static clases.Inits.conn;
import static clases.Inits.statement;
import static clases.Inits.result;
public class Empleado {
    public static void menuEmpleados(){
        scanner = new Scanner(System.in);
        int opt = 0;
        do {
            clearScreen();
            System.out.println("\n------- [ EMPLEADOS ] -------\n");
            System.out.println("\n1. MOSTRAR EMPLEADOS. \n2. INSERTAR EMPLEADO. \n3. ACTUALIZAR EMPLEADO. \n4. BORRAR EMPLEADO. \n100. Salir. ");
            System.out.print("DIGITE LA OPCION: ");
            opt = scanner.nextInt();

            System.out.println("\n\n");

            switch (opt){
                case 1:
                    String value = showQueryEmpleados();
                    showEmpleado(value);
                    break;
                case 2: insertarEmpleado(); break;
                case 3: actualizarEmpleado(); break;
                case 4: borrarEmpleado(); break;
                default:
                    System.out.println("Opcion no disponible...");
            }
            pause();
            clearScreen();
        }while(opt != 100);
    }

    public static String showQueryEmpleados(){
        return "SELECT * FROM Empleados";
    }

    private static void insertarEmpleado() {
    }

    private static void borrarEmpleado() {
        scanner = new Scanner(System.in);
        try {
            String nombreEmpleado;

            System.out.print("Que empleado desea eliminar: ");
            nombreEmpleado = scanner.nextLine();

            String SQL = "DELETE FROM empleados WHERE nombre_empleado = '"+nombreEmpleado+"' ";

            statement = conn.createStatement();
            statement.executeUpdate(SQL);

            System.out.println("Empleado borrado con exito: " + nombreEmpleado);
        } catch (Exception e) {
            System.out.println("EXCEPTION: " + e);
            e.getMessage();
        }
    }

    private static void actualizarEmpleado() {
        scanner = new Scanner(System.in);
        try {
            String nombreEmpleado, nombreTienda_new;

            System.out.print("Que empleado desea despedir: ");
            nombreEmpleado = scanner.nextLine();

            System.out.print("\nA que empleado contratara ahora: ");
            nombreTienda_new = scanner.nextLine();

            String SQL = "UPDATE empleados set nombre_empleado = '"+nombreTienda_new+"' where nombre_empleado = '" + nombreEmpleado+ "' ";

            statement = conn.createStatement();
            statement.executeUpdate(SQL);

            System.out.println("Empleado actualizado con exito: " + nombreEmpleado + " a " + nombreTienda_new);
        } catch (Exception e) {
            System.out.println("EXCEPTION: " + e);
            e.getMessage();
        }
    }

    public static void showEmpleado(String sql){
        try {
            statement = conn.createStatement();
            result = statement.executeQuery(sql);

            System.out.println("\n----- [ EMPLEADOS ] -----\n");
            while (result.next()){
                int codigo_empleado = result.getInt(1);
                String nombre_empleado = result.getString(2);
                int telefono_empleado = result.getInt(3);
                String direccion_empleado = result.getString(4);
                int codigo_tienda = result.getInt(5);

                String output = "[*] - Empleado  %s - %s - %s - %s - %s";
                System.out.println(String.format
                        (output, String.valueOf(codigo_empleado), nombre_empleado, telefono_empleado, direccion_empleado, codigo_tienda));
                //System.out.println(String.format(output, ++count, name, pass, fullname, email));
            }
        } catch (Exception e) {
            System.out.println("EXCEPTION: " + e);
            e.getMessage();
        }
    }
}

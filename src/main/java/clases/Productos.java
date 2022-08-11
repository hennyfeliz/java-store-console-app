package clases;
/*
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
*/
import java.sql.PreparedStatement;
import java.util.Scanner;

import static clases.Inits.scanner;
import static clases.Inits.conn;
import static clases.Inits.statement;
import static clases.Inits.result;

import static clases.Principal.*;

public class Productos {

    public static void menuProductos(){
        scanner = new Scanner(System.in);
        int opt = 0;
        do {
            clearScreen();
            System.out.println("\n------- [ PRODUCTOS ] -------\n");
            System.out.println("\n1. MOSTRAR PRODUCTOS. \n2. INSERTAR PRODUCTOS. \n3. ACTUALIZAR PRODUCTOS. \n4. BORRAR PRODUCTOS. \n5. MANDAR PRODUCTOS. \n100. Salir. ");
            System.out.print("DIGITE LA OPCION: ");
            opt = scanner.nextInt();

            System.out.println("\n\n");

            switch (opt){
                case 1: showQueryProductos(); break;
                case 2: insertarProducto(); break;
                case 3: actualizarProducto(); break;
                case 4: borrarProducto(); break;
                case 5: mandarProducto(); break;
                default:
                    System.out.println("Opcion no disponible...");
            }
            pause();
            clearScreen();
        }while(opt != 100);
    }

    private static void mandarProducto() {
        scanner = new Scanner(System.in);
        int cantidadAMover = 0, contadorProductos = 0, codigo_tienda_mover, codigo_tienda_recivir;
        String nombreProducto;

        try {
            System.out.println("\nQue producto desea mover: ");
            nombreProducto = scanner.nextLine();

            System.out.println("\nDesde que tienda los desea mover: ");
            codigo_tienda_mover = scanner.nextInt();

            String sql = "SELECT * FROM productos where codigo_tienda like " + codigo_tienda_mover;

            statement = conn.createStatement();
            result = statement.executeQuery(sql);

            System.out.println("A que tienda los desea mover: ");
            codigo_tienda_recivir = scanner.nextInt();

            while (result.next()){
                if(nombreProducto.equals(result.getString(2))){
                    contadorProductos++;
                    System.out.println("Cantidad de productos: " + result.getInt(5));
                }
            }

            if (contadorProductos<=1){
                System.out.println("\nQue cantidad de productos desea mover: ");
                cantidadAMover = scanner.nextInt();

                if(cantidadAMover > result.getInt(5)){
                    System.out.println("No hay suficientes productos...");
                    return;
                }

                sql = "UPDATE productos set cantidad_productos = " + cantidadAMover + " where codigo_tienda '" + codigo_tienda_recivir + "' ";
                result = statement.executeQuery(sql);

                System.out.println("\n...productos movidos exitosamente!\n");
            } else {
                System.out.println("...no existe este producto.");
            }
        } catch (Exception e){
            System.out.println("EXCEPTION: " + e);
            e.getMessage();
        }
    }

    public static void createProductoRandom(){

    }

    private static void borrarProducto() {
        scanner = new Scanner(System.in);
        try {
            String nombreProducto;

            System.out.print("Que producto desea eliminar: ");
            nombreProducto = scanner.nextLine();

            String SQL = "DELETE FROM productos WHERE nombre_producto = '"+nombreProducto+"' ";


            statement = conn.createStatement();
            statement.executeUpdate(SQL);

            System.out.println("Producto borrado con exito: " + nombreProducto);
        } catch (Exception e) {
            System.out.println("EXCEPTION: " + e);
            e.getMessage();
        }
    }

    private static void actualizarProducto() {
        scanner = new Scanner(System.in);
        try {
            String nombreProducto, nombreProducto_new;

            System.out.print("Que producto desea actualizar: ");
            nombreProducto = scanner.nextLine();

            System.out.print("\nComo desea llamarlo ahora: ");
            nombreProducto_new = scanner.nextLine();

            String SQL = "UPDATE productos set nombre_producto = '"+nombreProducto_new+"' where nombre_producto = '" + nombreProducto+ "' ";

            statement = conn.createStatement();
            statement.executeUpdate(SQL);

            System.out.println("Producto actualizado con exito: " + nombreProducto + " a " + nombreProducto_new);
        } catch (Exception e) {
            System.out.println("EXCEPTION: " + e);
            e.getMessage();
        }
    }

    private static void insertarProducto() {
        scanner = new Scanner(System.in);
        try {
            String nombre_producto = "Silantro";
            float precio_act_producto = 100.0f, precio_tot_producto = 120.0f;
            int codigo_producto = 4812, codigo_tienda = 1223, cantidad_productos = 40;
            /*
            System.out.println("\n------ [ INGRESAR PRODUCTO ] ------\n");
            System.out.print("Codigo producto: ");
            codigo_producto = scanner.nextInt();
            System.out.print("Nombre producto: ");
            nombre_producto = scanner.nextLine();
            System.out.print("Precio tienda actual: ");
            precio_act_producto = scanner.nextFloat();
            System.out.print("Precio tiendas en general: ");
            precio_tot_producto = scanner.nextFloat();
            System.out.print("Cantidad: ");
            cantidad_productos = scanner.nextInt();
            System.out.print("Codigo tienda: ");
            codigo_tienda = scanner.nextInt();
            */
            String sql = "INSERT INTO productos (codigo_producto, nombre_producto, precio_act_producto, precio_tot_producto, cantidad_productos, codigo_tienda) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setInt(1, codigo_producto);
            statement.setString(2, nombre_producto);
            statement.setFloat(3, precio_act_producto);
            statement.setFloat(4, precio_tot_producto);
            statement.setInt(5, cantidad_productos);
            statement.setInt(6, codigo_tienda);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("\n\nA new product was inserted successfully!");
            }
        } catch (Exception e) {
            System.out.println("EXCEPTION: " + e);
            e.getMessage();
        }
    }

    public static void verTodosProductos() {
        try {
            scanner = new Scanner(System.in);
            int codigo_tienda_input;
            System.out.print("\nDigite el codigo de la tienda para mostrar los productos: ");
            codigo_tienda_input = scanner.nextInt();

            String sql = "SELECT * FROM productos where codigo_tienda = " + codigo_tienda_input;

            statement = conn.createStatement();
            result = statement.executeQuery(sql);

            System.out.println("\n----- [ PRODUCTOS ] -----\n");
            while (result.next()) {
                int codigo_producto = result.getInt(1);
                String nombre_producto = result.getString(2);
                float precio_act_producto = result.getFloat(3);
                float precio_tot_producto = result.getFloat(4);
                int cantidad_producto = result.getInt(5);
                int codigo_tienda = result.getInt(6);

                String output = "[*] - Producto  %s - %s - %s - %s - %s";
                System.out.println(String.format
                        (output, String.valueOf(codigo_producto), nombre_producto, cantidad_producto, precio_act_producto, precio_tot_producto, codigo_tienda));
                //System.out.println(String.format(output, ++count, name, pass, fullname, email));
            }
        } catch (Exception e) {
            System.out.println("EXCEPTION: " + e);
            e.getMessage();
        }
    }

    public static void showProductos(String sql){
        try{
            statement = conn.createStatement();
            result = statement.executeQuery(sql);

            System.out.println("\n----- [ PRODUCTOS ] -----\n");
            while (result.next()){
                int codigo_producto = result.getInt(1);
                String nombre_producto = result.getString(2);
                float precio_act_producto = result.getFloat(3);
                float precio_tot_producto = result.getFloat(4);
                int cantidad_producto = result.getInt(5);
                int codigo_tienda = result.getInt(6);

                String output = "[*] - Producto  %s - %s - %s - %s - %s";
                System.out.println(String.format
                        (output, String.valueOf(codigo_producto), nombre_producto, cantidad_producto, precio_act_producto, precio_tot_producto,codigo_tienda));
                //System.out.println(String.format(output, ++count, name, pass, fullname, email));
            }
        } catch (Exception e) {
            System.out.println("EXCEPTION: " + e);
            e.getMessage();
        }
    }

    private static String showQueryProductos() {
        return "SELECT * FROM productos";
    }
}

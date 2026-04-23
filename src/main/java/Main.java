import java.sql.*;
import java.util.Scanner;

public class Main {
    //Conexion con la bd urusairo base (root) y sin contraseña entramos por el pueto 3306 y la base se llama gestion_usuarios
    static final String URL = "jdbc:mysql://localhost:3306/gestion_usuarios";
    static final String USUARIO = "root";
    static final String CONTRASENIA = "";
    //Implementamos Scanner para hacer las cosas con menu
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        boolean salir = false;
        //Creamos un menu basico con un bucle while
        while (!salir) {
            System.out.println("1. Listar socios");
            System.out.println("2. Insertar socios");
            System.out.println("3. Actualizar socios");
            System.out.println("4. Eliminar un socio");
            System.out.println("0. Salir");

            switch (sc.nextInt()) {
                //direncamente entramos sin pedir datos
                case 1 -> listarSocios();
                //para insertar pedimos dos parametros de entrada ya que el id es autoincrementado
                case 2 -> {
                    System.out.println("Ingrese el nombre del socio");
                    String nombre = sc.next();
                    System.out.println("Ingrese el email del socio");
                    String email = sc.next();
                    insertarUsuarios(nombre, email);
                }
                case 3 -> {
                    System.out.println("Ingrese el id del socio");
                    int id = sc.nextInt();
                    System.out.println("Datos a cambiar");
                    System.out.println("1 - el nombre del socio");
                    System.out.println("2 - el email del socio");
                    System.out.println("3 - ambos");
                    int opcion = sc.nextInt();
                    switch (opcion) {
                        //3 opciones para cambiar los parametrop dependiendo si es solo 1 o ambos a la vez
                        case 1 -> {
                            System.out.println("Ingrese el nuebo nombre del socio");
                            String nombre = sc.next();
                            actualizarSociosNombre(id, nombre);
                        }
                        case 2 -> {
                            System.out.println("Ingrese el nuebo email del socio");
                            String email = sc.next();
                            actualizarSociosEmail(id, email);
                        }
                        case 3 -> {
                            System.out.println("Ingrese el nuebo nombre del socio");
                            String nombre = sc.next();
                            System.out.println("Ingrese el nuebo email del socio");
                            String email = sc.next();
                            actualizarSocios(id, nombre, email);
                        }
                        default -> System.out.println("Opción no válida");
                    }
                }
                case 4 ->{
                    System.out.println("Ingrese el id del socio a borrar");
                    int id = sc.nextInt();
                    System.out.println("Estas seguro");
                    String aux = sc.next();
                    //Verificamos si estas seguro
                    if(aux.equals("si") || aux.equals("Si")){
                        borrarUsuario(id);
                    }
                }
                case 0 -> {
                    salir = true;
                    System.out.println("Saliendo . . .");
                }
                default -> System.out.println("Opción no válida");
            }
        }
        sc.close();
    }


    static void listarSocios() {
        //Usando la conexion de antes entramos con lo parametros ya expefificados
        try (Connection conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENIA);
             //Y creamos una sentencia
             Statement sentencia = conexion.createStatement()) {
            //Guardamos esa sentecia en una variable
            String consultaSQL = "SELECT * FROM usuarios";
            try (ResultSet resultado = sentencia.executeQuery(consultaSQL)) {
                //Try para manejar errores
                boolean hayDatos = false;
                //Comrpobamos sia hay datos
                System.out.println();
                // Recorre los resultados de la consulta
                while (resultado.next()) {
                    //Metemos los datos de java en columnas del SQL
                    hayDatos = true;
                    String id = resultado.getString("id");
                    String nombre = resultado.getString("nombre");
                    String correo = resultado.getString("email");
                    System.out.println("  ID: " + id + " | Nombre: " + nombre + " | Email: " + correo);
                }
                //Si no hay datos en la bd salta el print
                if (!hayDatos) {
                    System.out.println("No hay socios");
                }
            }
            //Si por alguna casuidad encuentra erro salta el carch
        } catch (SQLException e) {
            System.err.println("Error al leer socios: " + e.getMessage());
        }
    }

    static void insertarUsuarios(String nombre, String email) {
        //creamos una consulta donde vamos a insertar los datos con dos valoers z (?, ?)
        String consultaSQL = "INSERT INTO usuarios (nombre, email) VALUES (?, ?)";
        try (Connection conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENIA);
             //Usamos los valores que emos metido de entrada para meter los datos
             PreparedStatement sentencia = conexion.prepareStatement(consultaSQL)) {
            sentencia.setString(1, nombre);
            sentencia.setString(2, email);
            //Comprobamos que se ayan echo los cambio comprobado de que almenos se alla actualizado una sentencia
            if (sentencia.executeUpdate() > 0) {
                System.out.println("Usuario actualizado correctamente");
            } else {
                //Si no se habisa al ususario
                System.out.println("No se pudo actualizar");
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar usuario: " + e.getMessage());
        }

    }

    // Tengo 3 updates uanque podria haberlo echo con uno o con if y seguro que hay alguna opcion para que se peude hacer de forma mas eficiente
    //En los tre se cogen los parametros de entrada del metodo y se actualiza mendiante el id
    //En los tres se comprueba que la sentencia alla funcionado (Si pones los datos iguales te segira diciendo que se a actualizado)

    static void actualizarSociosNombre(int id, String nombre) {
        String consultaSQL = "UPDATE usuarios SET nombre = ? WHERE id = ?";
        try (Connection conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENIA);
             PreparedStatement sentencia = conexion.prepareStatement(consultaSQL)) {
            sentencia.setString(1, nombre);
            sentencia.setInt(2, id);
            if (sentencia.executeUpdate() > 0) {
                System.out.println("Usuario actualizado correctamente");
            } else {
                System.out.println("No se pudo actualizar");
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
        }
    }

    static void actualizarSociosEmail(int id, String email) {
        String consultaSQL = "UPDATE usuarios SET email = ? WHERE id = ?";
        try (Connection conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENIA);
             PreparedStatement sentencia = conexion.prepareStatement(consultaSQL)) {
            sentencia.setString(1, email);
            sentencia.setInt(2, id);
            if (sentencia.executeUpdate() > 0) {
                System.out.println("Usuario actualizado correctamente");
            } else {
                System.out.println("No se pudo actualizar");
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
        }
    }
    static void actualizarSocios(int id, String nombre, String email) {
        String consultaSQL = "UPDATE usuarios SET nombre = ?, email = ? WHERE id = ?";
        try (Connection conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENIA);
             PreparedStatement sentencia = conexion.prepareStatement(consultaSQL)) {
            sentencia.setString(1, nombre);
            sentencia.setString(2, email);
            sentencia.setInt(3, id);
            int filas = sentencia.executeUpdate();
            if (filas > 0) {
                System.out.println("Usuario actualizado correctamente");
            } else {
                System.out.println("No se pudo actualizar");
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
        }
    }
    //Y el delete tambien funciona bastante igual simplemente se combreuba de que el codigo funciones
    //Lo unico que tiene especial es que para llegar al metodo se pide la verificacion del usuario para no brrar sin querer
    static  void borrarUsuario(int id) {
        String consultaSQL = "DELETE FROM usuarios WHERE id = ?";
        try (Connection conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENIA);
             PreparedStatement sentencia = conexion.prepareStatement(consultaSQL)) {
            sentencia.setInt(1, id);
            if (sentencia.executeUpdate() > 0) {
                System.out.println("Usuario borrado correctamente");
            } else {
                System.out.println("No se pudo borrado");
            }
        } catch (SQLException e) {
            System.err.println("Error al borrar usuario: " + e.getMessage());
        }
    }
}

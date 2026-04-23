# ConexionBD-Java

Proyecto de aprendizaje para practicar el uso de SQL y la conexión de bases de datos en Java mediante JDBC.

## Descripción

Este proyecto es un ejercicio práctico cuyo objetivo es aprender a conectar una aplicación Java con una base de datos MySQL utilizando JDBC y el conector oficial de MySQL (`mysql-connector-j`). A través de una sencilla aplicación de consola con menú interactivo, se practican las operaciones fundamentales de SQL: consulta, inserción, actualización y eliminación de registros (CRUD) sobre una tabla llamada `usuarios`.

## Requisitos previos

- Java 8 o superior
- Maven
- MySQL Server corriendo en `localhost:3306`
- Base de datos llamada `gestion_usuarios` con una tabla `usuarios`

## Estructura de la base de datos

La tabla `usuarios` debe tener la siguiente estructura:

```sql
CREATE DATABASE gestion_usuarios;

USE gestion_usuarios;

CREATE TABLE usuarios (
    id    INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email  VARCHAR(100) NOT NULL
);
```

## Configuración de la conexión

Los parámetros de conexión se encuentran al inicio de `Main.java`:

```java
static final String URL        = "jdbc:mysql://localhost:3306/gestion_usuarios";
static final String USUARIO    = "root";
static final String CONTRASENIA = "";
```

Modifícalos según tu entorno si el usuario o la contraseña son diferentes.

## Instalación y ejecución

1. Clona el repositorio:
   ```bash
   git clone https://github.com/XDX8/ConexionBD-Java.git
   cd ConexionBD-Java
   ```

2. Compila el proyecto con Maven:
   ```bash
   mvn compile
   ```

3. Ejecuta la aplicación:
   ```bash
   mvn exec:java -Dexec.mainClass="Main"
   ```

## Uso

Al iniciar la aplicación, aparece un menú con las siguientes opciones:

```
1. Listar socios
2. Insertar socios
3. Actualizar socios
4. Eliminar un socio
0. Salir
```

### Opciones del menú

| Opción | Descripción |
|--------|-------------|
| `1` | Muestra todos los socios registrados (ID, nombre y email). |
| `2` | Solicita nombre y email para insertar un nuevo socio. El ID se genera automáticamente. |
| `3` | Solicita el ID del socio y permite actualizar el nombre, el email o ambos. |
| `4` | Solicita el ID del socio a eliminar y pide confirmación (`si`/`Si`) antes de borrar. |
| `0` | Cierra la aplicación. |

## Tecnologías y conceptos aprendidos

- **JDBC** – API estándar de Java para establecer conexiones con bases de datos relacionales
- **MySQL Connector/J 8.3.0** – driver JDBC oficial de MySQL
- **SQL (CRUD)** – sentencias `SELECT`, `INSERT`, `UPDATE` y `DELETE`
- **PreparedStatement** – consultas parametrizadas para evitar inyección SQL
- **Maven** – gestión de dependencias y construcción del proyecto

## Estructura del proyecto

```
    ConexionBD-Java/
    ├── pom.xml                          # Configuración de Maven
    └── src/
        └── main/
            └── java/
                └── Main.java            # Clase principal con toda la lógica
```

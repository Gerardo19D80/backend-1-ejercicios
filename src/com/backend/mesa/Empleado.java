package com.backend.mesa;

import com.backend.clase.Animal;
import org.apache.log4j.Logger;

import java.sql.*;

public class Empleado {

    //metodo 1
    public static void main(String[] args) {
        Logger LOGGER = Logger.getLogger(Empleado.class);
        Connection connection = null;

        String create = "DROP TABLE IF EXISTS EMPLEADOS; " +
                "CREATE TABLE EMPLEADOS(" +
                "ID INT AUTO_INCREMENT PRIMARY KEY, " +
                "NOMBRE VARCHAR(50) NOT NULL, " +
                "EDAD INT NOT NULL, " +
                "EMPRESA VARCHAR(50) NOT NULL, " +
                "FECHA DATE NOT NULL)";

        String insert = "INSERT INTO EMPLEADOS (NOMBRE, EDAD, EMPRESA, FECHA) VALUES " +
                "('Fernando', 44, 'Digital', '2023-09-14'), " +
                "('Gerardo', 41, 'Facebook', '2026-10-10'), " +
                "('Michael', 38, 'Google', '2022-11-08'), " +
                "('Elton', 25, 'Digital', '2021-12-03'), " +
                "('Moro', 22, 'Google', '2023-10-04')";

        try {
            // llamo al metodo 2
            connection = getConnection();

            //crearTabla
            Statement empleadosBase = connection.createStatement();
            empleadosBase.execute(create);

            //insertarRegistros
            empleadosBase.execute(insert);

            //select all
            ResultSet resultSet = empleadosBase.executeQuery("SELECT * FROM EMPLEADOS");

            //recorrer el result set
            while(resultSet.next()){
                LOGGER.info("El empleado es: " + resultSet.getInt(1) + " - " +
                        resultSet.getString("NOMBRE") + " - " +
                        resultSet.getInt("EDAD") + " - " +
                        resultSet.getString("EMPRESA") + " - " +
                        resultSet.getDate("FECHA"));
            }


        } catch (Exception e){

            e.printStackTrace();
            //LOGGER.log(Level.ERROR, "Se ha producido un error");
            LOGGER.error(e.getMessage());

        } finally {

            try{
                // NO OLVIDAR!!! - cierro la conexion con la base de datos
                // NUNCA NUNC@ SE DEJA LA CONEXION ABIERTA DE LA BASE

                assert connection != null;
                connection.close();

            } catch (Exception e){

                LOGGER.error(e.getMessage());

            }

        }
    }

    // metodo 2 - acceso a la base de datos
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        //indicar que Driver vamos a usar
        Class.forName("org.h2.Driver");
        return DriverManager.getConnection("jdbc:h2:~/basedatosempleado", "fer", "fer");
    }

}

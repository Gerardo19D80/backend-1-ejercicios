package com.backend.clase;

import org.apache.log4j.Logger;

import java.sql.*;

public class Animal {

    //metodo1
    public static void main(String[] args) {

        Logger LOGGER = Logger.getLogger(Animal.class);

        String create = "DROP TABLE IF EXISTS ANIMALES; CREATE TABLE ANIMALES(ID INT AUTO_INCREMENT PRIMARY KEY, NOMBRE VARCHAR(50) NOT NULL, TIPO VARCHAR(50) NOT NULL)";
        //no se pasa el #ID porque es auto-incremental
        String insert = "INSERT INTO ANIMALES(NOMBRE, TIPO) VALUES ('Firulais', 'Perro'), ('Lola', 'Vaca'), ('Homero', 'Perro'), ('Pepe', 'Sapo'), ('Tuki', 'Loro')";

        Connection connection = null;

        try {
            // llamo al metodo 2
            connection = getConnection();

            //crearTabla
            Statement statement = connection.createStatement();
            statement.execute(create);

            //insertarRegistros
            statement.execute(insert);

            //select all
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ANIMALES");

            //recorrer el result set
            while(resultSet.next()){
                LOGGER.info("Animal: " + resultSet.getInt(1) + " - " + resultSet.getString("nombre") + " - " + resultSet.getString("tipo"));
            }

            //eliminar un registro
            statement.execute("DELETE FROM ANIMALES WHERE ID = 1");

            LOGGER.info(" ------------ *** ------------ ");

            //select all
            resultSet = statement.executeQuery("SELECT * FROM ANIMALES");

            //recorrer el result set con el elemento eliminado
            while(resultSet.next()){
                LOGGER.info("Animal: " + resultSet.getInt(1) + " - " + resultSet.getString("nombre") + " - " + resultSet.getString("tipo"));
            }

        } catch (Exception exception){
            exception.printStackTrace();
            LOGGER.error(exception.getMessage());

        } finally {

            try{
                // NO OLVIDAR!!! - cierro la conexion con la base de datos
                // NUNCA NUNC@ SE DEJA LA CONEXION ABIERTA DE LA BASE
                connection.close();
            } catch (Exception exception){
                LOGGER.error(exception.getMessage());
            }
        }

    }

    //metodo2 - para establecer la conexion con la base de datos.
    public static Connection getConnection () throws ClassNotFoundException, SQLException {
        //indicar que Driver vamos a usar
        Class.forName("org.h2.Driver");
        return DriverManager.getConnection("jdbc:h2:~/clase8c1", "sa", "sa");
        //jdbc:h2:mem:testdb en memoria
    }
}

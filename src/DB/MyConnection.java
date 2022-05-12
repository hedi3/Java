/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author hp
 */public class MyConnection {

    public Connection getCnx() {
        return cnx;
    }
    public String url = "jdbc:mysql://localhost:3306/pi";
    public String login = "root";
    public String pwd = "";
    Connection cnx;
    public static MyConnection instance;

    private MyConnection() {
        try {

            cnx = DriverManager.getConnection(url, login, pwd);
            System.out.println("Connexion etablie");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());

        }
    }

   

    public static MyConnection getInstance() {

        if (instance == null) {

            instance = new MyConnection();
        }
        return instance;

    }

}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import DB.MyConnection;
import Entities.Employe;
import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Administrateur
 */
public class LoginController implements Initializable {
 public static Employe connectedUser;
    @FXML
    private TextField email;
    @FXML
    private TextField mdp;
    @FXML
    private Button login;
    @FXML
    private Button inscrivezvous;
  public LoginController() throws IOException, SQLException, NoSuchAlgorithmException {

        connexion = MyConnection.getInstance().getCnx();
    }
    Connection connexion;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void login(ActionEvent event) throws NoSuchAlgorithmException, SQLException, IOException {
        String req = "SELECT * from employe WHERE mail LIKE '" + email.getText() + "' and pass LIKE '" + hashmdp(mdp.getText()) + "' ";

            Statement stm = connexion.createStatement();
            ResultSet rst = stm.executeQuery(req);

            while (rst.next()) {
                Employe p = new Employe(rst.getInt("id")
                    , rst.getString("nom")
                    , rst.getString("prenom")
                    , rst.getDate("dat_embauche")
                    , rst.getString("mail")
                    , rst.getString("pass")
                    , rst.getString("img")
                
                );
                      
                LoginController.connectedUser = p;  
        
                
               
                       Parent page1 = FXMLLoader.load(getClass().getResource("ListEmployee.fxml"));
        Scene scene = new Scene(page1);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Profil");
        stage.setScene(scene);
        stage.show();    
           
                
                
            
       
        
    } 
        
        
    }

    @FXML
    private void InscrivezVous(ActionEvent event) throws IOException {
           Parent page1 = FXMLLoader.load(getClass().getResource("Ajout.fxml"));
        Scene scene = new Scene(page1);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Profil");
        stage.setScene(scene);
        stage.show();     
        
        
    }
    private String hashmdp(String mdp) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(mdp.getBytes());

        byte byteData[] = md.digest();

        //convertir le tableau de bits en une format hexadécimal - méthode 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        //convertir le tableau de bits en une format hexadécimal - méthode 2
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            String hex = Integer.toHexString(0xff & byteData[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }
}

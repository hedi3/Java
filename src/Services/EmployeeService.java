/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import DB.MyConnection;
import Entities.Employe;
import java.security.MessageDigest;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Administrateur
 */
public class EmployeeService {
    Connection connexion;   
  public EmployeeService() {
        connexion = MyConnection.getInstance().getCnx();
    }
  public String hashmdp (String mdp) throws NoSuchAlgorithmException{
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(mdp.getBytes());

        byte byteData[] = md.digest();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        StringBuffer hexString = new StringBuffer();
     for (int i=0;i<byteData.length;i++) {
      String hex=Integer.toHexString(0xff & byteData[i]);
          if(hex.length()==1) hexString.append('0');
          hexString.append(hex);
     }
     
    
       return hexString.toString();
    }


  public void ajouterEmployee(Employe e) throws SQLException, NoSuchAlgorithmException {
        String req = "INSERT INTO `employe` (`nom`,`prenom`,`dat_embauche`,`mail`,`pass`,`img`) "
                + "VALUES (?,?,?,?,?,?) ";
        PreparedStatement ps = connexion.prepareStatement(req);
        ps.setString(1, e.getNom());
        ps.setString(2, e.getPrenom());
        ps.setDate(3, (java.sql.Date) (Date) e.getDat_embauche());
        ps.setString(4, e.getMail());
               ps.setString(5,  hashmdp (e.getPass()));
                 ps.setString(6, e.getImg());
                  

        ps.executeUpdate();
    }
  

     public List<Employe> AfficherAllEmployee() throws SQLException {

        List<Employe> Employes = new ArrayList<>();
        String req = "select * from employe ";
        Statement stm = connexion.createStatement();
        ResultSet rst = stm.executeQuery(req);

        while (rst.next()) {
            Employe e = new Employe(rst.getInt("id")
                    , rst.getString("nom")
                    , rst.getString("prenom")
                    , rst.getDate("dat_embauche")
                    , rst.getString("mail")
                    , rst.getString("pass")
                    , rst.getString("img")
               );
            Employes.add(e);
        }
        return Employes;
    }
     
  
     public void SupprimerEmployee(Employe e) throws SQLException {

        String req = "DELETE FROM employe WHERE id =?";
        try {
            PreparedStatement ps = connexion.prepareStatement(req);
            ps.setInt(1, e.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
        }
    }

     
     
      public void modifierEmployee(Employe e) throws SQLException, NoSuchAlgorithmException {
        String req = "UPDATE employe SET "
                + " nom='"+e.getNom()+"'"
                + ", prenom='"+e.getPrenom()+"'"
                + ", dat_embauche  ='"+(java.sql.Date) (Date) e.getDat_embauche()+"'"
                + ", mail='"+e.getMail()+"'"
                + ", pass='"+  hashmdp(e.getPass())+"'"
                + ", img='"+e.getImg()+"' where id  = "+e.getId()+"";
        Statement stm = connexion.createStatement();
        stm.executeUpdate(req);
    }
}

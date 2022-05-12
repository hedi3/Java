/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import DB.MyConnection;
import Entities.Congee;
import Entities.Employe;
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
public class CongeeService {
        Connection connexion;   
  public CongeeService() {
        connexion = MyConnection.getInstance().getCnx();
    }
 


  public void ajouterCongee(Congee e) throws SQLException {
        String req = "INSERT INTO `congee` (`date_deb`,`date_fin`,`employee`) "
                + "VALUES (?,?,?) ";
        PreparedStatement ps = connexion.prepareStatement(req);
        ps.setDate(1, (java.sql.Date) (Date) e.getDate_deb());
     
        ps.setDate(2, (java.sql.Date) (Date) e.getDate_fin());
        ps.setString(3, e.getEmployee());
        
                  

        ps.executeUpdate();
    }
  

     public List<Congee> AfficherAllCongee() throws SQLException {

        List<Congee> Congees = new ArrayList<>();
        String req = "select * from congee ";
        Statement stm = connexion.createStatement();
        ResultSet rst = stm.executeQuery(req);

        while (rst.next()) {
            Congee e = new Congee(rst.getInt("id")
                    , rst.getDate("date_deb")
                    , rst.getDate("date_fin")
                    , rst.getString("employee")
                   
               );
            Congees.add(e);
        }
        return Congees;
    }
     
  
     public void SupprimerCongee(Congee e) throws SQLException {

        String req = "DELETE FROM congee WHERE id =?";
        try {
            PreparedStatement ps = connexion.prepareStatement(req);
            ps.setInt(1, e.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
        }
    }

     
     
      public void modifierCongee(Congee e) throws SQLException, NoSuchAlgorithmException {
        String req = "UPDATE congee SET "
                + " date_deb='"+ (java.sql.Date) (Date)e.getDate_deb()+"'"
                + ", date_fin='"+ (java.sql.Date) (Date)e.getDate_fin()+"'"
        
             
                + ", employee='"+e.getEmployee()+"' where id  = "+e.getId()+"";
        Statement stm = connexion.createStatement();
        stm.executeUpdate(req);
    }
}

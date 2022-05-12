/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import DB.MyConnection;
import Entities.Congee;
import Entities.Employe;
import static GUI.ListEmployeeController.sendMail;
import Services.CongeeService;
import Services.EmployeeService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.mail.MessagingException;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author Administrateur
 */
public class ListCongeeController implements Initializable {

    @FXML
    private TextField inputRech;
    @FXML
    private Button supp;
    @FXML
    private Button supp1;
    @FXML
    private Button Ajouter;
    @FXML
    private TableView<Congee> tableview;
    @FXML
    private TableColumn<?, ?> date_deb;
    @FXML
    private TableColumn<?, ?> date_fin;
    @FXML
    private TableColumn<?, ?> employee;
    @FXML
    private Button Confirmer;
    @FXML
    private Label labelid;
    @FXML
    private Hyperlink gcategorie;
    @FXML
    private Hyperlink Front;
    @FXML
    private Button pdf2;
    @FXML
    private DatePicker inputdate_deb;
    @FXML
    private DatePicker inputdatefin;
    CongeeService cs = new CongeeService();
   
      public ObservableList<Congee> list;
    @FXML
    private ComboBox<String> inputuser;
    
        Connection connexion;   
  public ListCongeeController() {
        connexion = MyConnection.getInstance().getCnx();
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         try {
            String req = "select * from employe";
            Statement stm = connexion.createStatement();
            ResultSet rst = stm.executeQuery(req);
            
            while (rst.next()) {
             //   Users a = new Users(rst.getInt("id"));
                
                String xx = rst.getString("nom");
                inputuser.getItems().add(xx);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        
        CongeeService pss = new CongeeService();
        ArrayList<Congee> c = new ArrayList<>();
        try {
            c = (ArrayList<Congee>) pss.AfficherAllCongee();
        } catch (SQLException ex) {
            System.out.println("erreurrrrrrrrrrrrr");
        }
        
        ObservableList<Congee> obs2 = FXCollections.observableArrayList(c);
        tableview.setItems(obs2);
        
        
 date_deb.setCellValueFactory(new PropertyValueFactory<>("date_deb"));
        date_fin.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
employee.setCellValueFactory(new PropertyValueFactory<>("employee"));
             
            try {
            list = FXCollections.observableArrayList(
                    pss.AfficherAllCongee()
            );        
        
        
   FilteredList<Congee> filteredData = new FilteredList<>(list, e -> true);
            inputRech.setOnKeyReleased(e -> {
                inputRech.textProperty().addListener((ObservableValue, oldValue, newValue) -> {
                    filteredData.setPredicate((Predicate<? super Congee>) Congees -> {
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }
                        String lower = newValue.toLowerCase();
                        if (Congees.getEmployee().toLowerCase().contains(lower)) {
                            return true;
                        }

                        return false;
                    });
                });
                SortedList<Congee> sortedData = new SortedList<>(filteredData);
                sortedData.comparatorProperty().bind(tableview.comparatorProperty());
                tableview.setItems(sortedData);
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }    
  public void resetTableData() throws SQLDataException, SQLException {

        List<Congee> listevents = new ArrayList<>();
        listevents = cs.AfficherAllCongee();
        ObservableList<Congee> data = FXCollections.observableArrayList(listevents);
        tableview.setItems(data);
    }    
    @FXML
    private void supp(ActionEvent event) throws SQLException {
              if (event.getSource() == supp) {
            Congee e = new Congee();
            e.setId(tableview.getSelectionModel().getSelectedItem().getId());  
            CongeeService cs = new CongeeService();
            cs.SupprimerCongee(e);
            resetTableData();  
        
               TrayNotification tray = new TrayNotification();
            AnimationType type = AnimationType.POPUP;
            tray.setAnimationType(type);
            tray.setTitle("Vous avez Supprimé un Congee");
            tray.setMessage("");
            tray.setNotificationType(NotificationType.SUCCESS);
            tray.showAndDismiss(Duration.millis(3000));
        
    }
        
        
    }
    @FXML
    private void Modif(ActionEvent event) {
         CongeeService ps = new CongeeService();
          


     
                
                            labelid.setText(Integer.toString(tableview.getSelectionModel().getSelectedItem().getId()));

        
           
         
      java.sql.Date r;
        r = new java.sql.Date(tableview.getSelectionModel().getSelectedItem().getDate_deb().getTime());
        LocalDate date = r.toLocalDate();
        
            java.sql.Date r2;
        r2 = new java.sql.Date(tableview.getSelectionModel().getSelectedItem().getDate_fin().getTime());
        LocalDate date2 = r.toLocalDate();
        
 inputdate_deb.setValue(date);
 inputdatefin.setValue(date2);
inputuser.setValue(  tableview.getSelectionModel().getSelectedItem().getEmployee());
 
           Confirmer.setVisible(true);  
        
        
    }

    @FXML
    private void Ajouter(ActionEvent event) {
         CongeeService productService = new CongeeService();
        java.util.Date date2
                = java.util.Date.from(this.inputdate_deb.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                 java.sql.Date sqlDate2 = new java.sql.Date(date2.getTime());
                 
               java.util.Date date3
                = java.util.Date.from(this.inputdatefin.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                 java.sql.Date sqlDate3 = new java.sql.Date(date3.getTime());

              if (sqlDate3.before(sqlDate2)) {
                    Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("la date fin est inferieur a la date debut");
            a.setHeaderText(null);
            a.showAndWait();  
              }
                 
                 
                 
              else {
                      
                     

            Congee c = new Congee(sqlDate2 ,sqlDate3,inputuser.getValue()      );
        try {
                      
                           Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setContentText("confirmer");
            a.setHeaderText(null);
            a.showAndWait();  
              
            productService.ajouterCongee(c);
             resetTableData();
        } catch (SQLException ex) {
           
        }
      
         } 
        
    }

    @FXML
    private void Confirmer(ActionEvent event) throws NoSuchAlgorithmException {
         CongeeService productService = new CongeeService();
        java.util.Date date2
                = java.util.Date.from(this.inputdate_deb.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                 java.sql.Date sqlDate2 = new java.sql.Date(date2.getTime());
                 
               java.util.Date date3
                = java.util.Date.from(this.inputdatefin.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                 java.sql.Date sqlDate3 = new java.sql.Date(date3.getTime());


            Congee c = new Congee(Integer.parseInt(labelid.getText()),sqlDate2 ,sqlDate3,inputuser.getValue()      );
        try {
            productService.modifierCongee(c);
             resetTableData();
        } catch (SQLException ex) {
           
        }
        
           try {
                                   sendMail("fares.moalla@esprit.tn");
                               
                                   
                                
                                } catch (MessagingException ex) {
                                }  
        
    }

    @FXML
    private void gcategorie(ActionEvent event) throws IOException {
           Parent page1 = FXMLLoader.load(getClass().getResource("ListEmployee.fxml"));
        Scene scene = new Scene(page1);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        
        
    }

    @FXML
    private void Front(ActionEvent event) {
    }

  private void printPDF() throws FileNotFoundException, DocumentException, IOException {
        ;
        Document d = new Document();
        PdfWriter.getInstance(d, new FileOutputStream("C:\\Users\\Administrateur.DESKTOP-BMBSNE9\\Desktop\\ListEmployee.pdf"));
        d.open();
        d.add(new Paragraph("Liste des Congee"));
        
        PdfPTable pTable = new PdfPTable(1);
       
     //   pTable.addCell("NomEvent");
     
        
        tableview.getItems().forEach((t) -> {
            pTable.addCell(String.valueOf(t.getEmployee()));
            //pTable.addCell(t.getNomEvent());
          //  pTable.addCell(t.getDescriptionEvent());
            
            try {
                d.add(pTable);
            } catch (DocumentException ex) {
                System.out.println(ex);
            }
        });
        
        
        d.close();
        Desktop.getDesktop().open(new File("C:\\Users\\Administrateur.DESKTOP-BMBSNE9\\Desktop\\ListEmployee.pdf"));

    } 
      @FXML
    private void pdf2(ActionEvent event) throws FileNotFoundException, DocumentException, IOException {
         if (event.getSource() == pdf2) {
            
             printPDF();
            

        }
    
}
}
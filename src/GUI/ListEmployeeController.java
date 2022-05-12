/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entities.Employe;


import Services.CongeeService;
import Services.EmployeeService;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author Administrateur
 */
public class ListEmployeeController implements Initializable {
    EmployeeService cs = new EmployeeService();
    public static Employe connectedEmployee;
      public ObservableList<Employe> list;
    @FXML
    private TextField inputRech;
    @FXML
    private Button supp;
    @FXML
    private Button supp1;
    @FXML
    private Button Ajouter;
    @FXML
    private TableView<Employe> tableview;
    @FXML
    private TableColumn<?, ?> nom;
    @FXML
    private TableColumn<?, ?> prenom;
    @FXML
    private TableColumn<?, ?> dat_embauche;
    @FXML
    private TableColumn<?, ?> mail;
    @FXML
    private TextField inputnom;
    @FXML
    private TextField inputprenom;
    @FXML
    private TextField inputmail;
    @FXML
    private Button Confirmer;
    @FXML
    private Label labelid;
    @FXML
    private Button Timage;
    @FXML
    private ImageView imgajoutincours;
    @FXML
    private Hyperlink gcategorie;
    @FXML
    private Hyperlink Front;
    @FXML
    private Button pdf2;
    @FXML
    private TextField inputmdp;
    @FXML
    private DatePicker DateEvent;
    @FXML
    private Label imgpathttt;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        EmployeeService pss = new EmployeeService();
        ArrayList<Employe> c = new ArrayList<>();
        try {
            c = (ArrayList<Employe>) pss.AfficherAllEmployee();
        } catch (SQLException ex) {
            System.out.println("erreurrrrrrrrrrrrr");
        }
        
        ObservableList<Employe> obs2 = FXCollections.observableArrayList(c);
        tableview.setItems(obs2);
        
        
 nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        dat_embauche.setCellValueFactory(new PropertyValueFactory<>("dat_embauche"));
        mail.setCellValueFactory(new PropertyValueFactory<>("mail"));
             
            try {
            list = FXCollections.observableArrayList(
                    pss.AfficherAllEmployee()
            );        
        
        
   FilteredList<Employe> filteredData = new FilteredList<>(list, e -> true);
            inputRech.setOnKeyReleased(e -> {
                inputRech.textProperty().addListener((ObservableValue, oldValue, newValue) -> {
                    filteredData.setPredicate((Predicate<? super Employe>) Employes -> {
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }
                        String lower = newValue.toLowerCase();
                        if (Employes.getNom().toLowerCase().contains(lower)) {
                            return true;
                        }

                        return false;
                    });
                });
                SortedList<Employe> sortedData = new SortedList<>(filteredData);
                sortedData.comparatorProperty().bind(tableview.comparatorProperty());
                tableview.setItems(sortedData);
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }    
  public void resetTableData() throws SQLDataException, SQLException {

        List<Employe> listevents = new ArrayList<>();
        listevents = cs.AfficherAllEmployee();
        ObservableList<Employe> data = FXCollections.observableArrayList(listevents);
        tableview.setItems(data);
    }    
    @FXML
    private void supp(ActionEvent event) throws SQLException {
              if (event.getSource() == supp) {
            Employe e = new Employe();
            e.setId(tableview.getSelectionModel().getSelectedItem().getId());  
            EmployeeService cs = new EmployeeService();
            cs.SupprimerEmployee(e);
            resetTableData();  
        
               TrayNotification tray = new TrayNotification();
            AnimationType type = AnimationType.POPUP;
            tray.setAnimationType(type);
            tray.setTitle("Vous avez Supprimé un Employee");
            tray.setMessage("");
            tray.setNotificationType(NotificationType.SUCCESS);
            tray.showAndDismiss(Duration.millis(3000));
        
    }
        
        
    }

    @FXML
    private void Modif(ActionEvent event) {
         EmployeeService ps = new EmployeeService();
          


     
                
                
        
           
            labelid.setText(Integer.toString(tableview.getSelectionModel().getSelectedItem().getId()));
         
            inputnom.setText(tableview.getSelectionModel().getSelectedItem().getNom());
            inputprenom.setText(tableview.getSelectionModel().getSelectedItem().getPrenom());
                 inputmail.setText(tableview.getSelectionModel().getSelectedItem().getMail());
   
 inputmdp.setText(tableview.getSelectionModel().getSelectedItem().getPass());
   Timage.setText(tableview.getSelectionModel().getSelectedItem().getImg());
      java.sql.Date r;
        r = new java.sql.Date(tableview.getSelectionModel().getSelectedItem().getDat_embauche().getTime());
        LocalDate date = r.toLocalDate();
 DateEvent.setValue(date);

 
           Confirmer.setVisible(true);  
        
        
        
    }

    @FXML
    private void Ajouter(ActionEvent event) throws NoSuchAlgorithmException {
         EmployeeService productService = new EmployeeService();
  
        if (inputnom.getText().equals("")
                || inputprenom.getText().equals("") || inputmail.getText().equals("")
               ) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Please fill all fields ");
            a.setHeaderText(null);
            a.showAndWait();
        } else if (inputnom.getText().equals("")
                || inputmail.getText().equals("") || inputprenom.getText().matches("[\\\\!\"#$%&()*+,./:;<=>?@\\[\\]^_{|}~]+")
               ) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Une erreur s’est produite. Veuillez réessayer. ");
            a.setHeaderText(null);
            a.showAndWait();
        }
          else if( inputnom.getText().matches("0")|| inputnom.getText().matches("1")
          || inputnom.getText().matches("2")
          || inputnom.getText().matches("3")
          || inputnom.getText().matches("4")
          || inputnom.getText().matches("5")
          || inputnom.getText().matches("6")
          || inputnom.getText().matches("7")
          || inputnom.getText().matches("8")
          || inputnom.getText().matches("9")  
         ){
                       Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("le nom ne doit pas contenir un numéro");
            a.setHeaderText(null);
            a.showAndWait();  
              
          }
          
          else if (   !(inputmail.getText().contains("@"))){
                        Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("email invalide");
            a.setHeaderText(null);
            a.showAndWait();  
                  }
          
          
        
          else {
              
              
           java.util.Date date2
                = java.util.Date.from(this.DateEvent.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                 java.sql.Date sqlDate2 = new java.sql.Date(date2.getTime());
                 
               

               
                         
                        
            Employe c = new Employe(inputnom.getText(),inputprenom.getText(),sqlDate2 ,inputmail.getText(),inputmdp.getText(),Timage.getText()                  );
        try {
                  Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setContentText("Confirmer");
            a.setHeaderText(null);
            a.showAndWait();  
            productService.ajouterEmployee(c);
             resetTableData();
        } catch (SQLException ex) {
           
        }
         
           try {
                                 sendMail("mohamedRassem.hammami@esprit.tn");
                               
                                   
                                
                                } catch (MessagingException ex) {
                                }  
         } 
        
        
    }
public static void sendMail(String recipient) throws MessagingException {
        System.out.println("Preparing to send email");
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        String myAccountEmail = "pidev45@gmail.com";
        String password = "pidev455683@";
        Session session = Session.getInstance(properties, new Authenticator() {
             @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(myAccountEmail, password);
            }
        });
            
        Message message = prepareMessage(session, myAccountEmail, recipient);

        Transport.send(message);
        System.out.println("Message sent successfully");
    }  
   
    
    private static Message prepareMessage(Session session, String myAccountEmail, String recipient) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject("Vous Avez Ajouté un nouveau Employee");
            message.setText("Vous Avez Ajouté un nouveau Employee");
            return message;
        } catch (MessagingException ex) {
          
        }
        return null;
    }     
    @FXML
    private void Confirmer(ActionEvent event) throws NoSuchAlgorithmException {
           EmployeeService productService = new EmployeeService();
  
        if (inputnom.getText().equals("")
                || inputprenom.getText().equals("") || inputmail.getText().equals("")
               ) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Please fill all fields ");
            a.setHeaderText(null);
            a.showAndWait();
        } else if (inputnom.getText().equals("")
                || inputmail.getText().equals("") || inputprenom.getText().matches("[\\\\!\"#$%&()*+,./:;<=>?@\\[\\]^_{|}~]+")
               ) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Une erreur s’est produite. Veuillez réessayer. ");
            a.setHeaderText(null);
            a.showAndWait();
        }
       
                             
           java.util.Date date2
                = java.util.Date.from(this.DateEvent.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                 java.sql.Date sqlDate2 = new java.sql.Date(date2.getTime());
                 
               


            Employe c = new Employe(Integer.parseInt(labelid.getText()),inputnom.getText(),inputprenom.getText(),sqlDate2 ,inputmail.getText(),inputmdp.getText(),Timage.getText()                  );
        try {
            productService.modifierEmployee(c);
             resetTableData();
        } catch (SQLException ex) {
           
        }
        
        
        
    }



    @FXML
    private void gcategorie(ActionEvent event) throws IOException {
            Parent page1 = FXMLLoader.load(getClass().getResource("ListCongee.fxml"));
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
        PdfWriter.getInstance(d, new FileOutputStream("C:\\Users\\PC\\Desktop\\ListEmployee.pdf"));
        d.open();
        d.add(new Paragraph("Liste des Produits"));
        
        PdfPTable pTable = new PdfPTable(1);
       
     //   pTable.addCell("NomEvent");
     
        
        tableview.getItems().forEach((t) -> {
            pTable.addCell(String.valueOf(t.getNom()));
            //pTable.addCell(t.getNomEvent());
          //  pTable.addCell(t.getDescriptionEvent());
            
            try {
                d.add(pTable);
            } catch (DocumentException ex) {
                System.out.println(ex);
            }
        });
        
        
        d.close();
        Desktop.getDesktop().open(new File("C:\\Users\\PC\\Desktop\\ListEmployee.pdf"));

    } 
      @FXML
    private void pdf2(ActionEvent event) throws FileNotFoundException, DocumentException, IOException {
         if (event.getSource() == pdf2) {
            
             printPDF();
            

        }
        
        
        
    }
    
    @FXML
    private void addimgcours(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterJPG
                = new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
        FileChooser.ExtensionFilter extFilterjpg
                = new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extFilterPNG
                = new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
        FileChooser.ExtensionFilter extFilterpng
                = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        fileChooser.getExtensionFilters()
                .addAll(extFilterJPG, extFilterjpg, extFilterPNG, extFilterpng);
        File file = fileChooser.showOpenDialog(null);
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            WritableImage image = SwingFXUtils.toFXImage(bufferedImage, null);
            imgajoutincours.setImage(image);
            imgajoutincours.setFitWidth(200);
            imgajoutincours.setFitHeight(200);
            imgajoutincours.scaleXProperty();
            imgajoutincours.scaleYProperty();
            imgajoutincours.setSmooth(true);
            imgajoutincours.setCache(true);
            FileInputStream fin = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            for (int readNum; (readNum = fin.read(buf)) != -1;) {
                bos.write(buf, 0, readNum);
            }
            byte[] person_image = bos.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger("ss");
        }
        imgpathttt.setText(file.getAbsolutePath());
    }
    
    
    
}

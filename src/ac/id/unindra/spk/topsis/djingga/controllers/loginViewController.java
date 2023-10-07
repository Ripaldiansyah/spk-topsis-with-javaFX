package ac.id.unindra.spk.topsis.djingga.controllers;

import ac.id.unindra.spk.topsis.djingga.models.loginModel;
import ac.id.unindra.spk.topsis.djingga.services.loginService;
import ac.id.unindra.spk.topsis.djingga.utilities.DatabaseConnection;
import ac.id.unindra.spk.topsis.djingga.utilities.NotificationManager;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;


public class loginViewController implements Initializable,loginService{

    @FXML private ImageView imgLogo;
    @FXML private MFXTextField txtUsername;
    @FXML private MFXPasswordField txtPassword;
    @FXML private AnchorPane loginPane;
    @FXML private AnchorPane registerPane;
    
     private Connection conn = new DatabaseConnection().getConnection();
     private NotificationManager notificationManager = new NotificationManager();
     loginService loginService = this;
    
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image img  = new Image("/ac/id/unindra/spk/topsis/djingga/media/logoDjingga.gif");
        imgLogo.setImage(img);
        
    }
    
    @Override
    public void processLogin(loginModel loginModel) {
       String sql = "SELECT * FROM pengguna WHERE LOWER(namaPengguna)=LOWER(?) AND kataSandi=?";
       PreparedStatement stat=null;
       ResultSet rs=null;
       String id,fullName,role=null;
       byte[] profilePicture;
       
        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, loginModel.getUsername());
            stat.setString(2, (loginModel.getPassword()));
            rs = stat.executeQuery();
            
            if (rs.next()) {
                id = rs.getString("idPengguna");
                fullName = rs.getString("namaLengkap");
                role = rs.getString("level");
            }else{
                notificationManager.notification("Tidak Dapat Masuk", "Periksa Nama Pengguna dan Kata Sandi Anda");
            }
            
        } catch (Exception e) {
            System.err.println("e");
        }
       
    }
    
     private boolean inputValidation() {
         boolean valid=false;
         if (txtUsername.getText().trim().isEmpty() | txtPassword.getText().trim().isEmpty()) {
             valid=false;
         }else{
             valid=true;
         }
            return valid;  
}
    
     
    @FXML
    private void login(MouseEvent event){
        if (inputValidation()==true){
            
            String username = txtUsername.getText();
            String password = txtPassword.getText();
            
            loginModel loginModel = new loginModel();
            loginModel.setUsername(username);
            loginModel.setPassword(password);
            loginService.processLogin(loginModel);
            
        }else{
           notificationManager.notification("Peringatan", "Nama Pengguna atau Kata Sandi tidak boleh kosong");
        }
}
    
    @FXML
    private void exit(MouseEvent event){
        System.exit(0);
    }
    
    @FXML
    private void register(MouseEvent event) throws IOException{
         registerPane.setVisible(true);
         registerPane.setDisable(false);

         loginPane.setVisible(false);
         loginPane.setDisable(true);
    }
}

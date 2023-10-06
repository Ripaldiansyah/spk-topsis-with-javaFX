package ac.id.unindra.spk.topsis.djingga.controllers;

import ac.id.unindra.spk.topsis.djingga.services.loginService;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class loginViewController implements Initializable{

    @FXML private ImageView imgLogo;
    @FXML private MFXTextField txtUsername;
    @FXML private MFXPasswordField txtPassword;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image img  = new Image("/ac/id/unindra/spk/topsis/djingga/media/logoDjingga.gif");
        imgLogo.setImage(img);
        
    }
    
     private boolean inputValidation() {
	boolean valid=false;
	if (txtUsername.getText().trim().isEmpty() | txtPassword.getText().trim().isEmpty()) {
                        notificationFieldEmpty();
	} else {
		valid=true;
	}
	    return valid;
    }
     
     private void notificationFieldEmpty(){
                    Notifications notification = Notifications.create();
                    notification.title("Peringatan");
                    notification.text("Username atau Password tidak boleh kosong");
                    notification.hideAfter(Duration.seconds(5));
                    notification.position(Pos.BASELINE_RIGHT);
                    notification.show();
     }
    private void login(MouseEvent event){
        if (inputValidation()==true){
		    String username = txtUsername.getText();
		    String password = txtPassword.getText();
		    
		    loginModel loginModel = new loginModel();	
		    loginModel.setUsername(username);
		    loginModel.setPassword(password);
		    
                                                
		    service.prosesLogin(loginModel);
    }
}

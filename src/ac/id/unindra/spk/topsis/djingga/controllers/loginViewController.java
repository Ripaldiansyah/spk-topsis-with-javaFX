package ac.id.unindra.spk.topsis.djingga.controllers;

//import java.awt.Image;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class loginViewController implements Initializable{

    @FXML private ImageView imgLogo;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image img  = new Image("/ac/id/unindra/spk/topsis/djingga/media/logoDjingga.gif");
        imgLogo.setImage(img);
        
    }
    
}

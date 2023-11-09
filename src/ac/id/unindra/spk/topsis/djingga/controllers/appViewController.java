package ac.id.unindra.spk.topsis.djingga.controllers;

import java.io.IOException;

import ac.id.unindra.spk.topsis.djingga.utilities.NotificationManager;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class appViewController {
    @FXML
    private MFXButton alternativeButton;

    @FXML
    private MFXButton closeButton;

    @FXML
    private MFXButton criteriaButton;

    @FXML
    private MFXButton dashboardButton;

    @FXML
    private MFXButton gradeButton;

    @FXML
    private MFXButton logoutButton;

    @FXML
    private MFXButton periodButton;

    @FXML
    private MFXButton settingButton;

    @FXML
    private MFXButton userButton;

    @FXML
    private Pane pane;


    private double xOffset, yOffset = 0;
    public static String idUser;



    @FXML
    private void exit(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    private void logout(MouseEvent event) throws IOException {
      
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/ac/id/unindra/spk/topsis/djingga/views/loginView.fxml"));
        Scene scene = new Scene(root);

        root.setOnMousePressed(events -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        root.setOnMouseDragged(events -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });

        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

        ((Node) event.getSource()).getScene().getWindow().hide();
                NotificationManager.notification("Logout", "Berhasil Keluar");
                loginViewController.main = false;          
    }

    @FXML
    private void userManagement(MouseEvent event) {
       try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ac/id/unindra/spk/topsis/djingga/views/userView.fxml"));
        Parent newContent = loader.load();
        pane.getChildren().setAll(newContent);
       } catch (Exception e) {
        e.printStackTrace();
       }
    }
}

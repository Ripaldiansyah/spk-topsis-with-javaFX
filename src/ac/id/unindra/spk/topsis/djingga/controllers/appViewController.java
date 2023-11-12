package ac.id.unindra.spk.topsis.djingga.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import ac.id.unindra.spk.topsis.djingga.utilities.NotificationManager;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class appViewController implements Initializable {
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

    @FXML
    private MFXButton addCriteriaButton;

    @FXML
    private MFXButton readCriteriaButton;

    @FXML
    private ImageView imgCollapse;

    @FXML
    private VBox vboxMenu;

    private double xOffset, yOffset = 0;
    public static String idUser;
    int click = 0;

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
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/ac/id/unindra/spk/topsis/djingga/views/userView.fxml"));
            Parent newContent = loader.load();
            pane.getChildren().setAll(newContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addCriteria(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/ac/id/unindra/spk/topsis/djingga/views/addCriteriaView.fxml"));
            Parent newContent = loader.load();
            pane.getChildren().setAll(newContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showCriteria(MouseEvent event) {

        if (click == 0) {
            showCriteriaMenu();
            click++;
        } else {
            hideCriteriaMenu();
            click --;
        }
    }

    private void showCriteriaMenu() {
        Image img = new Image("/ac/id/unindra/spk/topsis/djingga/resources/media/icons8_expand_arrow_23px.png");
        imgCollapse.setImage(img);
        addCriteriaButton.setVisible(true);
        readCriteriaButton.setVisible(true);
        addCriteriaButton.setDisable(false);
        readCriteriaButton.setDisable(false);
        addCriteriaButton.setManaged(true);
        readCriteriaButton.setManaged(true);
        vboxMenu.layout();
    }

    private void hideCriteriaMenu() {
        Image img = new Image("/ac/id/unindra/spk/topsis/djingga/resources/media/icons8_collapse_arrow_23px.png");
        imgCollapse.setImage(img);
        addCriteriaButton.setVisible(false);
        readCriteriaButton.setVisible(false);
        addCriteriaButton.setDisable(true);
        readCriteriaButton.setDisable(true);
        vboxMenu.setVgrow(addCriteriaButton, Priority.ALWAYS);
        vboxMenu.setVgrow(readCriteriaButton, Priority.ALWAYS);
        addCriteriaButton.setManaged(false);
        readCriteriaButton.setManaged(false);
        vboxMenu.layout();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hideCriteriaMenu();
    }
}

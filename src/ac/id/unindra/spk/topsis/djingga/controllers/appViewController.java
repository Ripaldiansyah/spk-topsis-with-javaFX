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

public class AppViewController implements Initializable {
    @FXML
    private MFXButton alternativeButton;

    @FXML
    private MFXButton closeButton;

    @FXML
    private MFXButton criteriaButton;

    @FXML
    private MFXButton dashboardButton;

    @FXML
    private MFXButton topsisButton;

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
    private MFXButton viewCriteriaButton;

    @FXML
    private MFXButton viewAlternativeButton;

    @FXML
    private ImageView imgCollapse;

    @FXML
    private VBox vboxMenu;

    @FXML
    private MFXButton addAlternativeButton;

    @FXML
    private MFXButton calcTopsisButton;

    @FXML
    private MFXButton viewTopsisButton;

    @FXML
    private ImageView imgCollapse2;

    @FXML
    private ImageView imgCollapse3;

    @FXML
    private MFXButton readAlternativeButton;

    private double xOffset, yOffset = 0;
    public static String idUser;
    int clickCriteriaButton, clickAlternativeButton, clickTopsisButton = 0;

    @FXML
    private void exit(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    private void logout(MouseEvent event) throws IOException {

        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/ac/id/unindra/spk/topsis/djingga/views/LoginView.fxml"));
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
        LoginViewController.main = false;
    }

    @FXML
    private void userManagement(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/ac/id/unindra/spk/topsis/djingga/views/UserView.fxml"));
            Parent newContent = loader.load();
            pane.getChildren().setAll(newContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addAlternative(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/ac/id/unindra/spk/topsis/djingga/views/AddAlternativeView.fxml"));
            Parent newContent = loader.load();
            pane.getChildren().setAll(newContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void alternativeView(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/ac/id/unindra/spk/topsis/djingga/views/AlternativeView.fxml"));
            Parent newContent = loader.load();
            pane.getChildren().setAll(newContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void criteriaView(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/ac/id/unindra/spk/topsis/djingga/views/CriteriaView.fxml"));
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
                    getClass().getResource("/ac/id/unindra/spk/topsis/djingga/views/AddCriteriaView.fxml"));
            Parent newContent = loader.load();
            pane.getChildren().setAll(newContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void calcTopsis(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/ac/id/unindra/spk/topsis/djingga/views/calcTopsisView.fxml"));
            Parent newContent = loader.load();
            pane.getChildren().setAll(newContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showCriteria(MouseEvent event) {

        if (clickCriteriaButton == 0) {
            showCriteriaMenu();
            clickCriteriaButton++;
        } else {
            hideCriteriaMenu();
            clickCriteriaButton--;
        }
    }

    @FXML
    private void showAlternative(MouseEvent event) {

        if (clickAlternativeButton == 0) {
            showAlternativeMenu();
            clickAlternativeButton++;
        } else {
            hideAlternativeMenu();
            clickAlternativeButton--;
        }
    }

    @FXML
    private void showTopsis(MouseEvent event) {

        if (clickTopsisButton == 0) {
            showTopsisMenu();
            clickTopsisButton++;
        } else {
            hideTopsisMenu();
            clickTopsisButton--;
        }
    }

    private void showMenu(MFXButton MFXButtonTop, MFXButton MFXButtonDown, ImageView ImageView) {
        Image img = new Image("/ac/id/unindra/spk/topsis/djingga/resources/media/icons8_expand_arrow_23px.png");
        ImageView.setImage(img);
        MFXButtonTop.setVisible(true);
        MFXButtonDown.setVisible(true);
        MFXButtonTop.setDisable(false);
        MFXButtonDown.setDisable(false);
        MFXButtonTop.setManaged(true);
        MFXButtonDown.setManaged(true);
        vboxMenu.layout();
    }

    private void hideMenu(MFXButton MFXButtonTop, MFXButton MFXButtonDown, ImageView ImageView) {
        Image img = new Image("/ac/id/unindra/spk/topsis/djingga/resources/media/icons8_collapse_arrow_23px.png");
        ImageView.setImage(img);
        MFXButtonTop.setVisible(false);
        MFXButtonDown.setVisible(false);
        MFXButtonTop.setDisable(true);
        MFXButtonDown.setDisable(true);
        vboxMenu.setVgrow(MFXButtonTop, Priority.ALWAYS);
        vboxMenu.setVgrow(MFXButtonDown, Priority.ALWAYS);
        MFXButtonTop.setManaged(false);
        MFXButtonDown.setManaged(false);
        vboxMenu.layout();
    }

    private void showCriteriaMenu() {
        showMenu(viewCriteriaButton, addCriteriaButton, imgCollapse);
    }

    private void showAlternativeMenu() {
        showMenu(viewAlternativeButton, addAlternativeButton, imgCollapse2);
    }

    private void showTopsisMenu() {
        showMenu(calcTopsisButton, viewTopsisButton, imgCollapse3);
    }

    private void hideCriteriaMenu() {
        hideMenu(viewCriteriaButton, addCriteriaButton, imgCollapse);
    }

    private void hideAlternativeMenu() {
        hideMenu(viewAlternativeButton, addAlternativeButton, imgCollapse2);
    }

    private void hideTopsisMenu() {
        hideMenu(calcTopsisButton, viewTopsisButton, imgCollapse3);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hideCriteriaMenu();
        hideAlternativeMenu();
        hideTopsisMenu();
    }
}

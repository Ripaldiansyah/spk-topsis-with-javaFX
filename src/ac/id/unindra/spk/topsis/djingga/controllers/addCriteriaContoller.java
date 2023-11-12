package ac.id.unindra.spk.topsis.djingga.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class addCriteriaContoller implements Initializable {
    @FXML
    private MFXComboBox<String> valueWeight;

    @FXML
    private MFXComboBox<String> typeCriteria;
    
    @FXML
    private MFXButton backButton;

    @FXML
    private HBox buttonHbox;

    @FXML
    private MFXTextField criteria1;

    @FXML
    private MFXTextField criteria2;

    @FXML
    private MFXTextField criteria3;

    @FXML
    private MFXTextField criteria4;

    @FXML
    private MFXTextField criteria5;

    @FXML
    private HBox criteriaHbox;

    @FXML
    private MFXTextField criteriaName;

    @FXML
    private AnchorPane nameCriteria;

    @FXML
    private VBox nameCriteriaHbox;

    @FXML
    private MFXButton nextButton;

    @FXML
    private MFXButton saveButton;

    @FXML
    private AnchorPane setCriteria;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setDataValueWeight();
        setDataTypeCriteria();
    }

    private void setDataValueWeight() {
        ObservableList<String> options = FXCollections.observableArrayList(
                "Tidak Penting",
                "Kurang Penting",
                "Cukup Penting",
                "Penting",
                "Sangat Penting");

        valueWeight.getItems().addAll(options);
    }

    private void setDataTypeCriteria() {
        ObservableList<String> options = FXCollections.observableArrayList(
                "Cost",
                "Benefit");

        typeCriteria.getItems().addAll(options);
    }

    @FXML
    private void nextPane(MouseEvent event){
        activeAchorPane(setCriteria);
    }
    
    @FXML
    private void backPane(MouseEvent event){
        activeAchorPane(nameCriteria);
    }

    private void activeAchorPane(AnchorPane anchorPane){
        nameCriteria.setVisible(false);
        setCriteria.setVisible(false);
        nameCriteria.setDisable(true);
        setCriteria.setDisable(true);

        if (true) {
            anchorPane.setVisible(true);
            anchorPane.setDisable(false);
        }
    }

}

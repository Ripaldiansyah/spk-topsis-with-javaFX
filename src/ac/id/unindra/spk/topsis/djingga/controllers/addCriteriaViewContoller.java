package ac.id.unindra.spk.topsis.djingga.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import ac.id.unindra.spk.topsis.djingga.models.addCriteriaModel;
import ac.id.unindra.spk.topsis.djingga.services.addCriteriaService;
import ac.id.unindra.spk.topsis.djingga.utilities.NotificationManager;
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

public class addCriteriaViewContoller implements Initializable {
    @FXML
    private MFXComboBox<String> valueWeight;

    @FXML
    private MFXComboBox<String> criteriaType;
    
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

    addCriteriaService addCriteriaService = new addCriteriaController();
   addCriteriaModel addCriteriaModel = new addCriteriaModel();
   public static boolean runPane =false;




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

            criteriaType.getItems().addAll(options);
    }

    private void nextPane(){
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

    @FXML
    private void addCriteriaProcess(){
        if (nameCriteriaValidation()) {
            String nameCriteriaString = criteriaName.getText();
            String criteriaTypeString = criteriaType.getText();
            String valueWeightString = valueWeight.getText();
            addCriteriaModel.setCriteriaName(nameCriteriaString);
            addCriteriaModel.setCriteriaType(criteriaTypeString);
            addCriteriaModel.setValueWeight(valueWeightString);
            boolean valid = addCriteriaService.checkRegistered(addCriteriaModel);
            if (valid) {
               NotificationManager.notification("Kriteria sudah ada", "Nama Kriteria sudah terdaftar");
            }else{
                nextPane();
            }

        }
    }

    @FXML
    private void setCriteriaProcess(){
        if (setCriteriaValidation()) {
            String criteria1String = criteria1.getText();
            String criteria2String = criteria2.getText();
            String criteria3String = criteria3.getText();
            String criteria4String = criteria4.getText();
            String criteria5String = criteria5.getText();

            addCriteriaModel.setCriteria1(criteria1String);
            addCriteriaModel.setCriteria2(criteria2String);
            addCriteriaModel.setCriteria3(criteria3String);
            addCriteriaModel.setCriteria4(criteria4String);
            addCriteriaModel.setCriteria5(criteria5String);

            addCriteriaService.insertCriteria(addCriteriaModel);

            if (runPane) {
                 activeAchorPane(nameCriteria);
            }
        }
    }

    private boolean nameCriteriaValidation(){
        boolean valid =false;
        if (criteriaName.getText().trim().isEmpty()) {
            NotificationManager.notification("Nama Kriteria belum terisi", "Isi terlebih dahulu nama kriteria");
        }else if (criteriaType.getText().trim().isEmpty()) {
             NotificationManager.notification("Jenis Kriteria belum dipilih", "pilih Jenis Kriteria dahulu");
        }else if (valueWeight.getText().trim().isEmpty()) {
            NotificationManager.notification("Bobot Nilai belum dipilih", "pilih Bobot Nilai dahulu");
        }else{
            valid =true;
        }
        return valid;
    }

    private boolean setCriteriaValidation(){
        boolean valid=false;
        if (criteria1.getText().trim().isEmpty() || criteria2.getText().trim().isEmpty() 
            || criteria3.getText().trim().isEmpty() || criteria4.getText().trim().isEmpty()
            || criteria5.getText().trim().isEmpty()
        ) {
            NotificationManager.notification("Bobot tidak valid", "Pastikan semua bobot telah terisi");
        }else{
            valid = true;
        }
        return valid;
    }
}

package ac.id.unindra.spk.topsis.djingga.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import ac.id.unindra.spk.topsis.djingga.models.CriteriaModel;
import ac.id.unindra.spk.topsis.djingga.utilities.NotificationManager;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import ac.id.unindra.spk.topsis.djingga.services.CriteriaService;

public class AddCriteriaViewContoller implements Initializable {
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

    @FXML
    private Pane addCriteria;

    @FXML
    private Text criteriaText;

    CriteriaService criteriaService = new CriteriaController();
    CriteriaModel addCriteriaModel = new CriteriaModel();
    public static boolean runPane = false;
    public static String criteriaNameEdit;
    public static boolean editing = false;
    private boolean setEditing, addEditing,backEditing = false;
    CriteriaModel criteriaModel = new CriteriaModel();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setDataValueWeight();
        setDataTypeCriteria();
        criteriaText.setText("Tambah Kriteria");
        if (editing) {
            getData();
            editing = false;
            addEditing = true;
            criteriaText.setText("Ubah Kriteria");
        }

    }

    private void getData() {
        criteriaModel.setCriteriaName(criteriaNameEdit);
        criteriaService.getCriteria(criteriaModel);
        criteriaName.setText(criteriaModel.getCriteriaName());
        criteriaType.getSelectionModel().selectItem(criteriaModel.getCriteriaType());
        valueWeight.getSelectionModel().selectItem(criteriaModel.getValueWeight());
        criteria1.setText(criteriaModel.getCriteria1());
        criteria2.setText(criteriaModel.getCriteria2());
        criteria3.setText(criteriaModel.getCriteria3());
        criteria4.setText(criteriaModel.getCriteria4());
        criteria5.setText(criteriaModel.getCriteria5());
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

    private void nextPane() {
        activeAchorPane(setCriteria);
    }

    @FXML
    private void backPane(MouseEvent event) {
        if (backEditing) {
            addEditing = true;
            setEditing = false;
        } else {
           addEditing=false;
                setEditing=false;
        }
        activeAchorPane(nameCriteria);
    }

    private void activeAchorPane(AnchorPane anchorPane) {
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
    private void addCriteriaProcess() {
        if (nameCriteriaValidation()) {
            String nameCriteriaString = criteriaName.getText();
            String criteriaTypeString = criteriaType.getText();
            String valueWeightString = valueWeight.getText();
            if (addEditing) {
                addCriteriaModel.setCriteriaName(nameCriteriaString);
                addCriteriaModel.setCriteriaType(criteriaTypeString);
                addCriteriaModel.setValueWeight(valueWeightString);
                nextPane();
                addEditing = false;
                setEditing = true;
                backEditing=true;
            } else {
                addCriteriaModel.setCriteriaName(nameCriteriaString);
                addCriteriaModel.setCriteriaType(criteriaTypeString);
                addCriteriaModel.setValueWeight(valueWeightString);
                boolean valid = criteriaService.checkRegistered(addCriteriaModel);
                if (valid) {
                    NotificationManager.notification("Kriteria sudah ada", "Nama Kriteria sudah terdaftar");
                } else {
                    nextPane();
                }
                backEditing=false;
            }

        }
    }

    @FXML
    private void setCriteriaProcess() {
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
            if (setEditing) {
                String idCriteria = criteriaModel.getCriteriaId();
                addCriteriaModel.setCriteriaId(idCriteria);
                criteriaService.updateCriteria(addCriteriaModel);
                if (runPane) {
                    criteriaView();
                    resetField();
                    runPane = false;
                    editing = false;
                }
            } else {


                criteriaService.insertCriteria(addCriteriaModel);

                if (runPane) {
                    activeAchorPane(nameCriteria);
                    resetField();
                    runPane = false;
                }

            }

        }
    }

    private void criteriaView() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/ac/id/unindra/spk/topsis/djingga/views/criteriaView.fxml"));
            Parent newContent = loader.load();
            addCriteria.getChildren().setAll(newContent);
            AddCriteriaViewContoller.editing = true;
            AddCriteriaViewContoller.criteriaNameEdit = criteriaModel.getCriteriaName();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean nameCriteriaValidation() {
        boolean valid = false;
        if (criteriaName.getText().trim().isEmpty()) {
            NotificationManager.notification("Nama Kriteria belum terisi", "Isi terlebih dahulu nama kriteria");
        } else if (criteriaType.getText().trim().isEmpty()) {
            NotificationManager.notification("Jenis Kriteria belum dipilih", "pilih Jenis Kriteria dahulu");
        } else if (valueWeight.getText().trim().isEmpty()) {
            NotificationManager.notification("Bobot Nilai belum dipilih", "pilih Bobot Nilai dahulu");
        } else {
            valid = true;
        }
        return valid;
    }

    private boolean setCriteriaValidation() {
        boolean valid = false;
        if (criteria1.getText().trim().isEmpty() || criteria2.getText().trim().isEmpty()
                || criteria3.getText().trim().isEmpty() || criteria4.getText().trim().isEmpty()
                || criteria5.getText().trim().isEmpty()) {
            NotificationManager.notification("Bobot tidak valid", "Pastikan semua bobot telah terisi");
        } else {
            valid = true;
        }
        return valid;
    }

    private void resetField() {
        criteriaName.clear();
        criteriaType.clear();
        valueWeight.clear();
        criteria1.clear();
        criteria2.clear();
        criteria3.clear();
        criteria4.clear();
        criteria5.clear();
    }
}

package ac.id.unindra.spk.topsis.djingga.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import ac.id.unindra.spk.topsis.djingga.models.AlternativeModel;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import ac.id.unindra.spk.topsis.djingga.services.AlternativeService;

public class AddAlternativeViewController implements Initializable {
    AlternativeService alternativeService = new AlternativeController();
    AlternativeModel alternativeModel = new AlternativeModel();

    @FXML
    private Text alternativeText;

    @FXML
    private Pane addAlternative;

    @FXML
    private MFXComboBox<String> alternativeCategory;

    @FXML
    private MFXTextField alternativeName;

    @FXML
    private VBox nameCriteriaHbox;

    @FXML
    private MFXButton saveAlternativeButton;

    public static boolean editing = false;
    public static String alternativeNameEdit,alternativeCategoryEdit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setDataCategory();
        if (editing) {
            setEdit();
        }
    }

    private void setDataCategory() {
        ObservableList<String> options = FXCollections.observableArrayList(
                "Filter Lensa",
                "Background",
                "Lighting",
                "Tripod",
                "Laptop Editing",
                "Storage",
                "Kamera",
                "Lensa Kamera",
                "Shutter Release",
                "Drone",
                "Smartphone Operasional",
                "Gimbal stabilizer",
                "Lainnya");

        alternativeCategory.getItems().addAll(options);
    }

    private boolean addAlternativeValidation() {
        boolean valid = false;
        if (alternativeName.getText().trim().isEmpty()) {
            NotificationManager.notification("Nama alternatif belum terisi", "Isi terlebih dahulu nama alternatif");
        } else if (alternativeCategory.getText().trim().isEmpty()) {
            NotificationManager.notification("Kategori belum dipilih", "pilih kategori terlebih dahulu");
        } else {
            valid = true;
        }
        return valid;
    }

    @FXML
    void addAlternativeProcess(MouseEvent event) {
        if (addAlternativeValidation()) {
            String nameAlternativeString = alternativeName.getText();
            String categoryAlternativeString = alternativeCategory.getText();
            alternativeModel.setAlternativeName(nameAlternativeString);
            alternativeModel.setAlternativeCategory(categoryAlternativeString);
            if (editing) {
                updateAlternative();
                NotificationManager.notification("Berhasil ", "Perubahan alternatif berhasil disimpan");
                editing = false;
                clearField();
                backToListAlternative();
            } else {
                if (alternativeService.checkAlternative(alternativeModel)) {
                    NotificationManager.notification("Alternatif Sudah terdaftar",
                            "Nama Alternatif " + nameAlternativeString + " dalam kategori "+ categoryAlternativeString+" Sudah terdaftar");
                } else {
                    insertAlternative();
                    NotificationManager.notification("Berhasil ",
                            "Alternatif " + nameAlternativeString + " telah ditambahkan");
                             clearField();
                }
            }
           
        }
    }

    private void setEdit(){
        alternativeText.setText("Edit Alternatif");
        alternativeName.setText(alternativeNameEdit);
        alternativeCategory.getSelectionModel().selectItem(alternativeCategoryEdit);

        alternativeModel.setAlternativeName(alternativeNameEdit);
        alternativeModel.setAlternativeCategory(alternativeCategoryEdit);
        alternativeService.getId(alternativeModel);
    }
     private void backToListAlternative(){
        alternativeText.setText("Tambah Alternatif");
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/ac/id/unindra/spk/topsis/djingga/views/AlternativeView.fxml"));
            Parent newContent = loader.load();
            addAlternative.getChildren().setAll(newContent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearField() {
        alternativeName.clear();
        alternativeCategory.clear();
    }

    private void insertAlternative() {
        alternativeService.insertAlternative(alternativeModel);
    }

    private void updateAlternative() { 
        alternativeService.updateAlternative(alternativeModel);
    }
}

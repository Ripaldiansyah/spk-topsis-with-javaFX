package ac.id.unindra.spk.topsis.djingga.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.controlsfx.control.Rating;

import ac.id.unindra.spk.topsis.djingga.models.TopsisModel;
import ac.id.unindra.spk.topsis.djingga.services.TopsisService;
import ac.id.unindra.spk.topsis.djingga.utilities.CurrentDate;
import ac.id.unindra.spk.topsis.djingga.utilities.NotificationManager;
import ac.id.unindra.spk.topsis.djingga.utilities.RandomTextGenerator;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.enums.FloatMode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.converter.IntegerStringConverter;

public class calcTopsisViewController implements Initializable {
    @FXML
    private MFXComboBox<String> alternativeCategory;

    @FXML
    private MFXCheckbox criteriaComboBox;

    @FXML
    private VBox nameCriteriaHbox;
    @FXML
    private HBox headerListHbox;
    @FXML
    private HBox dataHbox;

    @FXML
    private MFXButton nextButton;
    @FXML
    private MFXButton backButtonCategory;

    @FXML
    private AnchorPane selectCategory;
    @FXML
    private AnchorPane calcTopsisScene;

    @FXML
    private AnchorPane selectCriteria;

    @FXML
    private MFXButton nextButtonCalc;

    @FXML
    private MFXButton calcTopsis;

    @FXML
    private VBox boxContainerCriteria;

    @FXML
    private Text alternativeText;
    @FXML
    private Text criteriaText;

    @FXML
    private MFXTextField ratingField;

    @FXML
    private Rating ratingStar;

    @FXML
    private VBox alternativeVbox;

    @FXML
    private VBox criteriaVbox;

    @FXML
    private GridPane headerGrid;

    private TopsisService topsisService = new TopsisController();
    private TopsisModel topsisModel = new TopsisModel();
    private boolean isAtLeastOneItemSelected = false;
    List<String> selectedCriteriaList = new ArrayList<>();
    List<MFXTextField> ratingFieldData = new ArrayList<>();
    List<Rating> ratingFieldStar = new ArrayList<>();
    List<String> ratingFieldDataCalc = new ArrayList<>();
    List<Double> ratingFieldStarCalc = new ArrayList<>();
    List<String> ratingStardData = new ArrayList<>();
    List<Double> groupStar = new ArrayList<>();
    List<String> group = new ArrayList<>();
    int count, countHeader;
    boolean isNotEmpty = false;
    private Map<MFXTextField, List> textFieldToVBoxMap = new HashMap<>();
    private Map<Rating, VBox> ratingToVBoxMap = new HashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setDataCategory();
        loadCriteria();
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

    @FXML
    private void nextPane() {
        if (alternatifCategoryValidation()) {
            activeAchorPane(selectCriteria);
            topsisModel.setCategory(alternativeCategory.getText());
        }
    }

    private boolean alternatifCategoryValidation() {
        if (alternativeCategory.getText().trim().isEmpty()) {
            NotificationManager.notification("Pilih Kategori Terlebih dahulu",
                    "Pastikan Anda telah memilih kategori terlebih dahulu");
            return false;
        }
        return true;
    }

    private void activeAchorPane(AnchorPane anchorPane) {
        selectCategory.setDisable(true);
        selectCriteria.setDisable(true);
        calcTopsisScene.setDisable(true);

        selectCategory.setVisible(false);
        selectCriteria.setVisible(false);
        calcTopsisScene.setVisible(false);
        if (true) {
            anchorPane.setDisable(false);
            anchorPane.setVisible(true);
        }

    }

    private void loadCriteria() {
        String[] dataCheckBox = topsisService.getData();
        boxContainerCriteria.getChildren().clear();
        for (String item : dataCheckBox) {
            criteriaComboBox = new MFXCheckbox(item);
            criteriaComboBox.setStyle("-mfx-main: black;");
            criteriaComboBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    selectedCriteriaList.add(item);
                    isAtLeastOneItemSelected = true;
                } else {
                    selectedCriteriaList.remove(item);
                    isAtLeastOneItemSelected = !selectedCriteriaList.isEmpty();
                }
            });
            boxContainerCriteria.getChildren().add(criteriaComboBox);
        }
    }

    @FXML
    private void gotoCalcTopsis(MouseEvent event) {
        if (isAtLeastOneItemSelected) {
            if (topsisService.getDataByCategory(topsisModel) != null) {
                ratingFieldData.clear();
                ratingFieldStar.clear();
                loadDataByCategory();
                activeAchorPane(calcTopsisScene);
                loadDataCriteria();

            } else {
                NotificationManager.notification("Data Alternatif tidak ditemukan",
                        "Tidak ada alternatif pada kategori " + topsisModel.getCategory());
            }

        } else {
            NotificationManager.notification("Peringatan", "Pilih Minimal 1 Kriteria");
        }
    }

    private void loadDataByCategory() {
        String[] dataAlternative = topsisService.getDataByCategory(topsisModel);
        alternativeVbox.getChildren().clear();
        for (String item : dataAlternative) {
            alternativeText = new Text(item);
            alternativeVbox.getChildren().add(alternativeText);
            count++;
        }
    }

    private void loadDataCriteria() {
        GridPane headerGrid = new GridPane();
        headerGrid.setPadding(new Insets(10, 10, 10, -40));
        headerGrid.setHgap(145);

        ObservableList<Node> children = headerListHbox.getChildren();
        children.remove(1, children.size());
        ObservableList<Node> dataChildren = dataHbox.getChildren();// hapus template penilaian
        dataChildren.remove(1, dataChildren.size());
        // setHeader
        for (int i = 0; i < selectedCriteriaList.size(); i++) {
            Text criteriaText = new Text(selectedCriteriaList.get(i));
            criteriaText.setFont(Font.font("Roboto", 13));

            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100.0);

            headerGrid.getColumnConstraints().add(column);
            headerGrid.add(criteriaText, i, 0);
            topsisModel.setNameCriteria(selectedCriteriaList.get(i));

            criteriaVbox = new VBox();
            criteriaVbox.setPrefWidth(196);
            criteriaVbox.setPadding(new Insets(10, 10, 10, 10));
            criteriaVbox.setSpacing(10);
            dataHbox.getChildren().add(criteriaVbox);
            topsisService.getTypeCriteria(topsisModel);
            dataCriteriaType();
            countHeader++;

        }
        headerListHbox.getChildren().add(headerGrid);
    }

    private void dataCriteriaType() {
        if (topsisModel.getTypeCriteria().equalsIgnoreCase("Benefit")) {
            for (int i = 0; i < count; i++) {
                ratingStar = new Rating();
                // ratingToVBoxMap.put(ratingStar, criteriaVbox);
                ratingFieldStar.add(ratingStar);
                criteriaVbox.getChildren().add(ratingStar);

            }
        } else {
            for (int i = 0; i < count; i++) {
                ratingField = new MFXTextField();
                ratingField.setPrefSize(174, 32);
                ratingField.setStyle("-mfx-main: black;");
                ratingField.setFloatMode(FloatMode.DISABLED);
                ratingField.addEventFilter(KeyEvent.KEY_TYPED, event -> {
                    String input = event.getCharacter();
                    if (!input.matches("\\d")) {
                        event.consume();
                    }
                });
                ratingFieldData.add(ratingField);
                criteriaVbox.getChildren().add(ratingField);
            }
        }

    }

    @FXML
    private void backToSelectingCategory(MouseEvent event) {
        activeAchorPane(selectCategory);
    }

    @FXML
    private void backToSelectingCriteria(MouseEvent event) {
        activeAchorPane(selectCriteria);
        count = 0;
    }

    @FXML
    private void calcTopsisField() {
        int i = 0;

        if (ratingFieldData.size() != 0) {
            for (MFXTextField textField : ratingFieldData) {
                int size = ratingFieldData.size();
                String textValue = textField.getText();
                if (textField.getText().trim().isEmpty()) {
                    NotificationManager.notification("Nilai Cost Belum Terisi",
                            "Pastikan Nilai Cost telah terisi semua");
                    isNotEmpty = false;
                    ratingFieldDataCalc.removeAll(ratingFieldDataCalc);
                    break;
                } else {
                    ratingFieldDataCalc.add(textValue);
                    i++;
                }

                if (i == size) {
                    for (Rating starRating : ratingFieldStar) {
                        double ratingValue = starRating.getRating();
                        ratingFieldStarCalc.add(ratingValue);
                    }
                    isNotEmpty = true;
                }
            }
        } else {
            for (Rating starRating : ratingFieldStar) {
                double ratingValue = starRating.getRating();
                ratingFieldStarCalc.add(ratingValue);
            }
            isNotEmpty = true;
            System.out.println("ya");
        }

        if (isNotEmpty) {

            processTopsis();
        }

    }

    private void processTopsis() {
 for (int i = 0; i < ratingFieldDataCalc.size(); i += count) {
            int endIndex = Math.min(i + count, ratingFieldDataCalc.size());
            List<String> group = ratingFieldDataCalc.subList(i, endIndex);
            double sqrtTotal = 0;
            double division = 0;
            double valueMatrixTernormalisasi = 0;
            // pembagi
            for (String subString : group) {
                for (int x = 0; x < subString.length(); x++) {
                    int digit;
                    digit = Character.getNumericValue(subString.charAt(x));
                    sqrtTotal += Math.pow(digit, 2);
                }
            }
            // MATRIX KEPUTUSAN TERNORMALISASI
            division = Math.sqrt(sqrtTotal);
            System.out.println(division);
            for (String subString : group) {
                for (int x = 0; x < subString.length(); x++) {
                    double digit;
                    digit = Character.getNumericValue(subString.charAt(x));
                    valueMatrixTernormalisasi = digit / division;
                    
                }
               

            }



        }
         for(int z=0;z<selectedCriteriaList.size();z++){
                        System.out.println(selectedCriteriaList.get(z));
                    
       
        }
        
        
    }

}

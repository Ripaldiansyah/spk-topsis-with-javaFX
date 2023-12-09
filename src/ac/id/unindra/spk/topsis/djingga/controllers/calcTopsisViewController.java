package ac.id.unindra.spk.topsis.djingga.controllers;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class CalcTopsisViewController implements Initializable {
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
    private AnchorPane topsisResult;

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
    private Pane topsisCalcPane;

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
    List<Double> ratingFieldDataCalc = new ArrayList<>();
    List<String> ratingStarData = new ArrayList<>();
    List<Double> groupStar = new ArrayList<>();
    List<String> listCriteriaHeader = new ArrayList<>();
    List<Double> preferenceList = new ArrayList<>();
    int count, countHeader;
    boolean isNotEmpty = false;
    String[] dataAlternative;
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
            activeAnchorPane(selectCriteria);
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

    private void activeAnchorPane(AnchorPane anchorPane) {
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
                activeAnchorPane(calcTopsisScene);
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
        dataAlternative = topsisService.getDataByCategory(topsisModel);
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
            listCriteriaHeader.add("Benefit");
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
            listCriteriaHeader.add("Cost");
        }

    }

    @FXML
    private void backToSelectingCategory(MouseEvent event) {
        activeAnchorPane(selectCategory);
    }

    @FXML
    private void backToSelectingCriteria(MouseEvent event) {
        activeAnchorPane(selectCriteria);
        count = 0;
    }

    @FXML
    private void calcTopsisField() {
        int i = 0;

        int iterationStar = 0;
        int iterationField = 0;
        int tempIterationStar = 0 + count;
        int tempIterationField = 0 + count;

        if (ratingFieldData.size() != 0 && ratingFieldStar.size() != 0) {
            int passInt = 0;

            for (String criteriaType : listCriteriaHeader) {
                if (criteriaType.equalsIgnoreCase("Benefit")) {
                    for (int x = 0; x < ratingFieldStar.size(); x++) {
                        ratingFieldDataCalc.add(ratingFieldStar.get(iterationStar).getRating());
                        iterationStar++;
                        if (iterationStar == tempIterationStar) {

                            tempIterationStar += count;
                            passInt++;
                            break;

                        }

                    }
                } else {
                    if (checkValidationField()) {
                        for (int x = 0; x < ratingFieldData.size(); x++) {
                            ratingFieldDataCalc.add(Double.parseDouble(ratingFieldData.get(iterationField).getText()));
                            iterationField++;
                            if (iterationField == tempIterationField) {
                                tempIterationField += count;
                                passInt++;
                                break;
                            }

                        }
                    } else {
                        ratingFieldDataCalc.removeAll(ratingFieldDataCalc);
                        break;
                    }
                }
                if (passInt == listCriteriaHeader.size()) {
                    isNotEmpty = true;
                }
            }

        } else if (ratingFieldData.size() != 0) {
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
                    ratingFieldDataCalc.add(Double.parseDouble(textValue));

                    i++;
                }

                if (i == size) {
                    isNotEmpty = true;
                }
            }
        } else if (ratingFieldStar.size() != 0) {
            for (Rating starRating : ratingFieldStar) {
                ratingFieldDataCalc.add(starRating.getRating());
            }
            isNotEmpty = true;
        }

        if (isNotEmpty) {

            processTopsis();
        }

    }

    private boolean checkValidationField() {
        int i = 0;
        boolean valid = false;
        for (MFXTextField textField : ratingFieldData) {

            int size = ratingFieldData.size();

            if (textField.getText().trim().isEmpty()) {
                NotificationManager.notification("Nilai Cost Belum Terisi",
                        "Pastikan Nilai Cost telah terisi semua");
                isNotEmpty = false;
                valid = false;
                break;

            } else {
                i++;
            }

            if (i == size) {
                return valid = true;
            }

        }
        return valid;
    }

    private void processTopsis() {
        String idNormalizedDecisionMatrix = CurrentDate.date("") + "GRADE" + RandomTextGenerator.generateRandomText(3);
        int setCriteriaIndex = 0;
        int size = ratingFieldDataCalc.size();
        topsisModel.setIdNormalizedDecisionMatrix(idNormalizedDecisionMatrix);
        topsisSaveList();
        for (int i = 0; i < size; i += count) {
            int endIndexField = Math.min(i + count, ratingFieldDataCalc.size());

            double sqrtTotalStar = 0;
            double divisionStar = 0;
            double valueMatrixNormalize = 0;
            double valueNormalizedAndWeighted = 0;
            int categoryIndex = 0;

            if (i <= ratingFieldDataCalc.size()) {
                groupStar = ratingFieldDataCalc.subList(i, endIndexField);
                for (double valueDouble : groupStar) {

                    sqrtTotalStar += Math.pow(valueDouble, 2);
                    categoryIndex++;

                    if (categoryIndex == groupStar.size()) {
                        divisionStar = Math.sqrt(sqrtTotalStar);
                        int indexGroup = 0;
                        List<Double> temptList = new ArrayList<>();
                        for (String alternative : dataAlternative) {// 3x iterasi untuk saat ini, sesuai data alternatif
                            double max = 0;
                            double min = 0;

                             DecimalFormat decimalFormat = new DecimalFormat("#.###");

                            topsisModel.setNameAlternative(alternative);
                            topsisService.getIdAlternative(topsisModel);

                            String criteriaName = selectedCriteriaList.get(setCriteriaIndex);
                            topsisModel.setNameCriteria(criteriaName);
                            topsisService.getTypeCriteria(topsisModel);

                            valueMatrixNormalize = groupStar.get(indexGroup) / divisionStar;
                            double valueMatrixNormalizeFormat= Double.parseDouble(decimalFormat.format(valueMatrixNormalize));
                            topsisModel.setWeightAlternative(groupStar.get(indexGroup));
                            indexGroup++;

                            topsisModel.setMatrixNormalize(valueMatrixNormalizeFormat);

                            valueNormalizedAndWeighted = valueMatrixNormalizeFormat * topsisModel.getWeightCriteria();
                            topsisModel.setMatrixNormalizedAndWeighted(Double.parseDouble(decimalFormat.format(valueNormalizedAndWeighted)));
                            
                            temptList.add(Double.parseDouble(decimalFormat.format(valueNormalizedAndWeighted)));

                            if (indexGroup == dataAlternative.length) {
                                if (listCriteriaHeader.get(setCriteriaIndex).equalsIgnoreCase("Benefit")) {
                                    max = Collections.max(temptList);
                                    min = Collections.min(temptList);
                                } else {
                                    max = Collections.min(temptList);
                                    min = Collections.max(temptList);
                                }
                                topsisModel.setMax(max);
                                topsisModel.setMin(min);
                                topsisService.setMaxMinTopsis(topsisModel);
                            }

                            topsisService.setTopsisNormalizedDecisionmatrixAndWeighted(topsisModel);

                        }

                    }

                }

            }
            setCriteriaIndex++;
        }
        topsisIdeal(idNormalizedDecisionMatrix);
    }

    private void topsisIdeal(String idNormalizedDecisionMatrix) {

        int index = 0;
        List<Double> valueAlternative = new ArrayList<>();
        List<Double> valueMaxMin = new ArrayList<>();

        for (String alternative : dataAlternative) {
            topsisModel.setNameAlternative(alternative);
            topsisService.getIdAlternative(topsisModel);

            valueAlternative.addAll(
                    topsisService.normalizeAndWeight(idNormalizedDecisionMatrix, topsisModel.getIdAlternative()));
        }
        for (int i = 0; i < count; i++) {
            double idealPositive = 0;
            double idealNegative = 0;

            topsisModel.setNameAlternative(dataAlternative[i]);
            topsisService.getIdAlternative(topsisModel);

            for (String criteria : selectedCriteriaList) {

                topsisModel.setNameCriteria(criteria);
                topsisService.getTypeCriteria(topsisModel);
                valueMaxMin = topsisService.getMaxMin(idNormalizedDecisionMatrix, topsisModel.getIdCriteria());

                double valueMax = valueMaxMin.get(0) - valueAlternative.get(index);
                double valueMin = valueMaxMin.get(1) - valueAlternative.get(index);

                idealPositive += Math.pow(valueMax, 2);
                idealNegative += Math.pow(valueMin, 2);
                index++;

            }
            double sqrtIdealPositive = Math.sqrt(idealPositive);
            double sqrtIdealNegative = Math.sqrt(idealNegative);
            DecimalFormat decimalFormat = new DecimalFormat("#.###");

            double sqrtIdealPositiveFormated = Double.parseDouble(decimalFormat.format(sqrtIdealPositive));
            double sqrtIdealNegativeFormated = Double.parseDouble(decimalFormat.format(sqrtIdealNegative));

            double preference = Double.parseDouble(decimalFormat
                    .format(sqrtIdealNegative / (sqrtIdealNegative + sqrtIdealPositive)));

            preferenceList.add(preference);

            topsisModel.setPreference(preference);
            topsisModel.setPositiveIdealValue(sqrtIdealPositiveFormated);
            topsisModel.setNegativeIdealValue(sqrtIdealNegativeFormated);
            topsisService.setTopsisIdeal(topsisModel);
        }

        topsisRanking(idNormalizedDecisionMatrix);
    }

    private void topsisRanking(String idNormalizedDecisionMatrix) {
        List<Double> sortedList = new ArrayList<>(preferenceList);
        Collections.sort(sortedList, Collections.reverseOrder());
        Map<Double, Integer> topsisRank = new HashMap<>();
        int x = 0;

        for (int i = 0; i < sortedList.size(); i++) {
            topsisRank.put(sortedList.get(i), i + 1);
        }

        for (String alternative : dataAlternative) {
            topsisModel.setNameAlternative(alternative);
            topsisService.getIdAlternative(topsisModel);

            double value = preferenceList.get(x);
            int rank = topsisRank.get(value);
            x++;

            topsisModel.setRank(rank);
            topsisService.setRank(topsisModel);
        }

        topsisResultProcess(idNormalizedDecisionMatrix);
        
    }

    private void topsisResultProcess(String idNormalizedDecisionMatrix) {
        try {
            ResultTopsisViewController resultTopsisViewController = new ResultTopsisViewController();
            resultTopsisViewController.setTopsisModel(topsisModel);
            resultTopsisViewController.setIdTopsis(idNormalizedDecisionMatrix);

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/ac/id/unindra/spk/topsis/djingga/views/TopsisResultView.fxml"));
            loader.setController(resultTopsisViewController); // Hapus baris ini

            Parent newContent = loader.load();
            topsisCalcPane.getChildren().setAll(newContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void topsisSaveList() {
        topsisModel.setTotalCriteria(countHeader);
        topsisModel.setDate(CurrentDate.formatDate());
        topsisService.setListTopsis(topsisModel);

    }

}

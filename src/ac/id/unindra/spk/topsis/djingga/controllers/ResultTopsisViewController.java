package ac.id.unindra.spk.topsis.djingga.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import ac.id.unindra.spk.topsis.djingga.models.TopsisModel;
import ac.id.unindra.spk.topsis.djingga.models.TopsisTableRankModel;
import ac.id.unindra.spk.topsis.djingga.services.TopsisService;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ResultTopsisViewController implements Initializable {

    @FXML
    private TableColumn<TopsisTableRankModel, String> alternativeNameColumn;
    @FXML
    private TableColumn<TopsisTableRankModel, String> preferenceColumn;
    @FXML
    private TableColumn<TopsisTableRankModel, String> rankColumn;
    @FXML
    private TableView<TopsisTableRankModel> rankTable;

    @FXML
    private Text idTopsisText;
    @FXML
    private Text detailInformation;
    @FXML
    private Text decisionMatrixRight;
    @FXML
    private Text decisionMatrixLeft;
    @FXML
    private Text normalizedDecisionMatrixRight;
    @FXML
    private Text normalizedDecisionMatrixLeft;
    @FXML
    private Text normalizedAndWeightedDecisionMatrixRight;
    @FXML
    private Text normalizedAndWeightedDecisionMatrixLeft;
    @FXML
    private Text idealPositiveRight;
    @FXML
    private Text idealPositiveLeft;
    @FXML
    private Text idealNegativeRight;
    @FXML
    private Text idealNegativeLeft;

    @FXML
    private MFXScrollPane detailTopsis;

    @FXML
    private VBox decisionMatrixVbox;
    @FXML
    private VBox normalizedDecisionMatrixVbox;
    @FXML
    private VBox normalizedAndWeightedDecisionMatrixVbox;
    @FXML
    private VBox idealPositiveVbox;
    @FXML
    private VBox idealNegativeVbox;

    @FXML
    private HBox decisionMatrixHbox;
    @FXML
    private HBox normalizedDecisionMatrixHbox;
    @FXML
    private HBox normalizedAndWeightedDecisionMatrixHbox;
    @FXML
    private HBox idealPositiveHbox;
    @FXML
    private HBox idealNegativeHbox;

    private TopsisService topsisService = new TopsisController();
    private TopsisModel topsisModel = new TopsisModel();
    private String idTopsis;

    public ResultTopsisViewController() {
    }

    public void setTopsisModel(TopsisModel topsisModel) {
        this.topsisModel = topsisModel;
    }

    public void setIdTopsis(String idTopsis) {
        this.idTopsis = idTopsis;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setColumnRank();
        disableDetail();
        setMatrix();
    }

    private void disableDetail() {

        detailTopsis.setVisible(false);
        detailTopsis.setDisable(true);
        detailInformation.setText("Tampilakan informasi detail");
        detailInformation.setLayoutX(655);
    }

    private void enableDetail() {
        detailTopsis.setVisible(true);
        detailTopsis.setDisable(false);
        detailInformation.setText("Sembunyikan informasi detail");
        detailInformation.setLayoutX(642);
    }

    private void setColumnRank() {
        idTopsisText.setText(idTopsis);
        idTopsisText.setVisible(false);
        final ObservableList<TopsisTableRankModel> ObservableList = FXCollections.observableArrayList();
        if (topsisService.getDataRank(topsisModel) != null) {
            ObservableList.addAll(topsisService.getDataRank(topsisModel));

            alternativeNameColumn
                    .setCellValueFactory(new PropertyValueFactory<TopsisTableRankModel, String>("alternativeName"));
            preferenceColumn
                    .setCellValueFactory(new PropertyValueFactory<TopsisTableRankModel, String>("preference"));
            rankColumn
                    .setCellValueFactory(new PropertyValueFactory<TopsisTableRankModel, String>("rank"));

            rankTable.setItems(ObservableList);
        }
    }

    @FXML
    private void showDetail(MouseEvent event) {
        if (detailInformation.getText().equalsIgnoreCase("Tampilakan informasi detail")) {
            enableDetail();
        } else {
            disableDetail();
        }
    }

    private void setMatrix() {
        setDecisionMatrix();
        setNormalizedDecisionMatrix();
        setNormalizedAndWeightedDecisionMatrix();
        setIdealPositive();
        setIdealNegative();
    }

    private void setDecisionMatrix() {
        int subList = topsisService.totalAlternative(topsisModel);
        int subListTemp = topsisService.totalAlternative(topsisModel);
        int maxLooping = topsisService.totalCriteria(topsisModel);
        List<Integer> valueDecisionMatrix = topsisService.getAlternativeValue(topsisModel);
        int starIndex = 0;
        if (maxLooping > 3) {
            int setScaleY = maxLooping - 3;
            decisionMatrixRight.setScaleY(2 + setScaleY);
            decisionMatrixLeft.setScaleY(2 + setScaleY);
            decisionMatrixHbox.setPrefHeight(105 + (setScaleY * 10));
            HBox.setMargin(decisionMatrixRight, new Insets(-10 + (setScaleY * 10)));
            HBox.setMargin(decisionMatrixLeft, new Insets(-10 + (setScaleY * 10)));
        }

        for (int i = 0; i < maxLooping; i++) {
            List<Integer> valueSublist = valueDecisionMatrix.subList(starIndex, subList);
            starIndex += subListTemp;
            subList += subListTemp;

            String result = valueSublist.toString().replace("[", "").replace("]", "").replace(",", " ");
            Text text = new Text();
            text.setText(result);
            decisionMatrixVbox.getChildren().add(text);

        }

    }

    private void setNormalizedDecisionMatrix() {
        int subList = topsisService.totalAlternative(topsisModel);
        int subListTemp = topsisService.totalAlternative(topsisModel);
        int maxLooping = topsisService.totalCriteria(topsisModel);
        List<Double> valueNormalizedDecisionMatrix = topsisService.getNormalizedDecisionMatrixValue(topsisModel);
        int starIndex = 0;
        if (maxLooping > 3) {
            int setScaleY = maxLooping - 3;
            normalizedDecisionMatrixRight.setScaleY(2 + setScaleY);
            normalizedDecisionMatrixLeft.setScaleY(2 + setScaleY);
            normalizedDecisionMatrixHbox.setPrefHeight(105 + (setScaleY * 10));
            HBox.setMargin(normalizedDecisionMatrixRight, new Insets(-10 + (setScaleY * 10)));
            HBox.setMargin(normalizedDecisionMatrixLeft, new Insets(-10 + (setScaleY * 10)));
        }

        for (int i = 0; i < maxLooping; i++) {
            List<Double> valueSublist = valueNormalizedDecisionMatrix.subList(starIndex, subList);
            starIndex += subListTemp;
            subList += subListTemp;

            String result = valueSublist.toString().replace("[", "").replace("]", "").replace(",", " ");
            Text text = new Text();
            text.setText(result);
            normalizedDecisionMatrixVbox.getChildren().add(text);

        }

    }

    private void setNormalizedAndWeightedDecisionMatrix() {
        int subList = topsisService.totalAlternative(topsisModel);
        int subListTemp = topsisService.totalAlternative(topsisModel);
        int maxLooping = topsisService.totalCriteria(topsisModel);
        List<Double> valueNormalizedAndWeightedDecisionMatrix = topsisService
                .getNormalizedAndWeightedDecisionMatrixValue(topsisModel);
        int starIndex = 0;
        if (maxLooping > 3) {
            int setScaleY = maxLooping - 3;
            normalizedAndWeightedDecisionMatrixRight.setScaleY(2 + setScaleY);
            normalizedAndWeightedDecisionMatrixLeft.setScaleY(2 + setScaleY);
            normalizedAndWeightedDecisionMatrixHbox.setPrefHeight(105 + (setScaleY * 10));
            HBox.setMargin(normalizedAndWeightedDecisionMatrixRight, new Insets(-10 + (setScaleY * 10)));
            HBox.setMargin(normalizedAndWeightedDecisionMatrixLeft, new Insets(-10 + (setScaleY * 10)));
        }

        for (int i = 0; i < maxLooping; i++) {
            List<Double> valueSublist = valueNormalizedAndWeightedDecisionMatrix.subList(starIndex, subList);
            starIndex += subListTemp;
            subList += subListTemp;

            String result = valueSublist.toString().replace("[", "").replace("]", "").replace(",", " ");
            Text text = new Text();
            text.setText(result);
            normalizedAndWeightedDecisionMatrixVbox.getChildren().add(text);

        }

    }

    private void setIdealPositive() {
        int maxLooping = topsisService.totalAlternative(topsisModel);
        List<Double> valueIdealPositive = topsisService.getIdealPositive(topsisModel);
        if (maxLooping > 3) {
            int setScaleY = maxLooping - 3;
            idealPositiveRight.setScaleY(2 + setScaleY);
            idealPositiveLeft.setScaleY(2 + setScaleY);
            idealPositiveHbox.setPrefHeight(105 + (setScaleY * 10));
            HBox.setMargin(idealPositiveRight, new Insets(-10 + (setScaleY * 10)));
            HBox.setMargin(idealPositiveLeft, new Insets(-10 + (setScaleY * 10)));
        }

        for (int i = 0; i < maxLooping; i++) {
            List<Double> valueSublist = valueIdealPositive.subList(i, i + 1);

            String result = valueSublist.toString().replace("[", "").replace("]", "").replace(",", " ");
            Text text = new Text();
            text.setText(result);
            idealPositiveVbox.getChildren().add(text);
        }

    }

    private void setIdealNegative() {
        int maxLooping = topsisService.totalAlternative(topsisModel);
        List<Double> valueIdealNegative = topsisService.getIdealNegative(topsisModel);
        if (maxLooping > 3) {
            int setScaleY = maxLooping - 3;
            idealNegativeRight.setScaleY(2 + setScaleY);
            idealNegativeLeft.setScaleY(2 + setScaleY);
            idealNegativeHbox.setPrefHeight(105 + (setScaleY * 10));
            HBox.setMargin(idealNegativeRight, new Insets(-10 + (setScaleY * 10)));
            HBox.setMargin(idealNegativeLeft, new Insets(-10 + (setScaleY * 10)));
        }

        for (int i = 0; i < maxLooping; i++) {
            List<Double> valueSublist = valueIdealNegative.subList(i, i + 1);

            String result = valueSublist.toString().replace("[", "").replace("]", "").replace(",", " ");
            Text text = new Text();
            text.setText(result);
            idealNegativeVbox.getChildren().add(text);
        }

    }
}

package ac.id.unindra.spk.topsis.djingga.controllers;

import java.net.URL;
import java.util.ResourceBundle;


import ac.id.unindra.spk.topsis.djingga.models.TopsisModel;
import ac.id.unindra.spk.topsis.djingga.models.TopsisTableListModel;
import ac.id.unindra.spk.topsis.djingga.services.TopsisService;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPagination;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class TopsisListController implements Initializable {
    @FXML
    private HBox actionHbox;

    @FXML
    private TableColumn<TopsisTableListModel, String> categoryColumn;
    @FXML
    private TableColumn<TopsisTableListModel, String> dateColumn;

    @FXML
    private TableColumn<TopsisTableListModel, String> idColumn;
    @FXML
    private TableColumn<TopsisTableListModel, String> totalCriteraiColumn;

    @FXML
    private Pane topsisViewPane;

    @FXML
    private Text countTopsis;

    @FXML
    private MFXButton removeTopsisButton;
    @FXML
    private MFXButton detailTopsisButton;

    @FXML
    private MFXPagination topsisPagination;

    @FXML
    private TableView<TopsisTableListModel> topsisTable;

    TopsisModel topsisModel = new TopsisModel();
    TopsisService topsisService = new TopsisController();
    private int activePage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCount();
        setColumn();
        disableHbox(actionHbox);
    }

    @FXML
    void deleteTopsis(MouseEvent event) {
        topsisService.deleteTopsis(topsisModel);
        setColumn();
        setCount();
    }
    @FXML
    void detailTopsis(MouseEvent event) {
       try {
            ResultTopsisViewController resultTopsisViewController = new ResultTopsisViewController();
            resultTopsisViewController.setTopsisModel(topsisModel);
            resultTopsisViewController.setIdTopsis(topsisModel.getIdNormalizedDecisionMatrix());

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/ac/id/unindra/spk/topsis/djingga/views/TopsisResultView.fxml"));
            loader.setController(resultTopsisViewController); // Hapus baris ini

            Parent newContent = loader.load();
            topsisViewPane.getChildren().setAll(newContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void getSelectedData(MouseEvent event) {
        SelectionModel<TopsisTableListModel> selectionModel = topsisTable.getSelectionModel();
        TopsisTableListModel selecetedModel = selectionModel.getSelectedItem();
        if (selecetedModel != null) {
            String id = selecetedModel.getId();
            topsisModel.setIdNormalizedDecisionMatrix(id);
            enableHbox(actionHbox);
        }
    }

    @FXML
    void topsisPagination(MouseEvent event) {
        if (topsisPagination.getCurrentPage() != 1) {
            activePage = (topsisPagination.getCurrentPage() * 12) - 12;
        } else {
            activePage = 0;
        }
        disableHbox(actionHbox);
        setColumn();
    }

    private void setCount() {
        topsisService.countTopsis(topsisModel);
        countTopsis.setText(String.valueOf(topsisModel.getTotalTopsis()));
        topsisPagination.setMaxPage(topsisModel.getTotalPaginate());
    }

    private void disableHbox(HBox HBox) {
        HBox.setVisible(false);
        HBox.setDisable(true);
    }

    private void enableHbox(HBox HBox) {
        HBox.setVisible(true);
        HBox.setDisable(false);
    }

    private void setColumn() {
        final ObservableList<TopsisTableListModel> ObservableList = FXCollections.observableArrayList();
        topsisModel.setActivePaginate(activePage);
        if (topsisService.getDataTopsis(topsisModel) != null) {
            ObservableList.addAll(topsisService.getDataTopsis(topsisModel));

            idColumn
                    .setCellValueFactory(new PropertyValueFactory<TopsisTableListModel, String>("id"));
            categoryColumn
                    .setCellValueFactory(new PropertyValueFactory<TopsisTableListModel, String>("category"));
            totalCriteraiColumn
                    .setCellValueFactory(
                            new PropertyValueFactory<TopsisTableListModel, String>("totalCriteriaSelected"));
            dateColumn
                    .setCellValueFactory(new PropertyValueFactory<TopsisTableListModel, String>("date"));

            topsisTable.setItems(ObservableList);
        }

    }

}

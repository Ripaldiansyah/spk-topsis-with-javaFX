package ac.id.unindra.spk.topsis.djingga.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import ac.id.unindra.spk.topsis.djingga.models.AlternativeModel;
import ac.id.unindra.spk.topsis.djingga.models.AlternativeTableModel;
import ac.id.unindra.spk.topsis.djingga.services.AlternativeService;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class AlternativeViewController implements Initializable {
    @FXML
    private Pane alternativiewViewPane;

    @FXML
    private HBox actionHbox;

    @FXML
    private MFXButton addAlternativeButton;

    @FXML
    private TableColumn<AlternativeTableModel, String> alternativeCategoryColumn;

    @FXML
    private TableColumn<AlternativeTableModel, String> alternativeNameColumn;

    @FXML
    private MFXPagination alternativePagination;

    @FXML
    private TableView<AlternativeTableModel> alternativeTable;

    @FXML
    private Text countAlternative;

    @FXML
    private MFXButton editAlternativeButton;

    @FXML
    private ImageView imgAction;

    @FXML
    private ImageView imgChangeRole;

    @FXML
    private MFXButton removeAlternativeButton;

    AlternativeModel alternativeModel = new AlternativeModel();
    AlternativeService alternativeService = new AlternativeController();
    private int activePage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCount();
        setColumn();
        disableHbox(actionHbox);
        
    }
    @FXML
    void userPaginationControl(MouseEvent event) {
        // buat hitung offset
        if (alternativePagination.getCurrentPage() != 1) {
            activePage = (alternativePagination.getCurrentPage() * 12) - 12;
        } else {
            activePage = 0;
        }
        disableHbox(actionHbox);
        setColumn();
    }


    private void setColumn() {
        final ObservableList<AlternativeTableModel> ObservableList = FXCollections.observableArrayList();
        alternativeModel.setActivePaginate(activePage);
        if (alternativeService.getDataCriteria(alternativeModel)!=null) {
            ObservableList.addAll(alternativeService.getDataCriteria(alternativeModel));

        alternativeNameColumn
                .setCellValueFactory(new PropertyValueFactory<AlternativeTableModel, String>("alternativeName"));
        alternativeCategoryColumn
                .setCellValueFactory(new PropertyValueFactory<AlternativeTableModel, String>("alternativeCategory"));

        alternativeTable.setItems(ObservableList);
        }
        
    }

    @FXML
    void addAlternative(MouseEvent event) {
        AddAlternativeViewController.editing = false;
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/ac/id/unindra/spk/topsis/djingga/views/AddAlternativeView.fxml"));
            Parent newContent = loader.load();
            alternativiewViewPane.getChildren().setAll(newContent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void deleteAlternative(MouseEvent event) {
        alternativeService.deleteAlternative(alternativeModel);
        setColumn();
        setCount();
    }

    @FXML
    void editAlternative(MouseEvent event) {
        AddAlternativeViewController.alternativeNameEdit = alternativeModel.getAlternativeName();
        AddAlternativeViewController.alternativeCategoryEdit = alternativeModel.getAlternativeCategory();
        AddAlternativeViewController.editing = true;
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/ac/id/unindra/spk/topsis/djingga/views/AddAlternativeView.fxml"));
            Parent newContent = loader.load();
            alternativiewViewPane.getChildren().setAll(newContent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void getSelectedData(MouseEvent event) {
        SelectionModel<AlternativeTableModel> selectionModel = alternativeTable.getSelectionModel();
        AlternativeTableModel selecetedModel = selectionModel.getSelectedItem();
        if (selecetedModel != null) {
            String nameAlternativeString = selecetedModel.getAlternativeName();
            String categoryAlternativeString = selecetedModel.getAlternativeCategory();
            alternativeModel.setAlternativeName(nameAlternativeString);
            alternativeModel.setAlternativeCategory(categoryAlternativeString);
            enableHbox(actionHbox);
        }
    }

    

    private void disableHbox(HBox HBox) {
        HBox.setVisible(false);
        HBox.setDisable(true);
    }

    private void enableHbox(HBox HBox) {
        HBox.setVisible(true);
        HBox.setDisable(false);
    }

    private void setCount() {
        alternativeService.countAlternative(alternativeModel);
        countAlternative.setText(String.valueOf(alternativeModel.getTotalAlternatif()));
        alternativePagination.setMaxPage(alternativeModel.getTotalPaginate());
    }

}

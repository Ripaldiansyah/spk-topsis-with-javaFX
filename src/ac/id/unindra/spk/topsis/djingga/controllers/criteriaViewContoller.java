package ac.id.unindra.spk.topsis.djingga.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import ac.id.unindra.spk.topsis.djingga.models.CriteriaModel;
import ac.id.unindra.spk.topsis.djingga.models.CriteriaTableModel;
import ac.id.unindra.spk.topsis.djingga.models.UserTableModel;
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
import ac.id.unindra.spk.topsis.djingga.services.CriteriaService;

public class CriteriaViewContoller implements Initializable {
     @FXML
     private HBox actionHbox;

     @FXML
     private MFXButton addCriteriaButton;

     @FXML
     private Text countBenefits;

     @FXML
     private Text countCost;

     @FXML
     private Text countCriteria;

     @FXML
     private TableColumn<CriteriaTableModel, String> criteriaNameColumn;

     @FXML
     private MFXPagination criteriaPagination;

     @FXML
     private TableColumn<CriteriaTableModel, String> criteriaTypeColumn;

     @FXML
     private TableColumn<CriteriaTableModel, String> criteriaWeightColumn;

     @FXML
     private TableColumn<CriteriaTableModel, String> gradeWeightColumn;

     @FXML
     private MFXButton editCriteriaButton;


     @FXML
     private MFXButton removeCriteriaButton;

     @FXML
     private TableView<CriteriaTableModel> criteriaTable;

     @FXML
     private Pane criteriViewPane;

     CriteriaService criteriaService = new CriteriaController();
     CriteriaModel criteriaModel = new CriteriaModel();
     int activePage;

     @Override
     public void initialize(URL location, ResourceBundle resources) {
          setCount();
          setColumn();
          disableHbox(actionHbox);
     }

     @FXML
     private void userPaginationControl(MouseEvent event) {
          //buat hitung offset
          if (criteriaPagination.getCurrentPage() != 1) {
               activePage = (criteriaPagination.getCurrentPage() * 12) - 12;
          } else {
               activePage = 0;
          }
          disableHbox(actionHbox);
          setColumn();
     }

     @FXML
     private void getSelectedData(MouseEvent event) {
          SelectionModel<CriteriaTableModel> selectionModel = criteriaTable.getSelectionModel();
          CriteriaTableModel selecetedModel = selectionModel.getSelectedItem();
          if (selecetedModel != null) {
               String nameCriteria = selecetedModel.getCriteriaName();
               criteriaModel.setCriteriaName(nameCriteria);
               enableHbox(actionHbox);
          }
     }

     @FXML
     private void deleteButton(MouseEvent event) {
          criteriaService.deleteCriteria(criteriaModel);
          setColumn();
          setCount();
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
          criteriaService.countCriteria(criteriaModel);

          countCriteria.setText(String.valueOf(criteriaModel.getTotalCriteria()));
          countBenefits.setText(String.valueOf(criteriaModel.getTotalBenefits()));
          countCost.setText(String.valueOf(criteriaModel.getTotalCost()));
          criteriaPagination.setMaxPage(criteriaModel.getTotalPaginate());
     }

     private void setColumn() {
          final ObservableList<CriteriaTableModel> ObservableList = FXCollections.observableArrayList();
          criteriaModel.setActivePaginate(activePage);
          ObservableList.addAll(criteriaService.getDataCriteria(criteriaModel));

          criteriaNameColumn.setCellValueFactory(new PropertyValueFactory<CriteriaTableModel, String>("criteriaName"));
          criteriaTypeColumn.setCellValueFactory(new PropertyValueFactory<CriteriaTableModel, String>("criteriaType"));
          criteriaWeightColumn
                    .setCellValueFactory(new PropertyValueFactory<CriteriaTableModel, String>("criteriaWeight"));
          gradeWeightColumn.setCellValueFactory(new PropertyValueFactory<CriteriaTableModel, String>("gradeWeight"));

          criteriaTable.setItems(ObservableList);
     }

     @FXML
     private void addCriteria(MouseEvent event) {
          AddCriteriaViewContoller.editing = false;
          try {
               FXMLLoader loader = new FXMLLoader(
                         getClass().getResource("/ac/id/unindra/spk/topsis/djingga/views/AddCriteriaView.fxml"));
               Parent newContent = loader.load();
               criteriViewPane.getChildren().setAll(newContent);
          } catch (Exception e) {
               e.printStackTrace();
          }

     }

     @FXML
     private void editCriteria(MouseEvent event) {
          AddCriteriaViewContoller.criteriaNameEdit = criteriaModel.getCriteriaName();
          AddCriteriaViewContoller.editing = true;
          try {
               FXMLLoader loader = new FXMLLoader(
                         getClass().getResource("/ac/id/unindra/spk/topsis/djingga/views/AddCriteriaView.fxml"));
               Parent newContent = loader.load();
               criteriViewPane.getChildren().setAll(newContent);

          } catch (Exception e) {
               e.printStackTrace();
          }

     }
}

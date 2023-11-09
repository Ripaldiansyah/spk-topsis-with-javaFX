package ac.id.unindra.spk.topsis.djingga.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Function;

import ac.id.unindra.spk.topsis.djingga.models.userModel;
import ac.id.unindra.spk.topsis.djingga.models.userTableModel;
import ac.id.unindra.spk.topsis.djingga.services.userService;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPaginatedTableView;
import io.github.palexdev.materialfx.controls.MFXPagination;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.base.MFXLabeled;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class userViewController implements Initializable {

  @FXML
  private Text countAccount;

  @FXML
  private Text countActive;

  @FXML
  private Text countAdmin;

  @FXML
  private Text countPending;

  @FXML
  private Text countUser;

  @FXML
  private HBox actionHbox;

  @FXML
  private MFXButton activeButton;

  @FXML
  private TableView userTable;

  @FXML
  private MFXPagination userPagination;

  @FXML
  private TableColumn idColumn;
  @FXML
  private TableColumn fullnameColumn;
  @FXML
  private TableColumn usernameColumn;
  @FXML
  private TableColumn roleColumn;
  @FXML
  private TableColumn accountStatColumn;

  @FXML
  private MFXButton removeButton;

  private userService userService = new userController();
  private userModel userModel = new userModel();

  ObservableList<userTableModel> data = FXCollections.observableArrayList();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    setColumn();
    setCount();

  }

  private void setColumn() {

    final ObservableList<userTableModel> ObservableList = FXCollections.observableArrayList();
    ObservableList.addAll(userService.getDataUser());

    idColumn.setCellValueFactory(new PropertyValueFactory<userTableModel, String>("id"));
    fullnameColumn.setCellValueFactory(new PropertyValueFactory<userTableModel, String>("fullName"));
    usernameColumn.setCellValueFactory(new PropertyValueFactory<userTableModel, String>("username"));
    roleColumn.setCellValueFactory(new PropertyValueFactory<userTableModel, String>("role"));
    accountStatColumn.setCellValueFactory(new PropertyValueFactory<userTableModel, String>("accountStatus"));

    userTable.setItems(ObservableList);

  }

  private void setCount() {
    userService.countData(userModel);

    countAccount.setText(String.valueOf( userModel.getTotalAccout()));
    countActive.setText(String.valueOf( userModel.getTotalActive()));
    countPending.setText(String.valueOf( userModel.getTotalPending()));
    countAdmin.setText(String.valueOf( userModel.getTotalAdmin()));
    countUser.setText(String.valueOf( userModel.getTotalUser()));
    userPagination.setMaxPage(userModel.getTotalPaginate());

  }


  @FXML
  private void getSelectedData(MouseEvent event){
    int index = userTable.getSelected
  }
}

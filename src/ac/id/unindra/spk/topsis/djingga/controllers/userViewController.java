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
import javafx.geometry.Insets;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
  private MFXButton actionButton;

  @FXML
  private MFXButton changeRoleButton;

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
  private ImageView imgAction;
  @FXML
  private ImageView imgChangeRole;

  @FXML
  private MFXButton removeButton;

  private userService userService = new userController();
  private userModel userModel = new userModel();
  int activePage;

  ObservableList<userTableModel> data = FXCollections.observableArrayList();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    setCount();
    setColumn();
    disableHbox(actionHbox);
    disableChangeRoleButton(changeRoleButton);

  }

  @FXML
  private void userPaginationControl(MouseEvent event) {

    if (userPagination.getCurrentPage() != 1) {
      activePage = (userPagination.getCurrentPage() * 12) - 12;
    } else {
      activePage = 0;
    }
    disableHbox(actionHbox);
    disableChangeRoleButton(changeRoleButton);
    setColumn();
  }

  private void setColumn() {

    final ObservableList<userTableModel> ObservableList = FXCollections.observableArrayList();
    userModel.setActivePaginate(activePage);
    ObservableList.addAll(userService.getDataUser(userModel));

    idColumn.setCellValueFactory(new PropertyValueFactory<userTableModel, String>("id"));
    fullnameColumn.setCellValueFactory(new PropertyValueFactory<userTableModel, String>("fullName"));
    usernameColumn.setCellValueFactory(new PropertyValueFactory<userTableModel, String>("username"));
    roleColumn.setCellValueFactory(new PropertyValueFactory<userTableModel, String>("role"));
    accountStatColumn.setCellValueFactory(new PropertyValueFactory<userTableModel, String>("accountStatus"));

    userTable.setItems(ObservableList);

  }

  private void setCount() {
    userService.countData(userModel);

    countAccount.setText(String.valueOf(userModel.getTotalAccout()));
    countActive.setText(String.valueOf(userModel.getTotalActive()));
    countPending.setText(String.valueOf(userModel.getTotalPending()));
    countAdmin.setText(String.valueOf(userModel.getTotalAdmin()));
    countUser.setText(String.valueOf(userModel.getTotalUser()));
    userPagination.setMaxPage(userModel.getTotalPaginate());

  }

  private void activeHbox(HBox HBox) {
    HBox.setVisible(true);
    HBox.setDisable(false);
  }

  private void disableHbox(HBox HBox) {
    HBox.setVisible(false);
    HBox.setDisable(true);
  }

  private void ActiveChangeRoleButton(MFXButton MFXButton) {
    MFXButton.setVisible(true);
    MFXButton.setDisable(false);
  }

  private void disableChangeRoleButton(MFXButton MFXButton) {
    MFXButton.setVisible(false);
    MFXButton.setDisable(true);
  }

  private void setImagePending() {
    Image img = new Image("/ac/id/unindra/spk/topsis/djingga/resources/media/icons8_clock_10px_1.png");
    imgAction.setImage(img);
    actionButton.setText("Pending");
    actionButton.setPadding(new Insets(3, 0, 0, -10));
  }

  private void setImageActive() {
    Image img = new Image("/ac/id/unindra/spk/topsis/djingga/resources/media/icons8_ok_10px.png");
    imgAction.setImage(img);
    actionButton.setText("Active");
    actionButton.setPadding(new Insets(3, 0, 0, 0));
  }

  private void setImageAdmin() {
    Image img = new Image("/ac/id/unindra/spk/topsis/djingga/resources/media/icons8_Admin_Settings_Male_25px.png");
    imgChangeRole.setImage(img);
    changeRoleButton.setText("Jadikan Admin");
  }

  private void setImageUser() {
    Image img = new Image("/ac/id/unindra/spk/topsis/djingga/resources/media/icons8_user_shield_25px.png");
    imgChangeRole.setImage(img);
    changeRoleButton.setText("Jadikan User");
  }

  @FXML
  private void getSelectedData(MouseEvent event) {
    SelectionModel<userTableModel> selectionModel = userTable.getSelectionModel();

    userTableModel selecetedModel = selectionModel.getSelectedItem();
    if (selecetedModel != null) {
      if (selecetedModel.getRole().equalsIgnoreCase("user")
          &&
          selecetedModel.getAccountStatus().equalsIgnoreCase("pending")) {
        activeHbox(actionHbox);
        ActiveChangeRoleButton(changeRoleButton);
        setImageActive();
        setImageAdmin();
      }
      if (selecetedModel.getRole().equalsIgnoreCase("user")
          &&
          selecetedModel.getAccountStatus().equalsIgnoreCase("Active")) {
        activeHbox(actionHbox);
        ActiveChangeRoleButton(changeRoleButton);
        setImagePending();
        setImageAdmin();
      }
      if (selecetedModel.getRole().equalsIgnoreCase("Admin")
          &&
          selecetedModel.getAccountStatus().equalsIgnoreCase("Active")) {
        activeHbox(actionHbox);
        ActiveChangeRoleButton(changeRoleButton);
        setImagePending();
        setImageUser();
      }
      if (selecetedModel.getRole().equalsIgnoreCase("Admin")
          &&
          selecetedModel.getAccountStatus().equalsIgnoreCase("Pending")) {
        activeHbox(actionHbox);
        ActiveChangeRoleButton(changeRoleButton);
        setImageActive();
        setImageUser();
      }
    }

  }

}

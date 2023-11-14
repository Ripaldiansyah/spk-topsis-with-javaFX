package ac.id.unindra.spk.topsis.djingga.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import ac.id.unindra.spk.topsis.djingga.models.addCriteriaModel;
import ac.id.unindra.spk.topsis.djingga.services.addCriteriaService;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPagination;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class criteriaViewContoller implements Initializable {
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
    private TableColumn<?, ?> criteriaNameColumn;

    @FXML
    private MFXPagination criteriaPagination;

    @FXML
    private TableColumn<?, ?> criteriaTypeColumn;

    @FXML
    private TableColumn<?, ?> criteriaWeightColumn;

    @FXML
    private MFXButton editCriteriaButton;

    @FXML
    private ImageView imgAction;

    @FXML
    private ImageView imgChangeRole;

    @FXML
    private MFXButton removeCriteriaButton;

    addCriteriaService criteriaService = new addCriteriaController();
    addCriteriaModel criteriaModel = new addCriteriaModel();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'initialize'");
    }
    
    private void setCount() {
        criteriaService.countCriteria(criteriaModel);
    
        countCriteria.setText(String.valueOf(criteriaModel.getTotalCriteria()));
        countBenefits.setText(String.valueOf(criteriaModel.getTotalBenefits()));
        countCost.setText(String.valueOf(criteriaModel.getTotalCost()));
        criteriaPagination.setMaxPage(criteriaModel.getTotalPaginate());
      }
}

package ac.id.unindra.spk.topsis.djingga.models;

import javafx.beans.property.SimpleStringProperty;

public class criteriaTableModel {
    SimpleStringProperty criteriaName, criteriaType,criteriaWeight;

    public criteriaTableModel(String criteriaName, String criteriaType, String criteriaWeight) {
        this.criteriaName = new SimpleStringProperty(criteriaName);
        this.criteriaType = new SimpleStringProperty(criteriaType);;
        this.criteriaWeight = new SimpleStringProperty(criteriaWeight);;
    }

    public SimpleStringProperty getCriteriaName() {
        return criteriaName;
    }

    public void setCriteriaName(SimpleStringProperty criteriaName) {
        this.criteriaName = criteriaName;
    }

    public SimpleStringProperty getCriteriaType() {
        return criteriaType;
    }

    public void setCriteriaType(SimpleStringProperty criteriaType) {
        this.criteriaType = criteriaType;
    }

    public SimpleStringProperty getCriteriaWeight() {
        return criteriaWeight;
    }

    public void setCriteriaWeight(SimpleStringProperty criteriaWeight) {
        this.criteriaWeight = criteriaWeight;
    }

    


}

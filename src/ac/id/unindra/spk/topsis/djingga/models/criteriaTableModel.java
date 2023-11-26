package ac.id.unindra.spk.topsis.djingga.models;

import javafx.beans.property.SimpleStringProperty;

public class CriteriaTableModel {
    SimpleStringProperty criteriaName, criteriaType,criteriaWeight,gradeWeight;

    public CriteriaTableModel(String criteriaName, String criteriaType, String criteriaWeight, String gradeWeight) {
        this.criteriaName = new SimpleStringProperty(criteriaName);
        this.criteriaType = new SimpleStringProperty(criteriaType);
        this.criteriaWeight = new SimpleStringProperty(criteriaWeight);
        this.gradeWeight = new SimpleStringProperty(gradeWeight);
    }

    

    public String getCriteriaName() {
        return criteriaName.get();
    }

    public void setCriteriaName(SimpleStringProperty criteriaName) {
        this.criteriaName = criteriaName;
    }

    public String getCriteriaType() {
        return criteriaType.get();
    }

    public void setCriteriaType(SimpleStringProperty criteriaType) {
        this.criteriaType = criteriaType;
    }

    public String getCriteriaWeight() {
        return criteriaWeight.get();
    }

    public void setCriteriaWeight(SimpleStringProperty criteriaWeight) {
        this.criteriaWeight = criteriaWeight;
    }


    public String getGradeWeight() {
        return gradeWeight.get();
    }



    public void setGradeWeight(SimpleStringProperty gradeWeight) {
        this.gradeWeight = gradeWeight;
    }

    

    


}

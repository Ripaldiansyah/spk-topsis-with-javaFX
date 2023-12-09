package ac.id.unindra.spk.topsis.djingga.models;

import javafx.beans.property.SimpleStringProperty;

public class TopsisTableListModel {
    SimpleStringProperty id, category, totalCriteriaSelected, date;

    public TopsisTableListModel(String id, String category, String totalCriteriaSelected, String date) {
        this.id = new SimpleStringProperty(id);
        this.category =  new SimpleStringProperty(category);
        this.totalCriteriaSelected =  new SimpleStringProperty(totalCriteriaSelected);
        this.date =  new SimpleStringProperty(date);
    }

    public String getId() {
        return id.get();
    }

    public void setId(SimpleStringProperty id) {
        this.id = id;
    }

    public String getCategory() {
        return category.get();
    }

    public void setCategory(SimpleStringProperty category) {
        this.category = category;
    }

    public String getTotalCriteriaSelected() {
        return totalCriteriaSelected.get();
    }

    public void setTotalCriteriaSelected(SimpleStringProperty totalCriteriaSelected) {
        this.totalCriteriaSelected = totalCriteriaSelected;
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(SimpleStringProperty date) {
        this.date = date;
    }

    
}

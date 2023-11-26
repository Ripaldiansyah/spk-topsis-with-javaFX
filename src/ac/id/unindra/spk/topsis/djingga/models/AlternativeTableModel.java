package ac.id.unindra.spk.topsis.djingga.models;

import javafx.beans.property.SimpleStringProperty;

public class AlternativeTableModel {
    SimpleStringProperty alternativeName,alternativeCategory;

    public AlternativeTableModel(String alternativeName, String alternativeCategory) {
        this.alternativeName = new SimpleStringProperty(alternativeName);
        this.alternativeCategory = new SimpleStringProperty(alternativeCategory);
    }

    public String getAlternativeName() {
        return alternativeName.get();
    }

    public void setAlternativeName(SimpleStringProperty alternativeName) {
        this.alternativeName = alternativeName;
    }

    public String getAlternativeCategory() {
        return alternativeCategory.get();
    }

    public void setAlternativeCategory(SimpleStringProperty alternativeCategory) {
        this.alternativeCategory = alternativeCategory;
    }

    

}

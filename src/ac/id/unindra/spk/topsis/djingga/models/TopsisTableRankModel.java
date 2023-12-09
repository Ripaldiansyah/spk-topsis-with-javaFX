package ac.id.unindra.spk.topsis.djingga.models;

import javafx.beans.property.SimpleStringProperty;

public class TopsisTableRankModel {
    SimpleStringProperty alternativeName, preference, rank;

    

    public TopsisTableRankModel(String alternativeName, String preference, String rank) {
        this.alternativeName = new SimpleStringProperty(alternativeName);
        this.preference = new SimpleStringProperty(preference);
        this.rank = new SimpleStringProperty(rank);
    }

    public String getAlternativeName() {
        return alternativeName.get();
    }

    public void setAlternativeName(SimpleStringProperty alternativeName) {
        this.alternativeName = alternativeName;
    }

    public String getPreference() {
        return preference.get();
    }

    public void setPreference(SimpleStringProperty preference) {
        this.preference = preference;
    }

    public String getRank() {
        return rank.get();
    }

    public void setRank(SimpleStringProperty rank) {
        this.rank = rank;
    }

    
}

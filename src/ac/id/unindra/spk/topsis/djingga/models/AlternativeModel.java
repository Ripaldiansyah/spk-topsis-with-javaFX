package ac.id.unindra.spk.topsis.djingga.models;

public class AlternativeModel {
    String alternativeName, alternativeCategory,alternativeId;
    int totalAlternatif, totalPaginate, activePaginate;

    public int getTotalAlternatif() {
        return totalAlternatif;
    }

    public String getAlternativeId() {
        return alternativeId;
    }

    public void setAlternativeId(String alternativeId) {
        this.alternativeId = alternativeId;
    }

    public int getActivePaginate() {
        return activePaginate;
    }

    public void setActivePaginate(int activePaginate) {
        this.activePaginate = activePaginate;
    }

    public void setTotalAlternatif(int totalAlternatif) {
        this.totalAlternatif = totalAlternatif;
    }

    public int getTotalPaginate() {
        return totalPaginate;
    }

    public void setTotalPaginate(int totalPaginate) {
        this.totalPaginate = totalPaginate;
    }

    public String getAlternativeName() {
        return alternativeName;
    }

    public void setAlternativeName(String alternativeName) {
        this.alternativeName = alternativeName;
    }

    public String getAlternativeCategory() {
        return alternativeCategory;
    }

    public void setAlternativeCategory(String alternativeCategory) {
        this.alternativeCategory = alternativeCategory;
    }

    
}

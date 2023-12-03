package ac.id.unindra.spk.topsis.djingga.models;

public class TopsisModel {
    String category, nameCriteria, idCriteria, typeCriteria, idNormalizedDecisionMatrix, nameAlternative, idAlternative;
    double weightAlternative, matrixNormalize, weightCriteria, matrixNormalizedAndWeighted, max, min,
            positiveIdealValue, negativeIdealValue, preference;

    int rank;

    public String getCategory() {
        return category;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public double getPreference() {
        return preference;
    }

    public void setPreference(double preference) {
        this.preference = preference;
    }

    public double getMax() {
        return max;
    }

    public double getPositiveIdealValue() {
        return positiveIdealValue;
    }

    public void setPositiveIdealValue(double positiveIdealValue) {
        this.positiveIdealValue = positiveIdealValue;
    }

    public double getNegativeIdealValue() {
        return negativeIdealValue;
    }

    public void setNegativeIdealValue(double negativeIdealValue) {
        this.negativeIdealValue = negativeIdealValue;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMatrixNormalizedAndWeighted() {
        return matrixNormalizedAndWeighted;
    }

    public double getWeightAlternative() {
        return weightAlternative;
    }

    public void setWeightAlternative(double weightAlternative) {
        this.weightAlternative = weightAlternative;
    }

    public void setMatrixNormalizedAndWeighted(double matrixNormalizedAndWeighted) {
        this.matrixNormalizedAndWeighted = matrixNormalizedAndWeighted;
    }

    public String getNameAlternative() {
        return nameAlternative;
    }

    public void setNameAlternative(String nameAlternative) {
        this.nameAlternative = nameAlternative;
    }

    public String getIdCriteria() {
        return idCriteria;
    }

    public void setIdCriteria(String idCriteria) {
        this.idCriteria = idCriteria;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNameCriteria() {
        return nameCriteria;
    }

    public void setNameCriteria(String nameCriteria) {
        this.nameCriteria = nameCriteria;
    }

    public String getTypeCriteria() {
        return typeCriteria;
    }

    public void setTypeCriteria(String typeCriteria) {
        this.typeCriteria = typeCriteria;
    }

    public double getMatrixNormalize() {
        return matrixNormalize;
    }

    public void setMatrixNormalize(double matrixNormalize) {
        this.matrixNormalize = matrixNormalize;
    }

    public String getIdNormalizedDecisionMatrix() {
        return idNormalizedDecisionMatrix;
    }

    public void setIdNormalizedDecisionMatrix(String idNormalizedDecisionMatrix) {
        this.idNormalizedDecisionMatrix = idNormalizedDecisionMatrix;
    }

    public String getIdAlternative() {
        return idAlternative;
    }

    public void setIdAlternative(String idAlternative) {
        this.idAlternative = idAlternative;
    }

    public double getWeightCriteria() {
        return weightCriteria;
    }

    public void setWeightCriteria(double weightCriteria) {
        this.weightCriteria = weightCriteria;
    }

}

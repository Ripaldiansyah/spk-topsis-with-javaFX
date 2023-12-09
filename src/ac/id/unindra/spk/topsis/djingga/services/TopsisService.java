package ac.id.unindra.spk.topsis.djingga.services;

import java.util.List;
import java.util.Map;

import ac.id.unindra.spk.topsis.djingga.models.TopsisModel;
import ac.id.unindra.spk.topsis.djingga.models.TopsisTableListModel;
import ac.id.unindra.spk.topsis.djingga.models.TopsisTableRankModel;
import javafx.collections.ObservableList;

public interface TopsisService {
    String[] getData();

    String[] getDataByCategory(TopsisModel topsisModel);

    void getTypeCriteria(TopsisModel topsisModel);

    void setTopsisNormalizedDecisionmatrixAndWeighted(TopsisModel topsisModel);

    void setMaxMinTopsis(TopsisModel topsisModel);

    void setTopsisIdeal(TopsisModel topsisModel);
    void setListTopsis(TopsisModel topsisModel);
    void getIdAlternative(TopsisModel topsisModel);
    void countTopsis(TopsisModel topsisModel);
    void deleteTopsis(TopsisModel topsisModel);

    void setRank(TopsisModel topsisModel);

    List<Double> normalizeAndWeight(String idNormalizedDecisionMatrix, String idAlternative);

    List<Double> getMaxMin(String idNormalizedDecisionMatrix, String idCriteria);

    ObservableList<TopsisTableRankModel> getDataRank(TopsisModel topsisModel);
    ObservableList<TopsisTableListModel> getDataTopsis(TopsisModel topsisModel);

    int totalCriteria(TopsisModel topsisModel);
    int totalAlternative(TopsisModel topsisModel);
    List<Integer> getAlternativeValue(TopsisModel topsisModel); 
    List<Double> getNormalizedDecisionMatrixValue(TopsisModel topsisModel);
    List<Double> getNormalizedAndWeightedDecisionMatrixValue(TopsisModel topsisModel);
    List<Double> getIdealPositive(TopsisModel topsisModel);
    List<Double> getIdealNegative(TopsisModel topsisModel);
    

}

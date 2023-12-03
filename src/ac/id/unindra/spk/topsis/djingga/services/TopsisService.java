package ac.id.unindra.spk.topsis.djingga.services;

import java.util.List;

import ac.id.unindra.spk.topsis.djingga.models.TopsisModel;

public interface TopsisService {
    String[] getData();

    String[] getDataByCategory(TopsisModel topsisModel);

    void getTypeCriteria(TopsisModel topsisModel);

    void setTopsisNormalizedDecisionmatrixAndWeighted(TopsisModel topsisModel);

    void setMaxMinTopsis(TopsisModel topsisModel);

    void getIdAlternative(TopsisModel topsisModel);

    List<Double> normalizeAndWeight(String idNormalizedDecisionMatrix, String idAlternative);

}

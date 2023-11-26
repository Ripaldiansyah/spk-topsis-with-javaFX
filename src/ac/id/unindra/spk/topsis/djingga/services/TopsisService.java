package ac.id.unindra.spk.topsis.djingga.services;

import ac.id.unindra.spk.topsis.djingga.models.TopsisModel;

public interface TopsisService {
    String[] getData();
    String[] getDataByCategory(TopsisModel topsisModel);
    void getTypeCriteria(TopsisModel topsisModel);
    
} 

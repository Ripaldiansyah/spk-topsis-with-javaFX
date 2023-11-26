package ac.id.unindra.spk.topsis.djingga.services;

import ac.id.unindra.spk.topsis.djingga.models.CriteriaModel;
import ac.id.unindra.spk.topsis.djingga.models.CriteriaTableModel;
import javafx.collections.ObservableList;

public interface CriteriaService {
    boolean checkRegistered(CriteriaModel criteriaModel);
    void insertCriteria(CriteriaModel criteriaModel);
    void countCriteria(CriteriaModel criteriaModel);
    ObservableList<CriteriaTableModel> getDataCriteria(CriteriaModel criteriaModel);
    void deleteCriteria(CriteriaModel criteriaModel);
    void getCriteria(CriteriaModel criteriaModel);
    void updateCriteria(CriteriaModel criteriaModel);
}
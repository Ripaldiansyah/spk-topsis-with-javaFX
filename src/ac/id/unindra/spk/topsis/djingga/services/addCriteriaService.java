package ac.id.unindra.spk.topsis.djingga.services;

import ac.id.unindra.spk.topsis.djingga.models.addCriteriaModel;

public interface addCriteriaService {
    boolean checkRegistered(addCriteriaModel addCriteriaModel);
    void insertCriteria(addCriteriaModel addCriteriaModel);
    void countCriteria(addCriteriaModel addCriteriaModel);
}
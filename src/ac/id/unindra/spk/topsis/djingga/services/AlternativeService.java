package ac.id.unindra.spk.topsis.djingga.services;

import ac.id.unindra.spk.topsis.djingga.models.AlternativeModel;
import ac.id.unindra.spk.topsis.djingga.models.AlternativeTableModel;
import javafx.collections.ObservableList;

public interface AlternativeService {
    boolean checkAlternative(AlternativeModel alternativeModel);
    void insertAlternative(AlternativeModel alternativeModel);
    void updateAlternative(AlternativeModel alternativeModel);
    void deleteAlternative(AlternativeModel alternativeModel);
    void countAlternative(AlternativeModel alternativeModel);
    void getId(AlternativeModel alternativeModel);
    ObservableList<AlternativeTableModel> getDataCriteria(AlternativeModel alternativeModel);
}

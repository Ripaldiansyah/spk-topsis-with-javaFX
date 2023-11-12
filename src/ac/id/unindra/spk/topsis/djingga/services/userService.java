package ac.id.unindra.spk.topsis.djingga.services;

import java.util.List;

import ac.id.unindra.spk.topsis.djingga.models.userModel;
import ac.id.unindra.spk.topsis.djingga.models.userTableModel;
import javafx.collections.ObservableList;

public interface userService {
    ObservableList<userTableModel> getDataUser(userModel userModel);
    void updateUserRole(userModel userModel);
    void updateUserStatus(userModel userModel);
    void deleteUser(userModel userModel);
    void countData(userModel userModel);
    
}
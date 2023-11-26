package ac.id.unindra.spk.topsis.djingga.services;


import ac.id.unindra.spk.topsis.djingga.models.UserModel;
import ac.id.unindra.spk.topsis.djingga.models.UserTableModel;
import javafx.collections.ObservableList;

public interface UserService {
    ObservableList<UserTableModel> getDataUser(UserModel userModel);
    void updateUserRole(UserModel userModel);
    void updateUserStatus(UserModel userModel);
    void deleteUser(UserModel userModel);
    void countData(UserModel userModel);
    
}
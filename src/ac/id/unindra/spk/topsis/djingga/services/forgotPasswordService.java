package ac.id.unindra.spk.topsis.djingga.services;


import ac.id.unindra.spk.topsis.djingga.models.forgotPasswordModel;
import ac.id.unindra.spk.topsis.djingga.models.loginModel;

public interface forgotPasswordService {
    void searchAccount(forgotPasswordModel forgotPasswordModel, loginModel loginModel );
    void resetPassword(forgotPasswordModel forgotPasswordModel);
}

package ac.id.unindra.spk.topsis.djingga.services;

import ac.id.unindra.spk.topsis.djingga.models.ForgotPasswordModel;
import ac.id.unindra.spk.topsis.djingga.models.LoginModel;

public interface ForgotPasswordService {
    void searchAccount(ForgotPasswordModel forgotPasswordModel, LoginModel loginModel);

    void resetPassword(ForgotPasswordModel forgotPasswordModel);
}

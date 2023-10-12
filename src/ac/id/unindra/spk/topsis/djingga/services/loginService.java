package ac.id.unindra.spk.topsis.djingga.services;

import ac.id.unindra.spk.topsis.djingga.models.loginModel;

public interface loginService {
    void processLogin(loginModel loginModel);
    void processRegistration(loginModel loginModel);
    void sendOTP(loginModel loginModel);
    boolean checkUsernameRegistered(loginModel loginModel);
}

package ac.id.unindra.spk.topsis.djingga.services;

import ac.id.unindra.spk.topsis.djingga.models.OTPModel;
import ac.id.unindra.spk.topsis.djingga.models.LoginModel;
import ac.id.unindra.spk.topsis.djingga.models.RegisterModel;

public interface OTPService {
    void checkOTP(OTPModel OTPModel, RegisterModel registerModel);
    void setOTP(OTPModel OTPModel, boolean resetOTP);
    void resendOTP(LoginModel loginModel);
    void sendOTP(RegisterModel registerModel, OTPModel OTPModel);
    void destroyOTP(OTPModel OTPModel);
    
}

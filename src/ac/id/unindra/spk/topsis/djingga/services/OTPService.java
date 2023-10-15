package ac.id.unindra.spk.topsis.djingga.services;

import ac.id.unindra.spk.topsis.djingga.models.OTPModel;
import ac.id.unindra.spk.topsis.djingga.models.loginModel;
import ac.id.unindra.spk.topsis.djingga.models.registerModel;

public interface OTPService {
    void checkOTP(OTPModel OTPModel, registerModel registerModel);
    void setOTP(OTPModel OTPModel, boolean resetOTP);
    void resendOTP(loginModel loginModel);
    void sendOTP(registerModel registerModel, OTPModel OTPModel);
    
}

package ac.id.unindra.spk.topsis.djingga.services;

import ac.id.unindra.spk.topsis.djingga.models.OTPModel;
import ac.id.unindra.spk.topsis.djingga.models.RegisterModel;

public interface RegisterService {
    void processRegistration(RegisterModel registerModel, OTPModel OTPModel);
    boolean checkUsernameRegistered(RegisterModel registerModel);
}

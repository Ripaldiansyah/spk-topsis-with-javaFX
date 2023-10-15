package ac.id.unindra.spk.topsis.djingga.services;

import ac.id.unindra.spk.topsis.djingga.models.OTPModel;
import ac.id.unindra.spk.topsis.djingga.models.registerModel;

public interface registerService {
    void processRegistration(registerModel registerModel, OTPModel OTPModel);
    boolean checkUsernameRegistered(registerModel registerModel);
}

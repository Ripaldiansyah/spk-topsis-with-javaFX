package ac.id.unindra.spk.topsis.djingga.utilities;

import java.security.SecureRandom;

public class OTPGenerator {
    private static final String ALLOWED_CHARACTERS = "0123456789";
    
    public static String generateOTP(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder(length);
        
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(ALLOWED_CHARACTERS.length());
            char randomChar = ALLOWED_CHARACTERS.charAt(randomIndex);
            otp.append(randomChar);
        }
        
        return otp.toString();
    }
    
    public static boolean validateOTP(String storedOTP, String enteredOTP, long validityPeriodMillis) {
        // Lakukan validasi di sini, periksa apakah OTP cocok dan belum kadaluarsa.
        // Anda perlu membandingkan storedOTP dan enteredOTP,
        // serta memeriksa waktu kadaluwarsa dengan membandingkan dengan validityPeriodMillis.
        return storedOTP.equals(enteredOTP) && System.currentTimeMillis() <= validityPeriodMillis;
    }
}


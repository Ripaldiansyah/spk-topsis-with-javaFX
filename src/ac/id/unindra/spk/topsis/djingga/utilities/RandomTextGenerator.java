package ac.id.unindra.spk.topsis.djingga.utilities;

import java.util.Random;

public class RandomTextGenerator {
    private static final String ALLOWED_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String generateRandomText(int length) {
        if (length < 1) {
            throw new IllegalArgumentException("Length should be at least 1");
        }

        StringBuilder randomText = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(ALLOWED_CHARACTERS.length());
            char randomChar = ALLOWED_CHARACTERS.charAt(randomIndex);
            randomText.append(randomChar);
        }

        return randomText.toString();
    }
    
}

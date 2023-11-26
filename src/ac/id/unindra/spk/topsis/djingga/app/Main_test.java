package ac.id.unindra.spk.topsis.djingga.app;

import java.util.ArrayList;
import java.util.List;

public class Main_test {
    public static void main(String[] args) {
        // Contoh data
        String data = "422421"; // Data berupa substring

        // Ukuran setiap bagian (misalnya, 3)
        int blockSize = 3;

        // Hitung jumlah elemen yang akan dihasilkan
        int numElements = data.length() / blockSize;

        // Hitung akar kuadrat untuk setiap elemen dalam substring
        for (int i = 0; i < numElements; i++) {
            // Ambil substring sesuai dengan indeks i dan blockSize
            String substring = data.substring(i * blockSize, (i + 1) * blockSize);
        
            // Hitung akar kuadrat dari setiap elemen dalam substring
            double totalAkarKuadrat = 0;
            for (char digitChar : substring.toCharArray()) {
                int digit = Character.getNumericValue(digitChar);
                totalAkarKuadrat += Math.pow(digit, 2);
            }

            // Hitung akar kuadrat dari total
            double akarKuadratTotal = Math.sqrt(totalAkarKuadrat);

            // Tampilkan hasil
            System.out.println( akarKuadratTotal);
        }
    }
}


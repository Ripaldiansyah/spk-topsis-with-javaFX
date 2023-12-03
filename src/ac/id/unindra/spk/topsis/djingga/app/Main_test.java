package ac.id.unindra.spk.topsis.djingga.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main_test {
    public static void main(String[] args) {
        // Contoh List<Double>
        List<Double> nilaiList = new ArrayList<>();
        nilaiList.add(85.5);
        nilaiList.add(92.0);
        nilaiList.add(78.3);
        nilaiList.add(95.8);
        nilaiList.add(88.2);

        // Membuat salinan List untuk diurutkan
        List<Double> sortedList = new ArrayList<>(nilaiList);
        Collections.sort(sortedList, Collections.reverseOrder()); // Mengurutkan secara descending

        // Membuat Map untuk menyimpan peringkat
        Map<Double, Integer> peringkatMap = new HashMap<>();
        for (int i = 0; i < sortedList.size(); i++) {
            System.out.println(sortedList.get(i));
            peringkatMap.put(sortedList.get(i), i + 1);
        }

        // Menampilkan hasil perangkingan
        System.out.println("Nilai awal: " + nilaiList);
        System.out.println("Perangkingan: " + peringkatMap);
    }
}

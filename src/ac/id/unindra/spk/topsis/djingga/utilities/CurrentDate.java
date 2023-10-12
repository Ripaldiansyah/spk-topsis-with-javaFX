package ac.id.unindra.spk.topsis.djingga.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CurrentDate {
     public static String date(String result) {
        
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
        String formattedDate = dateFormat.format(currentDate);
        return result = formattedDate;
    }
}

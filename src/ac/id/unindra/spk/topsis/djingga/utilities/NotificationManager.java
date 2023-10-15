package ac.id.unindra.spk.topsis.djingga.utilities;

import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class NotificationManager {
    public static void notification(String title, String message){
                    Notifications notification = Notifications.create();
                    notification.title(title);
                    notification.text(message);
                    notification.hideAfter(Duration.seconds(5));
                    notification.position(Pos.BASELINE_RIGHT);
                    notification.show();
     }
}

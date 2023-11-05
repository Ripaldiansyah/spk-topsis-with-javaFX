package ac.id.unindra.spk.topsis.djingga.utilities;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

public class EmailSender {
    public static void sendOTP(String emailRecipent, String OTPStored) {
        final String username = "Ripaldiansyah13@gmail.com";
        final String password = "";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "127.0.0.1");
        props.put("mail.smtp.port", "1025");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailRecipent));
            message.setSubject("OTP SPK DJINGGA");
            message.setText("Berikut adalah kode OTP Anda " + OTPStored
                    + " Valid dalam waktu 5 menit, jangan berikan kode OTP Anda kesiapapun");

            Transport.send(message);
            System.out.println("Email terkirim");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

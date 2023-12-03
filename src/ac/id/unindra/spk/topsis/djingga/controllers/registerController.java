package ac.id.unindra.spk.topsis.djingga.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import ac.id.unindra.spk.topsis.djingga.models.OTPModel;
import ac.id.unindra.spk.topsis.djingga.models.RegisterModel;
import ac.id.unindra.spk.topsis.djingga.services.OTPService;
import ac.id.unindra.spk.topsis.djingga.utilities.DatabaseConnection;
import ac.id.unindra.spk.topsis.djingga.utilities.NotificationManager;
import ac.id.unindra.spk.topsis.djingga.utilities.OTPGenerator;
import ac.id.unindra.spk.topsis.djingga.services.RegisterService;

public class RegisterController implements RegisterService {
    private Connection conn = new DatabaseConnection().getConnection();
    private NotificationManager notificationManager = new NotificationManager();
    OTPService OTPService = new OTPController();

    @Override
    public void processRegistration(RegisterModel registerModel, OTPModel OTPModel) {
        PreparedStatement stat = null;
        String sql = "INSERT INTO pengguna(idPengguna,namaLengkap,email,namaPengguna,kataSandi,level,statusAkun,idOTP)VALUES(?,?,?,?,?,?,?,?)";
        try {
            if (checkUsernameRegistered(registerModel)) {
                stat = conn.prepareStatement(sql);

                stat.setString(1, registerModel.getIdUser());
                stat.setString(2, registerModel.getFullName());
                stat.setString(3, registerModel.getEmail());
                stat.setString(4, registerModel.getUsername());
                stat.setString(5, registerModel.getPassword());
                stat.setString(6, registerModel.getRole());
                stat.setString(7, registerModel.getAccountStatus());
                stat.setString(8, OTPModel.getIdOTP());

                stat.executeUpdate();

                String storedOTP = OTPGenerator.generateOTP(6);
                String idOTP = OTPModel.getIdOTP();

                OTPModel.setStoredOTP(storedOTP);
                OTPModel.setIdOTP(idOTP);

                OTPService.setOTP(OTPModel, false);
                OTPService.sendOTP(registerModel, OTPModel);
                LoginViewController loginViewController = new LoginViewController();
                loginViewController.runPane = true;
                loginViewController.idUser = registerModel.getIdUser();

                notificationManager.notification("Berhasil", "Akun Anda berhasil dibuat");
            } else {
                LoginViewController.runPane = false;
                notificationManager.notification("Peringatan", "Username Telah Terdaftar");
            }

        } catch (Exception e) {
            System.err.println(e);
        } finally {
            if (stat != null) {
                try {
                    stat.close();
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        }
    }

    @Override
    public boolean checkUsernameRegistered(RegisterModel registerModel) {
        PreparedStatement stat = null;
        ResultSet rs = null;
        boolean usernameIsValid = false;
        String sql = "SELECT COUNT(*) AS count FROM pengguna WHERE LOWER(namaPengguna) = LOWER(?)";

        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, registerModel.getUsername());
            rs = stat.executeQuery();

            if (rs.next()) {
                int count = rs.getInt("count");
                if (count > 0) {
                    usernameIsValid = false;
                } else {
                    usernameIsValid = true;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (stat != null) {
                try {
                    stat.close();
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        }
        return usernameIsValid;
    }

}

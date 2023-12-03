package ac.id.unindra.spk.topsis.djingga.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import ac.id.unindra.spk.topsis.djingga.models.LoginModel;
import ac.id.unindra.spk.topsis.djingga.services.OTPService;
import ac.id.unindra.spk.topsis.djingga.utilities.DatabaseConnection;
import ac.id.unindra.spk.topsis.djingga.utilities.NotificationManager;
import at.favre.lib.crypto.bcrypt.BCrypt;
import ac.id.unindra.spk.topsis.djingga.services.LoginService;

public class LoginController implements LoginService {
    private Connection conn = new DatabaseConnection().getConnection();
    private NotificationManager notificationManager = new NotificationManager();
    OTPService OTPService = new OTPController();

    @Override
    public void processLogin(LoginModel loginModel) {
        String sql = "SELECT * FROM pengguna WHERE LOWER(namaPengguna)=LOWER(?)";
        PreparedStatement stat = null;
        ResultSet rs = null;

        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, loginModel.getUsername());
            rs = stat.executeQuery();

            if (rs.next()) {

                BCrypt.Result result = BCrypt.verifyer().verify(loginModel.getPassword().toCharArray(),
                        rs.getString("kataSandi"));
                if (result.verified) {
                    loginModel.setIdUser(rs.getString("idPengguna"));
                    loginModel.setFullName(rs.getString("namaLengkap"));
                    loginModel.setRole(rs.getString("level"));
                    loginModel.setStatus(rs.getString("statusAkun"));
                    loginModel.setIdOTP(rs.getString("idOTP"));
                    loginModel.setEmail(rs.getString("email"));
                    if (loginModel.getStatus().equalsIgnoreCase("pending")) {

                        OTPService.resendOTP(loginModel);
                        LoginViewController loginViewController = new LoginViewController();
                        loginViewController.runPane = true;
                        loginViewController.idUser = loginModel.getIdUser();
                    } else {
                        LoginViewController.main = true;
                    }
                } else {
                    notificationManager.notification("Peringatan", "Periksa Kembali password Anda");
                    stat.close();
                }

            } else {
                notificationManager.notification("Tidak Dapat Masuk", "Nama Pengguna Tidak terdaftar");
                LoginViewController.runPane = false;
                stat.close();
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

}

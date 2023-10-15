package ac.id.unindra.spk.topsis.djingga.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import ac.id.unindra.spk.topsis.djingga.models.OTPModel;
import ac.id.unindra.spk.topsis.djingga.models.loginModel;
import ac.id.unindra.spk.topsis.djingga.models.registerModel;
import ac.id.unindra.spk.topsis.djingga.services.OTPService;
import ac.id.unindra.spk.topsis.djingga.services.loginService;
import ac.id.unindra.spk.topsis.djingga.utilities.DatabaseConnection;
import ac.id.unindra.spk.topsis.djingga.utilities.NotificationManager;
import at.favre.lib.crypto.bcrypt.BCrypt;

public class loginController implements loginService {
    private Connection conn = new DatabaseConnection().getConnection();
    private NotificationManager notificationManager = new NotificationManager();

    @Override
    public void processLogin(loginModel loginModel) {
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
                        OTPService OTPService = new OTPController();
                        OTPService.resendOTP(loginModel);
                        loginViewController loginViewController = new loginViewController();
                        loginViewController.runPane = true;
                        loginViewController.idUser = loginModel.getIdUser();
                    } else {
                        notificationManager.notification("Berhasil Masuk",
                                "Selamat Datang " + loginModel.getFullName());
                                  loginViewController.runPane = false;
                    }
                } else {
                    notificationManager.notification("Peringatan", "Periksa Kembali password Anda");
                }

            } else {
                notificationManager.notification("Tidak Dapat Masuk", "Nama Pengguna Tidak terdaftar");
            }

        } catch (Exception e) {
            System.err.println("e");
        }

    }

}

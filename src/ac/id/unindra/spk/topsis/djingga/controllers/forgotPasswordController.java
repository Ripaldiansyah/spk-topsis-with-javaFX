package ac.id.unindra.spk.topsis.djingga.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ac.id.unindra.spk.topsis.djingga.models.ForgotPasswordModel;
import ac.id.unindra.spk.topsis.djingga.models.LoginModel;
import ac.id.unindra.spk.topsis.djingga.services.OTPService;
import ac.id.unindra.spk.topsis.djingga.utilities.DatabaseConnection;
import ac.id.unindra.spk.topsis.djingga.utilities.NotificationManager;
import ac.id.unindra.spk.topsis.djingga.services.ForgotPasswordService;

public class ForgotPasswordController implements ForgotPasswordService {
    private Connection conn = new DatabaseConnection().getConnection();
    OTPService OTPService = new OTPController();

    @Override
    public void searchAccount(ForgotPasswordModel forgotPasswordModel, LoginModel loginModel) {
        String sql = "SELECT idPengguna, idOTP FROM pengguna WHERE LOWER(namaPengguna) = LOWER(?) AND LOWER(email) = LOWER(?)";
        PreparedStatement stat = null;
        ResultSet rs = null;

        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, forgotPasswordModel.getUsername());
            stat.setString(2, forgotPasswordModel.getEmail());
            rs = stat.executeQuery();

            if (rs.next()) {
                forgotPasswordModel.setIdUser(rs.getString("idPengguna"));
                forgotPasswordModel.setIdOTP(rs.getString("idOTP"));
                loginModel.setIdOTP(forgotPasswordModel.getIdOTP());
                loginModel.setEmail(forgotPasswordModel.getEmail());
                LoginViewController.idUserResetPassword = forgotPasswordModel.getIdUser();

                OTPService.resendOTP(loginModel);
                LoginViewController.runPane = true;

            } else {
                LoginViewController.runPane = false;
                NotificationManager.notification("Peringatan", "Akun tidak ditemukan");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stat != null) {
                    stat.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
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

    @Override
    public void resetPassword(ForgotPasswordModel forgotPasswordModel) {
        PreparedStatement stat = null;
        String sql = "UPDATE pengguna SET kataSandi = ? WHERE idPengguna = ?";
        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, forgotPasswordModel.getPassword());
            stat.setString(2, forgotPasswordModel.getIdUser());
            stat.executeUpdate();
            NotificationManager.notification("Berhasil", "Kata Sandi sudah diperbarui");
            LoginViewController.resetPass = true;
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

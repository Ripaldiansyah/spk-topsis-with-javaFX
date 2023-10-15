package ac.id.unindra.spk.topsis.djingga.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ac.id.unindra.spk.topsis.djingga.models.OTPModel;
import ac.id.unindra.spk.topsis.djingga.models.loginModel;
import ac.id.unindra.spk.topsis.djingga.models.registerModel;
import ac.id.unindra.spk.topsis.djingga.services.OTPService;
import ac.id.unindra.spk.topsis.djingga.utilities.DatabaseConnection;
import ac.id.unindra.spk.topsis.djingga.utilities.EmailSender;
import ac.id.unindra.spk.topsis.djingga.utilities.NotificationManager;
import ac.id.unindra.spk.topsis.djingga.utilities.OTPGenerator;

public class OTPController implements OTPService {
    private Connection conn = new DatabaseConnection().getConnection();
    OTPService OTPService = this;

    @Override
    public void checkOTP(OTPModel OTPModel, registerModel registerModel) {
        PreparedStatement stat = null;
        String sql = "SELECT pengguna.namaLengkap, OTP.OTPVerifikasi1 FROM pengguna INNER JOIN OTP ON pengguna.idOTP = OTP.idOTP WHERE pengguna.idPengguna = ?";
        ResultSet rs = null;

        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, registerModel.getIdUser());
            rs = stat.executeQuery();

            if (rs.next()) {
                registerModel.setFullName(rs.getString("namaLengkap"));
                OTPModel.setStoredOTP(rs.getString("OTPVerifikasi1"));

                if (OTPModel.getStoredOTP().equalsIgnoreCase(OTPModel.getEnteredOTP())) {
                    String sqlUpdate = "UPDATE pengguna SET statusAkun = 'Active' WHERE idPengguna=?";
                    stat.close();
                    try {
                        stat = conn.prepareStatement(sqlUpdate);
                        stat.setString(1, registerModel.getIdUser());
                        stat.executeUpdate();
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
                } else {
                    NotificationManager.notification("Peringatan", "Kode OTP yang dimasukan tidak sesuai");
                }

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
            }
        }

    }

    @Override
    public void setOTP(OTPModel OTPModel, boolean resetOTP) {
        PreparedStatement stat = null;
        String sqlInsert = "INSERT INTO OTP(idOTP,OTPVerifikasi1)VALUES(?,?)";
        String sqlUpdate = "UPDATE OTP SET OTPVerifikasi1 = ? WHERE idOTP =  ?";
        try {
            if (resetOTP) {
                stat = conn.prepareStatement(sqlUpdate);
                stat.setString(1, OTPModel.getStoredOTP());
                stat.setString(2, OTPModel.getIdOTP());
            } else {
                stat = conn.prepareStatement(sqlInsert);
                stat.setString(1, OTPModel.getIdOTP());
                stat.setString(2, OTPModel.getStoredOTP());
            }

            stat.executeUpdate();
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
    public void resendOTP(loginModel loginModel) {
        OTPModel OTPModel = new OTPModel();

        String storedOTP = OTPGenerator.generateOTP(6);
        String idOTP = loginModel.getIdOTP();
        EmailSender.sendOTP(loginModel.getEmail(), storedOTP);

        OTPModel.setStoredOTP(storedOTP);
        OTPModel.setIdOTP(idOTP);

        OTPService.setOTP(OTPModel, true);
    }

    @Override
    public void sendOTP(registerModel registerModel, OTPModel OTPModel) {
        String sql = "SELECT pengguna.email, OTP.OTPVerifikasi1 FROM pengguna INNER JOIN OTP ON pengguna.idOTP = OTP.idOTP WHERE pengguna.idPengguna = ?";
        PreparedStatement stat = null;
        ResultSet rs = null;

        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, registerModel.getIdUser());
            rs = stat.executeQuery();

            if (rs.next()) {
                registerModel.setEmail(rs.getString("email"));
                OTPModel.setStoredOTP(rs.getString("OTPVerifikasi1"));
                EmailSender.sendOTP(registerModel.getEmail(), OTPModel.getStoredOTP());
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
            }
        }
    }

}
package ac.id.unindra.spk.topsis.djingga.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import ac.id.unindra.spk.topsis.djingga.models.userModel;
import ac.id.unindra.spk.topsis.djingga.models.userTableModel;
import ac.id.unindra.spk.topsis.djingga.services.userService;
import ac.id.unindra.spk.topsis.djingga.utilities.DatabaseConnection;
import ac.id.unindra.spk.topsis.djingga.utilities.NotificationManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class userController implements userService {

    public static final ObservableList<userTableModel> ObservableList = null;
    private Connection conn = new DatabaseConnection().getConnection();
    userModel userModel = new userModel();

    @Override
    public ObservableList<userTableModel> getDataUser(userModel userModel) {
        String sql = "SELECT * FROM Pengguna LIMIT 12 OFFSET ?";
        PreparedStatement stat = null;
        int fetchingData = userModel.getActivePaginate();
        ResultSet rs = null;

        try {
            stat = conn.prepareStatement(sql);
            stat.setInt(1, fetchingData);

            rs = stat.executeQuery();
            ObservableList<userTableModel> userData = FXCollections.observableArrayList();
            while (rs.next()) {
                userTableModel user = new userTableModel(
                        rs.getString("idPengguna"),
                        rs.getString("namaLengkap"),
                        rs.getString("namaPengguna"),
                        rs.getString("level"),
                        rs.getString("statusAkun"));

                userData.add(user);
            }
            return userData;
        } catch (Exception e) {
            System.err.println(e);
            return null;
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
    public void updateUserRole(userModel userModel) {
        PreparedStatement stat = null;
        String sqlSetAdmin = "UPDATE pengguna SET level = 'Admin' Where idPengguna = ?";
        String sqlSetUser = "UPDATE pengguna SET level = 'User' Where idPengguna = ?";

        try {
            if (userModel.getRole().equalsIgnoreCase("Admin")) {
                stat = conn.prepareStatement(sqlSetAdmin);
                stat.setString(1, userModel.getId());
               
            } else {
                stat = conn.prepareStatement(sqlSetUser);
                stat.setString(1, userModel.getId());
                
            }
            stat.executeUpdate();
            NotificationManager.notification("Perubahan disimpan", "Akun diatur "+userModel.getRole());
            
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
    public void deleteUser(userModel userModel) {
        PreparedStatement stat = null;
        String sql = "DELETE FROM pengguna WHERE idPengguna = ?";
        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, userModel.getId());
            stat.executeUpdate();
            NotificationManager.notification("Berhasil dihapus", "Akun telah dihapus");
            
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
    public void countData(userModel userModel) {
        String sql = "SELECT  COUNT(*) AS total_pengguna,\r\n" + //
                "    SUM(CASE WHEN level = 'Admin' THEN 1 ELSE 0 END) AS jumlah_admin,\r\n" + //
                "    SUM(CASE WHEN level = 'User' THEN 1 ELSE 0 END) AS jumlah_user,\r\n" + //
                "    SUM(CASE WHEN statusAkun = 'Active' THEN 1 ELSE 0 END) AS jumlah_active,\r\n" + //
                "    SUM(CASE WHEN statusAkun = 'Pending' THEN 1 ELSE 0 END) AS jumlah_pending FROM Pengguna";
        PreparedStatement stat = null;
        ResultSet rs = null;

        try {
            stat = conn.prepareStatement(sql);
            rs = stat.executeQuery();

            if (rs.next()) {
                userModel.setTotalAccout(rs.getInt("total_pengguna"));
                userModel.setTotalAdmin(rs.getInt("jumlah_admin"));
                userModel.setTotalUser(rs.getInt("jumlah_user"));
                userModel.setTotalActive(rs.getInt("jumlah_active"));
                userModel.setTotalPending(rs.getInt("jumlah_pending"));

                if (userModel.getTotalAccout() < 12) {
                    userModel.setTotalPaginate(1);
                } else {
                    userModel.setTotalPaginate((int) Math.ceil((double) userModel.getTotalAccout() / 12.0));
                }
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
    public void updateUserStatus(userModel userModel) {
        PreparedStatement stat = null;
        String sqlSetActive = "UPDATE pengguna SET statusAkun = 'Active' Where idPengguna = ?";
        String sqlSetPending = "UPDATE pengguna SET statusAkun = 'Pending' Where idPengguna = ?";

        try {
            if (userModel.getAccountStatus().equalsIgnoreCase("Active")) {
                stat = conn.prepareStatement(sqlSetActive);
                stat.setString(1, userModel.getId());
               
            } else {
                stat = conn.prepareStatement(sqlSetPending);
                stat.setString(1, userModel.getId());
                
            }
            stat.executeUpdate();
            NotificationManager.notification("Perubahan disimpan", "Akun diatur "+userModel.getAccountStatus());
            
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

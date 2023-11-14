package ac.id.unindra.spk.topsis.djingga.controllers;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import ac.id.unindra.spk.topsis.djingga.models.OTPModel;
import ac.id.unindra.spk.topsis.djingga.models.addCriteriaModel;
import ac.id.unindra.spk.topsis.djingga.services.OTPService;
import ac.id.unindra.spk.topsis.djingga.services.addCriteriaService;
import ac.id.unindra.spk.topsis.djingga.utilities.DatabaseConnection;
import ac.id.unindra.spk.topsis.djingga.utilities.NotificationManager;
import ac.id.unindra.spk.topsis.djingga.utilities.OTPGenerator;

public class addCriteriaController implements addCriteriaService {
    private Connection conn = new DatabaseConnection().getConnection();
    private NotificationManager notificationManager = new NotificationManager();

    @Override
    public boolean checkRegistered(addCriteriaModel addCriteriaModel) {
        PreparedStatement stat = null;
        ResultSet rs = null;
        boolean criteriaNameRegistered = false;
        String sql = "SELECT COUNT(*) AS count FROM bobotKriteria WHERE LOWER(namaKriteria) = LOWER(?)";

        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, addCriteriaModel.getCriteriaName());
            rs = stat.executeQuery();

            if (rs.next()) {
                int count = rs.getInt("count");
                if (count > 0) {
                    criteriaNameRegistered = true;
                } else {
                    criteriaNameRegistered = false;
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
        return criteriaNameRegistered;
    }

    @Override
    public void insertCriteria(addCriteriaModel addCriteriaModel) {
    PreparedStatement statBobotKriteria = null;
    PreparedStatement statKriteriaPenilaian = null;

    String insertBobotKriteria = "INSERT INTO bobotKriteria (namaKriteria, jenisKriteria, bobotNilaiKriteria, idKriteriaPenilaian) VALUES (?, ?, ?, ?)";
    String insertKriteriaPenilaian = "INSERT INTO kriteriaPenilaian ( nilai1, nilai2, nilai3, nilai4, nilai5) VALUES ( ?, ?, ?, ?, ?)";

    try {
       
        statKriteriaPenilaian = conn.prepareStatement(insertKriteriaPenilaian, new String[]{"idBobotKriteria"});
        statKriteriaPenilaian.setString(1, addCriteriaModel.getCriteria1());
        statKriteriaPenilaian.setString(2, addCriteriaModel.getCriteria2());
        statKriteriaPenilaian.setString(3, addCriteriaModel.getCriteria3());
        statKriteriaPenilaian.setString(4, addCriteriaModel.getCriteria4());
        statKriteriaPenilaian.setString(5, addCriteriaModel.getCriteria5());
        statKriteriaPenilaian.executeUpdate();

        ResultSet generatedKeys = statKriteriaPenilaian.getGeneratedKeys();
        int idKriteriaPenilaian = -1;
        if (generatedKeys.next()) {
            idKriteriaPenilaian = generatedKeys.getInt(1);
        }

        statBobotKriteria = conn.prepareStatement(insertBobotKriteria);
        statBobotKriteria.setString(1, addCriteriaModel.getCriteriaName());
        statBobotKriteria.setString(2, addCriteriaModel.getCriteriaType());
        statBobotKriteria.setString(3, addCriteriaModel.getValueWeight());
        statBobotKriteria.setInt(4, idKriteriaPenilaian);
        statBobotKriteria.executeUpdate();

      
        notificationManager.notification("Berhasil", "Kriteria " + addCriteriaModel.getCriteriaName() + " telah ditambahkan");
        addCriteriaViewContoller.runPane=true;
    } catch (Exception e) {
        System.err.println(e);
    } finally {
        // Close the prepared statements
        if (statBobotKriteria != null) {
            try {
                statBobotKriteria.close();
            } catch (Exception e) {
                System.err.println(e);
            }
        }

        if (statKriteriaPenilaian != null) {
            try {
                statKriteriaPenilaian.close();
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }
}

    @Override
    public void countCriteria(addCriteriaModel addCriteriaModel) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'countCriteria'");
    }

}

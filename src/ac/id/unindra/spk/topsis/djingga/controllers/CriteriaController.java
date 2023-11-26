package ac.id.unindra.spk.topsis.djingga.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import ac.id.unindra.spk.topsis.djingga.models.CriteriaModel;
import ac.id.unindra.spk.topsis.djingga.models.CriteriaTableModel;

import ac.id.unindra.spk.topsis.djingga.utilities.DatabaseConnection;
import ac.id.unindra.spk.topsis.djingga.utilities.NotificationManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ac.id.unindra.spk.topsis.djingga.services.CriteriaService;

public class CriteriaController implements CriteriaService {
    private Connection conn = new DatabaseConnection().getConnection();
    private NotificationManager notificationManager = new NotificationManager();
    String oldCriteriaName;

    @Override
    public boolean checkRegistered(CriteriaModel criteriaModel) {
        PreparedStatement stat = null;
        ResultSet rs = null;
        boolean criteriaNameRegistered = false;
        String sql = "SELECT COUNT(*) AS count FROM bobotKriteria WHERE LOWER(namaKriteria) = LOWER(?)";

        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, criteriaModel.getCriteriaName());
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
    public void insertCriteria(CriteriaModel criteriaModel) {
        PreparedStatement statBobotKriteria = null;
        PreparedStatement statKriteriaPenilaian = null;

        String insertBobotKriteria = "INSERT INTO bobotKriteria (namaKriteria, jenisKriteria, bobotNilaiKriteria, bobot, idKriteriaPenilaian) VALUES (?, ?, ?, ?, ?)";
        String insertKriteriaPenilaian = "INSERT INTO kriteriaPenilaian ( nilai1, nilai2, nilai3, nilai4, nilai5) VALUES ( ?, ?, ?, ?, ?)";

        try {

            statKriteriaPenilaian = conn.prepareStatement(insertKriteriaPenilaian, new String[] { "idBobotKriteria" });
            statKriteriaPenilaian.setString(1, criteriaModel.getCriteria1());
            statKriteriaPenilaian.setString(2, criteriaModel.getCriteria2());
            statKriteriaPenilaian.setString(3, criteriaModel.getCriteria3());
            statKriteriaPenilaian.setString(4, criteriaModel.getCriteria4());
            statKriteriaPenilaian.setString(5, criteriaModel.getCriteria5());
            statKriteriaPenilaian.executeUpdate();

            ResultSet generatedKeys = statKriteriaPenilaian.getGeneratedKeys();
            int idKriteriaPenilaian = -1;
            if (generatedKeys.next()) {
                idKriteriaPenilaian = generatedKeys.getInt(1);
            }

            statBobotKriteria = conn.prepareStatement(insertBobotKriteria);
            statBobotKriteria.setString(1, criteriaModel.getCriteriaName());
            statBobotKriteria.setString(2, criteriaModel.getCriteriaType());
            statBobotKriteria.setString(3, criteriaModel.getValueWeight());
            statBobotKriteria.setInt(4, weight(criteriaModel));
            statBobotKriteria.setInt(5, idKriteriaPenilaian);
            statBobotKriteria.executeUpdate();

            notificationManager.notification("Berhasil",
                    "Kriteria " + criteriaModel.getCriteriaName() + " telah ditambahkan");
            AddCriteriaViewContoller.runPane = true;
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

    private int weight(CriteriaModel criteriaModel) {
        int bobot;
        switch (criteriaModel.getValueWeight()) {
            case "Tidak Penting":
                bobot = 1;
                break;
            case "Kurang Penting":
                bobot = 2;
                break;
            case "Cukup Penting":
                bobot = 3;
                break;
            case "Penting":
                bobot = 4;
                break;
            case "Sangat Penting":
                bobot = 5;
                break;
            default:
                bobot = 0;
                break;
        }
        return bobot;
    }

    @Override
    public void countCriteria(CriteriaModel criteriaModel) {
        String sql = "SELECT  COUNT(*) AS total_kriteria,\r\n" + //
                "    SUM(CASE WHEN jenisKriteria = 'Benefit' THEN 1 ELSE 0 END) AS jumlah_Benefit,\r\n" + //
                "    SUM(CASE WHEN jenisKriteria = 'Cost' THEN 1 ELSE 0 END) AS jumlah_cost FROM bobotkriteria";
        PreparedStatement stat = null;
        ResultSet rs = null;

        try {
            stat = conn.prepareStatement(sql);
            rs = stat.executeQuery();

            if (rs.next()) {
                criteriaModel.setTotalCriteria(rs.getInt("total_kriteria"));
                criteriaModel.setTotalBenefits(rs.getInt("jumlah_Benefit"));
                criteriaModel.setTotalCost(rs.getInt("jumlah_cost"));

                if (criteriaModel.getTotalCriteria() < 12) {
                    criteriaModel.setTotalPaginate(1);
                } else {
                    criteriaModel
                            .setTotalPaginate((int) Math.ceil((double) criteriaModel.getTotalCriteria() / 12.0));
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
    public ObservableList<CriteriaTableModel> getDataCriteria(CriteriaModel criteriaModel) {
        String sql = "SELECT * FROM bobotKriteria LIMIT 12 OFFSET ?";
        PreparedStatement stat = null;
        int fetchingData = criteriaModel.getActivePaginate();
        ResultSet rs = null;

        try {
            stat = conn.prepareStatement(sql);
            stat.setInt(1, fetchingData);

            rs = stat.executeQuery();
            ObservableList<CriteriaTableModel> criteriaData = FXCollections.observableArrayList();
            while (rs.next()) {
                CriteriaTableModel tableCriteria = new CriteriaTableModel(
                        rs.getString("namaKriteria"),
                        rs.getString("jenisKriteria"),
                        rs.getString("bobotNilaiKriteria"),
                        rs.getString("bobot"));

                criteriaData.add(tableCriteria);
            }
            return criteriaData;
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
    public void deleteCriteria(CriteriaModel criteriaModel) {
        PreparedStatement stat = null;
        String sql = "DELETE FROM bobotKriteria WHERE namaKriteria = ?";
        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, criteriaModel.getCriteriaName());
            stat.executeUpdate();
            NotificationManager.notification("Berhasil dihapus",
                    "Kriteria " + criteriaModel.getCriteriaName() + " telah dihapus");

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
    public void getCriteria(CriteriaModel criteriaModel) {
        String sql = "SELECT * FROM bobotKriteria\r\n" + //
                      "INNER JOIN kriteriaPenilaian ON \r\n" + //
                     "bobotKriteria.idKriteriaPenilaian = kriteriaPenilaian.idKriteriaPenilaian \r\n" + //
                       "WHERE bobotKriteria.namaKriteria = ?";
        PreparedStatement stat = null;
        ResultSet rs = null;

        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, criteriaModel.getCriteriaName());
            rs = stat.executeQuery();
            if (rs.next()) {
                criteriaModel.setCriteriaId(rs.getString("idBobotKriteria"));
                criteriaModel.setCriteriaName(rs.getString("namaKriteria"));
                criteriaModel.setCriteriaType(rs.getString("jenisKriteria"));
                criteriaModel.setValueWeight(rs.getString("bobotNilaiKriteria"));
                criteriaModel.setCriteria1(rs.getString("nilai1"));
                criteriaModel.setCriteria2(rs.getString("nilai2"));
                criteriaModel.setCriteria3(rs.getString("nilai3"));
                criteriaModel.setCriteria4(rs.getString("nilai4"));
                criteriaModel.setCriteria5(rs.getString("nilai5"));  
                oldCriteriaName = criteriaModel.getCriteriaName();         
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
    public void updateCriteria(CriteriaModel criteriaModel) {
        PreparedStatement statBobotKriteria = null;
        PreparedStatement statKriteriaPenilaian = null;

        String updateBobotKriteria = "UPDATE bobotKriteria SET namaKriteria=?, jenisKriteria=?, bobotNilaiKriteria=?, bobot=? WHERE IdBobotKriteria=?";
        String updateKriteriaPenilaian = "UPDATE kriteriaPenilaian SET nilai1=?, nilai2=?, nilai3=?, nilai4=?, nilai5=? WHERE idKriteriaPenilaian=?";

        try {

            statBobotKriteria = conn.prepareStatement(updateBobotKriteria);
            statBobotKriteria.setString(1, criteriaModel.getCriteriaName());
            statBobotKriteria.setString(2, criteriaModel.getCriteriaType());
            statBobotKriteria.setString(3, criteriaModel.getValueWeight());
            statBobotKriteria.setInt(4, weight(criteriaModel));
            statBobotKriteria.setString(5, criteriaModel.getCriteriaId());
            statBobotKriteria.executeUpdate();

            statKriteriaPenilaian = conn.prepareStatement(updateKriteriaPenilaian);
            statKriteriaPenilaian.setString(1, criteriaModel.getCriteria1());
            statKriteriaPenilaian.setString(2, criteriaModel.getCriteria2());
            statKriteriaPenilaian.setString(3, criteriaModel.getCriteria3());
            statKriteriaPenilaian.setString(4, criteriaModel.getCriteria4());
            statKriteriaPenilaian.setString(5, criteriaModel.getCriteria5());
            statKriteriaPenilaian.setString(6, criteriaModel.getCriteriaId());
            statKriteriaPenilaian.executeUpdate();
            notificationManager.notification("Berhasil",
                    "Kriteria " +oldCriteriaName + " telah diubah");
            AddCriteriaViewContoller.runPane = true;
        } catch (Exception e) {
            System.err.println(e);
        } finally {
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

}

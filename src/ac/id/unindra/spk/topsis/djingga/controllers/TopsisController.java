package ac.id.unindra.spk.topsis.djingga.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ac.id.unindra.spk.topsis.djingga.models.AlternativeTableModel;
import ac.id.unindra.spk.topsis.djingga.models.TopsisModel;
import ac.id.unindra.spk.topsis.djingga.models.TopsisTableListModel;
import ac.id.unindra.spk.topsis.djingga.models.TopsisTableRankModel;
import ac.id.unindra.spk.topsis.djingga.services.TopsisService;
import ac.id.unindra.spk.topsis.djingga.utilities.DatabaseConnection;
import ac.id.unindra.spk.topsis.djingga.utilities.NotificationManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TopsisController implements TopsisService {
    private Connection conn = new DatabaseConnection().getConnection();
    private NotificationManager notificationManager = new NotificationManager();

    @Override
    public String[] getData() {
        List<String> data = new ArrayList<>();
        String sql = "SELECT * FROM bobotKriteria";
        PreparedStatement stat = null;
        ResultSet rs = null;

        try {
            stat = conn.prepareStatement(sql);
            rs = stat.executeQuery();
            while (rs.next()) {
                String value = rs.getString("namaKriteria");
                data.add(value);
            }

            String[] dataArray = new String[data.size()];
            dataArray = data.toArray(dataArray);

            return dataArray;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stat != null) {
                try {
                    stat.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public String[] getDataByCategory(TopsisModel topsisModel) {
        List<String> data = new ArrayList<>();
        String sql = "SELECT * FROM alatfotografer WHERE kategori=?";
        PreparedStatement stat = null;
        ResultSet rs = null;

        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, topsisModel.getCategory());
            rs = stat.executeQuery();
            while (rs.next()) {
                String value = rs.getString("namaAlat");
                data.add(value);
            }

            if (!data.isEmpty()) {
                return data.toArray(new String[0]);
            } else {
                return null;
            }

        } catch (Exception e) {
            System.out.println(e);
            return null;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stat != null) {
                try {
                    stat.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void getTypeCriteria(TopsisModel topsisModel) {
        String sql = "SELECT * FROM bobotkriteria WHERE namaKriteria =? ";
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, topsisModel.getNameCriteria());
            rs = stat.executeQuery();
            if (rs.next()) {
                topsisModel.setTypeCriteria(rs.getString("jenisKriteria"));
                topsisModel.setIdCriteria(rs.getString("idBobotKriteria"));
                topsisModel.setWeightCriteria(rs.getDouble("bobot"));
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
    public void setTopsisNormalizedDecisionmatrixAndWeighted(TopsisModel topsisModel) {
        PreparedStatement stat = null;
        String sql = "INSERT INTO nilaiTopsis(idMatrixKeputusanTernormalisasi, idAlatFotografer, idBobotKriteria,nilaiAlternatif, nilaiMatrixKeputusanTernomalisasi, nilaiMatrixKeputusanTernomalisasiDanTerbobot) VALUES (?,?,?,?,?,?)";
        try {
            stat = conn.prepareStatement(sql);

            stat.setString(1, topsisModel.getIdNormalizedDecisionMatrix());
            stat.setString(2, topsisModel.getIdAlternative());
            stat.setString(3, topsisModel.getIdCriteria());
            stat.setDouble(4, topsisModel.getWeightAlternative());
            stat.setDouble(5, topsisModel.getMatrixNormalize());
            stat.setDouble(6, topsisModel.getMatrixNormalizedAndWeighted());

            stat.executeUpdate();

        } catch (

        Exception e) {
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
    public void getIdAlternative(TopsisModel topsisModel) {

        String sql = "SELECT * FROM alatfotografer WHERE namaAlat =? AND kategori=? ";
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, topsisModel.getNameAlternative());
            stat.setString(2, topsisModel.getCategory());
            rs = stat.executeQuery();
            if (rs.next()) {
                topsisModel.setIdAlternative(rs.getString("idAlatFotografer"));
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
    public void setMaxMinTopsis(TopsisModel topsisModel) {
        PreparedStatement stat = null;
        String sql = "INSERT INTO topsis_max_min(idbobotKriteria,idMatrixKeputusanTernormalisasi, max, min) VALUES (?,?,?,?)";
        try {
            stat = conn.prepareStatement(sql);

            stat.setString(1, topsisModel.getIdCriteria());
            stat.setString(2, topsisModel.getIdNormalizedDecisionMatrix());
            stat.setDouble(3, topsisModel.getMax());
            stat.setDouble(4, topsisModel.getMin());

            stat.executeUpdate();

        } catch (

        Exception e) {
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
    public List<Double> normalizeAndWeight(String idNormalizedDecisionMatrix, String idAlternative) {

        String sql = "SELECT * FROM nilaiTopsis WHERE idMatrixKeputusanTernormalisasi =? AND idAlatFotografer=? ";
        PreparedStatement stat = null;
        ResultSet rs = null;
        List<Double> normalizeAndWeightTemp = new ArrayList<>();
        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, idNormalizedDecisionMatrix);
            stat.setString(2, idAlternative);
            rs = stat.executeQuery();

            while (rs.next()) {
                double value = rs.getDouble("nilaiMatrixKeputusanTernomalisasiDanTerbobot");
                normalizeAndWeightTemp.add(value);
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
        return normalizeAndWeightTemp;
    }

    @Override
    public List<Double> getMaxMin(String idNormalizedDecisionMatrix, String idCriteria) {
        String sql = "SELECT * FROM topsis_max_min WHERE idMatrixKeputusanTernormalisasi =? AND idbobotKriteria=? ";
        PreparedStatement stat = null;
        ResultSet rs = null;
        List<Double> maxTemp = new ArrayList<>();
        List<Double> minTemp = new ArrayList<>();
        List<Double> valueTemp = new ArrayList<>();
        try {

            stat = conn.prepareStatement(sql);
            stat.setString(1, idNormalizedDecisionMatrix);
            stat.setString(2, idCriteria);
            rs = stat.executeQuery();

            if (rs.next()) {
                double valueMax = rs.getDouble("max");
                double valueMin = rs.getDouble("min");
                maxTemp.add(valueMax);
                minTemp.add(valueMin);

            }

            valueTemp.addAll(maxTemp);
            valueTemp.addAll(minTemp);

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
        return valueTemp;
    }

    @Override
    public void setTopsisIdeal(TopsisModel topsisModel) {
        PreparedStatement stat = null;
        String sql = "INSERT INTO topsis_nilai_ideal(idMatrixKeputusanTernormalisasi, idAlatFotografer, nilai_ideal_positif,nilai_ideal_negatif, preferensi) VALUES (?,?,?,?,?)";
        try {
            stat = conn.prepareStatement(sql);

            stat.setString(1, topsisModel.getIdNormalizedDecisionMatrix());
            stat.setString(2, topsisModel.getIdAlternative());
            stat.setDouble(3, topsisModel.getPositiveIdealValue());
            stat.setDouble(4, topsisModel.getNegativeIdealValue());
            stat.setDouble(5, topsisModel.getPreference());

            stat.executeUpdate();

        } catch (

        Exception e) {
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
    public void setRank(TopsisModel topsisModel) {
        PreparedStatement stat = null;
        String sql = "UPDATE topsis_nilai_ideal SET `rank`=? WHERE idMatrixKeputusanTernormalisasi=? AND idAlatFotografer=?";

        try {
            stat = conn.prepareStatement(sql);

            stat.setInt(1, topsisModel.getRank());
            stat.setString(2, topsisModel.getIdNormalizedDecisionMatrix());
            stat.setString(3, topsisModel.getIdAlternative());

            stat.executeUpdate();

        } catch (

        Exception e) {
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
    public ObservableList<TopsisTableRankModel> getDataRank(TopsisModel topsisModel) {
        String sql = "SELECT tni.*, af.namaAlat FROM topsis_nilai_ideal tni JOIN alatFotografer af ON tni.idAlatFotografer = af.idAlatFotografer WHERE tni.idMatrixKeputusanTernormalisasi = ?";
        PreparedStatement stat = null;
        ResultSet rs = null;

        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, topsisModel.getIdNormalizedDecisionMatrix());

            rs = stat.executeQuery();
            ObservableList<TopsisTableRankModel> alternativeData = FXCollections.observableArrayList();
            while (rs.next()) {
                TopsisTableRankModel tableAlternative = new TopsisTableRankModel(
                        rs.getString("namaAlat"),
                        rs.getString("preferensi"),
                        rs.getString("rank"));

                alternativeData.add(tableAlternative);
            }
            return alternativeData;
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
    public int totalCriteria(TopsisModel topsisModel) {
        PreparedStatement stat = null;
        ResultSet rs = null;
        int total=0;
        String sql = "SELECT COUNT(DISTINCT idBobotKriteria) AS count FROM nilaitopsis WHERE idMatrixKeputusanTernormalisasi = ?";

        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, topsisModel.getIdNormalizedDecisionMatrix());
            rs = stat.executeQuery();

            if (rs.next()) {
              total = rs.getInt("count");
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
        return total;
    }

    @Override
    public List<Integer> getAlternativeValue(TopsisModel topsisModel) {
       String sql = "SELECT nilaiAlternatif FROM nilaitopsis WHERE idMatrixKeputusanTernormalisasi =?";
        PreparedStatement stat = null;
        ResultSet rs = null;
        List<Integer> alternativeValueTemp= new ArrayList<>();
        try {

            stat = conn.prepareStatement(sql);
            stat.setString(1, topsisModel.getIdNormalizedDecisionMatrix());
            rs = stat.executeQuery();

            while (rs.next()) {
                int value = rs.getInt("nilaiAlternatif");
                alternativeValueTemp.add(value);
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
        return alternativeValueTemp;
    }

    @Override
    public int totalAlternative(TopsisModel topsisModel) {
        PreparedStatement stat = null;
        ResultSet rs = null;
        int total=0;
        String sql = "SELECT COUNT(DISTINCT idAlatFotografer) AS count FROM nilaitopsis WHERE idMatrixKeputusanTernormalisasi = ?";

        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, topsisModel.getIdNormalizedDecisionMatrix());
            rs = stat.executeQuery();

            if (rs.next()) {
               total = rs.getInt("count");
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
        return total;
    }

    @Override
    public List<Double> getNormalizedDecisionMatrixValue(TopsisModel topsisModel) {
        String sql = "SELECT nilaiMatrixKeputusanTernomalisasi FROM nilaitopsis WHERE idMatrixKeputusanTernormalisasi =?";
        PreparedStatement stat = null;
        ResultSet rs = null;
        List<Double> alternativeValueTemp= new ArrayList<>();
        try {

            stat = conn.prepareStatement(sql);
            stat.setString(1, topsisModel.getIdNormalizedDecisionMatrix());
            rs = stat.executeQuery();

            while (rs.next()) {
                Double value = rs.getDouble("nilaiMatrixKeputusanTernomalisasi");
                alternativeValueTemp.add(value);
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
        return alternativeValueTemp;
    }

    @Override
    public List<Double> getNormalizedAndWeightedDecisionMatrixValue(TopsisModel topsisModel) {
        String sql = "SELECT nilaiMatrixKeputusanTernomalisasiDanTerbobot FROM nilaitopsis WHERE idMatrixKeputusanTernormalisasi =?";
        PreparedStatement stat = null;
        ResultSet rs = null;
        List<Double> alternativeValueTemp= new ArrayList<>();
        try {

            stat = conn.prepareStatement(sql);
            stat.setString(1, topsisModel.getIdNormalizedDecisionMatrix());
            rs = stat.executeQuery();

            while (rs.next()) {
                Double value = rs.getDouble("nilaiMatrixKeputusanTernomalisasiDanTerbobot");
                alternativeValueTemp.add(value);
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
        return alternativeValueTemp;
    }

    @Override
    public List<Double> getIdealPositive(TopsisModel topsisModel) {
        String sql = "SELECT nilai_ideal_positif FROM topsis_nilai_ideal WHERE idMatrixKeputusanTernormalisasi =?";
        PreparedStatement stat = null;
        ResultSet rs = null;
        List<Double> alternativeValueTemp= new ArrayList<>();
        try {

            stat = conn.prepareStatement(sql);
            stat.setString(1, topsisModel.getIdNormalizedDecisionMatrix());
            rs = stat.executeQuery();

            while (rs.next()) {
                Double value = rs.getDouble("nilai_ideal_positif");
                alternativeValueTemp.add(value);
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
        return alternativeValueTemp;
    }

    @Override
    public List<Double> getIdealNegative(TopsisModel topsisModel) {
        String sql = "SELECT nilai_ideal_negatif FROM topsis_nilai_ideal WHERE idMatrixKeputusanTernormalisasi =?";
        PreparedStatement stat = null;
        ResultSet rs = null;
        List<Double> alternativeValueTemp= new ArrayList<>();
        try {

            stat = conn.prepareStatement(sql);
            stat.setString(1, topsisModel.getIdNormalizedDecisionMatrix());
            rs = stat.executeQuery();

            while (rs.next()) {
                Double value = rs.getDouble("nilai_ideal_negatif");
                alternativeValueTemp.add(value);
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
        return alternativeValueTemp;
    }

    @Override
    public void setListTopsis(TopsisModel topsisModel) {
       PreparedStatement stat = null;
        String sql = "INSERT INTO daftar_perhitungan(idMatrixKeputusanTernormalisasi, kategori, jumlah_bobot,tanggal_perhitungan) VALUES (?,?,?,?)";
        try {
            stat = conn.prepareStatement(sql);

            stat.setString(1, topsisModel.getIdNormalizedDecisionMatrix());
            stat.setString(2, topsisModel.getCategory());
            stat.setInt(3, topsisModel.getTotalCriteria());
            stat.setString(4, topsisModel.getDate());
            

            stat.executeUpdate();

        } catch (

        Exception e) {
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
    public void countTopsis(TopsisModel topsisModel) {
        String sql = "SELECT  COUNT(*) AS total_topsis FROM daftar_perhitungan";
        PreparedStatement stat = null;
        ResultSet rs = null;

        try {
            stat = conn.prepareStatement(sql);
            rs = stat.executeQuery();

            if (rs.next()) {
                topsisModel.setTotalTopsis(rs.getInt("total_topsis"));

                if (topsisModel.getTotalTopsis() < 12) {
                    topsisModel.setTotalPaginate(1);
                } else {
                    topsisModel.setTotalPaginate((int) Math.ceil((double) topsisModel.getTotalTopsis() / 12.0));
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
    public ObservableList<TopsisTableListModel> getDataTopsis(TopsisModel topsisModel) {
       String sql = "SELECT * FROM daftar_perhitungan LIMIT 12 OFFSET ?";
        PreparedStatement stat = null;
        int fetchingData = topsisModel.getActivePaginate();
        ResultSet rs = null;
        try {
            stat = conn.prepareStatement(sql);
            stat.setInt(1, fetchingData);

            rs = stat.executeQuery();
            ObservableList<TopsisTableListModel> topsisData = FXCollections.observableArrayList();
            while (rs.next()) {
                TopsisTableListModel topsisTable = new TopsisTableListModel(
                        rs.getString("idMatrixKeputusanTernormalisasi"),
                        rs.getString("kategori"),
                        rs.getString("jumlah_bobot"),
                        rs.getString("tanggal_perhitungan"));

                topsisData.add(topsisTable);
            }
            return topsisData;
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
    public void deleteTopsis(TopsisModel topsisModel) {
        PreparedStatement stat = null;
        String sql = "DELETE FROM daftar_perhitungan WHERE idMatrixKeputusanTernormalisasi = ? ";
        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, topsisModel.getIdNormalizedDecisionMatrix());
            stat.executeUpdate();
            NotificationManager.notification("Berhasil dihapus",
                    "Id Topsis " + topsisModel.getIdNormalizedDecisionMatrix() + " telah dihapus");

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

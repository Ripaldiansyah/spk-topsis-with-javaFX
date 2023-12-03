package ac.id.unindra.spk.topsis.djingga.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ac.id.unindra.spk.topsis.djingga.models.OTPModel;
import ac.id.unindra.spk.topsis.djingga.models.TopsisModel;
import ac.id.unindra.spk.topsis.djingga.services.OTPService;
import ac.id.unindra.spk.topsis.djingga.services.TopsisService;
import ac.id.unindra.spk.topsis.djingga.utilities.DatabaseConnection;
import ac.id.unindra.spk.topsis.djingga.utilities.NotificationManager;
import ac.id.unindra.spk.topsis.djingga.utilities.OTPGenerator;

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
                // System.out.println(normalizeAndWeightTemp);
                // System.out.println(value);
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
}

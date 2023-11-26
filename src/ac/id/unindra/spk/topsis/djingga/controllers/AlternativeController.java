package ac.id.unindra.spk.topsis.djingga.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import ac.id.unindra.spk.topsis.djingga.models.AlternativeModel;
import ac.id.unindra.spk.topsis.djingga.models.AlternativeTableModel;
import ac.id.unindra.spk.topsis.djingga.utilities.DatabaseConnection;
import ac.id.unindra.spk.topsis.djingga.utilities.NotificationManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ac.id.unindra.spk.topsis.djingga.services.AlternativeService;

public class AlternativeController implements AlternativeService {
    private Connection conn = new DatabaseConnection().getConnection();

    @Override
    public void insertAlternative(AlternativeModel alternativeModel) {
        PreparedStatement statAlternative = null;

        String insertAlternative = "INSERT INTO alatfotografer (namaAlat, kategori) VALUES (?, ?)";

        try {
            statAlternative = conn.prepareStatement(insertAlternative);
            statAlternative.setString(1, alternativeModel.getAlternativeName());
            statAlternative.setString(2, alternativeModel.getAlternativeCategory());
            statAlternative.executeUpdate();
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            
            if (statAlternative != null) {
                try {
                    statAlternative.close();
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        }
    }

    @Override
    public void updateAlternative(AlternativeModel alternativeModel) {
        PreparedStatement statAlternative = null;

        String updateAlternative = "UPDATE alatFotografer SET namaAlat=?, kategori=? WHERE idAlatFotografer=?";
    
        try {

            statAlternative = conn.prepareStatement(updateAlternative);
            statAlternative.setString(1, alternativeModel.getAlternativeName());
            statAlternative.setString(2, alternativeModel.getAlternativeCategory());
            statAlternative.setString(3, alternativeModel.getAlternativeId());
            statAlternative.executeUpdate();

        } catch (Exception e) {
            System.err.println(e);
        } finally {
            if (statAlternative != null) {
                try {
                    statAlternative.close();
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        }
    }

    @Override
    public void deleteAlternative(AlternativeModel alternativeModel) {
        PreparedStatement stat = null;
        String sql = "DELETE FROM alatFotografer WHERE namaAlat = ? AND kategori=?";
        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, alternativeModel.getAlternativeName());
            stat.setString(2, alternativeModel.getAlternativeCategory());
            stat.executeUpdate();
            NotificationManager.notification("Berhasil dihapus",
                    "Kriteria " + alternativeModel.getAlternativeName() + " telah dihapus");

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
    public boolean checkAlternative(AlternativeModel alternativeModel) {
        PreparedStatement stat = null;
        ResultSet rs = null;
        boolean alternativeNameRegistered = false;
        String sql = "SELECT COUNT(*) AS count FROM alatFotografer WHERE LOWER(namaAlat) = LOWER(?) AND LOWER(kategori) = LOWER(?)";

        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, alternativeModel.getAlternativeName());
            stat.setString(2, alternativeModel.getAlternativeCategory());
            rs = stat.executeQuery();

            if (rs.next()) {
                int count = rs.getInt("count");
                if (count > 0) {
                    alternativeNameRegistered = true;
                } else {
                    alternativeNameRegistered = false;
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
        return alternativeNameRegistered;
    }

    @Override
    public void countAlternative(AlternativeModel alternativeModel) {
        String sql = "SELECT  COUNT(*) AS total_alternatif FROM alatFotografer";
        PreparedStatement stat = null;
        ResultSet rs = null;

        try {
            stat = conn.prepareStatement(sql);
            rs = stat.executeQuery();

            if (rs.next()) {
                alternativeModel.setTotalAlternatif(rs.getInt("total_alternatif"));

                if (alternativeModel.getTotalAlternatif() < 12) {
                    alternativeModel.setTotalPaginate(1);
                } else {
                    alternativeModel.setTotalPaginate((int) Math.ceil((double) alternativeModel.getTotalAlternatif() / 12.0));
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
    public ObservableList<AlternativeTableModel> getDataCriteria(AlternativeModel alternativeModel) {
        String sql = "SELECT * FROM alatFotografer LIMIT 12 OFFSET ?";
        PreparedStatement stat = null;
        int fetchingData = alternativeModel.getActivePaginate();
        ResultSet rs = null;

        try {
            stat = conn.prepareStatement(sql);
            stat.setInt(1, fetchingData);

            rs = stat.executeQuery();
            ObservableList<AlternativeTableModel> alternativeData = FXCollections.observableArrayList();
            while (rs.next()) {
                AlternativeTableModel tableAlternative = new AlternativeTableModel(
                        rs.getString("namaAlat"),
                        rs.getString("kategori"));

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
    public void getId(AlternativeModel alternativeModel) {
        String sql = "SELECT idAlatFotografer FROM alatFotografer WHERE namaAlat =? AND kategori = ? ";
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, alternativeModel.getAlternativeName() );
            stat.setString(2, alternativeModel.getAlternativeCategory());
            rs = stat.executeQuery();
            if (rs.next()) {
                alternativeModel.setAlternativeId(rs.getString("idAlatFotografer"));       
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

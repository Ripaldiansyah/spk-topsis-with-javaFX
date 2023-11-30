package ac.id.unindra.spk.topsis.djingga.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ac.id.unindra.spk.topsis.djingga.models.TopsisModel;
import ac.id.unindra.spk.topsis.djingga.services.TopsisService;
import ac.id.unindra.spk.topsis.djingga.utilities.DatabaseConnection;
import ac.id.unindra.spk.topsis.djingga.utilities.NotificationManager;

public class TopsisController implements TopsisService {
    private Connection conn = new DatabaseConnection().getConnection();
    private NotificationManager notificationManager = new NotificationManager();

    @Override
    public String[] getData(){
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
            }else{
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
        String sql = "SELECT jenisKriteria FROM bobotkriteria WHERE namaKriteria =? ";
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, topsisModel.getNameCriteria() );
            rs = stat.executeQuery();
            if (rs.next()) {
                topsisModel.setTypeCriteria(rs.getString("jenisKriteria"));       
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
    public void setTopsisNormalizedDecisionmatrix(TopsisModel topsisModel) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setTopsisNormalizedDecisionmatrix'");
    }
    }

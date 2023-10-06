package ac.id.unindra.spk.topsis.djingga.controllers;

import ac.id.unindra.spk.topsis.djingga.models.loginModel;
import ac.id.unindra.spk.topsis.djingga.services.loginService;
import ac.id.unindra.spk.topsis.djingga.utilities.DatabaseConnection;
import at.favre.lib.crypto.bcrypt.BCrypt;
import java.sql.*;

public class loginController implements loginService {
private Connection conn = (Connection) new DatabaseConnection();
    @Override
    public void processLogin(loginModel loginModel) {
           String sql = "SELECT * FROM users WHERE LOWER(username)=LOWER(?) AND password_hash=?";
         PreparedStatement stat=null;
          ResultSet rs=null;
          String id,fullName,role=null;
          byte[] profilePicture;
          
           try {
        stat = conn.prepareStatement(sql);
	stat.setString(1, loginModel.getUsername());
        stat.setString(2, (loginModel.getPassword()));

        rs = stat.executeQuery();
        if (rs.next()) {
            id = rs.getString("id");
            fullName = rs.getString("fullName");
            role = rs.getString("role");
	    profilePicture = rs.getBytes("profilePicture");
		    
	   
	    loginModel.setPictures(profilePicture);
	    
	    
            homeApp menu = new homeApp(idEmployee, employeeName, role, picture);
            menu.setVisible(true);
            menu.revalidate();
	    formLogin login = new formLogin();
	    login.close=true;

        }else{
            JOptionPane.showMessageDialog(null, "Username atau Password salah","Kesalahan",JOptionPane.INFORMATION_MESSAGE);
	    formLogin login = new formLogin();
	    login.close=false;
	    
        }

    }
    catch (SQLException ex) {
        Logger.getLogger(DAO_Login.class.getName()).log(Level.SEVERE, null, ex);

    }finally{
        if (stat!=null) {
            try {
                stat.close();
            }catch(SQLException ex){
                Logger.getLogger(DAO_Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
    }
    
}

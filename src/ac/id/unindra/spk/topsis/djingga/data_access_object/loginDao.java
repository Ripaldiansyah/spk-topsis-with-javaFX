package ac.id.unindra.spk.topsis.djingga.data_access_object;

import ac.id.unindra.spk.topsis.djingga.models.loginModel;
import ac.id.unindra.spk.topsis.djingga.services.loginService;
import ac.id.unindra.spk.topsis.djingga.utilities.DatabaseConnection;
import java.sql.*;

public class loginDao implements loginService{
private Connection conn = (Connection) new DatabaseConnection();


    @Override
    public void processLogin(loginModel login) {
        String sql = "SELECT * FROM users WHERE LOWER(username)=LOWER(?) AND password_hash=?";
         PreparedStatement stat=null;
          ResultSet rs=null;
          String id,fullName,role=null;
          byte[] profilePicture;
    }
    
}

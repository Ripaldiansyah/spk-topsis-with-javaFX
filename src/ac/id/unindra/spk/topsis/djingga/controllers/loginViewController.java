package ac.id.unindra.spk.topsis.djingga.controllers;

import ac.id.unindra.spk.topsis.djingga.models.OTPModel;
import ac.id.unindra.spk.topsis.djingga.models.loginModel;
import ac.id.unindra.spk.topsis.djingga.services.OTPService;
import ac.id.unindra.spk.topsis.djingga.services.loginService;
import ac.id.unindra.spk.topsis.djingga.utilities.CurrentDate;
import ac.id.unindra.spk.topsis.djingga.utilities.DatabaseConnection;
import ac.id.unindra.spk.topsis.djingga.utilities.EmailSender;
import ac.id.unindra.spk.topsis.djingga.utilities.NotificationManager;
import ac.id.unindra.spk.topsis.djingga.utilities.OTPGenerator;
import ac.id.unindra.spk.topsis.djingga.utilities.RandomTextGenerator;
import at.favre.lib.crypto.bcrypt.BCrypt;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class loginViewController implements Initializable, loginService, OTPService {

    private void clear() {
        txtPassword.setText("");
        txtUsername.setText("");
        registUsername.setText("");
        registFullname.setText("");
        registPassword.setText("");
        registPasswordValidation.setText("");
        otpEntered.setText("");
        registEmail.setText("");
    }

    @FXML
    private ImageView imgLogo;
    @FXML
    private MFXTextField txtUsername;
    @FXML
    private MFXPasswordField txtPassword;
    @FXML
    private AnchorPane loginPane;
    @FXML
    private AnchorPane registerPane;
    @FXML
    private AnchorPane successPane;
    @FXML
    private Label accountNotConfirmMessage;
    @FXML
    private ImageView accountNotConfirmImage;

    // regis
    @FXML
    private MFXTextField registFullname;
    @FXML
    private MFXTextField registUsername;
    @FXML
    private MFXPasswordField registPassword;
    @FXML
    private MFXPasswordField registPasswordValidation;
    @FXML
    private MFXTextField registEmail;

    // otp
    @FXML
    private MFXTextField otpEntered;

    private Connection conn = new DatabaseConnection().getConnection();
    private NotificationManager notificationManager = new NotificationManager();
    loginService loginService = this;
    OTPService OTPService = this;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image img = new Image("/ac/id/unindra/spk/topsis/djingga/media/logoDjingga.gif");
        imgLogo.setImage(img);

    }

    @Override
    public boolean checkUsernameRegistered(loginModel loginModel) {
        PreparedStatement stat = null;
        ResultSet rs = null;
        boolean usernameIsValid = false;
        String sql = "SELECT COUNT(*) AS count FROM pengguna WHERE namaPengguna = ?";

        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, loginModel.getUsername());
            rs = stat.executeQuery();

            if (rs.next()) {
                int count = rs.getInt("count");
                if (count > 0) {
                    usernameIsValid = false;
                } else {
                    usernameIsValid = true;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return usernameIsValid;
    }

    @Override
    public void checkOTP(OTPModel OTPModel) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkOTP'");
    }

    @Override
    public void sendOTP(loginModel loginModel) {
        String sql = "SELECT pengguna.email, OTP.OTPVerifikasi1 FROM pengguna INNER JOIN OTP ON pengguna.idOTP = OTP.idOTP WHERE pengguna.idPengguna = ?";
        String idUser = loginModel.getIdUser();
        PreparedStatement stat = null;
        ResultSet rs = null;
        String email = null, otp = null;
        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, idUser);
            rs = stat.executeQuery();

            if (rs.next()) {
                email = rs.getString("email");
                otp = rs.getString("OTPVerifikasi1");
                EmailSender.sendOTP(email, otp);
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
    public void setOTP(OTPModel OTPModel) {
        PreparedStatement stat = null;
        String sql = "INSERT INTO OTP(idOTP,OTPVerifikasi1)VALUES(?,?)";
        try {
            stat = conn.prepareStatement(sql);

            stat.setString(1, OTPModel.getIdOTP());
            stat.setString(2, OTPModel.getStoredOTP());

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
    public void processLogin(loginModel loginModel) {
        String sql = "SELECT * FROM pengguna WHERE LOWER(namaPengguna)=LOWER(?) AND kataSandi=?";
        PreparedStatement stat = null;
        ResultSet rs = null;
        String id, fullName, role, status = null;

        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, loginModel.getUsername());
            stat.setString(2, (loginModel.getPassword()));
            rs = stat.executeQuery();

            if (rs.next()) {
                id = rs.getString("idPengguna");
                fullName = rs.getString("namaLengkap");
                role = rs.getString("level");
                status = rs.getString("statusAkun");
                notificationManager.notification("Berhasil Masuk", "Selamat Datang " + fullName);
            } else {
                notificationManager.notification("Tidak Dapat Masuk", "Periksa Nama Pengguna dan Kata Sandi Anda");
            }

        } catch (Exception e) {
            System.err.println("e");
        }

    }

    @Override
    public void processRegistration(loginModel loginModel) {
        PreparedStatement stat = null;
        String sql = "INSERT INTO pengguna(idPengguna,namaLengkap,email,namaPengguna,kataSandi,level,statusAkun,idOTP)VALUES(?,?,?,?,?,?,?,?)";
        try {
            if (checkUsernameRegistered(loginModel)) {
                stat = conn.prepareStatement(sql);
               
                stat.setString(1, loginModel.getIdUser());
                stat.setString(2, loginModel.getFullName());
                stat.setString(3, loginModel.getEmail());
                stat.setString(4, loginModel.getUsername());
                stat.setString(5, loginModel.getPassword());
                stat.setString(6, loginModel.getRole());
                stat.setString(7, loginModel.getAccountStatus());
                stat.setString(8, loginModel.getIdOTP());

                stat.executeUpdate();
                OTPModel OTPModel = new OTPModel();

                String storedOTP = OTPGenerator.generateOTP(6);
                String idOTP = loginModel.getIdOTP();

                OTPModel.setStoredOTP(storedOTP);
                OTPModel.setIdOTP(idOTP);

                OTPService.setOTP(OTPModel);
                successPane();
                 loginService.sendOTP(loginModel);
                

                notificationManager.notification("Berhasil", "Akun Anda berhasil dibuat");
            } else {
                notificationManager.notification("Peringatan", "Username Telah Terdaftar");
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

    private boolean loginValidation() {
        boolean valid = false;
        if (txtUsername.getText().trim().isEmpty() | txtPassword.getText().trim().isEmpty()) {
            valid = false;
        } else {
            valid = true;
        }
        return valid;
    }

    private boolean passwordValidation() {
        boolean valid = false;
        if (registPassword.getText().equals(registPasswordValidation.getText())) {
            valid = true;
        } else {
            valid = false;
        }
        return valid;
    }

    private boolean registrationValidation() {
        boolean valid = false;
        if (registFullname.getText().trim().isEmpty() ||
                registUsername.getText().trim().isEmpty() ||
                registPassword.getText().trim().isEmpty() ||
                registPasswordValidation.getText().trim().isEmpty() ||
                registEmail.getText().trim().isEmpty()) {
            valid = false;
        } else {
            valid = true;
        }
        return valid;
    }

    @FXML
    private void login(MouseEvent event) {
        if (loginValidation() == true) {

            String username = txtUsername.getText();
            String password = txtPassword.getText();

            loginModel loginModel = new loginModel();
            loginModel.setUsername(username);
            loginModel.setPassword(password);
            loginService.processLogin(loginModel);

        } else {
            notificationManager.notification("Peringatan", "Nama Pengguna atau Kata Sandi tidak boleh kosong");
        }
    }

    @FXML
    private void register(MouseEvent event) {
        if (registrationValidation() == true) {
            if (passwordValidation() == true) {
                String fullName = registFullname.getText();
                String username = registUsername.getText();
                String password = BCrypt.withDefaults().hashToString(12, txtPassword.getText().toCharArray());
                String role = "User";
                String accountStatus = "pending";
                String email = registEmail.getText();
                String idOTP = RandomTextGenerator.generateRandomText(4);
                String idUser = CurrentDate.date("")+RandomTextGenerator.generateRandomText(4);

                loginModel loginModel = new loginModel();
                loginModel.setIdUser(idUser);
                loginModel.setFullName(fullName);
                loginModel.setUsername(username);
                loginModel.setPassword(password);
                loginModel.setRole(role);
                loginModel.setEmail(email);
                loginModel.setAccountStatus(accountStatus);
                loginModel.setIdOTP(idOTP);
                loginService.processRegistration(loginModel);
               
            } else {
                notificationManager.notification("Peringatan", "Kata Sandi konfirmasi tidak sesuai");
            }

        } else {
            notificationManager.notification("Peringatan", "Pastikan semua data telah terisi");
        }
    }

    @FXML
    private void exit(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    private void registerPane(MouseEvent event) throws IOException {
        registerPane.setVisible(true);
        registerPane.setDisable(false);

        loginPane.setVisible(false);
        loginPane.setDisable(true);

        successPane.setVisible(false);
        successPane.setDisable(true);
        clear();

    }

    @FXML
    private void loginPane(MouseEvent event) throws IOException {
        registerPane.setVisible(false);
        registerPane.setDisable(true);

        loginPane.setVisible(true);
        loginPane.setDisable(false);

        successPane.setVisible(false);
        successPane.setDisable(true);
        clear();
    }

    private void successPane() {
        registerPane.setVisible(false);
        registerPane.setDisable(true);

        loginPane.setVisible(false);
        loginPane.setDisable(true);

        successPane.setVisible(true);
        successPane.setDisable(false);
        accountNotConfirmMessage.setVisible(false);
        accountNotConfirmImage.setVisible(false);
        clear();
    }

}

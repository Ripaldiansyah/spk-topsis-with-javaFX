package ac.id.unindra.spk.topsis.djingga.controllers;

import ac.id.unindra.spk.topsis.djingga.models.OTPModel;
import ac.id.unindra.spk.topsis.djingga.models.loginModel;
import ac.id.unindra.spk.topsis.djingga.models.registerModel;
import ac.id.unindra.spk.topsis.djingga.services.OTPService;
import ac.id.unindra.spk.topsis.djingga.services.loginService;
import ac.id.unindra.spk.topsis.djingga.services.registerService;
import ac.id.unindra.spk.topsis.djingga.utilities.CurrentDate;
import ac.id.unindra.spk.topsis.djingga.utilities.NotificationManager;
import ac.id.unindra.spk.topsis.djingga.utilities.RandomTextGenerator;
import at.favre.lib.crypto.bcrypt.BCrypt;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class loginViewController implements Initializable {

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
    @FXML
    private Label accountSuccessMessage;
    @FXML
    private ImageView accountSuccessCreateImage;

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

    private NotificationManager notificationManager = new NotificationManager();
    public static boolean runPane=false;
    public static String idUser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image img = new Image("/ac/id/unindra/spk/topsis/djingga/media/logoDjingga.gif");
        imgLogo.setImage(img);

    }

    public void clear() {
        txtPassword.setText("");
        txtUsername.setText("");
        registUsername.setText("");
        registFullname.setText("");
        registPassword.setText("");
        registPasswordValidation.setText("");
        otpEntered.setText("");
        registEmail.setText("");
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

    private boolean OTPValidation() {
        boolean valid = false;
        if (otpEntered.getText().trim().isEmpty() ) {
            valid = false;
        } else {
            valid = true;
        }
        return valid;
    }

    @FXML
    private void login(MouseEvent event) {
        if (loginValidation()) {

            String username = txtUsername.getText();
            String password = txtPassword.getText();

            loginModel loginModel = new loginModel();
            loginService loginService = new loginController();
            loginModel.setUsername(username);
            loginModel.setPassword(password);
            loginService.processLogin(loginModel);
            if (runPane) {
                unSuccessPane();
            }

        } else {
            notificationManager.notification("Peringatan", "Nama Pengguna atau Kata Sandi tidak boleh kosong");
        }
    }

    @FXML
    private void otp(MouseEvent event) {
        if (OTPValidation()) {
            String OTPInput = otpEntered.getText();
            OTPModel OTPModel = new OTPModel();
            OTPService OTPService = new OTPController();
            registerModel registerModel = new registerModel();

            registerModel.setIdUser(idUser);
            OTPModel.setEnteredOTP(OTPInput);
            OTPService.checkOTP(OTPModel, registerModel);

        } else {
            notificationManager.notification("Peringatan", "OTP Tidak Boleh Kosong");
        }
    }

    @FXML
    private void register(MouseEvent event) {
        if (registrationValidation()) {
            if (passwordValidation()) {
                String fullName = registFullname.getText();
                String username = registUsername.getText();
                String password = BCrypt.withDefaults().hashToString(12, registPassword.getText().toCharArray());
                String role = "User";
                String accountStatus = "pending";
                String email = registEmail.getText();
                String idOTP = RandomTextGenerator.generateRandomText(4) + CurrentDate.date("");
                String idUser = CurrentDate.date("") + RandomTextGenerator.generateRandomText(4);

                registerModel registerModel = new registerModel();
                registerService registerService = new registerController();
                OTPModel OTPModel = new OTPModel();
                registerModel.setIdUser(idUser);
                registerModel.setFullName(fullName);
                registerModel.setUsername(username);
                registerModel.setPassword(password);
                registerModel.setRole(role);
                registerModel.setEmail(email);
                registerModel.setAccountStatus(accountStatus);
                OTPModel.setIdOTP(idOTP);
                registerService.processRegistration(registerModel, OTPModel);
                if (runPane) {
                    successPane();
                }

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

    public void successPane() {
        registerPane.setVisible(false);
        registerPane.setDisable(true);

        loginPane.setVisible(false);
        loginPane.setDisable(true);

        successPane.setVisible(true);
        successPane.setDisable(false);
        accountNotConfirmMessage.setVisible(false);
        accountNotConfirmImage.setVisible(false);
        accountSuccessMessage.setVisible(true);
        accountSuccessCreateImage.setVisible(true);
        clear();
    }

    public void unSuccessPane() {
        registerPane.setVisible(false);
        registerPane.setDisable(true);

        loginPane.setVisible(false);
        loginPane.setDisable(true);

        successPane.setVisible(true);
        successPane.setDisable(false);
        accountNotConfirmMessage.setVisible(true);
        accountNotConfirmImage.setVisible(true);
        accountSuccessMessage.setVisible(false);
        accountSuccessCreateImage.setVisible(false);
        clear();
    }

}

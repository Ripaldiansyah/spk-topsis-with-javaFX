package ac.id.unindra.spk.topsis.djingga.controllers;

import ac.id.unindra.spk.topsis.djingga.models.OTPModel;
import ac.id.unindra.spk.topsis.djingga.models.forgotPasswordModel;
import ac.id.unindra.spk.topsis.djingga.models.loginModel;
import ac.id.unindra.spk.topsis.djingga.models.registerModel;
import ac.id.unindra.spk.topsis.djingga.services.OTPService;
import ac.id.unindra.spk.topsis.djingga.services.forgotPasswordService;
import ac.id.unindra.spk.topsis.djingga.services.loginService;
import ac.id.unindra.spk.topsis.djingga.services.registerService;
import ac.id.unindra.spk.topsis.djingga.utilities.CurrentDate;
import ac.id.unindra.spk.topsis.djingga.utilities.NotificationManager;
import ac.id.unindra.spk.topsis.djingga.utilities.RandomTextGenerator;
import at.favre.lib.crypto.bcrypt.BCrypt;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
    @FXML
    private MFXButton btnConfirm;

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
    @FXML
    private Label countdownOTP;
    @FXML
    private MFXGenericDialog dialogOTP;
    @FXML
    private MFXButton btnLogin;

    // dialog
    @FXML
    private AnchorPane dialogPane;

    // forgotPassword
    @FXML
    private Label labelForgot;
    @FXML
    private AnchorPane resetPasswordPane;
    @FXML
    private MFXPasswordField resetPasswordValidationField;
    @FXML
    private MFXPasswordField resetPasswordField;
    @FXML
    private MFXButton btnResetPassword;
    @FXML
    private MFXButton btnCancelSearch;
    @FXML
    private MFXButton btnSearch;
    @FXML
    private MFXButton btnCancel;
    @FXML
    private MFXTextField searchUsername;
    @FXML
    private MFXTextField searchEmail;
    @FXML
    private AnchorPane searchPane;
    @FXML
    private Label labelInformation;

    private NotificationManager notificationManager = new NotificationManager();
    public static boolean runPane = false;
    public static boolean main = false;
    public static String idUser, idUserResetPassword;
    private int clickCount = 0;
    private int countdownSeconds = 90;
    private double xOffset, yOffset = 0;

    registerModel registerModel = new registerModel();
    OTPService OTPService = new OTPController();
    loginService loginService = new loginController();
    loginModel loginModel = new loginModel();
    OTPModel OTPModel = new OTPModel();
    Timer timer = new Timer();
    forgotPasswordModel forgotPasswordModel = new forgotPasswordModel();
    forgotPasswordService forgotPasswordService = new forgotPasswordController();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image img = new Image("/ac/id/unindra/spk/topsis/djingga/resources/media/logoDjingga.gif");
        imgLogo.setImage(img);
        dialogPane.setVisible(false);
        dialogPane.setDisable(true);

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
        searchUsername.setText("");
        searchEmail.setText("");
    }

    private boolean loginValidation() {
        boolean valid = false;
        if (txtUsername.getText().trim().isEmpty() || txtPassword.getText().trim().isEmpty()) {
            valid = false;
        } else {
            valid = true;
        }
        return valid;
    }

    private boolean passwordValidation(MFXPasswordField MFXPasswordField,
            MFXPasswordField MFXPasswordFieldVerification) {
        boolean valid = false;
        if (MFXPasswordField.getText().equals(MFXPasswordFieldVerification.getText())) {
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

    private boolean searchAccountValidation() {
        boolean valid = false;
        if (searchUsername.getText().trim().isEmpty() ||
                searchEmail.getText().trim().isEmpty()) {
            valid = false;
            NotificationManager.notification("Peringatan", "Username dan email tidak boleh kosong");
        } else {
            valid = true;
        }
        return valid;
    }

    private boolean resetPasswordValidation() {
        boolean valid = false;
        if (resetPasswordField.getText().trim().isEmpty() ||
                resetPasswordValidationField.getText().trim().isEmpty()) {
            valid = false;
        } else {
            valid = true;
        }
        return valid;
    }

    private boolean OTPValidation() {
        boolean valid = false;
        if (otpEntered.getText().trim().isEmpty()) {
            valid = false;
        } else {
            valid = true;
        }
        return valid;
    }

    @FXML
    private void login(MouseEvent event) throws IOException {
        if (loginValidation()) {

            String username = txtUsername.getText();
            String password = txtPassword.getText();

            loginModel.setUsername(username);
            loginModel.setPassword(password);
            loginService.processLogin(loginModel);
            if (runPane) {
                unSuccessPane();
            }
            if (main) {
                mainApp();
                ((Node) event.getSource()).getScene().getWindow().hide();
                NotificationManager.notification("Berhasil Masuk", "Selamat Datang " + loginModel.getFullName());
                appViewController.idUser = loginModel.getIdUser();
            }

        } else {
            notificationManager.notification("Peringatan", "Nama Pengguna atau Kata Sandi tidak boleh kosong");
        }
    }

    @FXML
    private void disableButton(MFXButton MFXButton, String message) {
        MFXButton.setOnAction(event -> {
            clickCount++;
        });
        if (clickCount >= 5) {
            MFXButton.setDisable(true);
            NotificationManager.notification("Periangatan", message);
            schedulerEnabledButton(MFXButton);
        }

    }

    private void schedulerEnabledButton(MFXButton MFXButton) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            clickCount = 0;
            if (clickCount >= 0) {
                MFXButton.setDisable(false);
                scheduler.shutdown();
            }

        }, 60, 1, TimeUnit.SECONDS);

    }

    @FXML
    private void otp(MouseEvent event) {
        if (OTPValidation()) {
            String OTPInput = otpEntered.getText();
            OTPModel.setEnteredOTP(OTPInput);
            if (idUser == null) {
                registerModel.setIdUser(idUserResetPassword);
                OTPService.checkOTP(OTPModel, registerModel);
                if (runPane) {
                    resetPasswordPage();
                }
            } else {
                registerModel.setIdUser(idUser);
                OTPService.checkOTP(OTPModel, registerModel);
                if (runPane) {
                    activePane(loginPane);
                    NotificationManager.notification("Berhasil", "Kamu berhasil mendaftarkan akun kamu");
                }

            }

            disableButton(btnConfirm, "Terlalu banyak percobaan OTP salah. Tunggu sebentar sebelum mencoba lagi.");

        } else {
            notificationManager.notification("Peringatan", "OTP Tidak Boleh Kosong");
        }
    }

    @FXML
    private void resetPassword(MouseEvent event) {
        if (resetPasswordValidation()) {
            if (passwordValidation(resetPasswordField, resetPasswordValidationField)) {
                String password = BCrypt.withDefaults().hashToString(12, resetPasswordField.getText().toCharArray());
                forgotPasswordModel.setPassword(password);
                forgotPasswordModel.setIdUser(idUserResetPassword);
                forgotPasswordService.resetPassword(forgotPasswordModel);
                if (runPane) {
                    activePane(loginPane);
                }
                runPane = false;
            }

        }

    }

    @FXML
    private void searchAccountForgot(MouseEvent event) {
        if (searchAccountValidation()) {
            String username = searchUsername.getText();
            String email = searchEmail.getText();

            forgotPasswordModel.setUsername(username);
            forgotPasswordModel.setEmail(email);

            forgotPasswordService.searchAccount(forgotPasswordModel, loginModel);

            if (runPane) {
                forgotPassword();
            }
        }

        runPane = false;

    }

    @FXML
    private void register(MouseEvent event) {
        if (registrationValidation()) {
            if (passwordValidation(registPassword, registPasswordValidation)) {
                String fullName = registFullname.getText();
                String username = registUsername.getText();
                String password = BCrypt.withDefaults().hashToString(12, registPassword.getText().toCharArray());
                String role = "User";
                String accountStatus = "pending";
                String email = registEmail.getText();
                String idOTP = RandomTextGenerator.generateRandomText(4) + CurrentDate.date("");
                String idUser = CurrentDate.date("") + RandomTextGenerator.generateRandomText(4);

                registerService registerService = new registerController();

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

    private void countDownOTPRequest() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        timer = new Timer();
        countdownSeconds = 90;

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    countdownOTP.setText("Kirim Ulang Kode Verifikasi (Dalam " + countdownSeconds + " Detik)");
                    countdownSeconds--;

                    if (countdownSeconds < 0) {
                        countdownOTP.setText("Kirim ulang kode OTP");
                        timer.cancel();
                        countdownOTP.setDisable(false);
                    }
                });
            }
        }, 0, 1000);
    }

    private void activePane(AnchorPane AnchorPane) {

        // visible
        registerPane.setVisible(false);
        dialogPane.setVisible(false);
        loginPane.setVisible(false);
        resetPasswordPane.setVisible(false);
        searchPane.setVisible(false);
        successPane.setVisible(false);

        // disable
        registerPane.setDisable(true);
        dialogPane.setDisable(true);
        loginPane.setDisable(true);
        resetPasswordPane.setDisable(true);
        searchPane.setDisable(true);
        successPane.setDisable(true);
        countdownOTP.setDisable(true);
        if (true) {
            AnchorPane.setVisible(true);
            AnchorPane.setDisable(false);
            countDownOTPRequest();
            clear();
        }

    }

    private void activeMessage(Label Label, ImageView ImageView) {
        accountNotConfirmMessage.setVisible(false);
        accountNotConfirmImage.setVisible(false);
        accountSuccessMessage.setVisible(false);
        accountSuccessCreateImage.setVisible(false);

        if (true) {
            Label.setVisible(true);
            ImageView.setVisible(true);
        }
    }

    @FXML
    private void searchPane(MouseEvent event) {
        activePane(searchPane);
    }

    @FXML
    private void resetPasswordPage() {
        activePane(resetPasswordPane);
    }

    @FXML
    private void dialogOTPClick(MouseEvent event) {
        activePane(successPane);
    }

    @FXML
    private void closeDialog(MouseEvent event) {
        activePane(successPane);
    }

    @FXML
    private void resendOTPConfirm(MouseEvent event) {
        activePane(successPane);
        registerModel.setIdUser(idUser);
        OTPService.sendOTP(registerModel, OTPModel);
        NotificationManager.notification("Berhasil", "Kode OTP Berhasil dikirimkan ke email Anda");
    }

    public void successPane() {
        activePane(successPane);
        activeMessage(accountSuccessMessage, accountSuccessCreateImage);
        countdownOTP.setDisable(true);

    }

    public void unSuccessPane() {
        activePane(successPane);
        activeMessage(accountNotConfirmMessage, accountNotConfirmImage);
        countdownOTP.setDisable(true);
    }

    public void mainApp() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/ac/id/unindra/spk/topsis/djingga/views/appView.fxml"));
        Scene scene = new Scene(root);

        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });

        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

    }

    @FXML
    private void backToLoginPage(MouseEvent event) throws IOException {
        activePane(loginPane);
    }

    @FXML
    private void registerPage(MouseEvent event) throws IOException {
        activePane(registerPane);

    }

    @FXML
    private void forgotPassword() {
        activePane(successPane);
        activeMessage(accountSuccessMessage, accountSuccessCreateImage);
        accountSuccessMessage.setText("TINGGAL SEDIKIT LAGI");
        labelInformation.setText("UNTUK MENGGANTI KATA SANDI");
    }

}

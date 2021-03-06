package ba.unsa.etf.rpr.project.controllers;

import ba.unsa.etf.rpr.project.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;


public class LoginController {
    public TextField fldUsername;
    public PasswordField fldPassword;
    public Button btnLogIn;
    public Button btnSignUp;
    public Button btnContinueAsGuest;
    public VBox vboxError;
    public Button btnChangePassword;

    private ScientificWorkDAO instance;

    public LoginController() {
    }

    @FXML
    public void initialize() {
        instance = ScientificWorkDAO.getInstance();
        fldUsername.getStyleClass().add("fieldNotValid");
        fldUsername.textProperty().addListener(
                (observableValue, o, n) -> {
                    if (fldUsername.getText().trim().isEmpty()) {
                        fldUsername.getStyleClass().removeAll("fieldValid");
                        fldUsername.getStyleClass().add("fieldNotValid");
                    } else {
                        fldUsername.getStyleClass().removeAll("fieldNotValid");
                        fldUsername.getStyleClass().add("fieldValid");
                    }
                }
        );
        fldPassword.getStyleClass().add("fieldNotValid");
        fldPassword.textProperty().addListener(
                (observableValue, o, n) -> {
                    if (fldPassword.getText().trim().isEmpty() || fldPassword.getText().trim().length() < 8) {
                        fldPassword.getStyleClass().removeAll("fieldValid");
                        fldPassword.getStyleClass().add("fieldNotValid");
                    } else {
                        fldPassword.getStyleClass().removeAll("fieldNotValid");
                        fldPassword.getStyleClass().add("fieldValid");
                    }
                }
        );
    }

    public void actionLogin() throws IOException {
        boolean test = instance.isAccount(fldUsername.getText(), fldPassword.getText());
        if (fldUsername.getStyleClass().contains("fieldNotValid") || fldPassword.getStyleClass().contains("fieldNotValid") || !test) {
            vboxError.setVisible(true);
        }
        else {
            vboxError.setVisible(false);
            fldUsername.getScene().getWindow().hide();
            if (instance.isAdministrator(fldUsername.getText(), fldPassword.getText())) {
                showAdministratorView();
            }
            else {
                showUserView();
            }
        }
    }

    public void showUserView() throws IOException {
        Stage stage = new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/home.fxml"), bundle);
        Parent root = loader.load();
        HomeController homeController = loader.getController();
        stage.setTitle(bundle.getString("appName"));
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setMaxWidth(1000);
        stage.setMinHeight(200);
        stage.setMinWidth(200);
        stage.show();
    }

    public void showAdministratorView() throws IOException {
        Stage stage = new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/homeAdministrator.fxml"), bundle);
        Parent root = loader.load();
        HomeAdminController homeController = loader.getController();
        stage.setTitle(bundle.getString("appName"));
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE,USE_COMPUTED_SIZE));
        stage.setMaxWidth(1000);
        stage.setMinHeight(240);
        stage.setMinWidth(480);
        stage.show();
    }

    public void actionSignUp() throws IOException {
        fldUsername.getScene().getWindow().hide();
        Stage stage = new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/signup.fxml"), bundle);
        Parent root = loader.load();
        stage.setTitle(bundle.getString("signup"));
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE,USE_COMPUTED_SIZE));
        stage.setMinHeight(370);
        stage.setMinWidth(320);
        stage.show();
    }

    public void actionContinueAsGuest() throws IOException {
        fldUsername.getScene().getWindow().hide();
        Stage stage = new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/guest.fxml"), bundle);
        Parent root = loader.load();
        stage.setTitle(bundle.getString("appName"));
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE,USE_COMPUTED_SIZE));
        stage.setMaxWidth(695);
        stage.setMinHeight(240);
        stage.setMinWidth(480);
        stage.show();
    }

    public void actionChangePassword() throws IOException {
        Stage stage = new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/password.fxml"), bundle);
        Parent root = loader.load();
        stage.setTitle(bundle.getString("password"));
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE,USE_COMPUTED_SIZE));
        stage.show();
    }
}
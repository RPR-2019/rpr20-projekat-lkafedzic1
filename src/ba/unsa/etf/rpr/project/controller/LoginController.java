package ba.unsa.etf.rpr.project.controller;

import ba.unsa.etf.rpr.project.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;


public class LoginController {
    public TextField fldUsername;
    public PasswordField fldPassword;
    public Button btnLogIn;
    public Button btnSignUp;
    public Button btnContinueAsGuest;
    public VBox vboxError;

    private ScientificWorkDAO instance;
    private ObservableList<User> usersList;

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

    public void actionLogin(ActionEvent actionEvent) throws IOException {
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

    private void showUserView() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/user.fxml"));
        Parent root = loader.load();
        MemberController homeController = loader.getController();
        stage.setTitle("Scientific works database");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setMaxWidth(1100);
        stage.setMinHeight(200);
        stage.setMinWidth(200);
        stage.show();
    }

    private void showAdministratorView() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/administrator.fxml"));
        Parent root = loader.load();
        AdminController homeController = loader.getController();
        stage.setTitle("Scientific works database");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE,USE_COMPUTED_SIZE));
        stage.setMaxWidth(1100);
        stage.setMinHeight(240);
        stage.setMinWidth(480);
        stage.show();
    }

    public void actionSignUp(ActionEvent actionEvent) throws IOException {
        fldUsername.getScene().getWindow().hide();
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/signup.fxml"));
        Parent root = loader.load();
        SignUpController signupWindow = loader.getController();
        stage.setTitle("Sign up");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE,USE_COMPUTED_SIZE));
        stage.setMinHeight(370);
        stage.setMinWidth(320);
        stage.show();
    }

    public void actionContinueAsGuest(ActionEvent actionEvent) throws IOException {
        fldUsername.getScene().getWindow().hide();
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/guest.fxml"));
        Parent root = loader.load();
        GuestController guestController = loader.getController();
        stage.setTitle("Scientific works database");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE,USE_COMPUTED_SIZE));
        stage.setMinHeight(240);
        stage.setMinWidth(480);
        stage.show();
    }
}

package ba.unsa.etf.rpr.project;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;


public class LoginController {
    public TextField fldUsername;
    public PasswordField fldPassword;
    public Button btnLogIn;
    public Button btnSignUp;
    public Button btnContinueAsGuest;

    private ScientificWorkDAO instance;

    public LoginController() {
    }

    @FXML
    public void initialize() {
/*
        instance = ScientificWorkDAO.getInstance();
*/

/*        fldUsername.getStyleClass().add("fieldNotValid");
        fldUsername.textProperty().addListener(
                new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                        if (fldUsername.getText().trim().isEmpty()) {
                            fldUsername.getStyleClass().removeAll("fieldValid");
                            fldUsername.getStyleClass().add("fieldNotValid");
                        } else {
                            fldUsername.getStyleClass().removeAll("fieldNotValid");
                            fldUsername.getStyleClass().add("fieldValid");
                        }
                    }
                }
        );*/
    }

    public void actionLogin(ActionEvent actionEvent) throws IOException {
        if (fldUsername.getText().isEmpty()) return;
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
        Parent root = loader.load();
        MainWindowController mainWindowController = loader.getController();
        stage.setTitle("Scientific works database");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE,USE_COMPUTED_SIZE));
/*        Stage window = (Stage) fldUsername.getScene().getWindow();
        window.close();*/
        stage.show();
    }

    public void actionSignUp(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/signup.fxml"));
        Parent root = loader.load();
        SignUpController signupWindow = loader.getController();
        stage.setTitle("Sign up");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE,USE_COMPUTED_SIZE));
/*        Stage window = (Stage) fldUsername.getScene().getWindow();
        window.close();*/
        stage.setMinHeight(370);
        stage.setMinWidth(320);
        stage.show();
    }

    public void actionContinueAsGuest(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/guest.fxml"));
        Parent root = loader.load();
        GuestController guestController = loader.getController();
        stage.setTitle("Scientific works database");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE,USE_COMPUTED_SIZE));
/*        Stage window = (Stage) fldUsername.getScene().getWindow();
        window.close();*/
        stage.show();
    }
}

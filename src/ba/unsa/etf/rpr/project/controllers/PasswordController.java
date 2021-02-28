package ba.unsa.etf.rpr.project.controllers;

import ba.unsa.etf.rpr.project.ScientificWorkDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class PasswordController {

    public TextField fldUsername;
    public PasswordField fldCurrentPassword;
    public PasswordField fldNewPassword;
    public PasswordField fldConfirmPassword;
    public Label lblIncorrect;
    public Label lblNewPasswordError;

    private ScientificWorkDAO instance;

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
        fldCurrentPassword.getStyleClass().add("fieldNotValid");
        fldCurrentPassword.textProperty().addListener(
                (observableValue, o, n) -> {
                    if (fldCurrentPassword.getText().trim().isEmpty() || fldCurrentPassword.getText().trim().length() < 8) {
                        fldCurrentPassword.getStyleClass().removeAll("fieldValid");
                        fldCurrentPassword.getStyleClass().add("fieldNotValid");
                    } else {
                        fldCurrentPassword.getStyleClass().removeAll("fieldNotValid");
                        fldCurrentPassword.getStyleClass().add("fieldValid");
                    }
                }
        );
        fldNewPassword.getStyleClass().add("fieldNotValid");
        fldNewPassword.textProperty().addListener(
                (observableValue, o, n) -> {
                    if (fldNewPassword.getText().trim().isEmpty() || fldNewPassword.getText().trim().length() < 8) {
                        fldNewPassword.getStyleClass().removeAll("fieldValid");
                        fldNewPassword.getStyleClass().add("fieldNotValid");
                    } else {
                        fldNewPassword.getStyleClass().removeAll("fieldNotValid");
                        fldNewPassword.getStyleClass().add("fieldValid");
                    }
                }
        );
        fldConfirmPassword.getStyleClass().add("fieldNotValid");
        fldConfirmPassword.textProperty().addListener(
                (observableValue, o, n) -> {
                    if (fldConfirmPassword.getText().trim().isEmpty() || fldConfirmPassword.getText().trim().length() < 8) {
                        fldConfirmPassword.getStyleClass().removeAll("fieldValid");
                        fldConfirmPassword.getStyleClass().add("fieldNotValid");
                    } else {
                        fldConfirmPassword.getStyleClass().removeAll("fieldNotValid");
                        fldConfirmPassword.getStyleClass().add("fieldValid");
                    }
                }
        );
    }
    
    public void actionSave() {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        String username = fldUsername.getText();
        String oldPassword = fldCurrentPassword.getText();
        //doesn't exist in database
        lblIncorrect.setVisible(isInvalidLength(fldCurrentPassword) || ! instance.isAccount(username, oldPassword));
        if (isInvalidLength(fldNewPassword)) {
            lblNewPasswordError.setVisible(true);
        }
        else if(fldCurrentPassword.getText().equals(fldNewPassword.getText())) {
            lblNewPasswordError.setText(bundle.getString("newCurrent"));
            lblNewPasswordError.setVisible(true);
        }
        else if (isInvalidLength(fldConfirmPassword) || !fldConfirmPassword.getText().equals(fldNewPassword.getText())) {
            lblNewPasswordError.setText(bundle.getString("notMatch"));
            lblNewPasswordError.setVisible(true);
        }
        else if (isInputValid(fldUsername) && isInputValid(fldCurrentPassword) && isInputValid(fldNewPassword) && isInputValid(fldConfirmPassword)) {
            lblNewPasswordError.setVisible(false);

            String newPassword = fldNewPassword.getText();
            instance.updatePassword(username, newPassword);
            fldUsername.getScene().getWindow().hide();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(bundle.getString("success"));
            alert.setHeaderText(null);
            alert.setContentText(bundle.getString("passwordChanged"));
            alert.showAndWait();
        }
    }

    private boolean isInputValid(TextField fld) {
        return fld.getStyleClass().stream().anyMatch(style -> style.equals("fieldValid"));
    }

    private boolean isInvalidLength(PasswordField field) {
        return field==null || field.getText().trim().length() < 8;
    }

    public void actionCancel() {
        Stage window = (Stage) fldUsername.getScene().getWindow();
        window.close();
    }
}

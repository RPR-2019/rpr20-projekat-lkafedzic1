package ba.unsa.etf.rpr.project.controllers;

import ba.unsa.etf.rpr.project.models.FileTypeFilter;
import ba.unsa.etf.rpr.project.models.ScientificWork;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class DocumentController {
    public Label lblTitle;
    public Label lblAuthor;
    public Label lblYear;
    public Label lblTags;
    public Label lblReference;
    public TextArea txtArea;
    public Label lblStatusBar;

    public RadioMenuItem itemEnglish;
    public RadioMenuItem itemBosnian;

    ScientificWork scientificWork = null;

    public DocumentController(ScientificWork scientificWork) {
        setScientificWork(scientificWork);
    }

    public void setScientificWork(ScientificWork scientificWork) {
        this.scientificWork = scientificWork;
    }

    @FXML
    public void initialize() {
        if (scientificWork!= null) {
            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            String name = scientificWork.getAuthor();
            String lastName = "";
            String firstName = "";
            if (name.split("\\w+").length > 1) {
                lastName = name.substring(name.lastIndexOf(" ") + 1);
                firstName = name.substring(0, name.lastIndexOf(' '));

            }
            String titleSentenceCase = Character.toUpperCase(scientificWork.getTitle().charAt(0)) + scientificWork.getTitle().substring(1);
            titleSentenceCase = lastName + ", " + firstName.charAt(0) + "." + "(" + scientificWork.getYear() + "). " + titleSentenceCase + ". " + scientificWork.getAdditional();

            lblTitle.setText(scientificWork.getTitle());
            lblAuthor.setText(name);
            lblYear.setText(scientificWork.getYear()+".");
            lblTags.setText(scientificWork.getTags());
            lblReference.setText(titleSentenceCase);
            lblStatusBar.setText(bundle.getString("reading") + lblTitle.getText());
            txtArea.setText(scientificWork.getContent());
        }

        itemEnglish.selectedProperty().addListener((obs, o, n) -> {
            if(n){
                itemBosnian.setSelected(false);
                Locale.setDefault(new Locale("en", "US"));
                Scene scene = lblTitle.getScene();
                ResourceBundle bundle = ResourceBundle.getBundle("Translation");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/document.fxml"), bundle);
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                scene.setRoot(root);
            }else if(o && !itemBosnian.isSelected()){
                itemEnglish.setSelected(true);
            }
        });

        itemBosnian.selectedProperty().addListener((obs, o, n) -> {
            if(n){
                itemEnglish.setSelected(false);
                Locale.setDefault(new Locale("bs", "BA"));
                Scene scene = lblTitle.getScene();
                ResourceBundle bundle = ResourceBundle.getBundle("Translation");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/document.fxml"), bundle);
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                scene.setRoot(root);
            }else if(o && !itemBosnian.isSelected()){
                itemBosnian.setSelected(true);
            }
        });
    }

    public void actionDownload(ActionEvent actionEvent) {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        if (scientificWork!= null) {
            JFileChooser chooser = new JFileChooser(new File("c::\\"));
            chooser.setDialogTitle(bundle.getString("saveDocument"));
            chooser.setFileFilter(new FileTypeFilter(".txt", bundle.getString("extension")));
            int result = chooser.showSaveDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                String content = scientificWork.getContent();
                File file = chooser.getSelectedFile();

                try {
                    FileWriter fw = new FileWriter(file.getPath());
                    fw.write(content);
                    fw.flush();
                    fw.close();
                    lblStatusBar.setText(bundle.getString("downloaded"));

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
            }
        }
    }

    public void actionClose(ActionEvent actionEvent) {
        Stage stage = (Stage) lblStatusBar.getScene().getWindow();
        stage.close();
    }

    public void actionAbout(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        Parent root;
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/about.fxml"), bundle);
        AboutController aboutController = new AboutController();
        loader.setController(aboutController);
        root = loader.load();

        stage.setTitle(bundle.getString("about"));
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE,USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
    }
}

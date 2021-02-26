package ba.unsa.etf.rpr.project.controllers;

import ba.unsa.etf.rpr.project.models.FileTypeFilter;
import ba.unsa.etf.rpr.project.models.ScientificWork;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;

public class DocumentController {
    public Label lblTitle;
    public Label lblAuthor;
    public Label lblYear;
    public Label lblTags;
    public Label lblReference;
    public TextArea txtArea;
    public Label lblStatusBar;

    ScientificWork scientificWork = null;

    public DocumentController(ScientificWork scientificWork) {
        setScientificWork(scientificWork);
    }

    public ScientificWork getScientificWork() {
        return scientificWork;
    }

    public void setScientificWork(ScientificWork scientificWork) {
        this.scientificWork = scientificWork;
    }

    @FXML
    public void initialize() {
        if (scientificWork!= null) {
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
            lblStatusBar.setText("Reading " + lblTitle.getText());
            txtArea.setText(scientificWork.getContent());
        }
    }

    public void actionDownload(ActionEvent actionEvent) {
        if (scientificWork!= null) {
            JFileChooser chooser = new JFileChooser(new File("c::\\"));
            chooser.setDialogTitle("Save document");

            chooser.setFileFilter(new FileTypeFilter(".txt", "Tekstualna datoteka"));
            int result = chooser.showSaveDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                String content = scientificWork.getContent();
                File file = chooser.getSelectedFile();

                try {
                    FileWriter fw = new FileWriter(file.getPath());
                    fw.write(content);
                    fw.flush();
                    fw.close();
                    lblStatusBar.setText("File successfully downloaded!");

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

    public void actionDelete(ActionEvent actionEvent) {
    }

    public void actionAbout(ActionEvent actionEvent) {
    }
}

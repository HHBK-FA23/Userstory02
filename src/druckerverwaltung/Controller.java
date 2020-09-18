package druckerverwaltung;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Controller implements Initializable {


    @FXML
    private TextField textfieldId;
    @FXML
    private TextField textfieldSeriennummer;
    @FXML
    private TextField textfieldModell;

    @FXML
    private TextField textfieldHersteller;
    @FXML
    private ChoiceBox<String> choiceboxStatus;
    @FXML
    private TextField textfieldHerstellergarantie;
    @FXML
    private DatePicker datepickerLieferdatum;
    @FXML
    private CheckBox checkboxFarbdruckfunktion;
    @FXML
    private ChoiceBox<String> choiceboxTechnologie;
    @FXML
    private ChoiceBox<String> choiceboxMaxPapierformat;

    @FXML
    private ListView<Drucker> listviewDrucker = new ListView<>();

    private ObservableList<Drucker> druckerObservableList;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadHardware();
        textfieldId.setText(String.valueOf(Drucker.getAnzahl() + 1));
        listviewDrucker.setItems(druckerObservableList);



        choiceboxStatus.setItems(FXCollections.observableArrayList(
                "ok",
                "in Reperatur",
                "defekt"));

        choiceboxTechnologie.setItems(FXCollections.observableArrayList(
                "Tintenstrahldrucker",
                "Farbtintenstrahldrucker",
                "Farblaserdrucker",
                "Laserdrucker"));

        choiceboxMaxPapierformat.setItems(FXCollections.observableArrayList(
                "A3",
                "A4"));

    }

    private void loadHardware() {
        druckerObservableList = FXCollections.observableArrayList(
                new Drucker(
                        "S001",
                        "HP DeskJet 2630",
                        "HP",
                        "ok",
                        12,
                        LocalDate.of(2019, 9, 3),
                        "Tintenstrahldrucker",
                        true,
                        "A4"
                ),
                new Drucker(
                        "S002",
                        "Samsung XPRESS C480FW",
                        "Samsung",
                        "ok",
                        24,
                        LocalDate.of(2019, 9, 3),
                        "Farblaserdrucker",
                        true,
                        "A4"
                ),
                new Drucker(
                        "S003",
                        "Brother MFC-J6930DW",
                        "Brother",
                        "ok",
                        36,
                        LocalDate.of(2019, 9, 3),
                        "Farbtintenstrahldrucker",
                        true,
                        "A3"
                )
        );
    }

    @FXML
    private void sichern(ActionEvent e) {
        //Kontrolliert falsche Eingaben
        String status = choiceboxStatus.getSelectionModel().getSelectedItem();
        if (status == null || status.compareTo("") == 0) {
            errWindow("Status nicht ausgewählt!");
            return;
        }

        String technologie = choiceboxTechnologie.getSelectionModel().getSelectedItem();
        if (technologie == null || technologie.compareTo("") == 0) {
            errWindow("Technologie nicht ausgewählt!");
            return;
        }

        String maxPapierFormat = choiceboxMaxPapierformat.getSelectionModel().getSelectedItem();
        if (maxPapierFormat == null || maxPapierFormat.compareTo("") == 0) {
            errWindow("Max Papierformat nicht ausgewählt!");
            return;
        }

        int herstellergarantie;
        try {
            herstellergarantie = Integer.parseInt(textfieldHerstellergarantie.getText());
            if (herstellergarantie < 0) {
                errWindow("Herrstellergarantie darf nicht negativ sein!");
            }
        } catch(NumberFormatException nfe) {
            errWindow("Nur Ganzzahlen in der Herstellergarantie eintragen");
            return;
        }

        if (datepickerLieferdatum.getValue() == null) {
            errWindow("Bitte Lieferdatum auswählen!");
            return;
        }

        if (textfieldSeriennummer.getText().equals("")) {
            errWindow("Bitte Seriennummer angeben!");
            return;
        }

        if (textfieldHersteller.getText().equals("")) {
            errWindow("Bitte Hersteller angeben!");
            return;
        }

        if (textfieldModell.getText().equals("")) {
            errWindow("Bitte Modell angeben!");
            return;
        }

        //Erzeugt einen neuen Drucker und fügt ihn hinzu
        Drucker d = new Drucker(
                textfieldSeriennummer.getText(),
                textfieldModell.getText(),
                textfieldHersteller.getText(),
                status,
                herstellergarantie,
                datepickerLieferdatum.getValue(),
                technologie,
                checkboxFarbdruckfunktion.isSelected(),
                maxPapierFormat
        );
        listviewDrucker.getItems().add(d);
        clear();
        e.consume();
    }

    private void clear() {
        textfieldId.setText(String.valueOf(Drucker.getAnzahl() + 1));
        textfieldSeriennummer.setText("");
        textfieldModell.setText("");
        textfieldHersteller.setText("");
        textfieldHerstellergarantie.setText("");

        datepickerLieferdatum.setValue(null);
        checkboxFarbdruckfunktion.setSelected(false);

        choiceboxStatus.setValue(null);
        choiceboxTechnologie.setValue(null);
        choiceboxMaxPapierformat.setValue(null);
    }

    @FXML
    private void abbrechen(ActionEvent e) {
        clear();
        e.consume();
    }

    private void errWindow(String s) {
        new Alert(Alert.AlertType.ERROR, s).show();
    }
}

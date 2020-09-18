package druckerverwaltung;

import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.time.LocalDate;

public class Drucker {
    private static int anzahl;
    private final int id;
    private String seriennummer;
    private String modell;
    private String hersteller;
    private String status;
    private int herstellergarantie;
    private LocalDate lieferdatum;
    private String technologie;
    private boolean farbdruckfunktion;
    private String paperformatmax;
    private int druckseitengesamt;
    private int restkapazitaet;
    private int kapazitaetbetriebsmittel;

    public Drucker() {
        anzahl++;
        id = anzahl;
        setStatus("ok");
        setKapazitaetbetriebsmittel(200);
        setRestkapazitaet(200);
    }

    public Drucker(String seriennummer,
                   String modell,
                   String hersteller,
                   String status,
                   int herstellergarantie,
                   LocalDate lieferdatum,
                   String technologie,
                   boolean farbdruckfunktion,
                   String paperformatmax) {
        this();
        this.seriennummer = seriennummer;
        this.modell = modell;
        this.hersteller = hersteller;
        this.status = status;
        this.herstellergarantie = herstellergarantie;
        this.lieferdatum = lieferdatum;
        this.technologie = technologie;
        this.farbdruckfunktion = farbdruckfunktion;
        this.paperformatmax = paperformatmax;
    }

    public static int getAnzahl() {
        return anzahl;
    }

    public int getId() {
        return id;
    }

    public String getSeriennummer() {
        return seriennummer;
    }

    public String getModell() {
        return modell;
    }

    public String getHersteller() {
        return hersteller;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getHerstellergarantie() {
        return herstellergarantie;
    }

    public LocalDate getLieferdatum() {
        return lieferdatum;
    }

    public String getTechnologie() {
        return technologie;
    }

    public boolean isFarbdruckfunktion() {
        return farbdruckfunktion;
    }

    public String getPaperformatmax() {
        return paperformatmax;
    }

    public int getDruckseitengesamt() {
        return druckseitengesamt;
    }

    public void setDruckseitengesamt(int druckseitengesamt) {
        this.druckseitengesamt = druckseitengesamt;
    }

    public int getRestkapazitaet() {
        return restkapazitaet;
    }

    public void setRestkapazitaet(int restkapazitaet) {
        this.restkapazitaet = restkapazitaet;
    }

    public int getKapazitaetbetriebsmittel() {
        return kapazitaetbetriebsmittel;
    }

    public void setKapazitaetbetriebsmittel(int kapazitaetbetriebsmittel) {
        this.kapazitaetbetriebsmittel = kapazitaetbetriebsmittel;
    }

    @Override
    public String toString() {
        String result = "";
        result += getId() + ";"
                + getSeriennummer() + ";"
                + getModell() + ";"
                + getHersteller() + ";"
                + getStatus() + ";"
                + getHerstellergarantie() + ";"
                + getLieferdatum() + ";"
                + getTechnologie() + ";"
                + isFarbdruckfunktion() + ";"
                + getPaperformatmax() + ";"
                + getDruckseitengesamt() + ";"
                + getRestkapazitaet() + ";"
                + getKapazitaetbetriebsmittel()
        ;
        return result;
    }

    public void wechsleBetriebsmittel(int kapazitaet) {
        if(kapazitaet <= 0) {
            Alert error = new Alert(Alert.AlertType.ERROR, "Kapaztät muss größer als 0 sein!");

            error.setResizable(true);
            error.onShownProperty().addListener(e -> Platform.runLater(() -> error.setResizable(false)));

            error.showAndWait();
        } else {
            this.restkapazitaet = kapazitaet;
            this.kapazitaetbetriebsmittel = kapazitaet;
        }


    }

    public LocalDate berechneGarantieende() {
        return getLieferdatum().plusMonths(getHerstellergarantie());
    }

    public void drucken(int anzahlseiten) {
        if(anzahlseiten <= 0) {
            Alert error = new Alert(Alert.AlertType.ERROR, "Anzahl Seiten muss größer 0 sein!");
            error.showAndWait();
        } else {
            if(anzahlseiten > getRestkapazitaet()) {
                String s = "Es konnten nur " + getRestkapazitaet() + " Seiten gedruckt werden!\n" +
                        "Bitte wechseln Sie das Betriebsmittel!";
                Alert warning = new Alert(Alert.AlertType.WARNING, s);

                setDruckseitengesamt(getDruckseitengesamt() + getRestkapazitaet());

                setRestkapazitaet(0);

                warning.setResizable(true);
                warning.onShownProperty().addListener(e -> Platform.runLater(() -> warning.setResizable(false)));

                warning.showAndWait();
            } else {
                Alert info = new Alert(Alert.AlertType.INFORMATION, "" + anzahlseiten + " Seiten erfolgreich gedruckt!");
                setRestkapazitaet(getRestkapazitaet() - anzahlseiten);

                setDruckseitengesamt(getDruckseitengesamt() + anzahlseiten);

                info.setResizable(true);
                info.onShownProperty().addListener(e -> Platform.runLater(() -> info.setResizable(false)));

                info.showAndWait();
            }
        }
    }
}

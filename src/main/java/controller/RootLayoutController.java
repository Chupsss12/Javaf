package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RootLayoutController implements Initializable {
    @FXML
    private BorderPane pane;

    private ObservableList<Antropological> antropologicalList;
    private ObservableList<Coach> coachList;

    private ObservableList<CoachHistory> coachHistoryList;

    private ObservableList<Competition> competitionList;

    private ObservableList<Gym> gymList;

    private ObservableList<Participant> participantList;
    private ObservableList<Sponsor> sponsorList;

    private ObservableList<SportClub> sportClubList;

    private ObservableList<Transport> transportList;

    @FXML
    public void onAntropologicalSelected() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Antropological.fxml"));
            Parent root = loader.load();
            AntropologicalController api = loader.getController();
            api.setRoot(this);
            pane.setCenter(root);

        } catch (IOException ex) {
            Logger.getLogger(AntropologicalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void onCoachesSelected() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Coach.fxml"));
            Parent root = loader.load();
            CoachController api = loader.getController();
            api.setRoot(this);
            pane.setCenter(root);

        } catch (IOException ex) {
            Logger.getLogger(CoachController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void onCoachHistorySelected() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../CoachHistory.fxml"));
            Parent root = loader.load();
            CoachHistoryController api = loader.getController();
            api.setRoot(this);
            pane.setCenter(root);

        } catch (IOException ex) {
            Logger.getLogger(CoachHistoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void onCompetitionSelected() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Competition.fxml"));
            Parent root = loader.load();
            CompetitionController api = loader.getController();
            api.setRoot(this);
            pane.setCenter(root);

        } catch (IOException ex) {
            Logger.getLogger(CompetitionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void onGymSelected() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Gym.fxml"));
            Parent root = loader.load();
            GymController api = loader.getController();
            api.setRoot(this);
            pane.setCenter(root);

        } catch (IOException ex) {
            Logger.getLogger(GymController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void onParticipantSelected() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Participant.fxml"));
            Parent root = loader.load();
            ParticipantController api = loader.getController();
            api.setRoot(this);
            pane.setCenter(root);

        } catch (IOException ex) {
            Logger.getLogger(ParticipantController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void onSponsorsSelected() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Sponsor.fxml"));
            Parent root = loader.load();
            SponsorController api = loader.getController();
            api.setRoot(this);
            pane.setCenter(root);

        } catch (IOException ex) {
            Logger.getLogger(SponsorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @FXML
    public void onSportClubSelected() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../SportClub.fxml"));
            Parent root = loader.load();
            SportClubController api = loader.getController();
            api.setRoot(this);
            pane.setCenter(root);

        } catch (IOException ex) {
            Logger.getLogger(SportClubController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void onTransportSelected() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Transport.fxml"));
            Parent root = loader.load();
            TransportController api = loader.getController();
            api.setRoot(this);
            pane.setCenter(root);

        } catch (IOException ex) {
            Logger.getLogger(TransportController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        antropologicalList = FXCollections.observableArrayList();
        coachList = FXCollections.observableArrayList();
        coachHistoryList = FXCollections.observableArrayList();
        competitionList = FXCollections.observableArrayList();
        gymList = FXCollections.observableArrayList();
        participantList = FXCollections.observableArrayList();
        sponsorList = FXCollections.observableArrayList();
        sportClubList = FXCollections.observableArrayList();
        transportList = FXCollections.observableArrayList();
        onCoachesSelected();
    }

    public ObservableList<Antropological> getAntropologicalList() {
        return antropologicalList;
    }

    public ObservableList<Coach> getCoachList() {
        return coachList;
    }

    public ObservableList<CoachHistory> getCoachHistoryList() {
        return coachHistoryList;
    }

    public ObservableList<Competition> getCompetitionList() {
        return competitionList;
    }

    public ObservableList<Gym> getGymList() {
        return gymList;
    }

    public ObservableList<Participant> getParticipantList() {
        return participantList;
    }

    public ObservableList<Sponsor> getSponsorList() {
        return sponsorList;
    }

    public ObservableList<SportClub> getSportClubList() {
        return sportClubList;
    }

    public ObservableList<Transport> getTransportList() {
        return transportList;
    }
}

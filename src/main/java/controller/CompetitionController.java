package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.*;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CompetitionController extends AbstractFormController<Competition> {
    @FXML
    private Button editButton;

    @FXML
    private Button newButton;

    @FXML
    private TextField idCompetitionField;

    @FXML
    private TextField clubField;

    @FXML
    private TextField yearField;

    @FXML
    private TextField participantField;

    @FXML
    private TableView<Competition> competitionTable;

    @FXML
    private TableColumn<Competition, Integer> idCompetitionColumn;

    @FXML
    private TableColumn<Competition, SportClub> clubColumn;

    @FXML
    private TableColumn<Competition, Integer> yearColumn;

    @FXML
    private TableColumn<Competition, Participant> participantColumn;

    @Override
    protected void setData() {
        competitionTable.setItems(getRoot().getCompetitionList());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idCompetitionColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        clubColumn.setCellValueFactory(new PropertyValueFactory<>("sportClub"));
        clubColumn.setCellFactory(column -> {
            TableCell<Competition, SportClub> cell = new TableCell<Competition, SportClub>() {
                @Override
                protected void updateItem(SportClub item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty) {
                        setText(null);
                    }
                    else {
                        this.setText(Integer.toString(item.getIdclub()));

                    }
                }
            };

            return cell;
        });
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        participantColumn.setCellValueFactory(new PropertyValueFactory<>("participant"));
        participantColumn.setCellFactory(column -> {
            TableCell<Competition, Participant> cell = new TableCell<Competition, Participant>() {
                @Override
                protected void updateItem(Participant item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty) {
                        setText(null);
                    }
                    else {
                        this.setText(Integer.toString(item.getIdparticipant()));

                    }
                }
            };

            return cell;
        });

        competitionTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        competitionTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection == null) {
                clearFields();
                editButton.setDisable(true);
            }
            else {
                fillFieldsFields(newSelection);
                editButton.setDisable(false);
            }
        });
        competitionTable.getSelectionModel().clearSelection();
        newButton.setDisable(false);
        editButton.setDisable(true);
    }

    @Override
    protected void clearFields() {
        idCompetitionField.setText("");
        clubField.setText("");
        yearField.setText("");
        participantField.setText("");
    }

    @Override
    protected void fillFieldsFields(Competition competition) {
        idCompetitionField.setText(Integer.toString(competition.getId()));
        clubField.setText(Integer.toString(competition.getSportClub().getIdclub()));
        yearField.setText(Integer.toString(competition.getYear()));
        participantField.setText(Integer.toString(competition.getParticipant().getIdparticipant()));
    }

    @FXML
    public void onNewClick() {
        processClick(true, Competition::new, c -> competitionTable.getItems().add(c));
    }

    @FXML
    public void onEditClick() {
        processClick(false, () -> competitionTable.getSelectionModel().getSelectedItem(), c -> competitionTable.refresh());
    }

    @Override
    protected void processClick(boolean isNew, Supplier<Competition> supplier, Consumer<Competition> consumer) {
        Competition competition = supplier.get();
        int idCompetition;
        try {
            idCompetition = Integer.parseInt(idCompetitionField.getText());
        }
        catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Can not parse integer from ID field");
            a.showAndWait();
            return;
        }

        int idClub;
        try {
            idClub = Integer.parseInt(clubField.getText());
        }
        catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Can not parse integer from club field");
            a.showAndWait();
            return;
        }

        int year;
        try {
            year = Integer.parseInt(yearField.getText());
        }
        catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Can not parse integer from year field");
            a.showAndWait();
            return;
        }

        int idParticipant;
        try {
            idParticipant = Integer.parseInt(participantField.getText());
        }
        catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Can not parse integer from participant field");
            a.showAndWait();
            return;
        }

        if (validate(isNew, idCompetition, idClub, year, idParticipant)) {
            competition.setId(idCompetition);
            competition.setSportClub(getRoot().getSportClubList().stream().filter(sc -> sc.getIdclub() == idClub).findAny().orElse(null));
            competition.setYear(year);
            competition.setParticipant(getRoot().getParticipantList().stream().filter(p -> p.getIdparticipant() == idParticipant).findAny().orElse(null));
            consumer.accept(competition);
        }
    }

    private boolean validate(boolean isNew, int idCompetition, int idClub, int year, int idParticipant) {
        if (isNew && competitionTable.getItems().stream().anyMatch(c -> c.getId() == idCompetition)) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Competition ID already exists");
            a.showAndWait();
            return false;
        }
        if (idCompetition < 0) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Competition ID must be non-negative");
            a.showAndWait();
            return false;
        }
        if (year < 1900) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Competition year must be greater than 1900");
            a.showAndWait();
            return false;
        }
        if (getRoot().getSportClubList().stream().noneMatch(c -> c.getIdclub() == idClub)) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Club with given ID does not exist");
            a.showAndWait();
            return false;
        }
        if (getRoot().getParticipantList().stream().noneMatch(p -> p.getIdparticipant() == idParticipant)) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Participant with given ID does not exist");
            a.showAndWait();
            return false;
        }
        return true;
    }
}

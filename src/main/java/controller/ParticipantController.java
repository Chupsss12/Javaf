package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Antropological;
import model.Participant;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ParticipantController extends AbstractFormController<Participant> {
    @FXML
    private Button editButton;

    @FXML
    private Button newButton;

    @FXML
    private TextField idParticipantField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField birthDayField;

    @FXML
    private TextField rankField;

    @FXML
    private TextField antropologicalField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField roleField;

    @FXML
    private TableView<Participant> participantTable;

    @FXML
    private TableColumn<Participant, Integer> idParticipantColumn;

    @FXML
    private TableColumn<Participant, String> nameColumn;

    @FXML
    private TableColumn<Participant, Date> birthDayColumn;

    @FXML
    private TableColumn<Participant, String> rankColumn;

    @FXML
    private TableColumn<Participant, Antropological> antropologicalColumn;

    @FXML
    private TableColumn<Participant, Integer> phoneNumberColumn;

    @FXML
    private TableColumn<Participant, String> roleColumn;

    @Override
    protected void setData() {
        participantTable.setItems(getRoot().getParticipantList());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idParticipantColumn.setCellValueFactory(new PropertyValueFactory<>("idparticipant"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        birthDayColumn.setCellValueFactory(new PropertyValueFactory<>("birthDay"));
        birthDayColumn.setCellFactory(column -> {
            TableCell<Participant, Date> cell = new TableCell<Participant, Date>() {
                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty) {
                        setText(null);
                    }
                    else {
                        this.setText(dateToStr(item));

                    }
                }
            };

            return cell;
        });
        rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));
        antropologicalColumn.setCellValueFactory(new PropertyValueFactory<>("antropological_folder"));
        antropologicalColumn.setCellFactory(column -> {
            TableCell<Participant, Antropological> cell = new TableCell<Participant, Antropological>() {
                @Override
                protected void updateItem(Antropological item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty) {
                        setText(null);
                    }
                    else {
                        this.setText(Integer.toString(item.getIdAntropological()));

                    }
                }
            };

            return cell;
        });

        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        participantTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        participantTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection == null) {
                clearFields();
                editButton.setDisable(true);
            }
            else {
                fillFieldsFields(newSelection);
                editButton.setDisable(false);
            }
        });
        participantTable.getSelectionModel().clearSelection();
        newButton.setDisable(false);
        editButton.setDisable(true);
    }

    @Override
    protected void clearFields() {
        idParticipantField.setText("");
        nameField.setText("");
        birthDayField.setText("");
        rankField.setText("");
        antropologicalField.setText("");
        phoneNumberField.setText("");
        roleField.setText("");
    }

    @Override
    protected void fillFieldsFields(Participant participant) {
        idParticipantField.setText(Integer.toString(participant.getIdparticipant()));
        nameField.setText(participant.getName());
        birthDayField.setText(dateToStr(participant.getBirthDay()));
        rankField.setText(participant.getRank());
        antropologicalField.setText(Integer.toString(participant.getAntropological_folder().getIdAntropological()));
        phoneNumberField.setText(Integer.toString(participant.getPhoneNumber()));
        roleField.setText(participant.getRole());
    }

    @FXML
    public void onNewClick() {
        processClick(true, Participant::new, p -> participantTable.getItems().add(p));
    }

    @FXML
    public void onEditClick() {
        processClick(false, () -> participantTable.getSelectionModel().getSelectedItem(), p -> participantTable.refresh());
    }

    @Override
    protected void processClick(boolean isNew, Supplier<Participant> supplier, Consumer<Participant> consumer) {
        Participant participant = supplier.get();
        int idParticipant;
        try {
            idParticipant = Integer.parseInt(idParticipantField.getText());
        }
        catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Can not parse integer from ID field");
            a.showAndWait();
            return;
        }
        String name = nameField.getText();

        Date birthDay = tryParseDate(birthDayField.getText());
        if (birthDay == null) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Can not parse date from birthday field (required format 'dd-MM-yyyy')");
            a.showAndWait();
            return;
        }

        String rank = rankField.getText();

        int idAntropological;
        try {
            idAntropological = Integer.parseInt(antropologicalField.getText());
        }
        catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Can not parse integer from antropological field");
            a.showAndWait();
            return;
        }

        int phoneNumber;
        try {
            phoneNumber = Integer.parseInt(phoneNumberField.getText());
        }
        catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Can not parse integer from phoneNumber field");
            a.showAndWait();
            return;
        }

        String role = roleField.getText();

        if (validate(isNew, idParticipant, name, birthDay, rank, idAntropological, phoneNumber, role)) {
            participant.setIdparticipant(idParticipant);
            participant.setName(name);
            participant.setBirthDay(birthDay);
            participant.setRank(rank);
            participant.setAntropological_folder(getRoot().getAntropologicalList().stream().filter(a -> a.getIdAntropological() == idAntropological).findAny().orElse(null));
            participant.setPhoneNumber(phoneNumber);
            participant.setRole(role);
            consumer.accept(participant);
        }
    }

    private boolean validate(boolean isNew, int idParticipant, String name, Date birthDay, String rank, int idAntropological, int phoneNumber, String role) {
        if (isNew && participantTable.getItems().stream().anyMatch(p -> p.getIdparticipant() == idParticipant)) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Participant ID already exists");
            a.showAndWait();
            return false;
        }
        if (idParticipant < 0) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Participant ID must be non-negative");
            a.showAndWait();
            return false;
        }
        if (name.isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Participant name must not be empty");
            a.showAndWait();
            return false;
        }
        if (getRoot().getAntropologicalList().stream().noneMatch(a -> a.getIdAntropological() == idAntropological)) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Antropological with given ID does not exist");
            a.showAndWait();
            return false;
        }
        if (phoneNumber <= 0) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Participant phone number must be positive");
            a.showAndWait();
            return false;
        }
        return true;
    }
}

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

public class SportClubController extends AbstractFormController<SportClub> {
    @FXML
    private Button editButton;

    @FXML
    private Button newButton;

    @FXML
    private TextField idClubField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField dateCreationField;

    @FXML
    private TextField cityField;

    @FXML
    private TextField coachField;

    @FXML
    private TextField sponsorField;

    @FXML
    private TextField gymField;

    @FXML
    private TextField transportField;

    @FXML
    private TableView<SportClub> sportClubTable;

    @FXML
    private TableColumn<SportClub, Integer> idClubColumn;

    @FXML
    private TableColumn<SportClub, String> nameColumn;

    @FXML
    private TableColumn<SportClub, Date> dateCreationColumn;

    @FXML
    private TableColumn<SportClub, String> cityColumn;

    @FXML
    private TableColumn<SportClub, Coach> coachColumn;

    @FXML
    private TableColumn<SportClub, Sponsor> sponsorColumn;

    @FXML
    private TableColumn<SportClub, Gym> gymColumn;

    @FXML
    private TableColumn<SportClub, Transport> transportColumn;

    @Override
    protected void setData() {
        sportClubTable.setItems(getRoot().getSportClubList());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idClubColumn.setCellValueFactory(new PropertyValueFactory<>("idclub"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        dateCreationColumn.setCellValueFactory(new PropertyValueFactory<>("date_creation"));
        dateCreationColumn.setCellFactory(column -> {
            TableCell<SportClub, Date> cell = new TableCell<SportClub, Date>() {
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
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        coachColumn.setCellValueFactory(new PropertyValueFactory<>("maincoach_folder"));
        coachColumn.setCellFactory(column -> {
            TableCell<SportClub, Coach> cell = new TableCell<SportClub, Coach>() {
                @Override
                protected void updateItem(Coach item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty) {
                        setText(null);
                    }
                    else {
                        this.setText(Integer.toString(item.getIdcoach()));

                    }
                }
            };

            return cell;
        });

        sponsorColumn.setCellValueFactory(new PropertyValueFactory<>("sponsor_folder"));
        sponsorColumn.setCellFactory(column -> {
            TableCell<SportClub, Sponsor> cell = new TableCell<SportClub, Sponsor>() {
                @Override
                protected void updateItem(Sponsor item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty) {
                        setText(null);
                    }
                    else {
                        this.setText(Integer.toString(item.getIdsponsor()));

                    }
                }
            };

            return cell;
        });

        gymColumn.setCellValueFactory(new PropertyValueFactory<>("gym"));
        gymColumn.setCellFactory(column -> {
            TableCell<SportClub, Gym> cell = new TableCell<SportClub, Gym>() {
                @Override
                protected void updateItem(Gym item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty) {
                        setText(null);
                    }
                    else {
                        this.setText(Integer.toString(item.getIdgym()));

                    }
                }
            };

            return cell;
        });

        transportColumn.setCellValueFactory(new PropertyValueFactory<>("transport"));
        transportColumn.setCellFactory(column -> {
            TableCell<SportClub, Transport> cell = new TableCell<SportClub, Transport>() {
                @Override
                protected void updateItem(Transport item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty) {
                        setText(null);
                    }
                    else {
                        this.setText(Integer.toString(item.getIdtransport()));

                    }
                }
            };

            return cell;
        });

        sportClubTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        sportClubTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection == null) {
                clearFields();
                editButton.setDisable(true);
            }
            else {
                fillFieldsFields(newSelection);
                editButton.setDisable(false);
            }
        });
        sportClubTable.getSelectionModel().clearSelection();
        newButton.setDisable(false);
        editButton.setDisable(true);
    }

    @Override
    protected void clearFields() {
        idClubColumn.setText("");
        nameField.setText("");
        dateCreationField.setText("");
        cityField.setText("");
        coachField.setText("");
        sponsorField.setText("");
        gymField.setText("");
        transportField.setText("");
    }

    @Override
    protected void fillFieldsFields(SportClub sportClub) {
        idClubColumn.setText(Integer.toString(sportClub.getIdclub()));
        nameField.setText(sportClub.getName());
        dateCreationField.setText(dateToStr(sportClub.getDate_creation()));
        cityField.setText(sportClub.getCity());
        coachField.setText(Integer.toString(sportClub.getMaincoach_folder().getIdcoach()));
        sponsorField.setText(Integer.toString(sportClub.getSponsor_folder().getIdsponsor()));
        gymField.setText(Integer.toString(sportClub.getGym().getIdgym()));
        transportField.setText(Integer.toString(sportClub.getTransport().getIdtransport()));
    }

    @FXML
    public void onNewClick() {
        processClick(true, SportClub::new, sc -> sportClubTable.getItems().add(sc));
    }

    @FXML
    public void onEditClick() {
        processClick(false, () -> sportClubTable.getSelectionModel().getSelectedItem(), sc -> sportClubTable.refresh());
    }

    @Override
    protected void processClick(boolean isNew, Supplier<SportClub> supplier, Consumer<SportClub> consumer) {
        SportClub sportClub = supplier.get();
        int idClub;
        try {
            idClub = Integer.parseInt(idClubField.getText());
        }
        catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Can not parse integer from ID field");
            a.showAndWait();
            return;
        }
        String name = nameField.getText();

        Date dateCreation = tryParseDate(dateCreationField.getText());
        if (dateCreation == null) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Can not parse date from date creation field (required format 'dd-MM-yyyy')");
            a.showAndWait();
            return;
        }

        String city = cityField.getText();

        int idCoach;
        try {
            idCoach = Integer.parseInt(coachField.getText());
        }
        catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Can not parse integer from coach field");
            a.showAndWait();
            return;
        }

        int idSponsor;
        try {
            idSponsor = Integer.parseInt(sponsorField.getText());
        }
        catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Can not parse integer from sponsor field");
            a.showAndWait();
            return;
        }

        int idGym;
        try {
            idGym = Integer.parseInt(gymField.getText());
        }
        catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Can not parse integer from gym field");
            a.showAndWait();
            return;
        }


        int idTransport;
        try {
            idTransport = Integer.parseInt(transportField.getText());
        }
        catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Can not parse integer from transport field");
            a.showAndWait();
            return;
        }


        if (validate(isNew, idClub, name, dateCreation, city, idCoach, idSponsor, idGym, idTransport)) {
            sportClub.setIdclub(idClub);
            sportClub.setName(name);
            sportClub.setDate_creation(dateCreation);
            sportClub.setCity(city);
            sportClub.setMaincoach_folder(getRoot().getCoachList().stream().filter(c -> c.getIdcoach() == idCoach).findAny().orElse(null));
            sportClub.setSponsor_folder(getRoot().getSponsorList().stream().filter(s -> s.getIdsponsor() == idSponsor).findAny().orElse(null));
            sportClub.setGym(getRoot().getGymList().stream().filter(c -> c.getIdgym() == idGym).findAny().orElse(null));
            sportClub.setTransport(getRoot().getTransportList().stream().filter(t -> t.getIdtransport() == idTransport).findAny().orElse(null));
            consumer.accept(sportClub);
        }
    }

    private boolean validate(boolean isNew, int idClub, String name, Date dateCreation, String city, int idCoach, int idSponsor, int idGym, int idTransport) {
        if (isNew && sportClubTable.getItems().stream().anyMatch(sc -> sc.getIdclub() == idClub)) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("SportClub ID already exists");
            a.showAndWait();
            return false;
        }
        if (idClub < 0) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("SportClub ID must be non-negative");
            a.showAndWait();
            return false;
        }
        if (name.isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("SportClub name must not be empty");
            a.showAndWait();
            return false;
        }
        if (city.isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("SportClub city must not be empty");
            a.showAndWait();
            return false;
        }
        if (getRoot().getCoachList().stream().noneMatch(c -> c.getIdcoach() == idCoach)) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Coach with given ID does not exist");
            a.showAndWait();
            return false;
        }
        if (getRoot().getSponsorList().stream().noneMatch(s -> s.getIdsponsor() == idSponsor)) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Sponsor with given ID does not exist");
            a.showAndWait();
            return false;
        }
        if (getRoot().getGymList().stream().noneMatch(g -> g.getIdgym() == idGym)) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Gym with given ID does not exist");
            a.showAndWait();
            return false;
        }
        if (getRoot().getTransportList().stream().noneMatch(t -> t.getIdtransport() == idTransport)) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Transport with given ID does not exist");
            a.showAndWait();
            return false;
        }
        return true;
    }
}

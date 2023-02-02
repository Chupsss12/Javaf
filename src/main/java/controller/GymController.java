package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Coach;
import model.Gym;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class GymController extends AbstractFormController<Gym> {
    @FXML
    private Button editButton;

    @FXML
    private Button newButton;

    @FXML
    private TextField idGymField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField categoryField;

    @FXML
    private TextField capacityField;

    @FXML
    private TableView<Gym> gymTable;

    @FXML
    private TableColumn<Coach, Integer> idGymColumn;

    @FXML
    private TableColumn<Coach, String> nameColumn;

    @FXML
    private TableColumn<Coach, Integer> phoneNumberColumn;

    @FXML
    private TableColumn<Coach, String> addressColumn;

    @FXML
    private TableColumn<Coach, String> categoryColumn;

    @FXML
    private TableColumn<Coach, Integer> capacityColumn;

    @Override
    protected void setData() {
        gymTable.setItems(getRoot().getGymList());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idGymColumn.setCellValueFactory(new PropertyValueFactory<>("idgym"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));

        gymTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        gymTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection == null) {
                clearFields();
                editButton.setDisable(true);
            }
            else {
                fillFieldsFields(newSelection);
                editButton.setDisable(false);
            }
        });
        gymTable.getSelectionModel().clearSelection();
        newButton.setDisable(false);
        editButton.setDisable(true);
    }

    @Override
    protected void clearFields() {
        idGymField.setText("");
        nameField.setText("");
        phoneNumberField.setText("");;
        addressField.setText("");;
        categoryColumn.setText("");;
        capacityColumn.setText("");
    }

    @Override
    protected void fillFieldsFields(Gym gym) {
        idGymField.setText(Integer.toString(gym.getIdgym()));
        nameField.setText(gym.getName());
        phoneNumberField.setText(Integer.toString(gym.getPhoneNumber()));
        addressField.setText(gym.getAddress());
        categoryField.setText(gym.getCategory());
        capacityField.setText(Integer.toString(gym.getCapacity()));
    }

    @FXML
    public void onNewClick() {
        processClick(true, Gym::new, g -> gymTable.getItems().add(g));
    }

    @FXML
    public void onEditClick() {
        processClick(false, () -> gymTable.getSelectionModel().getSelectedItem(), g -> gymTable.refresh());
    }

    @Override
    protected void processClick(boolean isNew, Supplier<Gym> supplier, Consumer<Gym> consumer) {
        Gym gym = supplier.get();
        int idGym;
        try {
            idGym = Integer.parseInt(idGymField.getText());
        }
        catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Can not parse integer from ID field");
            a.showAndWait();
            return;
        }
        String name = nameField.getText();
        int phoneNumber;
        try {
            phoneNumber = Integer.parseInt(phoneNumberField.getText());
        }
        catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Can not parse integer from phone number field");
            a.showAndWait();
            return;
        }

        String address = addressField.getText();
        String category = categoryField.getText();
        int capacity;
        try {
            capacity = Integer.parseInt(capacityField.getText());
        }
        catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Can not parse integer from capacity field");
            a.showAndWait();
            return;
        }
        if (validate(isNew, idGym, name, phoneNumber, address, category, capacity)) {
            gym.setIdgym(idGym);
            gym.setName(name);
            gym.setPhoneNumber(phoneNumber);
            gym.setAddress(address);
            gym.setCategory(category);
            gym.setCapacity(capacity);
            consumer.accept(gym);
        }
    }

    private boolean validate(boolean isNew, int idGym, String name, int phoneNumber, String address, String category, int capacity) {
        if (isNew && gymTable.getItems().stream().anyMatch(g -> g.getIdgym() == idGym)) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Gym ID already exists");
            a.showAndWait();
            return false;
        }
        if (idGym < 0) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Gym ID must be non-negative");
            a.showAndWait();
            return false;
        }
        if (name.isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Gym name must not be empty");
            a.showAndWait();
            return false;
        }

        if (phoneNumber <= 0) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Gym phone number must be positive");
            a.showAndWait();
            return false;
        }

        if (address.isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Gym address must not be empty");
            a.showAndWait();
            return false;
        }

        if (capacity < 0) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Gym capacity must be non-negative");
            a.showAndWait();
            return false;
        }
        return true;
    }
}

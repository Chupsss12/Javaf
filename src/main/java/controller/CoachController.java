package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Coach;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CoachController extends AbstractFormController<Coach> {
    @FXML
    private Button editButton;

    @FXML
    private Button newButton;

    @FXML
    private TextField idCoachField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField ageField;

    @FXML
    private TextField rankField;

    @FXML
    private TableView<Coach> coachTable;

    @FXML
    private TableColumn<Coach, Integer> idCoachColumn;

    @FXML
    private TableColumn<Coach, String> nameColumn;

    @FXML
    private TableColumn<Coach, Integer> ageColumn;

    @FXML
    private TableColumn<Coach, String> rankColumn;

    @Override
    protected void setData() {
        coachTable.setItems(getRoot().getCoachList());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idCoachColumn.setCellValueFactory(new PropertyValueFactory<>("idcoach"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));

        coachTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        coachTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection == null) {
                clearFields();
                editButton.setDisable(true);
            }
            else {
                fillFieldsFields(newSelection);
                editButton.setDisable(false);
            }
        });
        coachTable.getSelectionModel().clearSelection();
        newButton.setDisable(false);
        editButton.setDisable(true);
    }

    @Override
    protected void clearFields() {
        idCoachField.setText("");
        nameField.setText("");
        ageField.setText("");
        rankField.setText("");
    }

    @Override
    protected void fillFieldsFields(Coach coach) {
        idCoachField.setText(Integer.toString(coach.getIdcoach()));
        nameField.setText(coach.getName());
        ageField.setText(Integer.toString(coach.getAge()));
        rankField.setText(coach.getRank());
    }

    @FXML
    public void onNewClick() {
        processClick(true, Coach::new, c -> coachTable.getItems().add(c));
    }

    @FXML
    public void onEditClick() {
        processClick(false, () -> coachTable.getSelectionModel().getSelectedItem(), c -> coachTable.refresh());
    }

    @Override
    protected void processClick(boolean isNew, Supplier<Coach> supplier, Consumer<Coach> consumer) {
        Coach coach = supplier.get();
        int idCoach;
        try {
            idCoach = Integer.parseInt(idCoachField.getText());
        }
        catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Can not parse integer from ID field");
            a.showAndWait();
            return;
        }
        String name = nameField.getText();
        int age;
        try {
            age = Integer.parseInt(ageField.getText());
        }
        catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Can not parse integer from age field");
            a.showAndWait();
            return;
        }
        String rank = rankField.getText();
        if (validate(isNew, idCoach, name, age, rank)) {
            coach.setIdcoach(idCoach);
            coach.setName(name);
            coach.setAge(age);
            coach.setRank(rank);
            consumer.accept(coach);
        }
    }

    private boolean validate(boolean isNew, int idCoach, String name, int age, String rank) {
        if (isNew && coachTable.getItems().stream().anyMatch(c -> c.getIdcoach() == idCoach)) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Coach ID already exists");
            a.showAndWait();
            return false;
        }
        if (idCoach < 0) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Coach ID must be non-negative");
            a.showAndWait();
            return false;
        }
        if (name.isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Coach name must not be empty");
            a.showAndWait();
            return false;
        }
        if (age < 18 || age > 100) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Age must be in range [18; 100]");
            a.showAndWait();
            return false;
        }
        return true;
    }
}

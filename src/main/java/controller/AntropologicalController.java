package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Antropological;
import model.Coach;
import model.Sponsor;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class AntropologicalController extends AbstractFormController<Antropological> {
    @FXML
    private Button editButton;

    @FXML
    private Button newButton;

    @FXML
    private TextField idAntropologicalField;

    @FXML
    private TextField dateField;

    @FXML
    private TextField heightField;
    @FXML
    private TextField weightField;

    @FXML
    private TableView<Antropological> antropologicalTable;

    @FXML
    private TableColumn<Antropological, Integer> idAntropologicalColumn;

    @FXML
    private TableColumn<Antropological, Date> dateColumn;

    @FXML
    private TableColumn<Antropological, Double> heightColumn;

    @FXML
    private TableColumn<Antropological, Double> weightColumn;

    @Override
    protected void setData() {
        antropologicalTable.setItems(getRoot().getAntropologicalList());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idAntropologicalColumn.setCellValueFactory(new PropertyValueFactory<>("idAntropological"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateColumn.setCellFactory(column -> {
            TableCell<Antropological, Date> cell = new TableCell<Antropological, Date>() {
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
        heightColumn.setCellValueFactory(new PropertyValueFactory<>("height"));
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));

        antropologicalTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        antropologicalTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection == null) {
                clearFields();
                editButton.setDisable(true);
            }
            else {
                fillFieldsFields(newSelection);
                editButton.setDisable(false);
            }
        });
        antropologicalTable.getSelectionModel().clearSelection();
        newButton.setDisable(false);
        editButton.setDisable(true);
    }

    @Override
    protected void clearFields() {
        idAntropologicalField.setText("");
        dateField.setText("");
        heightField.setText("");
        weightField.setText("");
    }

    @Override
    protected void fillFieldsFields(Antropological antropological) {
        idAntropologicalField.setText(Integer.toString(antropological.getIdAntropological()));
        dateField.setText(dateToStr(antropological.getDate()));
        heightField.setText(Double.toString(antropological.getHeight()));
        weightField.setText(Double.toString(antropological.getWeight()));
    }

    @FXML
    public void onNewClick() {
        processClick(true, Antropological::new, a -> antropologicalTable.getItems().add(a));
    }

    @FXML
    public void onEditClick() {
        processClick(false, () -> antropologicalTable.getSelectionModel().getSelectedItem(), a -> antropologicalTable.refresh());
    }

    @Override
    protected void processClick(boolean isNew, Supplier<Antropological> supplier, Consumer<Antropological> consumer) {
        Antropological antropological = supplier.get();
        int idAntropological;
        try {
            idAntropological = Integer.parseInt(idAntropologicalField.getText());
        }
        catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Can not parse integer from ID field");
            a.showAndWait();
            return;
        }
        Date date = tryParseDate(dateField.getText());
        if (date == null) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Can not parse date from date field (required format 'dd-MM-yyyy')");
            a.showAndWait();
            return;
        }

        double height;
        try {
            height = Double.parseDouble(heightField.getText());
        }
        catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Can not parse double from height field");
            a.showAndWait();
            return;
        }
        double weight;
        try {
            weight = Double.parseDouble(weightField.getText());
        }
        catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Can not parse double from weight field");
            a.showAndWait();
            return;
        }
        if (validate(isNew, idAntropological, date, height, weight)) {
            antropological.setIdAntropological(idAntropological);
            antropological.setDate(date);
            antropological.setHeight(height);
            antropological.setWeight(weight);
            consumer.accept(antropological);
        }
    }

    private boolean validate(boolean isNew, int idAntropological, Date date, double height, double weight) {
        if (isNew && antropologicalTable.getItems().stream().anyMatch(a -> a.getIdAntropological() == idAntropological)) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Antropological ID already exists");
            a.showAndWait();
            return false;
        }
        if (idAntropological < 0) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Antropological ID must be non-negative");
            a.showAndWait();
            return false;
        }
        if (height <= 0.0) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Antropological height must be positive");
            a.showAndWait();
            return false;
        }
        if (weight <= 0.0) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Antropological weight must be positive");
            a.showAndWait();
            return false;
        }
        return true;
    }
}

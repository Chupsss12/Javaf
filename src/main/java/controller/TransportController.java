package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Coach;
import model.Sponsor;
import model.Transport;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class TransportController extends AbstractFormController<Transport> {
    @FXML
    private Button editButton;

    @FXML
    private Button newButton;

    @FXML
    private TextField idTransportField;

    @FXML
    private TextField capacityField;

    @FXML
    private TextField typeField;


    @FXML
    private TableView<Transport> transportTable;

    @FXML
    private TableColumn<Transport, Integer> idTransportColumn;

    @FXML
    private TableColumn<Transport, Integer> capacityColumn;

    @FXML
    private TableColumn<Transport, String> typeColumn;

    @Override
    protected void setData() {
        transportTable.setItems(getRoot().getTransportList());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idTransportColumn.setCellValueFactory(new PropertyValueFactory<>("idtransport"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        transportTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        transportTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection == null) {
                clearFields();
                editButton.setDisable(true);
            }
            else {
                fillFieldsFields(newSelection);
                editButton.setDisable(false);
            }
        });
        transportTable.getSelectionModel().clearSelection();
        newButton.setDisable(false);
        editButton.setDisable(true);
    }

    @Override
    protected void clearFields() {
        idTransportField.setText("");
        capacityField.setText("");
        typeField.setText("");
    }

    @Override
    protected void fillFieldsFields(Transport transport) {
        idTransportField.setText(Integer.toString(transport.getIdtransport()));
        capacityField.setText(Integer.toString(transport.getCapacity()));
        typeField.setText(transport.getType());
    }

    @FXML
    public void onNewClick() {
        processClick(true, Transport::new, t -> transportTable.getItems().add(t));
    }

    @FXML
    public void onEditClick() {
        processClick(false, () -> transportTable.getSelectionModel().getSelectedItem(), t -> transportTable.refresh());
    }

    @Override
    protected void processClick(boolean isNew, Supplier<Transport> supplier, Consumer<Transport> consumer) {
        Transport transport = supplier.get();
        int idTransport;
        try {
            idTransport = Integer.parseInt(idTransportField.getText());
        }
        catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Can not parse integer from ID field");
            a.showAndWait();
            return;
        }
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
        String type = typeField.getText();
        if (validate(isNew, idTransport, capacity, type)) {
            transport.setIdtransport(idTransport);
            transport.setCapacity(capacity);
            transport.setType(type);
            consumer.accept(transport);
        }
    }

    private boolean validate(boolean isNew, int idTransport, int capacity, String type) {
        if (isNew && transportTable.getItems().stream().anyMatch(t -> t.getIdtransport() == idTransport)) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Transport ID already exists");
            a.showAndWait();
            return false;
        }
        if (idTransport < 0) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Transport ID must be non-negative");
            a.showAndWait();
            return false;
        }
        if (capacity < 0) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Transport capacity must be non-negative");
            a.showAndWait();
            return false;
        }
        if (type.isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Transport type must not be empty");
            a.showAndWait();
            return false;
        }
        return true;
    }
}

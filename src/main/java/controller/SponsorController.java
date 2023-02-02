package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Coach;
import model.Sponsor;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SponsorController extends AbstractFormController<Sponsor> {
    @FXML
    private Button editButton;

    @FXML
    private Button newButton;

    @FXML
    private TextField idSponsorField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField organizationField;

    @FXML
    private TableView<Sponsor> sponsorTable;

    @FXML
    private TableColumn<Coach, Integer> idSponsorColumn;

    @FXML
    private TableColumn<Coach, String> nameColumn;

    @FXML
    private TableColumn<Coach, String> organizationColumn;

    @Override
    protected void setData() {
        sponsorTable.setItems(getRoot().getSponsorList());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idSponsorColumn.setCellValueFactory(new PropertyValueFactory<>("idsponsor"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        organizationColumn.setCellValueFactory(new PropertyValueFactory<>("organization"));

        sponsorTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        sponsorTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection == null) {
                clearFields();
                editButton.setDisable(true);
            }
            else {
                fillFieldsFields(newSelection);
                editButton.setDisable(false);
            }
        });
        sponsorTable.getSelectionModel().clearSelection();
        newButton.setDisable(false);
        editButton.setDisable(true);
    }

    @Override
    protected void clearFields() {
        idSponsorField.setText("");
        nameField.setText("");
        organizationField.setText("");
    }

    @Override
    protected void fillFieldsFields(Sponsor sponsor) {
        idSponsorField.setText(Integer.toString(sponsor.getIdsponsor()));
        nameField.setText(sponsor.getName());
        organizationField.setText(sponsor.getOrganization());
    }

    @FXML
    public void onNewClick() {
        processClick(true, Sponsor::new, c -> sponsorTable.getItems().add(c));
    }

    @FXML
    public void onEditClick() {
        processClick(false, () -> sponsorTable.getSelectionModel().getSelectedItem(), c -> sponsorTable.refresh());
    }

    @Override
    protected void processClick(boolean isNew, Supplier<Sponsor> supplier, Consumer<Sponsor> consumer) {
        Sponsor sponsor = supplier.get();
        int idSponsor;
        try {
            idSponsor = Integer.parseInt(idSponsorField.getText());
        }
        catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Can not parse integer from ID field");
            a.showAndWait();
            return;
        }
        String name = nameField.getText();
        String organization = organizationField.getText();
        if (validate(isNew, idSponsor, name, organization)) {
            sponsor.setIdsponsor(idSponsor);
            sponsor.setName(name);
            sponsor.setOrganization(organization);
            consumer.accept(sponsor);
        }
    }

    private boolean validate(boolean isNew, int idSponsor, String name, String organization) {
        if (isNew && sponsorTable.getItems().stream().anyMatch(s -> s.getIdsponsor() == idSponsor)) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Sponsor ID already exists");
            a.showAndWait();
            return false;
        }
        if (idSponsor < 0) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Sponsor ID must be non-negative");
            a.showAndWait();
            return false;
        }
        if (name.isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Sponsor name must not be empty");
            a.showAndWait();
            return false;
        }
        if (organization.isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Sponsor organization must not be empty");
            a.showAndWait();
            return false;
        }
        return true;
    }
}

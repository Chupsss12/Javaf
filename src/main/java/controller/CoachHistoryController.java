package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Antropological;
import model.Coach;
import model.CoachHistory;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CoachHistoryController extends AbstractFormController<CoachHistory> {
    @FXML
    private Button editButton;

    @FXML
    private Button newButton;

    @FXML
    private TextField idCoachHistoryField;

    @FXML
    private TextField coachField;

    @FXML
    private TextField dateFromField;

    @FXML
    private TextField dateToField;

    @FXML
    private TableView<CoachHistory> coachHistoryTable;

    @FXML
    private TableColumn<CoachHistory, Integer> idCoachHistoryColumn;

    @FXML
    private TableColumn<CoachHistory, Coach> coachColumn;

    @FXML
    private TableColumn<CoachHistory, Date> dateFromColumn;

    @FXML
    private TableColumn<CoachHistory, Date> dateToColumn;

    @Override
    protected void setData() {
        coachHistoryTable.setItems(getRoot().getCoachHistoryList());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idCoachHistoryColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        coachColumn.setCellValueFactory(new PropertyValueFactory<>("coach"));
        coachColumn.setCellFactory(column -> {
            TableCell<CoachHistory, Coach> cell = new TableCell<CoachHistory, Coach>() {
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

        dateFromColumn.setCellValueFactory(new PropertyValueFactory<>("dateFrom"));
        dateFromColumn.setCellFactory(column -> {
            TableCell<CoachHistory, Date> cell = new TableCell<CoachHistory, Date>() {
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


        dateToColumn.setCellValueFactory(new PropertyValueFactory<>("dateTo"));
        dateToColumn.setCellFactory(column -> {
            TableCell<CoachHistory, Date> cell = new TableCell<CoachHistory, Date>() {
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

        coachHistoryTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        coachHistoryTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection == null) {
                clearFields();
                editButton.setDisable(true);
            }
            else {
                fillFieldsFields(newSelection);
                editButton.setDisable(false);
            }
        });
        coachHistoryTable.getSelectionModel().clearSelection();
        newButton.setDisable(false);
        editButton.setDisable(true);
    }

    @Override
    protected void clearFields() {
        idCoachHistoryField.setText("");
        coachField.setText("");
        dateFromField.setText("");
        dateToField.setText("");
    }

    @Override
    protected void fillFieldsFields(CoachHistory coachHistory) {
        idCoachHistoryField.setText(Integer.toString(coachHistory.getId()));
        coachField.setText(Integer.toString(coachHistory.getCoach().getIdcoach()));
        dateFromField.setText(dateToStr(coachHistory.getDateFrom()));
        dateToField.setText(dateToStr(coachHistory.getDateTo()));
    }

    @FXML
    public void onNewClick() {
        processClick(true, CoachHistory::new, ch -> coachHistoryTable.getItems().add(ch));
    }

    @FXML
    public void onEditClick() {
        processClick(false, () -> coachHistoryTable.getSelectionModel().getSelectedItem(), a -> coachHistoryTable.refresh());
    }

    @Override
    protected void processClick(boolean isNew, Supplier<CoachHistory> supplier, Consumer<CoachHistory> consumer) {
        CoachHistory coachHistory = supplier.get();
        int idCoachHistory;
        try {
            idCoachHistory = Integer.parseInt(idCoachHistoryField.getText());
        }
        catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Can not parse integer from ID field");
            a.showAndWait();
            return;
        }

        int idCoach;
        try {
            idCoach = Integer.parseInt(coachField.getText());
        }
        catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Can not parse integer from coach ID field");
            a.showAndWait();
            return;
        }

        Date dateFrom = tryParseDate(dateFromField.getText());
        if (dateFrom == null) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Can not parse date from dateFrom field (required format 'dd-MM-yyyy')");
            a.showAndWait();
            return;
        }
        Date dateTo = tryParseDate(dateToField.getText());
        if (dateTo == null) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Can not parse date from dateTo field (required format 'dd-MM-yyyy')");
            a.showAndWait();
            return;
        }

        if (validate(isNew, idCoachHistory, idCoach, dateFrom, dateTo)) {
            coachHistory.setId(idCoachHistory);
            coachHistory.setCoach(getRoot().getCoachList().stream().filter(c -> c.getIdcoach() == idCoach).findAny().orElse(null));
            coachHistory.setDateFrom(dateFrom);
            coachHistory.setDateTo(dateTo);
            consumer.accept(coachHistory);
        }
    }

    private boolean validate(boolean isNew, int idCoachHistory, int idCoach, Date dateFrom, Date dateTo) {
        if (isNew && coachHistoryTable.getItems().stream().anyMatch(ch -> ch.getId() == idCoachHistory)) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Coach History ID already exists");
            a.showAndWait();
            return false;
        }
        if (idCoachHistory < 0) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Coach History ID must be non-negative");
            a.showAndWait();
            return false;
        }
        if (getRoot().getCoachList().stream().noneMatch(c -> c.getIdcoach() == idCoach)) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Coach with given ID does not exist");
            a.showAndWait();
            return false;
        }

        if (dateFrom.compareTo(dateTo) > 0) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("DateTo is before DateFrom");
            a.showAndWait();
            return false;
        }
        return true;
    }
}

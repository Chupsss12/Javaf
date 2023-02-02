package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import model.Sponsor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class AbstractFormController<S> implements Initializable {
    private static final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    private RootLayoutController root;

    public RootLayoutController getRoot() {
        return root;
    }

    public void setRoot(RootLayoutController root) {
        this.root = root;
        setData();
    }

    protected abstract void setData();


    protected abstract void clearFields();

    protected abstract void fillFieldsFields(S s);

    protected abstract void processClick(boolean isNew, Supplier<S> supplier, Consumer<S> consumer);

    public Date tryParseDate(String s) {
        try {
            return format.parse(s);
        }
        catch (Exception e) {
            return null;
        }
    }

    public String dateToStr(Date date) {
        return format.format(date);
    }
}

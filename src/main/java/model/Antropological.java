package model;

import java.util.Date;

public class Antropological {
    private int idAntropological;
    private Date date;
    private double height;
    private double weight;

    public Antropological() {
    }

    public Antropological(int idAntropological, Date date, double height, double weight) {
        this.idAntropological = idAntropological;
        this.date = date;
        this.height = height;
        this.weight = weight;
    }

    public int getIdAntropological() {
        return idAntropological;
    }

    public void setIdAntropological(int idAntropological) {
        this.idAntropological = idAntropological;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}

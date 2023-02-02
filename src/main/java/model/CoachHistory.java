package model;

import java.util.Date;

public class CoachHistory {
    private int id;
    private Coach coach;
    private Date dateFrom;
    private Date dateTo;

    public CoachHistory() {
    }

    public CoachHistory(int id, Coach coach, Date dateFrom, Date dateTo) {
        this.id = id;
        this.coach = coach;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }
}

package model;

public class Competition {
    private int id;
    private SportClub sportClub;
    private int year;
    private Participant participant;

    public Competition() {
    }

    public Competition(int id, SportClub sportClub, int year, Participant participant) {
        this.id = id;
        this.sportClub = sportClub;
        this.year = year;
        this.participant = participant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SportClub getSportClub() {
        return sportClub;
    }

    public void setSportClub(SportClub sportClub) {
        this.sportClub = sportClub;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }
}

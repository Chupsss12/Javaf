package model;

import java.util.Date;

public class Participant {
    private int idparticipant;
    private String name;
    private Date birthDay;
    private String rank;
    private Antropological antropological_folder;
    private int phoneNumber;
    private String role;

    public Participant() {
    }

    public Participant(int idparticipant, String name, Date birthDay, String rank, Antropological antropological_folder, int phoneNumber, String role) {
        this.idparticipant = idparticipant;
        this.name = name;
        this.birthDay = birthDay;
        this.rank = rank;
        this.antropological_folder = antropological_folder;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public int getIdparticipant() {
        return idparticipant;
    }

    public void setIdparticipant(int idparticipant) {
        this.idparticipant = idparticipant;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public Antropological getAntropological_folder() {
        return antropological_folder;
    }

    public void setAntropological_folder(Antropological antropological_folder) {
        this.antropological_folder = antropological_folder;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

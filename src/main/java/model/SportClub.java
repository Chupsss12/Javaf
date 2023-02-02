package model;

import java.util.Date;

public class SportClub {
    private int idclub;
    private String name;
    private Date date_creation;
    private String city;
    private Coach maincoach_folder;
    private Sponsor sponsor_folder;
    private Gym gym;
    private Transport transport;

    public SportClub() {
    }

    public SportClub(int idclub, String name, Date date_creation, String city, Coach maincoach_folder, Sponsor sponsor_folder, Gym gym, Transport transport) {
        this.idclub = idclub;
        this.name = name;
        this.date_creation = date_creation;
        this.city = city;
        this.maincoach_folder = maincoach_folder;
        this.sponsor_folder = sponsor_folder;
        this.gym = gym;
        this.transport = transport;
    }

    public int getIdclub() {
        return idclub;
    }

    public void setIdclub(int idclub) {
        this.idclub = idclub;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(Date date_creation) {
        this.date_creation = date_creation;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Coach getMaincoach_folder() {
        return maincoach_folder;
    }

    public void setMaincoach_folder(Coach maincoach_folder) {
        this.maincoach_folder = maincoach_folder;
    }

    public Sponsor getSponsor_folder() {
        return sponsor_folder;
    }

    public void setSponsor_folder(Sponsor sponsor_folder) {
        this.sponsor_folder = sponsor_folder;
    }

    public Gym getGym() {
        return gym;
    }

    public void setGym(Gym gym) {
        this.gym = gym;
    }

    public Transport getTransport() {
        return transport;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }
}

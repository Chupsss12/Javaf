package model;

public class Gym {
    private int idgym;
    private String name;
    private int phoneNumber;
    private String address;
    private String category;
    private int capacity;

    public Gym() {
    }

    public Gym(int idgym, String name, int phoneNumber, String address, String category, int capacity) {
        this.idgym = idgym;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.category = category;
        this.capacity = capacity;
    }

    public int getIdgym() {
        return idgym;
    }

    public void setIdgym(int idgym) {
        this.idgym = idgym;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}

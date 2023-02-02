package model;

public class Transport {
    private int idtransport;
    private int capacity;
    private String type;

    public Transport() {
    }

    public Transport(int idtransport, int capacity, String type) {
        this.idtransport = idtransport;
        this.capacity = capacity;
        this.type = type;
    }

    public int getIdtransport() {
        return idtransport;
    }

    public void setIdtransport(int idtransport) {
        this.idtransport = idtransport;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

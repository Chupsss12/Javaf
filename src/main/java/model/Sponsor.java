package model;

public class Sponsor {
    private int idsponsor;
    private String name;
    private String organization;

    public Sponsor() {
    }

    public Sponsor(int idsponsor, String name, String organization) {
        this.idsponsor = idsponsor;
        this.name = name;
        this.organization = organization;
    }

    public int getIdsponsor() {
        return idsponsor;
    }

    public void setIdsponsor(int idsponsor) {
        this.idsponsor = idsponsor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }
}

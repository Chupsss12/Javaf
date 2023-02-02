package model;

public class Coach {
    private int idcoach;
    private String name;
    private int age;
    private String rank;

    public Coach() {
    }

    public Coach(int idcoach, String name, int age, String rank) {
        this.idcoach = idcoach;
        this.name = name;
        this.age = age;
        this.rank = rank;
    }

    public int getIdcoach() {
        return idcoach;
    }

    public void setIdcoach(int idcoach) {
        this.idcoach = idcoach;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}

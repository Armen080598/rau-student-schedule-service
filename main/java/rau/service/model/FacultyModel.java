package rau.service.model;

public class FacultyModel {
    private int id;
    private String name;
    private String plan;

    public FacultyModel(int id, String name, String plan) {
        this.id = id;
        this.name = name;
        this.plan = plan;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPlan() {
        return this.plan;
    }
}

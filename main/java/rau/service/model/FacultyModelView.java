package rau.service.model;

public class FacultyModelView {
    int id;
    String name;

    public FacultyModelView(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

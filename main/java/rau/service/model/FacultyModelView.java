package rau.service.model;

public class FacultyModelView {
    private int id;
    private String name;

    public FacultyModelView(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public String getName() {
        return name;
    }
}

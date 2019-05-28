package rau.service.model;

public class StudentModelView {
    private int id;
    private int facultyId;
    private String firstName;
    private String lastName;

    public StudentModelView(int id, int facultyId, String firstName, String lastName) {
        this.id = id;
        this.facultyId = facultyId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public int getFacultyId() {
        return facultyId;
    }

    @SuppressWarnings("unused")
    public String getFirstName() {
        return firstName;
    }

    @SuppressWarnings("unused")
    public String getLastName() {
        return lastName;
    }
}

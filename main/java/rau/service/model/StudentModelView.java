package rau.service.model;

public class StudentModelView {
    int id;
    int facultyId;
    String firstName;
    String lastName;

    public StudentModelView(int id, int facultyId, String firstName, String lastName) {
        this.id = id;
        this.facultyId = facultyId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public int getFacultyId() {
        return facultyId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}

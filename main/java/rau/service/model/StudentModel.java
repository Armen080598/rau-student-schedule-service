package rau.service.model;

import java.util.List;

public class StudentModel {
    private int id;
    private int facultyId;
    private String firstName;
    private String lastName;
    private String plan;

    public StudentModel(int id, int facultyId, String firstName, String lastName, String plan) {
        this.id = id;
        this.facultyId = facultyId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.plan = plan;
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

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }
}

package rau.service.model;

public class StudentModel {
    private int id;
    private int facultyId;
    private String firstName;
    private String lastName;
    private String plan;

    @SuppressWarnings("unused")
    public StudentModel() { }

    public StudentModel(int id){
        this.id = id;
    }

    @SuppressWarnings("unused")
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

    public void setId(int id) {
        this.id = id;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}

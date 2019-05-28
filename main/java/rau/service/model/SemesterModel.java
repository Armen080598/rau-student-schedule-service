package rau.service.model;

public class SemesterModel {
    private String semester;
    private DisciplineModel discipline;

    public SemesterModel() {
    }

    @SuppressWarnings("unused")
    public SemesterModel(String semester, DisciplineModel discipline) {
        this.semester = semester;
        this.discipline = discipline;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public DisciplineModel getDiscipline() {
        return discipline;
    }

    public void setDiscipline(DisciplineModel discipline) {
        this.discipline = discipline;
    }

}

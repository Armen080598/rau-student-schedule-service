package rau.service.model;

public class DisciplineModel {
    private String id;
    private String name;
    private String time;
    private boolean isExam;

    public DisciplineModel(){

    }

    private DisciplineModel(String id, String name, String time, boolean isExam) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.isExam = isExam;
    }

    DisciplineModel copy(){
        return new DisciplineModel(this.id, this.name, this.time, this.isExam);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @SuppressWarnings("unused")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @SuppressWarnings("unused")
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @SuppressWarnings("unused")
    public boolean isExam() {
        return isExam;
    }

    public void setExam(boolean exam) {
        isExam = exam;
    }
}

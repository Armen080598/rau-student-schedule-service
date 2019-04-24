package rau.service.model;

import java.util.List;
import java.util.stream.Collectors;

public class FacultyModel {
    private int id;
    private String name;
    private List<SemesterModel> semesters;

    public FacultyModel(int id, String name, List<SemesterModel> semesters) {
        this.id = id;
        this.name = name;
        this.semesters = semesters;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<SemesterModel> getSemesters() {
        return this.semesters.stream().map(SemesterModel::copy).collect(Collectors.toList());
    }
}

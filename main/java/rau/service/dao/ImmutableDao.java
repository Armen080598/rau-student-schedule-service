package rau.service.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import rau.service.model.FacultyModel;
import rau.service.model.FacultyModelView;
import rau.service.model.StudentModel;
import rau.service.model.StudentModelView;

import java.util.List;

@Repository
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ImmutableDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ImmutableDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertStudent(StudentModel studentModel) {
        String SQL = "INSERT INTO student(facultyid, firstname, lastname, plan) VALUES (?,?,?,?)";
        this.jdbcTemplate.update(SQL, studentModel.getFacultyId(), studentModel.getFirstName(),
                studentModel.getLastName(), studentModel.getPlan());
    }

    public void deleteStudent(StudentModel studentModel) {
        String SQL = "DELETE FROM student WHERE id = ?";
        this.jdbcTemplate.update(SQL, studentModel.getId());
    }

    public void insertFaculty(FacultyModel facultyModel) {
        String SQL = "INSERT INTO faculty(name, plan) VALUES (?,?)";
        this.jdbcTemplate.update(SQL, facultyModel.getName(), facultyModel.getPlan());
    }

    public void deleteFaculty(FacultyModel facultyModel) {
        String SQL = "DELETE FROM faculty WHERE id = ?";
        this.jdbcTemplate.update(SQL, facultyModel.getId());
    }

    public void updateStudentPlanByFacultyPlan(StudentModel studentModel) {
        String SQL = "SELECT plan FROM faculty WHERE id = ?";
        String plan = this.jdbcTemplate.queryForObject(SQL, new Object[]{studentModel.getFacultyId()}, String.class);
        studentModel.setPlan(plan);
    }

    public List<StudentModelView> getStudentsByFacultyId(int facultyId) {
        String SQL = "SELECT id,facultyid,firstname,lastname FROM student WHERE facultyid = ?";
        return this.jdbcTemplate.queryForList(SQL, new Object[]{facultyId}, StudentModelView.class);
    }

    public StudentModel getStudentById(int id) {
        String SQL = "SELECT id,facultyid,firstname,lastname,plan FROM student WHERE id = ?";
        return this.jdbcTemplate.queryForObject(SQL, new Object[]{id}, StudentModel.class);
    }

    public List<FacultyModelView> getFaculties() {
        String SQL = "SELECT id,name FROM faculty";
        return this.jdbcTemplate.queryForList(SQL, FacultyModelView.class);
    }

    public void updateStudentPlan(StudentModel studentModel){
        String SQL = "UPDATE student SET plan = ? WHERE id = ?";
        this.jdbcTemplate.update(SQL, studentModel.getPlan(), studentModel.getId());
    }

}
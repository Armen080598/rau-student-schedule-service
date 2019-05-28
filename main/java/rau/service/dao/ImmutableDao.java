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

import java.util.ArrayList;
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

    public void deleteStudent(int id) {
        String SQL = "DELETE FROM student WHERE id = ?";
        this.jdbcTemplate.update(SQL, id);
    }

    public void insertFaculty(FacultyModel facultyModel) {
        String SQL = "INSERT INTO faculty(name, plan) VALUES (?,?)";
        this.jdbcTemplate.update(SQL, facultyModel.getName(), facultyModel.getPlan());
    }

    public void deleteFaculty(int id) {
        String SQL = "DELETE FROM faculty WHERE id = ?";
        this.jdbcTemplate.update(SQL, id);
    }

    public void updateStudentPlanByFacultyPlan(StudentModel studentModel) {
        String SQL = "SELECT plan FROM faculty WHERE id = ?";
        String plan = this.jdbcTemplate.queryForObject(SQL, new Object[]{studentModel.getFacultyId()}, String.class);
        studentModel.setPlan(plan);
    }

    public List<StudentModelView> getStudentsByFacultyId(int facultyId) {
        String SQL = "SELECT id,firstname,lastname FROM student WHERE facultyid = ?";
        List<StudentModelView> students = new ArrayList<>();
        this.jdbcTemplate.query(SQL, new Object[]{facultyId}, rs -> {
            do {
                int id = rs.getInt(1);
                String firstName = rs.getString(2);
                String lastName = rs.getString(3);
                StudentModelView student = new StudentModelView(id, facultyId, firstName, lastName);
                students.add(student);
            } while (rs.next());
        });
        return students;
    }

    public StudentModel getStudentById(int id) {
        String SQL = "SELECT facultyid,firstname,lastname,plan FROM student WHERE id = ?";
        final StudentModel studentModel = new StudentModel(id);
        this.jdbcTemplate.query(SQL, new Object[]{id}, rs -> {
            studentModel.setFacultyId(rs.getInt(1));
            studentModel.setFirstName(rs.getString(2));
            studentModel.setLastName(rs.getString(3));
            studentModel.setPlan(rs.getString(4));
        });
        return studentModel;
    }

    public List<FacultyModelView> getFaculties() {
        String SQL = "SELECT id,name FROM faculty";
        List<FacultyModelView> faculties = new ArrayList<>();
        this.jdbcTemplate.query(SQL, rs -> {
            do {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                FacultyModelView faculty = new FacultyModelView(id, name);
                faculties.add(faculty);
            } while (rs.next());
        });
        return faculties;
    }

    public void updateStudentPlan(StudentModel studentModel){
        String SQL = "UPDATE student SET plan = ? WHERE id = ?";
        this.jdbcTemplate.update(SQL, studentModel.getPlan(), studentModel.getId());
    }
    
    public String getStudentFacultyName(int studentId){
        String SQL =
            "SELECT faculty.name FROM faculty where id = (SELECT student.facultyid from student WHERE student.id = ?)";
        return this.jdbcTemplate.queryForObject(SQL, new Object[]{studentId}, String.class);
    }

}
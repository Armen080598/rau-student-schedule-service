package rau.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import rau.service.dao.ImmutableDao;
import rau.service.model.StudentModel;
import rau.service.model.StudentModelView;

import java.util.List;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class StudentService {
    private ImmutableDao immutableDao;

    @Autowired
    public StudentService(ImmutableDao immutableDao){
        this.immutableDao = immutableDao;
    }

    public List<StudentModelView> getAllStudentsByFaculty(int facultyId){
        return this.immutableDao.getStudentsByFacultyId(facultyId);
    }

    public StudentModel getStudentById(int id){
        return this.immutableDao.getStudentById(id);
    }

    public void addStudent(StudentModel studentModel){
        this.immutableDao.updateStudentPlanByFacultyPlan(studentModel);
        this.immutableDao.insertStudent(studentModel);
    }

    public void deleteStudent(int id){
        this.immutableDao.deleteStudent(id);
    }

    public void updateStudentsPlan(StudentModel studentModel){
        this.immutableDao.updateStudentPlan(studentModel);
    }
}

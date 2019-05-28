package rau.service.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;
import rau.service.dao.ImmutableDao;
import rau.service.model.SemesterModel;
import rau.service.model.StudentModel;
import rau.service.model.StudentModelView;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class StudentService {
    private ImmutableDao immutableDao;
    private XMLService xmlService;

    @Autowired
    public StudentService(ImmutableDao immutableDao, XMLService xmlService) {
        this.immutableDao = immutableDao;
        this.xmlService = xmlService;
    }

    public List<StudentModelView> getAllStudentsByFaculty(int facultyId) {
        return this.immutableDao.getStudentsByFacultyId(facultyId);
    }

    public StudentModel getStudentById(int id) {
        return this.immutableDao.getStudentById(id);
    }

    public void addStudent(StudentModel studentModel) {
        this.immutableDao.updateStudentPlanByFacultyPlan(studentModel);
        this.immutableDao.insertStudent(studentModel);
    }

    public void deleteStudent(int id) {
        this.immutableDao.deleteStudent(id);
    }

    public void updateStudentsPlan(StudentModel studentModel) {
        this.immutableDao.updateStudentPlan(studentModel);
    }

    public File getStudentPlan(int studentId) throws ParserConfigurationException, TransformerException, SAXException, IOException {
        String planString = this.immutableDao.getStudentById(studentId).getPlan();
        Gson gson = new Gson();
        Type listType = new TypeToken<List<SemesterModel>>(){}.getType();
        List<SemesterModel> plan = gson.fromJson(planString, listType);
        String facultyName = this.immutableDao.getStudentFacultyName(studentId);
        this.xmlService.update(plan, facultyName);
        return this.xmlService.downloadFile(facultyName);
    }
}

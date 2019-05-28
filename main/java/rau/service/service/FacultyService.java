package rau.service.service;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;
import rau.service.dao.ImmutableDao;
import rau.service.model.FacultyModel;
import rau.service.model.FacultyModelView;
import rau.service.model.SemesterModel;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class FacultyService {

    private ImmutableDao immutableDao;
    private XMLService xmlService;

    @Autowired
    public FacultyService(ImmutableDao immutableDao, XMLService xmlService){
        this.immutableDao = immutableDao;
        this.xmlService = xmlService;
    }

    public void insertFaculty(MultipartFile file, String facultyName) throws ParserConfigurationException, SAXException, IOException {
        List<SemesterModel> processResult = this.xmlService.processFile(file);
        String plan = new Gson().toJson(processResult);
        FacultyModel facultyModel = new FacultyModel(0, facultyName, plan);
        this.immutableDao.insertFaculty(facultyModel);
    }

    public void deleteFaculty(int id) {
        this.immutableDao.deleteFaculty(id);
    }

    public List<FacultyModelView> getFaculties() {
        return this.immutableDao.getFaculties();
    }
}

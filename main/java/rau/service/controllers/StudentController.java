package rau.service.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;
import rau.service.model.StudentModel;
import rau.service.model.StudentModelView;
import rau.service.service.StudentService;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin("*")
public class StudentController {
    private StudentService studentService;

    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @GetMapping(value = "/student/allStudents", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public List<StudentModelView> getAllStudentsByFaculty(@RequestParam int facultyId) throws XMLStreamException, IOException, ParserConfigurationException, SAXException, TransformerException {
        return this.studentService.getAllStudentsByFaculty(facultyId);
    }

    @GetMapping(value = "/student/{id}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public StudentModel saveResponse(@PathVariable int id) throws XMLStreamException, IOException, ParserConfigurationException, SAXException, TransformerException {
        return this.studentService.getStudentById(id);
    }
}

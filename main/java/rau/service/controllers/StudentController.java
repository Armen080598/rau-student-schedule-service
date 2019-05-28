package rau.service.controllers;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;
import rau.service.model.StudentModel;
import rau.service.model.StudentModelView;
import rau.service.service.StudentService;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import java.io.FileInputStream;
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
    public StudentModel getStudentById(@PathVariable int id) throws XMLStreamException, IOException, ParserConfigurationException, SAXException, TransformerException {
        return this.studentService.getStudentById(id);
    }

    @DeleteMapping(value = "/student/{id}")
    public void deleteStudent(@PathVariable int id) throws XMLStreamException, IOException, ParserConfigurationException, SAXException, TransformerException {
        this.studentService.deleteStudent(id);
    }

    @PostMapping(value = "/student/add")
    public void addStudent(@RequestBody StudentModel studentModel) throws XMLStreamException, IOException, ParserConfigurationException, SAXException, TransformerException {
        this.studentService.addStudent(studentModel);
    }

    @PutMapping(value = "/student/update")
    public void updateStudentPlan(@RequestBody StudentModel studentModel) throws XMLStreamException, IOException, ParserConfigurationException, SAXException, TransformerException {
        this.studentService.updateStudentsPlan(studentModel);
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/student/plan/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity getStudentPlan(@PathVariable int id) throws XMLStreamException, IOException, ParserConfigurationException, SAXException, TransformerException {
        InputStreamResource resource = new InputStreamResource(new FileInputStream(this.studentService.getStudentPlan(id)));
        return ResponseEntity.ok()
                .header("Content-disposition", "attachment;")
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }
}

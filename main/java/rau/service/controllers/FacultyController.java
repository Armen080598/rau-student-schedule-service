package rau.service.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;
import rau.service.model.*;
import rau.service.service.FacultyService;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin("*")
public class FacultyController {

    private FacultyService facultyService;

    public FacultyController(FacultyService facultyService){
        this.facultyService = facultyService;
    }

    @PostMapping(value = "/faculty/add", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public void addFaculty(@RequestParam("file") MultipartFile file, @RequestParam("name") String facultyName) throws XMLStreamException, IOException, ParserConfigurationException, SAXException {
        this.facultyService.insertFaculty(file, facultyName);
    }

    @DeleteMapping(value = "/faculty/{id}")
    public void deleteFaculty(@PathVariable int id) throws XMLStreamException, IOException, ParserConfigurationException, SAXException, TransformerException {
        this.facultyService.deleteFaculty(id);
    }

    @GetMapping(value = "/faculty/all", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public List<FacultyModelView> getAllFaculties() throws XMLStreamException, IOException, ParserConfigurationException, SAXException, TransformerException {
        return this.facultyService.getFaculties();
    }
}

package rau.service.controllers;

import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;
import rau.service.service.XMLService;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin("*")
public class ServiceController {

    private XMLService xmlService;

    public ServiceController(XMLService xmlService) {
        this.xmlService = xmlService;
    }

    @PostMapping(value = "/xml", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity processXMLFile(@RequestParam("file") MultipartFile file) throws XMLStreamException, IOException, ParserConfigurationException, SAXException {
        List xmlProcessingResult = this.xmlService.processFile(file);
        return new ResponseEntity<>(new Gson().toJson(xmlProcessingResult), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/xml/latest", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity processXMLFile() throws XMLStreamException, IOException, ParserConfigurationException, SAXException {
        List xmlProcessingResult = this.xmlService.processCachedFile();
        return new ResponseEntity<>(new Gson().toJson(xmlProcessingResult), HttpStatus.OK);
    }

}

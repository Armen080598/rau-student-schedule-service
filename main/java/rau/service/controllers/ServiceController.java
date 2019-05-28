//package rau.service.controllers;
//
//import org.springframework.core.io.InputStreamResource;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import org.xml.sax.SAXException;
//import rau.service.model.SemesterModel;
//import rau.service.service.XMLService;
//
//import javax.xml.parsers.ParserConfigurationException;
//import javax.xml.stream.XMLStreamException;
//import javax.xml.transform.TransformerException;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.List;
//
//@RestController
//@CrossOrigin("*")
//public class ServiceController {
//
//    private XMLService xmlService;
//
//    public ServiceController(XMLService xmlService) {
//        this.xmlService = xmlService;
//    }
//
//    @PostMapping(value = "/xml", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
//    public List<SemesterModel> processXMLFile(@RequestParam("file") MultipartFile file) throws XMLStreamException, IOException, ParserConfigurationException, SAXException {
//        return this.xmlService.processFile(file);
//    }
//
//    @PostMapping(value = "/update", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
//    public List<SemesterModel> saveResponse(@RequestBody List<SemesterModel> data) throws XMLStreamException, IOException, ParserConfigurationException, SAXException, TransformerException {
//        this.xmlService.update(data);
//        return data;
//    }
//
//
//    @CrossOrigin(origins = "*")
//    @GetMapping(value = "/xml/download", produces = MediaType.APPLICATION_XML_VALUE)
//    public ResponseEntity saveChanges() throws XMLStreamException, IOException, ParserConfigurationException, SAXException, TransformerException {
//        InputStreamResource resource = new InputStreamResource(new FileInputStream(this.xmlService.downloadFile()));
//        return ResponseEntity.ok()
//                .header("Content-disposition", "attachment;")
//                .contentType(MediaType.parseMediaType("application/octet-stream"))
//                .body(resource);
//    }
//}

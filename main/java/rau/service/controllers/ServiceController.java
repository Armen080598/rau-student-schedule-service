package rau.service.controllers;

import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;
import rau.service.service.XMLService;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class ServiceController {

    private XMLService xmlService;


    public ServiceController(XMLService xmlService){
        this.xmlService = xmlService;
    }

//    @PostMapping(value = "/xml", produces = MediaType.APPLICATION_XML_VALUE)
//    public ResponseEntity saveXML(@RequestParam MultipartFile file) throws XMLStreamException, IOException {
//        List xmlProcessingResult = this.xmlService.processXMLFile(file.getInputStream());
//        return new ResponseEntity<>(new Gson().toJson(xmlProcessingResult), HttpStatus.OK);
//    }

    @PostMapping(value = "/xml", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity processXMLFile(@RequestParam MultipartFile file) throws XMLStreamException, IOException, ParserConfigurationException, SAXException {
        List xmlProcessingResult = this.xmlService.processAlternative(file);
        return new ResponseEntity<>(new Gson().toJson(xmlProcessingResult), HttpStatus.OK);
    }


//    @PostMapping(value = "/xml/parse", produces = MediaType.APPLICATION_XML_VALUE)
//    public ResponseEntity parseXmlToString(@RequestParam MultipartFile file) throws XMLStreamException, IOException, TransformerException, SAXException, ParserConfigurationException {
//        String xmlProcessingResult = this.xmlService.getXMLAsString(file.getInputStream());
//        return new ResponseEntity<>(xmlProcessingResult, HttpStatus.OK);
//    }
//
//    @PostMapping(value = "/xml/test", produces = MediaType.APPLICATION_XML_VALUE)
//    public ResponseEntity test(@RequestParam MultipartFile file) throws XMLStreamException, IOException, TransformerException, SAXException, ParserConfigurationException {
//        String xmlProcessingResult = this.xmlService.getXMLAsString(file.getInputStream());
//
//
//        return new ResponseEntity<>(xmlProcessingResult, HttpStatus.OK);
//    }


}

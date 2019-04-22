package rau.service.service;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

import org.w3c.dom.Document;


@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class XMLService {

    public static final String PLAN_ROW = "СтрокиПлана";
    public static final String ROW = "Строка";
    public static final String DISCIPLINE = "Дис";
    public static final String SEMESTER = "Ном";


//    public List processXMLFile(InputStream xmlFile) throws XMLStreamException {
//        XMLEventReader eventReader = XMLInputFactory.newInstance().createXMLEventReader(xmlFile);
//        return this.processXMLFile(eventReader);
//
//    }

//    public List processXMLFile(XMLEventReader eventReader) throws XMLStreamException {
//        boolean isRow = false;
//        List<Map> rows = new ArrayList<>();
//
//        while (eventReader.hasNext()) {
//            XMLEvent event = eventReader.nextEvent();
//            if (event.isStartElement()) {
//                StartElement startElement = event.asStartElement();
//                if (startElement.getName().getLocalPart().equals(PLAN_ROW)) {
//                    isRow = true;
//                }
//                if (isRow && startElement.getName().getLocalPart().equals(ROW)) {
//                    Map<String, Object> row = new HashMap<>();
//                    rows.add(row);
//                    Iterator<Attribute> attributes = startElement.getAttributes();
//                    while (attributes.hasNext()) {
//                        Attribute attribute = attributes.next();
//                        if (attribute.getName().getLocalPart().equals(DISCIPLINE)) {
//                            row.put("Discipline",attribute.getValue());
//                        }
//                    }
//                }
//            }
//            if (event.isEndElement()) {
//                EndElement endElement = event.asEndElement();
//                if (endElement.getName().getLocalPart().equals(PLAN_ROW)) {
//                    isRow = false;
//                }
//            }
//
//        }
//        return rows;
//    }

    public List processAlternative(MultipartFile file) throws IOException, ParserConfigurationException, SAXException {
        List<Map> rows = new ArrayList<>();
        File xmlFile = convert(file);
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);
        NodeList rowList = document.getElementsByTagName(ROW);
        for(int i = 0; i < rowList.getLength(); ++i){
            Node row = rowList.item(i);
            this.processRow(row, rows);
        }
        return rows;
    }

    public void processRow(Node row, List<Map> rowsMap) {
        if(row != null && row.getAttributes().getNamedItem(DISCIPLINE) != null) {
            Map<String, Object> rowMap = new HashMap<>();
            rowMap.put("Discipline", row.getAttributes().getNamedItem(DISCIPLINE).getNodeValue());
            rowMap.put("Semester", row.getChildNodes().item(1).getAttributes().getNamedItem(SEMESTER).getNodeValue());
            rowsMap.add(rowMap);
        }
    }

    public File convert(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}

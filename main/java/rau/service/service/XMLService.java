package rau.service.service;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
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


    public Map<String, Object> processXMLFile(InputStream xmlFile) throws XMLStreamException {
        XMLEventReader eventReader = XMLInputFactory.newInstance().createXMLEventReader(xmlFile);
        return this.processXMLFile(eventReader);

    }

    public Map<String, Object> processXMLFile(XMLEventReader eventReader) throws XMLStreamException {
        Map<String, Object> result = new HashMap<>();
        boolean isRow = false;
        List<Map> rows = new ArrayList<>();
        result.put("rows", rows);

        while (eventReader.hasNext()) {
            XMLEvent event = eventReader.nextEvent();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                if (startElement.getName().getLocalPart().equals(PLAN_ROW)) {
                    isRow = true;
                }
                if (isRow && startElement.getName().getLocalPart().equals(ROW)) {
                    Map<String, Object> row = new HashMap<>();
                    rows.add(row);
                    List<String> rowAttributes = new ArrayList<>();
                    row.put("Attributes", rowAttributes);
                    Iterator<Attribute> attributes = startElement.getAttributes();
                    while (attributes.hasNext()) {
                        Attribute attribute = attributes.next();
                        if (attribute.getName().getLocalPart().equals(DISCIPLINE)) {
                            rowAttributes.add(attribute.getValue());
                        }
                    }
                }
            }
            if (event.isEndElement()) {
                EndElement endElement = event.asEndElement();
                if (endElement.getName().getLocalPart().equals(PLAN_ROW)) {
                    isRow = false;
                }
            }

        }
        return result;
    }

    public String getXMLAsString(InputStream xmlFile) throws IOException, ParserConfigurationException, SAXException, TransformerException {
        Document xmlDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);
        StringWriter write = new StringWriter();
        TransformerFactory.newInstance().newTransformer().transform(new DOMSource(xmlDocument), new StreamResult(write));
        return write.getBuffer().toString().replaceAll("undefined", "");
    }

    public Document convertStringToXML(String xml) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        return factory.newDocumentBuilder().parse(new InputSource(new StringReader(xml)));
    }
}

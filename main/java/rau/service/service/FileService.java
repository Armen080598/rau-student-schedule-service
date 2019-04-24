package rau.service.service;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;


@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class FileService {

    public File createXMLFile() throws TransformerConfigurationException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
//        DOMSource domSource = new DOMSource(document);
//        StreamResult streamResult = new StreamResult(new File(xmlFilePath));
        return null;
    }
}

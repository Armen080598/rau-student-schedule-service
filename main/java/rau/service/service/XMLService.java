package rau.service.service;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class XMLService {

    private static File cachedFile;

    public static final String PLAN_ROW = "СтрокиПлана";
    public static final String ROW = "Строка";
    public static final String DISCIPLINE = "Дис";
    public static final String SEMESTER = "Ном";

    public List processFile(MultipartFile file) throws IOException, ParserConfigurationException, SAXException {
        File xmlFile = convert(file);
        return this.processFile(xmlFile);
    }

    public List processFile(File file) throws ParserConfigurationException, IOException, SAXException {
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
        NodeList rowList = document.getElementsByTagName(ROW);
        return this.processRowList(document, rowList);
    }

    public List processRowList(Document document, NodeList rowList) {
        List<Map> rows = new ArrayList<>();
        for (int i = 0; i < rowList.getLength(); ++i) {
            Node row = rowList.item(i);
            this.processRow(row, rows);
        }
        return rows;
    }

    private void processRow(Node row, List<Map> rowsMap) {
        if (row != null && row.getAttributes().getNamedItem(DISCIPLINE) != null) {
            Map<String, Object> rowMap = new HashMap<>();
            rowMap.put("Discipline", row.getAttributes().getNamedItem(DISCIPLINE).getNodeValue());
            rowMap.put("Semester", row.getChildNodes().item(1).getAttributes().getNamedItem(SEMESTER).getNodeValue());
            rowsMap.add(rowMap);
        }
    }

    private File convert(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        this.cacheFile(file);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private void cacheFile(MultipartFile file) throws IOException {
        cachedFile = new File(file.getOriginalFilename());
        FileOutputStream fileOutputStream = new FileOutputStream(cachedFile);
        fileOutputStream.write(file.getBytes());
        fileOutputStream.close();
    }

    public List processCachedFile() throws IOException, SAXException, ParserConfigurationException {
        return this.processFile(cachedFile);
    }
}

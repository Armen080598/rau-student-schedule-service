package rau.service.service;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.NamedNodeMap;
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
    public static final String SEMESTER_ATTR = "Ном";
    public static final String SEMESTER_NODE = "Сем";
    public static final String TIME_ATTR = "Пр";
    public static final String CREDIT_ATTR = "Зач";
    public static final String ROW_ID = "ИдетификаторДисциплины";

    public List processFile(MultipartFile file) throws IOException, ParserConfigurationException, SAXException {
        File xmlFile = convert(file);
        return this.processFile(xmlFile);
    }

    public List processFile(File file) throws ParserConfigurationException, IOException, SAXException {
        NodeList rowList = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse(file)
                .getElementsByTagName(ROW);
        return this.processRowList(rowList);
    }

    public List processRowList(NodeList rowList) {
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
            Map<String, Object> discipline = new HashMap<>();

            List<Node> semesterNodes = new ArrayList<>();
            NodeList childNodes = row.getChildNodes();
            for (int i = 0; i < childNodes.getLength(); ++i) {
                Node childNode = childNodes.item(i);
                if (SEMESTER_NODE.equals(childNode.getNodeName())) {
                    semesterNodes.add(childNode);
                }
            }
            for(int i = 0 ; i < semesterNodes.size(); ++i){
                Node semester = semesterNodes.get(i);
                discipline.put("DisciplineName", row.getAttributes().getNamedItem(DISCIPLINE).getNodeValue());
                NamedNodeMap attributes = semester.getAttributes();
                Node timeNode = attributes.getNamedItem(TIME_ATTR);
                if(timeNode != null){
                    discipline.put("time", timeNode.getNodeValue());
                }
                if(attributes.getNamedItem(CREDIT_ATTR) == null){
                    discipline.put("isExam", true);
                } else {
                    discipline.put("isExam", false);
                }
                rowMap.put("Discipline", discipline);
                discipline.put("ID", row.getAttributes().getNamedItem(ROW_ID).getNodeValue());
                rowMap.put("Semester", attributes.getNamedItem(SEMESTER_ATTR).getNodeValue());
                rowsMap.add(rowMap);
                discipline = new HashMap<>();
                rowMap = new HashMap<>();
            }
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

    public File updateFile() {
        return cachedFile;
    }
}

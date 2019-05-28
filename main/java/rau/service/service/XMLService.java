package rau.service.service;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import rau.service.model.DisciplineModel;
import rau.service.model.SemesterModel;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class XMLService {

    private static Map<String, File> cachedFiles = new HashMap<>();

    public static final String PLAN_ROW = "СтрокиПлана";
    public static final String ROW = "Строка";
    public static final String DISCIPLINE = "Дис";
    public static final String SEMESTER_ATTR = "Ном";
    public static final String SEMESTER_NODE = "Сем";
    public static final String TIME_ATTR = "Пр";
    public static final String CREDIT_ATTR = "Зач";
    public static final String ROW_ID = "ИдетификаторДисциплины";


    public void update(List<SemesterModel> data, String facultyName) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        File cachedFile = cachedFiles.get(facultyName);
        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse(cachedFile);
        NodeList rowList = document.getElementsByTagName(ROW);
        for(int i = 0; i < rowList.getLength(); ++i) {
            Node row = rowList.item(i);
            if(row != null && row.getAttributes().getNamedItem(DISCIPLINE) != null){
                this.updateRow(row, data);
            }
        }
        this.writeToFile(document, cachedFile);
    }

    public void updateRow(Node row, List<SemesterModel> data) {
        NamedNodeMap rowAttributes = row.getAttributes();
        String id = rowAttributes.getNamedItem(ROW_ID).getNodeValue();
        List<SemesterModel> matchingData = data.stream().filter(semesterModel -> semesterModel.getDiscipline().getId().equals(id)).collect(Collectors.toList());
        NodeList childNodes = row.getChildNodes();
        this.updateSemesterData(childNodes, matchingData);
    }

    public void updateSemesterData(NodeList childNodes, List<SemesterModel> matchingData) {
        int dataCounter = 0;
        for (int i = 0; i < childNodes.getLength(); ++i) {
            Node childNode = childNodes.item(i);
            if(SEMESTER_NODE.equals(childNode.getNodeName())) {
                childNode.getAttributes().getNamedItem(SEMESTER_ATTR).setNodeValue(
                        matchingData.get(dataCounter).getSemester()
                );
                ++dataCounter;
            }
        }
    }

    public List<SemesterModel> processFile(MultipartFile file, String facultyName) throws IOException,
                                                                      ParserConfigurationException,
                                                                      SAXException {
        File xmlFile = convert(file, facultyName);
        return this.processFile(xmlFile);
    }

    public List<SemesterModel> processFile(File file) throws ParserConfigurationException, IOException, SAXException {
        NodeList rowList = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse(file)
                .getElementsByTagName(ROW);
        return this.processRowList(rowList);
    }

    private List<SemesterModel> processRowList(NodeList rowList) {
        List<SemesterModel> rows = new ArrayList<>();
        for (int i = 0; i < rowList.getLength(); ++i) {
            Node row = rowList.item(i);
            this.processRow(row, rows);
        }
        return rows;
    }

    private void processRow(Node row, List<SemesterModel> rowsMap) {
        if (row != null && row.getAttributes().getNamedItem(DISCIPLINE) != null) {
            List<Node> semesterNodes = new ArrayList<>();
            NodeList childNodes = row.getChildNodes();
            for (int i = 0; i < childNodes.getLength(); ++i) {
                Node childNode = childNodes.item(i);
                if (SEMESTER_NODE.equals(childNode.getNodeName())) {
                    semesterNodes.add(childNode);
                }
            }
            this.processSemesterNodes(semesterNodes, row, rowsMap);
        }
    }

    private void processSemesterNodes(List<Node> semesterNodes, Node row, List<SemesterModel> semestersList) {
        for (Node semesterNode : semesterNodes) {
            SemesterModel semesterModel = new SemesterModel();
            DisciplineModel disciplineModel = new DisciplineModel();
            semestersList.add(semesterModel);
            NamedNodeMap attributes = semesterNode.getAttributes();
            this.initializeDisciplineAndSemester(disciplineModel, semesterModel, row, attributes);
        }
    }

    private void initializeDisciplineAndSemester(DisciplineModel disciplineModel,
                                                 SemesterModel semesterModel,
                                                 Node row,
                                                 NamedNodeMap attributes) {
        this.setTimeAndExam(disciplineModel, attributes);
        disciplineModel.setName(row.getAttributes().getNamedItem(DISCIPLINE).getNodeValue());
        disciplineModel.setId(row.getAttributes().getNamedItem(ROW_ID).getNodeValue());
        semesterModel.setDiscipline(disciplineModel);
        semesterModel.setSemester(attributes.getNamedItem(SEMESTER_ATTR).getNodeValue());
    }

    private void setTimeAndExam(DisciplineModel disciplineModel, NamedNodeMap attributes) {
        Node timeNode = attributes.getNamedItem(TIME_ATTR);
        if (timeNode != null) {
            disciplineModel.setTime(timeNode.getNodeValue());
        }
        if (attributes.getNamedItem(CREDIT_ATTR) == null) {
            disciplineModel.setExam(true);
        } else {
            disciplineModel.setExam(false);
        }
    }

    private File convert(MultipartFile file, String facultyName) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        this.cacheFile(file, facultyName);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private void cacheFile(MultipartFile file, String facultyName) throws IOException {
        File cachedFile = new File(file.getOriginalFilename());
        cachedFiles.put(facultyName, cachedFile);
        FileOutputStream fileOutputStream = new FileOutputStream(cachedFile);
        fileOutputStream.write(file.getBytes());
        fileOutputStream.close();
    }

    public File downloadFile(String facultyName) throws IOException, SAXException, ParserConfigurationException, TransformerException {
        File cachedFile = cachedFiles.get(facultyName);
        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse(cachedFile);
        this.writeToFile(document, cachedFile);
        return cachedFile;
    }

    private void writeToFile(Document document, File file) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(file);
        transformer.transform(domSource, streamResult);
    }
}

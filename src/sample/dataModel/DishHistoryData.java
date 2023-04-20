package sample.dataModel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Collections;

public class DishHistoryData {

    private static final String HISTORY_FILE = "dishesHistory.xml";
    private static final String HISTORY = "history";
    private static final String DISH = "dish";
    private static final String DATE = "date";
    private static final String NAME = "name";

    private ObservableList<DishHistory> history;

    public DishHistoryData() {
        this.history = FXCollections.observableArrayList();
    }

    public ObservableList<DishHistory> getHistory() {
        return this.history;
    }

    public void addDishHistory(DishHistory dishHistory) {
        this.history.add(dishHistory);
    }

    public void loadHistory() {
        try {
            File xmlFile = new File(HISTORY_FILE);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Document doc = documentBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName(DISH);

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element dishElement = (Element) node;

                    String dishName = dishElement.getElementsByTagName(NAME).item(0).getTextContent();
                    String dishDate = dishElement.getElementsByTagName(DATE).item(0).getTextContent();

                    history.add(new DishHistory(dishName, dishDate));
                }
            }

            Collections.sort(history);

        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
}

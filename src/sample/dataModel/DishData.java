package sample.dataModel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DishData {

    private static final String DISHES_FILE = "dishes.xml";
    private static final String DISHES = "dishes";
    private static final String DISH = "dish";
    private static final String NAME = "name";
    private static final String INGREDIENTS = "ingredients";
    private static final String INGREDIENT = "ingredient";

    private ObservableList<Dish> dishes;

    public DishData() {
        dishes = FXCollections.observableArrayList();
    }

    public ObservableList<Dish> getDishes() {
        return dishes;
    }

    public void addDish(Dish dish) {
        dishes.add(dish);
    }

    public void deleteDish(Dish dish) {
        dishes.remove(dish);
    }

    public void loadDishes() {
        try {
            String dishName = "";
            List<String> ingredients = new ArrayList<>();

            File xmlFile = new File(DISHES_FILE);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Document doc = documentBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName(DISH);

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element dishElement = (Element) node;

                    dishName = dishElement.getElementsByTagName(NAME).item(0).getTextContent();

                    Element ingredientsElement = (Element) dishElement.getElementsByTagName(INGREDIENTS).item(0);

                    for (int j = 0; j < ingredientsElement.getElementsByTagName(INGREDIENT).getLength(); j++) {
                        ingredients.add(ingredientsElement.getElementsByTagName(INGREDIENT).item(j).getTextContent());
                    }
                }
                addDish(new Dish(dishName, ingredients));
                ingredients.clear();
            }

        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void saveDishes() {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element rootElement = document.createElement(DISHES);
            document.appendChild(rootElement);

            for (Dish dish : dishes) {
                Element dishElement = document.createElement(DISH);
                Element dishNameElement = document.createElement(NAME);
                Element ingredientsElement = document.createElement(INGREDIENTS);

                rootElement.appendChild(dishElement);
                dishElement.appendChild(dishNameElement);
                dishElement.appendChild(ingredientsElement);

                dishNameElement.appendChild(document.createTextNode(dish.getName()));

                for (String ingredient : dish.getIngredients()) {
                    Element ingredientElement = document.createElement(INGREDIENT);
                    ingredientsElement.appendChild(ingredientElement);
                    ingredientElement.appendChild(document.createTextNode(ingredient));
                }
            }

            // Create the xml file
            // Transform the DOM Object to an XML File
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(DISHES_FILE));

            transformer.transform(domSource, streamResult);

        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }
}

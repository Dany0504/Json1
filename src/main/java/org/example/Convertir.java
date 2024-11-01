package org.example;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Convertir {

    public static void main(String[] args) {
        String xmlFile = "C:\\Users\\dany0\\Json\\src\\main\\java\\org\\example\\car_sales.xml";
        String jsonFile = "converted_car_sales.json";

        try {
            File xmlInputFile = new File(xmlFile);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlInputFile);
            doc.getDocumentElement().normalize();

            Element root = doc.getDocumentElement();
            Sale[] sales = new Sale[root.getElementsByTagName("sale").getLength()];

            for (int i = 0; i < root.getElementsByTagName("sale").getLength(); i++) {
                Element saleElement = (Element) root.getElementsByTagName("sale").item(i);

                String id = getElementTextContent(saleElement, "id");
                String firstName = getElementTextContent(saleElement, "first_name");
                String lastName = getElementTextContent(saleElement, "last_name");
                String car = getElementTextContent(saleElement, "car");
                String price = getElementTextContent(saleElement, "price");
                String state = getElementTextContent(saleElement, "state");

                sales[i] = new Sale(id, firstName, lastName, car, price, state);
            }

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonString = gson.toJson(sales);

            try (FileWriter fileWriter = new FileWriter(jsonFile)) {
                fileWriter.write(jsonString);
            }

            System.out.println("Conversión completa. JSON guardado en " + jsonFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class Sale {
        String id;
        String firstName;
        String lastName;
        String car;
        String price;
        String state;

        Sale(String id, String firstName, String lastName, String car, String price, String state) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.car = car;
            this.price = price;
            this.state = state;
        }
    }

    private static String getElementTextContent(Element parent, String tagName) {
        Element element = (Element) parent.getElementsByTagName(tagName).item(0);
        return element != null ? element.getTextContent() : "";
    }
}

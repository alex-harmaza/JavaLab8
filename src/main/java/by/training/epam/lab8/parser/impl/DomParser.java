package by.training.epam.lab8.parser.impl;

import by.training.epam.lab8.entity.impl.Dish;
import by.training.epam.lab8.entity.impl.DishType;
import by.training.epam.lab8.entity.impl.PriceMap;
import by.training.epam.lab8.entity.impl.PriceValue;
import by.training.epam.lab8.parser.IParser;
import by.training.epam.lab8.parser.exception.ParseException;
import by.training.epam.lab8.parser.utility.AttributeConstants;
import by.training.epam.lab8.parser.utility.TagConstants;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class DomParser implements IParser {

    private DocumentBuilderFactory factory;


    public DomParser(){
        factory = DocumentBuilderFactory.newInstance();
    }


    public List<Dish> parse(String filePath) throws ParseException {
        List<Dish> result = new ArrayList<>();

        try (InputStream fileStream = new FileInputStream(filePath)) {

            Element document = factory.newDocumentBuilder().parse(fileStream).getDocumentElement();
            NodeList nodeList = document.getElementsByTagName(TagConstants.DISH.getTagName());

            for (int dishIndex = 0; dishIndex < nodeList.getLength(); dishIndex++){
                Node node = nodeList.item(dishIndex);

                Dish dish = new Dish();
                dish.setId(node.getAttributes()
                        .getNamedItem(AttributeConstants.ID.toString()).getTextContent());

                NodeList childNodes = node.getChildNodes();
                for (int elementIndex = 0; elementIndex < childNodes.getLength(); elementIndex++){
                    node = childNodes.item(elementIndex);

                    if (node.getNodeType() != Node.ELEMENT_NODE){
                        continue;
                    }

                    Element element = (Element) node;
                    switch (TagConstants.valueOfTagName(element.getTagName())) {
                        case NAME:
                            dish.setName(element.getTextContent());
                            break;
                        case DESCRIPTION:
                            dish.setDescription(element.getTextContent());
                            break;
                        case PORTION:
                            dish.setPortion(element.getTextContent());
                            break;
                        case TYPE:
                            dish.setType(DishType.getTypeByTagValue(element.getTextContent()));
                            break;
                        case IMAGE:
                            dish.setImage(new URL(element.getTextContent()));
                            break;
                        case VALUE:
                            dish.setPrice(new PriceValue(Double.parseDouble(element.getTextContent())));
                            break;
                        case PRICE_POSITION:
                            if (dish.getPrice() == null){
                                dish.setPrice(new PriceMap());
                            }
                            String positionName = element.getAttribute(AttributeConstants.NAME.toString());
                            Double positionPrice = Double.parseDouble(element.getTextContent());
                            ((Map<String, Double>) dish.getPrice().getValue()).put(positionName, positionPrice);
                            break;
                    }
                }
                result.add(dish);
            }
        }
        catch (ParserConfigurationException | SAXException | IOException ex){
            throw new ParseException(ex.getMessage(), ex);
        }

        return result;
    }

}

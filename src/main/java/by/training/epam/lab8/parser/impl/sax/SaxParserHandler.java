package by.training.epam.lab8.parser.impl.sax;

import by.training.epam.lab8.entity.impl.Dish;
import by.training.epam.lab8.entity.impl.DishType;
import by.training.epam.lab8.entity.impl.PriceMap;
import by.training.epam.lab8.entity.impl.PriceValue;
import by.training.epam.lab8.parser.utility.AttributeConstants;
import by.training.epam.lab8.parser.utility.TagConstants;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class SaxParserHandler extends DefaultHandler {

    private TagConstants currentElement;
    private List<Dish> result;
    private Dish dish;
    private String pricePositionName;


    @Override
    public void startDocument() throws SAXException {
        result = new ArrayList<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        currentElement = TagConstants.valueOfTagName(localName);
        switch (currentElement){
            case DISH:
                dish = new Dish();
                dish.setId(attributes.getValue(AttributeConstants.ID.toString()));
                break;
            case PRICE_POSITION:
                if (dish.getPrice() == null){
                    dish.setPrice(new PriceMap());
                }
                pricePositionName = attributes.getValue(AttributeConstants.NAME.toString());
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        TagConstants endTag = TagConstants.valueOfTagName(localName);
        if (endTag == TagConstants.DISH){
            result.add(dish);
            dish = null;
        }
        currentElement = null;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (currentElement == null){
            return;
        }
        try {
            String value = new String(ch, start, length);
            switch (currentElement){
                case IMAGE:
                    dish.setImage(new URL(value));
                    break;
                case NAME:
                    dish.setName(value);
                    break;
                case DESCRIPTION:
                    dish.setDescription(value);
                    break;
                case PORTION:
                    dish.setPortion(value);
                    break;
                case TYPE:
                    dish.setType(DishType.getTypeByTagValue(value));
                    break;
                case VALUE:
                    dish.setPrice(new PriceValue(Double.parseDouble(value)));
                    break;
                case PRICE_POSITION:
                    Double positionPrice = Double.parseDouble(value);
                    ((Map<String, Double>)dish.getPrice().getValue()).put(pricePositionName, positionPrice);
                    pricePositionName = null;
                    break;
            }
        }
        catch (MalformedURLException ex){
            throw new SAXException(ex.getMessage(), ex);
        }
    }

    public List<Dish> getResult(){
        return result;
    }
}

package by.training.epam.lab8.parser.impl;

import by.training.epam.lab8.entity.impl.Dish;
import by.training.epam.lab8.entity.impl.DishType;
import by.training.epam.lab8.entity.impl.PriceMap;
import by.training.epam.lab8.entity.impl.PriceValue;
import by.training.epam.lab8.parser.IParser;
import by.training.epam.lab8.parser.exception.ParseException;
import by.training.epam.lab8.parser.utility.AttributeConstants;
import by.training.epam.lab8.parser.utility.TagConstants;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class StaxParser implements IParser{

    private XMLInputFactory factory;


    public StaxParser(){
        factory = XMLInputFactory.newInstance();
    }


    @Override
    public List<Dish> parse(String filePath) throws ParseException {
        List<Dish> result = new ArrayList<>();

        try (InputStream fileStream = new FileInputStream(filePath)) {

            Dish dish = null;
            XMLEventReader reader = factory.createXMLEventReader(fileStream);

            while (reader.hasNext()){
                XMLEvent event = reader.nextEvent();

                if (event.getEventType() == XMLStreamConstants.START_ELEMENT){
                    StartElement startElement = event.asStartElement();
                    switch (TagConstants.valueOfTagName(startElement.getName().getLocalPart())){
                        case DISH:
                            dish = new Dish();
                            dish.setId(startElement
                                    .getAttributeByName(new QName(AttributeConstants.ID.toString())).getValue());
                            break;
                        case IMAGE:
                            dish.setImage(new URL(reader.getElementText()));
                            break;
                        case DESCRIPTION:
                            dish.setDescription(reader.getElementText());
                            break;
                        case NAME:
                            dish.setName(reader.getElementText());
                            break;
                        case PORTION:
                            dish.setPortion(reader.getElementText());
                            break;
                        case TYPE:
                            dish.setType(DishType.getTypeByTagValue(reader.getElementText()));
                            break;
                        case VALUE:
                            dish.setPrice(new PriceValue(Double.parseDouble(reader.getElementText())));
                            break;
                        case PRICE_POSITION:
                            if (dish.getPrice() == null){
                                dish.setPrice(new PriceMap());
                            }
                            String positionName = startElement
                                    .getAttributeByName(new QName(AttributeConstants.NAME.toString())).getValue();
                            Double positionPrice = Double.parseDouble(reader.getElementText());
                            ((Map<String, Double>)dish.getPrice().getValue()).put(positionName, positionPrice);
                            break;
                    }
                }

                if (event.getEventType() == XMLStreamConstants.END_ELEMENT){
                    EndElement endElement = event.asEndElement();
                    TagConstants tagName = TagConstants.valueOfTagName(endElement.getName().getLocalPart());

                    if (tagName == TagConstants.DISH){
                        result.add(dish);
                        dish = null;
                    }
                }
            }
        } catch (XMLStreamException | IOException e) {
            throw new ParseException(e.getMessage(), e);
        }
        return result;
    }

}

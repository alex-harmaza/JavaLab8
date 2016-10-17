package by.training.epam.lab8.parser.impl.sax;

import by.training.epam.lab8.entity.impl.Dish;
import by.training.epam.lab8.parser.IParser;
import by.training.epam.lab8.parser.exception.ParseException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class SaxParser implements IParser {

    private SAXParser parser;


    public SaxParser(){
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);
        try {
            parser = factory.newSAXParser();
        } catch (ParserConfigurationException | SAXException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }


    @Override
    public List<Dish> parse(String filePath) throws ParseException {
        SaxParserHandler handler;
        try (InputStream fileStream = new FileInputStream(filePath)) {
            handler = new SaxParserHandler();
            parser.parse(fileStream, handler);
        }
        catch (SAXException | IOException ex){
            throw new ParseException(ex.getMessage(), ex);
        }
        return handler.getResult();
    }

}

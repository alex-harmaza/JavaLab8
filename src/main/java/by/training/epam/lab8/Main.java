package by.training.epam.lab8;

import by.training.epam.lab8.entity.impl.Dish;
import by.training.epam.lab8.parser.IParser;
import by.training.epam.lab8.parser.exception.ParseException;
import by.training.epam.lab8.parser.impl.DomParser;
import by.training.epam.lab8.parser.impl.StaxParser;
import by.training.epam.lab8.parser.impl.sax.SaxParser;
import org.xml.sax.SAXException;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Main {

    private final static String XML_FILE_PATH = "menu.xml";
    private final static String XSD_FILE_PATH = "menu-schema.xsd";


    public static void main(String[] args) throws IOException, ParseException {
        Scanner scanner = new Scanner(System.in);
        Map<String, IParser> parserMap = getParserMap();

        try {
            System.out.printf("Enter the xml file path [default: %s]: ", XML_FILE_PATH);
            String filePath = scanner.nextLine().trim();


            if (filePath.isEmpty()){
                filePath = Main.class.getClassLoader().getResource(XML_FILE_PATH).getPath();
            }
            File xmlFile = new File(filePath);
            if(!xmlFile.exists()) {
                throw new FileNotFoundException("file not found");
            }

            System.out.print("Select the parser (DOM, SAX, STAX): ");
            IParser parser = parserMap.get(scanner.nextLine().trim().toUpperCase());
            if (parser == null){
                throw new ClassNotFoundException("Incorrect parser name");
            }

            if(!isValidateXml(xmlFile)){
                throw new IllegalArgumentException("Incorrect xml file structure");
            }

            List<Dish> result = parser.parse(filePath);
            System.out.println(result.size() == 0 ? "Result: nothing" : "\n");
            result.forEach(dish -> System.out.println(dish));

        }
        catch (FileNotFoundException | ClassNotFoundException |IllegalArgumentException ex){
            System.out.printf("Error: %s", ex.getMessage());
        }
    }


    private static Map<String, IParser> getParserMap(){
        Map<String, IParser> parserMap = new HashMap<>();
        parserMap.put("DOM", new DomParser());
        parserMap.put("SAX", new SaxParser());
        parserMap.put("STAX", new StaxParser());
        return parserMap;
    }


    private static boolean isValidateXml(File file) throws IOException {
        boolean result = true;
        SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        try {
            Schema schema = factory.newSchema(Main.class.getClassLoader().getResource(XSD_FILE_PATH));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(file));
        }
        catch (SAXException ex){
            result = false;
        }
        return result;
    }

}

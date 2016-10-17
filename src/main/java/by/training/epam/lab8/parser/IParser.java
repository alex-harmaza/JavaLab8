package by.training.epam.lab8.parser;

import by.training.epam.lab8.entity.impl.Dish;
import by.training.epam.lab8.parser.exception.ParseException;

import java.util.List;


public interface IParser {

    List<Dish> parse(String filePath) throws ParseException;

}

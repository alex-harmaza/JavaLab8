package by.training.epam.lab8.parser.utility;


public enum TagConstants {

    DISHES,
    DISH,
    IMAGE,
    NAME,
    DESCRIPTION,
    PORTION,
    PRICE,
    PRICE_POSITION,
    TYPE,
    VALUE;

    public static TagConstants valueOfTagName(String tagName){
        return TagConstants.valueOf(tagName.toUpperCase().replace('-', '_'));
    }

    public String getTagName(){
        return this.name().toLowerCase().replace('_', '-');
    }
}

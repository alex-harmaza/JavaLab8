package by.training.epam.lab8.parser.utility;


public enum AttributeConstants {

    NAME,
    ID;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }

}

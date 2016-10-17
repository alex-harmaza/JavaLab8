package by.training.epam.lab8.parser.exception;


public class ParseException extends Exception {

    public ParseException(String message){
        super(message);
    }

    public ParseException(String message, Throwable throwable){
        super(message, throwable);
    }

    public ParseException(Throwable throwable){
        super(throwable);
    }

}

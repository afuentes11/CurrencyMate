package main.java.exceptions;

public class ApiRequestException extends DataAccessException{

    public ApiRequestException(String message) {
        super(message);
    }

}

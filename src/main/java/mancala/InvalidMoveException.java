package mancala;

public class InvalidMoveException extends Exception{
    public InvalidMoveException(final String errMessage){ 
        super(errMessage);
    }
}

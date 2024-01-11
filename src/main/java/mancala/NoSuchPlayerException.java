package mancala;

public class NoSuchPlayerException extends Exception{
    
    public NoSuchPlayerException(final String errMessage){
        super(errMessage);
    }

}

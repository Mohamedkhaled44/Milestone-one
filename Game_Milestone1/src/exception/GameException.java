package exception;

public abstract class GameException extends Exception{
	GameException(){
		super();
	}
	public GameException(String message){
		super(message);
		}
}
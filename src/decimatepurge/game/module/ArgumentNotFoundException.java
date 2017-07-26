package decimatepurge.game.module;

public class ArgumentNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public ArgumentNotFoundException() {
		  super();
	}
	
	public ArgumentNotFoundException(String message) {
		super(message); 
	}
	
	public ArgumentNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	  
	public ArgumentNotFoundException(Throwable cause) {
		super(cause);
	}
	
}

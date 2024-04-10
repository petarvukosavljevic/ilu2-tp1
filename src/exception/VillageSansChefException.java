package exception;

public class VillageSansChefException extends RuntimeException {
	
	public VillageSansChefException() {
		super();
	}
	
	public VillageSansChefException(String messageErreur) {
		super(messageErreur);
	}
	
	public VillageSansChefException(String message, Throwable cause) {
        super(message, cause);
    }
	
	public VillageSansChefException(Throwable cause) {
        super(cause);
    }
}

package exception;

public class UserRegistrationException extends RuntimeException {
	
    public enum Exceptiontype {
        EXISTING_USER;      
    }
    
    String message;
	Exceptiontype exceptionType;
	
	public UserRegistrationException(String message) {
		this.message = message;
	}
	
	public UserRegistrationException(String message, Exceptiontype exceptionType) {
		this.message = message;
		this.exceptionType = exceptionType;
	}
	
	public String getMessage() {
        return message;
    }

    public Exceptiontype getExceptionType() {
        return exceptionType;
    }

}

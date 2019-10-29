package exceptions;

import enums.ErrorMessages;

public class InvalidProbabilityValueException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2547577529271306411L;

	private String message;
	private String payload;

	public InvalidProbabilityValueException(String errorType, String payload) {
		message = errorType;
		this.payload = payload;
	}
	
	public InvalidProbabilityValueException(ErrorMessages errorType, String payload) {
		this(errorType.getMessage(),payload);
	}
	
	public InvalidProbabilityValueException(ErrorMessages errorType) {
		this(errorType.getMessage(),"");
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	@Override
	public String toString() {
		return message + " " + payload;
	}
}

package exceptions;

import enums.ErrorMessages;

public class FileFormatException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1076073809104531122L;
	private String message;
	private String payload;

	public FileFormatException(String errorType, String payload) {
		message = errorType;
		this.payload = payload;
	}
	
	public FileFormatException(ErrorMessages errorType, String payload) {
		this(errorType.getMessage(),payload);
	}

	public FileFormatException(ErrorMessages errorType) {
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

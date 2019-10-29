package exceptions;

import enums.ErrorMessages;

public class BaseException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5749463751181514868L;
	private String message;
	private String payload;

	public BaseException(String errorType, String payload) {
		message = errorType;
		this.payload = payload;
	}
	
	public BaseException(ErrorMessages errorType, String payload) {
		this(errorType.getMessage(),payload);
	}

	public BaseException(ErrorMessages errorType) {
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

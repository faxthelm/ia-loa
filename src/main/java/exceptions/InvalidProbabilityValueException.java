package exceptions;

import enums.ErrorMessages;

public class InvalidProbabilityValueException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2547577529271306411L;

	public InvalidProbabilityValueException(ErrorMessages errorType, String payload) {
		super(errorType, payload);
	}

	public InvalidProbabilityValueException(ErrorMessages errorType) {
		super(errorType);
	}

	public InvalidProbabilityValueException(String errorType, String payload) {
		super(errorType, payload);
	}

	
}

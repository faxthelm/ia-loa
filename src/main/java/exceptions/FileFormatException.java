package exceptions;

import enums.ErrorMessages;

public class FileFormatException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7298555352184736354L;

	public FileFormatException(ErrorMessages errorType, String payload) {
		super(errorType, payload);
	}

	public FileFormatException(ErrorMessages errorType) {
		super(errorType);
	}

	public FileFormatException(String errorType, String payload) {
		super(errorType, payload);
	}


	
}

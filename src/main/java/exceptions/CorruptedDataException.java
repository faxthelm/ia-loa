package exceptions;

import enums.ErrorMessages;

public class CorruptedDataException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6355449059190566848L;

	public CorruptedDataException(ErrorMessages errorType, String payload) {
		super(errorType, payload);
	}

	public CorruptedDataException(ErrorMessages errorType) {
		super(errorType);
	}

	public CorruptedDataException(String errorType, String payload) {
		super(errorType, payload);
	}


}

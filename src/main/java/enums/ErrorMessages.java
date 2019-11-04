package enums;

public enum ErrorMessages {
	
	FILE_FORMAT_EXCEPTION("The format of the file is inconsistent at: "), 
	INVALID_PROBABILITY_FOUND ("The probability has to be greater than zero. Found: "),
	CORRUPTED_DATA_ERROR("The data used to initialize was corrupted. Found: ");
	
	private String message;
	
	private ErrorMessages(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}

package bri;

public class ValidationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**@brief Exception lors qu'une classe ne correspond pas aux Critères pour être un Service;
	 */
	public ValidationException(String message) {
		super(message);
	}

}

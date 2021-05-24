package it.unipv.ingsw.pickuppoint.service.exception;

public class CustomerAlreadyExistException extends Exception {
	public CustomerAlreadyExistException(String errorMessage) {
		super(errorMessage);
	}
}

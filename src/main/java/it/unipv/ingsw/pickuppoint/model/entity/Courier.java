package it.unipv.ingsw.pickuppoint.model.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

import it.unipv.ingsw.pickuppoint.model.User;

@Entity
public class Courier extends User {

	/**
	 * Relazione 1:N con l'entità OrderDetails
	 */
	@OneToMany(mappedBy = "courier")
	private List<OrderDetails> orderDetails;
	
	
	@NotEmpty(message = "First name cannot be empty")
	private String firstName;
	@NotEmpty(message = "Surname cannot be empty")
	private String lastName;


	public List<OrderDetails> getOrderDetails() {
		return orderDetails;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String surname) {
		this.lastName = surname;
	}
}

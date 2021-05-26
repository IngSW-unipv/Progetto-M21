package it.unipv.ingsw.pickuppoint.model.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

import it.unipv.ingsw.pickuppoint.model.User;

@Entity
public class Customer extends User {

	/**
	 * Relazione 1:N con l'entità OrderDetails
	 */

	@OneToMany(mappedBy = "customer", cascade= {CascadeType.PERSIST, CascadeType.MERGE})
	private List<OrderDetails> orderDetails = new ArrayList<OrderDetails>();

	@NotEmpty(message = "First name cannot be empty")
	private String firstName;
	@NotEmpty(message = "Surname cannot be empty")
	private String surname;

	public List<OrderDetails> getOrderDetails() {
		return orderDetails;
	}

	public void addOrderDetails(OrderDetails orderDetails) {
		this.orderDetails.add(orderDetails);
	}
	
	public void setOrderDetails(List<OrderDetails> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

}

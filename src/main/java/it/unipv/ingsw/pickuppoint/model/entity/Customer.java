package it.unipv.ingsw.pickuppoint.model.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

import it.unipv.ingsw.pickuppoint.model.User;
import lombok.Getter;

/**
 * 
 * RequiredArgsConstructor crea un costruttore con attributi con modificatore
 * final oppure con attributi annotati con NotNull
 *
 */

@Entity
public class Customer extends User {

	/**
	 * Relazione 1:N con l'entità OrderDetails
	 */
	@OneToMany(mappedBy = "customer")
	private Set<OrderDetails> orderDetails;
	@NotEmpty(message = "First name cannot be empty")
	private String firstName;
	@NotEmpty(message = "Surname cannot be empty")
	private String surname;

	public Set<OrderDetails> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(Set<OrderDetails> orderDetails) {
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

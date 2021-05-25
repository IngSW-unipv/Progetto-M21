package it.unipv.ingsw.pickuppoint.model.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import it.unipv.ingsw.pickuppoint.model.User;

@Entity
public class Courier extends User {

	/**
	 * Relazione 1:N con l'entità OrderDetails
	 */
	@OneToMany(mappedBy = "courier")
	private List<OrderDetails> orderDetails;

	public List<OrderDetails> getOrderDetails() {
		return orderDetails;
	}
}

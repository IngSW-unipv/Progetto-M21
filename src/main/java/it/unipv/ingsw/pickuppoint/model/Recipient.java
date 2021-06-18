package it.unipv.ingsw.pickuppoint.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Recipient")
public class Recipient {

	@Id
	@Column(name = "order_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderDetailsId;

	private String firstName;
	private String lastName;
	private String email;

	/**
	 * Relazione 1:1 con l'entita orderDetails, chiave primaria condivisa
	 */
	
	@OneToOne
	@MapsId
	@JoinColumn(name = "order_id")
	private OrderDetails orderDetails;

	public Recipient() {}

	public Recipient(String firstName, String lastName, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public void setOrderDetails(OrderDetails orderDetails) {
		this.orderDetails = orderDetails;
	}

	public Long getOrderDetailsId() {
		return orderDetailsId;
	}

	public String getRecipientFirstName() {
		return firstName;
	}

	public String getRecipientLastName() {
		return lastName;
	}

	public String getRecipientEmail() {
		return email;
	}

	public OrderDetails getOrderDetails() {
		return orderDetails;
	}
}

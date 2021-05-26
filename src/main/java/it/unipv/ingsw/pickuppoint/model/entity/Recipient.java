package it.unipv.ingsw.pickuppoint.model.entity;

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

	private String recipientFirstName;
	private String recipientLastName;
	private String recipientEmail;

	/**
	 * Relazione 1:1 con l'entita orderDetails, chiave primaria condivisa
	 */
	@OneToOne
	@MapsId
	@JoinColumn(name = "order_id")
	private OrderDetails orderDetails;

	public Long getOrderDetailsId() {
		return orderDetailsId;
	}

	public String getRecipientFirstName() {
		return recipientFirstName;
	}

	public String getRecipientLastName() {
		return recipientLastName;
	}

	public String getRecipientEmail() {
		return recipientEmail;
	}

	public OrderDetails getOrderDetails() {
		return orderDetails;
	}
}

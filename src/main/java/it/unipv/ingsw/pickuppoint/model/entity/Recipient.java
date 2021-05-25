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

	private String recipientName;
	private String recipientSurname;
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

	public String getRecipientName() {
		return recipientName;
	}

	public String getRecipientSurname() {
		return recipientSurname;
	}

	public String getRecipientEmail() {
		return recipientEmail;
	}

	public OrderDetails getOrderDetails() {
		return orderDetails;
	}
}

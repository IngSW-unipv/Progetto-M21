package it.unipv.ingsw.pickuppoint.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import it.unipv.ingsw.pickuppoint.model.DeliveryStatus;
import lombok.Data;

@Data
@Entity
@Table(name = "DeliveryDetails")
public class DeliveryDetails {

	/**
	 * Notare che non è presente "@GeneratedValue perchè la chiave primaria viene
	 * condivisa con la chiave primaria di OrderDetails (MapsId)
	 */
	@Id
	@Column(name = "order_id")
	private Long orderDetailsId;

	private String arrivalDataHub;
	private String deliveryDate;
	private String dataDeliverd;

	/**
	 * Relazione 1:1 con l'entità OrderDetails con chiave primaria condivisa
	 */
	@OneToOne
	@MapsId
	@JoinColumn(name = "order_id")
	private OrderDetails orderDetails;

	/**
	 * Con Enumerated indichiamo che a JPA di memorizzare l'enum in formato di
	 * stringa attraverso Enum.name(), indica lo stato della consegna di default =
	 * HUB
	 */
	@Column(length = 32, columnDefinition = "varchar(32) default 'HUB'")
	@Enumerated(EnumType.STRING)
	private DeliveryStatus deliveryStatus = DeliveryStatus.HUB;

}

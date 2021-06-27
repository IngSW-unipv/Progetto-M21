package it.unipv.ingsw.pickuppoint.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import it.unipv.ingsw.pickuppoint.utility.DeliveryStatus;

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
	private String dataDelivered;
	private String hubDate;
	private String withdrawalDate;

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
	private DeliveryStatus deliveryStatus;
	
	public DeliveryDetails() {
		generateHubDate();
		
		
	}

	public String getWithdrawalDate() {
		return withdrawalDate;
	}

	public void setWithdrawalDate(String withdrawalDate) {
		this.withdrawalDate = withdrawalDate;
	}

	public Long getOrderDetailsId() {
		return orderDetailsId;
	}

	public void setOrderDetailsId(Long orderDetailsId) {
		this.orderDetailsId = orderDetailsId;
	}

	public String getDataDelivered() {
		return dataDelivered;
	}

	public void setDataDelivered(String dataDelivered) {
		this.dataDelivered = dataDelivered;
	}

	public OrderDetails getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(OrderDetails orderDetails) {
		this.orderDetails = orderDetails;
	}

	public DeliveryStatus getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public String getHubDate() {
		return hubDate;
	}

	public void setHubDate(String hubDate) {
		this.hubDate = hubDate;
	}
	
	private void generateHubDate() {
		Date date = Calendar.getInstance().getTime();  
		SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		setHubDate(currentDate.format(date));
		deliveryStatus = DeliveryStatus.HUB;
	}

}

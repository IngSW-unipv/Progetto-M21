package it.unipv.ingsw.pickuppoint.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "Product")
public class Product {

	@Id
	@Column(name = "product_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;

	private double weight;
	private double width;
	private double length;
	private double height;

	/**
	 * Relazione N:1 con la classe OrderDetails che mappa l'entita OrderDetails Il
	 * campo order_id in Product è FK
	 */
	@ManyToOne
	@JoinColumn(name = "order_id", referencedColumnName = "order_id")
	private OrderDetails orderDetails;

	/**
	 * Associazione 1:1 con l'entita slot
	 */
	@OneToOne(mappedBy = "product")
	private Slot slot;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public OrderDetails getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(OrderDetails orderDetails) {
		this.orderDetails = orderDetails;
	}

	public Slot getSlot() {
		return slot;
	}

	public void setSlot(Slot slot) {
		this.slot = slot;
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", weight=" + weight + ", width=" + width + ", length=" + length
				+ ", height=" + height + ", orderDetails=" + orderDetails.getOrderDetailsId() + ", slot=" + slot + "]";
	}
	
	
}

package it.unipv.ingsw.pickuppoint.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import it.unipv.ingsw.pickuppoint.utility.ProductSize;


@Entity
@Table(name = "Product")
public class Product {

	@Id
	@Column(name = "product_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;

	private double weight;
	
	@Enumerated(EnumType.STRING)
	private ProductSize size;
	

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
	@OneToOne(mappedBy = "product", orphanRemoval=true)
	private Slot slot;


	
	public Product() {}

	public Product(double weight, ProductSize size) {
		this.weight = weight;
		this.size = size;
	}

	public ProductSize getSize() {
		return size;
	}
	
	public double getVolume() {
		return size.getVolume();
	}

	public Long getOrderId() {
		return orderDetails.getOrderDetailsId();
	}
	
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public double getWeight() {
		return weight;
	}

	public double getWidth() {
		return size.getWidth();
	}

	public double getLength() {
		return size.getLength();
	}

	public double getHeight() {
		return size.getHeight();
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
	
}

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

import lombok.Data;

@Data
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
	@JoinColumn(name = "order_id")
	private OrderDetails orderDetails;

	/**
	 * Associazione 1:1 con l'entita slot
	 */
	@OneToOne(mappedBy = "product")
	private Slot slot;
}

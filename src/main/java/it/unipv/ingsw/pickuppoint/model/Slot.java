package it.unipv.ingsw.pickuppoint.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import it.unipv.ingsw.pickuppoint.utility.SlotSize;

@Entity
@Table(name = "slot")
public class Slot {

	/**
	 * Notare che non è presente "@GeneratedValue (@JoinColumn)
	 */
	@Id
	@Column(name = "slot_id")
	@GeneratedValue
	private Long slotId;

	/**
	 * I valore booleani sono definiti come tipo tinyint, JPA li identifica come
	 * BIGINT. In questo modo forziamo JPA a definire il tipo come tinyint.
	 * 
	 * Di default è true
	 */
	@Column(columnDefinition = "tinyint(1) default 1")
	private boolean isEmpty;

	/**
	 * Associazione N:1 con Locker
	 */
	@ManyToOne
	@JoinColumn(name = "locker_id")
	private Locker locker;

	/**
	 * PrimaryKeyJoinColumn in questo caso la chiave primaria dell'entita' Slot
	 * (slot_id) non viene utilizzata come chiave esterna per l'associazione con
	 * l'entita' Product perchè essa è anche in associazione con OrderDetails
	 * 
	 * Associazione 1:1 con Product
	 * 
	 */
	@OneToMany(mappedBy = "slot", cascade = CascadeType.ALL)
	private List<Product> products;

	@Enumerated(EnumType.STRING)

	private SlotSize size;

	public Long getSlotId() {
		return slotId;
	}

	public void setSlotId(Long slotId) {
		this.slotId = slotId;
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

	public double getVolume() {
		return size.getVolume();
	}

	public boolean isEmpty() {
		return isEmpty;
	}

	public void setEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}

	public Locker getLocker() {
		return locker;
	}

	public void setLocker(Locker locker) {
		this.locker = locker;
	}

	public List<Product> getProduct() {
		return products;
	}

	public void addProduct(Product product) {
		this.products.add(product);
		product.setSlot(this);
	}

	public void setProduct(List<Product> products) {
		this.products = products;
	}

	public void removeProduct(Product product) {
		this.products.remove(product);
	}

	public SlotSize getSize() {
		return size;
	}

	public void setSize(SlotSize size) {
		this.size = size;
	}
}

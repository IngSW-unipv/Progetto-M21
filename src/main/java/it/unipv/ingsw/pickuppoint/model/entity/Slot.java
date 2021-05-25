package it.unipv.ingsw.pickuppoint.model.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import it.unipv.ingsw.pickuppoint.model.SlotSize;

@Entity
@Table(name = "Slot")
public class Slot {

	/**
	 * Notare che non è presente "@GeneratedValue...
	 */
	@Id
	@Column(name = "slot_id")
	@GeneratedValue
	private Long slotId;

	private int height;
	private int width;
	private int length;

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

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "product_id")
	private Product product;

	@Enumerated(EnumType.STRING)
	private SlotSize size;

	public Long getSlotId() {
		return slotId;
	}

	public void setSlotId(Long slotId) {
		this.slotId = slotId;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
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

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public SlotSize getSize() {
		return size;
	}

	public void setSize(SlotSize size) {
		this.size = size;
	}

//	private void initSize() {
//		this.height = size.getHeight();
//		this.width = size.getWidth();
//		this.length = size.getLength();
//	}


}

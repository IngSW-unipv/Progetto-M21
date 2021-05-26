package it.unipv.ingsw.pickuppoint.model.entity;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "Locker")
public class Locker {

	@Id
	@Column(name = "locker_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long lockerId;
	private String name;

	/**
	 * Associazione 1:1 con l'entità LockerAddress In questo caso (con associazione
	 * 1:1) orphanRemoval = true indica che se una riga delll'entità locker viene
	 * cancellata, verra cancellata anche la riga dell'entità lockerAddres
	 * referenziata
	 * 
	 * = Disconnessione + cancellazione
	 * 
	 * La differenza con CascadeType.REMOVE è che quest'ultima disconette soltanto,
	 * ma in questo caso una sola disconnessione non ha senso poichè LockerAddress
	 * dipende esclusivamente da Locker
	 * 
	 * Inserire cascade = CascadeType.ALL, orphanRemoval = true è una ridondanza
	 */
	@OneToOne(mappedBy = "locker", orphanRemoval = true)
	@PrimaryKeyJoinColumn
	private LockerAddress lockerAddress;

	/**
	 * Relazione 1:1 con la classe slot che mappa la tabella slot
	 * 
	 * E' attivo il cascade su tutte le operazioni di INSERT, DELETE, UPDATE
	 * 
	 * Dato che è una relazione 1:1 possiamo condividere la chiave primaria per
	 * ottimizzare lo spazio di archiviazione
	 * 
	 * PrimaryKeyJoinColumn indica che la chiave primaria order_id viene utilizzata
	 * come chiave esterna per l'associazione con l'entità deliveryDetails
	 * 
	 */
	@OneToMany(mappedBy = "locker", cascade = CascadeType.ALL)
	private List<Slot> slot;

	/**
	 * Relazione 1:N con l'entita orderDetails
	 */
	@OneToMany(mappedBy = "locker", cascade = CascadeType.ALL)
	private List<OrderDetails> orderDetails;

	public Long getLockerId() {
		return lockerId;
	}

	public String getName() {
		return name;
	}

	public LockerAddress getLockerAddress() {
		return lockerAddress;
	}

	public void setLockerAddress(LockerAddress lockerAddress) {
		this.lockerAddress = lockerAddress;
	}

	public List<Slot> getSlot() {
		return slot;
	}

	public void setSlot(List<Slot> slot) {
		this.slot = slot;
	}

	public List<OrderDetails> getOrderDetails() {
		return orderDetails;
	}

//	private static final int SLOTNUMBER = 15;

	/**
	 * 
	 * @param lockerId
	 * @param city
	 * @param address
	 * @param postalCode
	 */
//	public Locker(int lockerId, String city, String address, int postalCode) {
//		this.lockerId = lockerId;
//		lockerAddress = new LockerAddress(city, address, postalCode);
//		lockerNumberToSlot = new HashMap<Integer, Slot>();
//		addSlots();
//	}
//
//	private void addSlots() {
//		for (int i = 0; i < SLOTNUMBER; i++) {
//			if (i <= Math.round(SLOTNUMBER / 3))
//				lockerNumberToSlot.put(i, new Slot(SlotSize.BIG));
//			else if (i < Math.round(SLOTNUMBER / 3 * 2))
//				lockerNumberToSlot.put(i, new Slot(SlotSize.MEDIUM));
//			else
//				lockerNumberToSlot.put(i, new Slot(SlotSize.SMALL));
//		}
//	}

	/**
	 * 
	 * @param slotSize
	 * @return slotId
	 */
//	public int getSlotId(SlotSize slotSize) {
//		for (Slot slot : lockerNumberToSlot.values()) {
//			if (slot.isEmpty() && slot.getSize() == slotSize) {
//				return slot.getSlotId();
//			}
//		}
//		return -1;
//	}
}

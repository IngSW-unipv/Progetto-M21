package it.unipv.ingsw.pickuppoint.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

	public Locker(Long lockerId) {
		this.lockerId = lockerId;
	}

	public Locker() {
	}

	public void setLockerId(Long lockerId) {
		this.lockerId = lockerId;
	}

	public Long getLockerId() {
		return lockerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
}

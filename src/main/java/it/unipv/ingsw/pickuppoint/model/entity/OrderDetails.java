package it.unipv.ingsw.pickuppoint.model.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.Data;

/**
 * Entity specifica che questa classe è un'entità che rappresenta una tabella
 * archiviata in un db
 * 
 * Il nome dell'entità di default è il nome della classe, altrimenti può essere
 * specificato esplicitamente con Table(name="")
 * 
 * Data è una scorciatoria per la generazione automatica di getter, setter,
 * tostring, equals, hashcode...
 */

@Data
@Entity
@Table(name = "OrderDetails")
public class OrderDetails {

	/**
	 * GeneratedValue indica che il valore della chiave primaria viene generato
	 * automaticamente
	 * 
	 * GenerationType.IDENTITY è uno die 4 tipi di generazione della chiave
	 * primaria, Incrementare il valore ad ogni operazione di inserimento nella
	 * tabella
	 * 
	 * Id specifica la chiave primaria dell'entità
	 * 
	 * Il nome di ogni colonna (campo) di default è il nome dell'attributo,
	 * altrimenti puo essere specificato esplicitamente con Column(name="")
	 */

	@Id
	@Column(name = "order_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderDetailsId;

	@Column(nullable = true)
	private int trackingCode;

	// Spostato pickupCode da DD a OD
	@Column(nullable = true)
	private String pickupCode;

	@OneToOne
	@JoinColumn(name = "locker_id")
	private Locker locker;

	/**
	 * Relazione 1:N con la classe Product che mappa la tabella Product Product ha
	 * un attributo chiamato orderDetails per mappare la relazione inversa N:1
	 * 
	 * E' attivo il cascade su tutte le operazioni di INSERT, DELETE, UPDATE
	 */
	@OneToMany(mappedBy = "orderDetails", cascade = CascadeType.ALL)
	private Set<Product> products;

	/**
	 * Relazione 1:1 con l'entita DeliveryDetails
	 * 
	 * DeliveryDetails ha un attributo chiamato deliveryDetails per mappare la
	 * relazione lato DeliveryDetails
	 * 
	 * E' attivo il cascade su tutte le operazioni di INSERT, DELETE, UPDATE
	 * 
	 * E' possibile condividere la chiave primaria per ottimizzare lo spazio di
	 * archiviazione attraverso PrimaryKeyJoinColumn: indica che la chiave primaria
	 * order_id viene utilizzata come chiave esterna per l'associazione con l'entità
	 * deliveryDetails
	 */
	@OneToOne(mappedBy = "orderDetails", cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private DeliveryDetails deliveryDetails;

	/**
	 * Relazione 1:1 con l'entità Recipient...
	 */

	@OneToOne(mappedBy = "orderDetails", cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private Recipient recipient;

	/**
	 * Relazione N:1 con l'entità Courier...
	 */
	@ManyToOne
	@JoinColumn(name = "courier_id", insertable = false, updatable = false)
	private Courier courier;

	/**
	 * Relazione N:1 con l'entita Customer
	 */
	@ManyToOne
	@JoinColumn(name = "customer_id", insertable = false, updatable = false)
	private Customer customer;

	/**
	 * insertable = false, updatable = false indica che la responsabilità di creare
	 * o aggiornare la colonna in questione non è dell'entità corrente, ma di altre
	 * entità
	 */

}
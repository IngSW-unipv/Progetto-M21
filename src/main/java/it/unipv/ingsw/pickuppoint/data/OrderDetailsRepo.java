package it.unipv.ingsw.pickuppoint.data;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.unipv.ingsw.pickuppoint.model.OrderDetails;
import it.unipv.ingsw.pickuppoint.utility.DeliveryStatus;

@Repository
public interface OrderDetailsRepo extends JpaRepository<OrderDetails, Long> {

	/**
	 * Restituisce la lista di ordini del customer data la chiave primaria di
	 * customer
	 * 
	 * @param customerId
	 * @return lista ordini
	 */
	List<OrderDetails> findByCustomer_userId(Long customerId);

	/**
	 * Restituisce la lista di ordini del courier data la chiave primaria di courier
	 * 
	 * @param customerId
	 * @return lista ordini
	 */
	List<OrderDetails> findByCourier_userId(Long customerId);

	/**
	 * Restituisce un ordine dato il tracking code
	 * 
	 * @param trackingCode
	 * @return ordine
	 */
	OrderDetails findByTrackingCode(String trackingCode);

	/**
	 * Restitusce una lista di ordini data la sua chiave primaria
	 */
	Optional<OrderDetails> findById(Long id);

	/**
	 * Restituisce una lista di ordini dato un deliveryStatus
	 * 
	 * @param deliveryStatus
	 * @return lista di ordini
	 */
	List<OrderDetails> findByDeliveryDetails_deliveryStatus(DeliveryStatus deliveryStatus);

	/**
	 * Restituisce un ordine dato un pickup code
	 * 
	 * @param pickupCode
	 * @return ordine
	 */
	OrderDetails findByPickupCode(String pickupCode);
}

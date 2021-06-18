package it.unipv.ingsw.pickuppoint.data;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.unipv.ingsw.pickuppoint.model.OrderDetails;
import it.unipv.ingsw.pickuppoint.utility.DeliveryStatus;

@Repository
public interface OrderDetailsRepo extends JpaRepository<OrderDetails, Long> {

	List<OrderDetails> findByCustomer_userId(Long customerId);
	List<OrderDetails> findByCourier_userId(Long customerId);
	OrderDetails findByTrackingCode(String trackingCode);
	Optional<OrderDetails> findById(Long id);	
	List <OrderDetails> findByDeliveryDetails_deliveryStatus(DeliveryStatus deliveryStatus);
	OrderDetails findByPickupCode(String pickupCode);
}

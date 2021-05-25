package it.unipv.ingsw.pickuppoint.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.unipv.ingsw.pickuppoint.model.entity.OrderDetails;

@Repository
public interface OrderDetailsRepo extends JpaRepository<OrderDetails, Long> {
	@Query("select od from OrderDetails od where customer_Id= :customerId")
	List<OrderDetails> getCustomerOrders(Long customerId);

	@Query("select od from OrderDetails od where courier_Id= :courierId")
	List<OrderDetails> getCourierOrders(Long courierId);
}

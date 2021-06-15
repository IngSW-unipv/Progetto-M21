package it.unipv.ingsw.pickuppoint.data;

import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.unipv.ingsw.pickuppoint.model.entity.DeliveryDetails;

@Repository
public interface DeliveryDetailsRepo extends JpaRepository<DeliveryDetails, Long> {
	

//	@Query(value = "SELECT order_id, DATEDIFF(CURRENT_TIMESTAMP(), progettom21.delivery_details.data_deliverd) as difference from progettom21.delivery_details where (progettom21.delivery_details.data_deliverd IS NOT NULL)", nativeQuery = true)
//	public Map<Long, Integer> findListOfDifferenceDeliverdDateAndCurrentDate3();
	 
	 
	 public default Map<Long, Integer> findListOfDifferenceDeliverdDateAndCurrentDate(EntityManager em){
		@SuppressWarnings("unchecked")

		Map<Long, Integer> findListOfDifferenceDeliverdDateAndCurrentDate = (Map<Long, Integer>) em.createNativeQuery("SELECT order_id, DATEDIFF(CURRENT_TIMESTAMP(), data_deliverd) "
				+ "as difference from delivery_details where (data_deliverd IS NOT NULL) AND (delivery_date IS NULL)", Tuple.class)
			.getResultStream()
			.collect(
			    Collectors.toMap(
			        tuple -> ((Number) ((Tuple) tuple).get("order_id")).longValue(),
			        tuple -> ((Number) ((Tuple) tuple).get("difference")).intValue()
			    )
			);
		
		return findListOfDifferenceDeliverdDateAndCurrentDate;
	
	 }

	



}

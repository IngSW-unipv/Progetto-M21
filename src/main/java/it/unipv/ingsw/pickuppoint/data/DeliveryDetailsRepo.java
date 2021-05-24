package it.unipv.ingsw.pickuppoint.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.unipv.ingsw.pickuppoint.model.entity.DeliveryDetails;

@Repository
public interface DeliveryDetailsRepo extends JpaRepository<DeliveryDetails, Long> {

}

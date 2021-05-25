package it.unipv.ingsw.pickuppoint.data;

import org.springframework.data.jpa.repository.JpaRepository;

import it.unipv.ingsw.pickuppoint.model.entity.Courier;

public interface CourierRepo extends JpaRepository<Courier, Long>{
	

}

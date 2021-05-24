package it.unipv.ingsw.pickuppoint.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.unipv.ingsw.pickuppoint.model.entity.Slot;

@Repository
public interface SlotRepo extends JpaRepository<Slot, Long>{

}

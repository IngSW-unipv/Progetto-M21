package it.unipv.ingsw.pickuppoint.data;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.unipv.ingsw.pickuppoint.model.Slot;

@Repository
public interface SlotRepo extends JpaRepository<Slot, Long> {

	/**
	 * Restitusice una lista di slot dato il locker di appartenenza
	 * 
	 * @param lockerId
	 * @return lista slot
	 */
	@Query(value = "SELECT * FROM slot WHERE locker_id = ?1", nativeQuery = true)
	ArrayList<Slot> findByLockerId(Long lockerId);

	/**
	 * Restituisce lo slot data la sua chiave primaria
	 * 
	 * @param slotId
	 * @return Slot
	 */
	Slot findBySlotId(Long slotId);
}

package it.unipv.ingsw.pickuppoint.data;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.unipv.ingsw.pickuppoint.model.Product;
import it.unipv.ingsw.pickuppoint.model.Slot;

@Repository
public interface SlotRepo extends JpaRepository<Slot, Long>{

	
//	List<Slot> findByLocker_lockerId(Long lockerId);
	
	//@Query("select u from slot u where u.locker_id = ?1")
	@Query(value = "SELECT * FROM SLOT WHERE locker_id = ?1", nativeQuery = true)
	ArrayList<Slot> findByLockerId(Long lockerId);
	
	void deleteByProduct(Product product);
	
}

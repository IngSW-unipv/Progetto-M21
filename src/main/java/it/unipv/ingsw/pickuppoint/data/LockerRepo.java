package it.unipv.ingsw.pickuppoint.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.unipv.ingsw.pickuppoint.model.Locker;

@Repository
public interface LockerRepo extends JpaRepository<Locker, Long> {

	/**
	 * Restituisce un locker data la sua chiave primaria
	 * 
	 * @param lockerId
	 * @return Locker
	 */
	Locker findByLockerId(Long lockerId);

}

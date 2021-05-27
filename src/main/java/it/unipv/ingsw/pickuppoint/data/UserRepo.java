package it.unipv.ingsw.pickuppoint.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.unipv.ingsw.pickuppoint.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
	User findByEmail(String email);

	/**
	 * Questo metodo cerca l'utenti per ruolo
	 * 
	 * @param name del ruolo
	 * @return lista di user
	 */
	List<User> findByRole_name(String name);

}

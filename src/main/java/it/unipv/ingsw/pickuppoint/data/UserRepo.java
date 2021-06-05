package it.unipv.ingsw.pickuppoint.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.unipv.ingsw.pickuppoint.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

	/**
	 * Questo metodo restituisce l'utente data l'email
	 * 
	 * @param email
	 * @return
	 */
	User findByEmail(String email);

	/**
	 * Questo metodo restituisce una lista di utenti in base la ruolo
	 * 
	 * @param name del ruolo
	 * @return lista di user
	 */
	List<User> findByRole_name(String name);

	/**
	 * Questo metodo restitusce l'utente data la chiave primaria
	 * 
	 * @param userId
	 * @return
	 */
	User findByUserId(Long userId);
}

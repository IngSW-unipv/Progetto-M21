package it.unipv.ingsw.pickuppoint.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.unipv.ingsw.pickuppoint.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

	/**
	 * Restituisce l'utente data l'email
	 * 
	 * @param email
	 * @return User
	 */
	User findByEmail(String email);

	/**
	 * Restituisce una lista di utenti in base al ruolo
	 * 
	 * @param name del ruolo
	 * @return lista di user
	 */
	List<User> findByRole_name(String name);

	/**
	 * Restitusce l'utente data la chiave primaria
	 * 
	 * @param userId
	 * @return User
	 */
	User findByUserId(Long userId);

	/**
	 * Elimina user data la chiave primaria
	 * 
	 * @param id
	 */
	void deleteByUserId(Long id);

	/**
	 * Restituisce tutte gli user
	 */
	List<User> findAll();

}

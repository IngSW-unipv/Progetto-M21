package it.unipv.ingsw.pickuppoint.data;

import org.springframework.data.jpa.repository.JpaRepository;

import it.unipv.ingsw.pickuppoint.model.Role;

public interface RoleRepo extends JpaRepository<Role, Long> {
	/**
	 * Restituisce il ruolo dato il nome
	 * 
	 * @param name
	 * @return Ruolo
	 */
	Role findByName(String name);
}

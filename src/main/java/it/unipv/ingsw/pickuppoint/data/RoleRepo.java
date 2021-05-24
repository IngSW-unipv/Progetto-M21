package it.unipv.ingsw.pickuppoint.data;

import org.springframework.data.jpa.repository.JpaRepository;

import it.unipv.ingsw.pickuppoint.model.entity.Role;

public interface RoleRepo extends JpaRepository<Role, Long>{
	
	Role findByName(String name);

}

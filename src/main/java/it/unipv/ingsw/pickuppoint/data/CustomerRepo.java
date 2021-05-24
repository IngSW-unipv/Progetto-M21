package it.unipv.ingsw.pickuppoint.data;

import org.springframework.data.jpa.repository.JpaRepository;

import it.unipv.ingsw.pickuppoint.model.User;
import it.unipv.ingsw.pickuppoint.model.entity.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
	User findByEmail(String email);
}

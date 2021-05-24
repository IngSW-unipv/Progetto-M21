package it.unipv.ingsw.pickuppoint.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import it.unipv.ingsw.pickuppoint.data.CustomerRepo;
import it.unipv.ingsw.pickuppoint.data.RoleRepo;
import it.unipv.ingsw.pickuppoint.data.UserRepo;
import it.unipv.ingsw.pickuppoint.model.entity.Customer;
import it.unipv.ingsw.pickuppoint.model.entity.Role;
import it.unipv.ingsw.pickuppoint.service.exception.CustomerAlreadyExistException;

@Service
public class CustomerService {

	/**
	 * L'annotazione Autowired permette a spring di risolvere e inserire una
	 * dipendenze di un bean in un altro bean, all'interno del contesto
	 * dell'applicazione.
	 * 
	 * Vi sono vari modi di utilizzare questa annotazione, costruttori, campi,
	 * metodi, parametri...
	 * 
	 * Nel nostro caso (fields) i campi vengono "iniettati" (=aggiunta istanza) dopo
	 * la creazione del un bean. Non devono essere pubblici
	 * 
	 */

	@Autowired
	UserRepo userRepo;
	@Autowired
	RoleRepo roleRepo;

	BCryptPasswordEncoder passwordEncoder;

	public void register(Customer customer) throws CustomerAlreadyExistException {
		if (checkIfUserExist(customer.getEmail())) {
			throw new CustomerAlreadyExistException("Customer already exists for this email");
		}
		customer.setEnabled(true);
		customer.setRole(getUserRole());
		encodePassword(customer);
		userRepo.save(customer);

	}

	private Role getUserRole() {
		Role role = roleRepo.findByName("CUSTOMER");
		return role;
	}

	private void encodePassword(Customer customer) {
		passwordEncoder = new BCryptPasswordEncoder();
		customer.setPassword(passwordEncoder.encode(customer.getPassword()));
	}

	private boolean checkIfUserExist(String email) {
		if (userRepo.findByEmail(email) != null) {
			return true;
		}
		return false;
	}
}

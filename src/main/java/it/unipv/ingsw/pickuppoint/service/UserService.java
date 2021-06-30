package it.unipv.ingsw.pickuppoint.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import it.unipv.ingsw.pickuppoint.data.RoleRepo;
import it.unipv.ingsw.pickuppoint.data.UserRepo;
import it.unipv.ingsw.pickuppoint.model.Role;
import it.unipv.ingsw.pickuppoint.model.User;
import it.unipv.ingsw.pickuppoint.service.exception.CustomerAlreadyExistException;

@Service
public class UserService {
	@Autowired
	UserRepo userRepo;
	@Autowired
	RoleRepo roleRepo;
	@Autowired
	OrderDetailsService orderDetailsService;
	@Autowired
	Date date;
	BCryptPasswordEncoder passwordEncoder;

	public List<User> getAllCouriers() {
		return userRepo.findByRole_name("COURIER");
	}

	/**
	 * Restituisce User autenticato nel momento della richiesta
	 * 
	 * @return User
	 */
	public User getAuthenticatedUser() {
		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser"))
			return null;
		return ((UserAuthorization) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
	}

	/**
	 * Metodo per la registrazione di un nuovo user:
	 * 
	 * @param newUser da registrare
	 * @throws CustomerAlreadyExistException
	 */
	public void register(User newUser) throws CustomerAlreadyExistException {
		if (checkIfUserExist(newUser.getEmail())) {
			throw new CustomerAlreadyExistException("Customer already exists for this email");
		}

		if (newUser.getRole() == null) {
			newUser.setEnabled(true);
			newUser.setRole(getCustomerRole());
		}
		newUser.setRegistrationDate(date.getCurrentDataTime());
		encodePassword(newUser);
		saveUser(newUser);
	}

	/**
	 * Restituisce il Customer con chiave primaria id
	 * 
	 * @param id
	 * @return Customer
	 */
	public User getUser(Long id) {
		return userRepo.findByUserId(id);
	}

	/**
	 * Restitusce il ruole associato al Customer, utile quando il customer viene
	 * registrato per settare il ruolo
	 * 
	 * @return Ruolo Customer
	 */
	private Role getCustomerRole() {
		Role role = roleRepo.findByName("CUSTOMER");
		return role;
	}

	/**
	 * Questo metodo cripta la password del Customer e la inserisce nella propria
	 * istanza
	 * 
	 * @param customer
	 */
	private void encodePassword(User customer) {
		passwordEncoder = new BCryptPasswordEncoder();
		customer.setPassword(passwordEncoder.encode(customer.getPassword()));
	}

	/**
	 * Verifica se all'interno dell'entita User è presente un record contente
	 * l'email passata
	 * 
	 * @param email
	 * @return true se esiste, altrimenti false
	 */
	private boolean checkIfUserExist(String email) {
		if (userRepo.findByEmail(email) != null) {
			return true;
		}
		return false;
	}

	/**
	 * Metodo per aggiungere la lista di orders al model in base al tipo di user
	 * 
	 * @param model
	 */
	public void addListOrders(Model model) {
		User user = getAuthenticatedUser();
		if (user.getRole().getName().equals("COURIER")) {
			model.addAttribute("listOrders", orderDetailsService.getCourierOrders(user.getUserId()));
		} else if (user.getRole().getName().equals("CUSTOMER")) {
			model.addAttribute("listOrders", orderDetailsService.getCustomerOrders(user.getUserId()));
		} else if (user.getRole().getName().equals("ADMINISTRATOR")) {
			model.addAttribute("listOrders", orderDetailsService.getAllOrderDetails());
		}

	}

	/**
	 * Metodo per aggiungere tutti gli user al modello di profile dell'admin
	 * 
	 * @param model
	 */
	public void findAllUsers(Model model) {
		List<User> users = userRepo.findAll(Sort.by(Sort.Direction.ASC, "role"));
		model.addAttribute("listUsers", users);
	}

	/**
	 * Metodo per il salvataggio di un nuovo User
	 * 
	 * @param user
	 */
	public void saveUser(User user) {
		userRepo.save(user);
	}

	/**
	 * Metodo per eliminare un u+ tente
	 * 
	 * @param id
	 */
	@Transactional
	public void delete(Long id) {
		userRepo.deleteByUserId(id);
	}
}

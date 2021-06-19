package it.unipv.ingsw.pickuppoint.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import it.unipv.ingsw.pickuppoint.data.RoleRepo;
import it.unipv.ingsw.pickuppoint.data.UserRepo;
import it.unipv.ingsw.pickuppoint.model.Product;
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
	 * @return utente autenticato
	 */
	public User getAuthenticatedUser() {
		return ((UserAuthorization) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
	}

	/**
	 * Questo metodo effettua la registrazione del Customer
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
	 * Questo metodo restitusce il ruole associato al Customer, utile quando il
	 * customer viene registrato per settare il ruolo
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

	public void findAllCouriers(Model model) {
		model.addAttribute("listCouriers", userRepo.findByRole_name("COURIER"));
	}

	public void findAllUsers(Model model) {
		List<User> users = userRepo.findAll();
		Collections.sort(users, new Comparator<User>() {

			@Override
			public int compare(User user0, User user1) {

				return user0.getRole().getName().compareTo(user1.getRole().getName());

			}
		});

		model.addAttribute("listUsers", users);
	}

	public void saveUser(User user) {
		userRepo.save(user);
	}

	public void editUser(Model model, Long id) {
		model.addAttribute("user", userRepo.findById(id));
	}

	@Transactional
	public void delete(Long id) {
		userRepo.deleteByUserId(id);
	}
}

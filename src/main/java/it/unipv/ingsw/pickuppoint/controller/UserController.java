package it.unipv.ingsw.pickuppoint.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import it.unipv.ingsw.pickuppoint.model.User;
import it.unipv.ingsw.pickuppoint.service.UserService;
import it.unipv.ingsw.pickuppoint.service.exception.CustomerAlreadyExistException;

@Controller
public class UserController {
	@Autowired
	private UserService userService;

	/**
	 * Invocato quando il client effettua una richiesta GET alla root del sito
	 * 
	 * @return home page
	 */
	@GetMapping("/")
	public String viewIndex() {
		return "index";
	}

	/**
	 * Invocato quando il client effettua una richiesta GET a /profile
	 * 
	 * @return pagina html profilo
	 */
	@GetMapping("/profile")
	public String viewProfile(Model model) {
		userService.addListOrders(model);
		return "profile";
	}

	/**
	 * Invocato quando il client effettua una richiesta GET a /Orders. Recupera e
	 * visualizza gli ordini del Customer.
	 * 
	 * @param model è un contenitore di attributi che viene inoltrato al client per
	 *              essere visualizzato o manipolato
	 * @return la pagina html della vista degli ordini
	 */
	@GetMapping("/Orders")
	public String viewCustomerOrders(Model model) {
		userService.addListOrders(model);
		return "viewOrders";
	}

	/**
	 * Invocato quando il client effettua una richiesta GET a /register. Viene
	 * istanziato un nuovo customer, viene aggiunto al modello che a sua volta viene
	 * inoltrato al client per essere manipolato.
	 * 
	 * @param model è un contenitore di attributi che viene inoltrato al client per
	 *              essere visualizzato o manipolato
	 * @return la pagina html di registrazione
	 */
	@GetMapping("/register")
	public String viewRegister(Model model) {
		User customer = new User();
		model.addAttribute("user", customer);
		return "registration";
	}

	/**
	 * Invocato quando il client effettua una richiesta POST a /register.
	 * 
	 * @param customer      popolato dai sui attributi attraverso input html
	 * @param bindingResult per la verifica di errori di registrazione
	 * @param model         si riferisce all'istanza del Customer, utile nel caso in
	 *                      cui ci fossero degli errori nella registrazione per
	 *                      inoltrare la stessa istanza (ed evitare di crearne
	 *                      un'altra) alla pagina di registrazione
	 * @return In caso di errori ritorna la pagina di registrazione, altrimenti se
	 *         l'utente loggato è un admin ritorna al profilo, sennò ritorna un
	 *         reindirizzamento alla pagina root
	 */
	@PostMapping(value = "/register")
	public String userRegistration(@Valid User customer, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("user", customer);
			return "registration";
		}

		try {
			userService.register(customer);
		} catch (CustomerAlreadyExistException e) {
			bindingResult.rejectValue("email", "user.email", "An account already exists for this email");
			model.addAttribute("user", customer);
			return "registration";
		}

		if (userService.getAuthenticatedUser() == null)
			return "index";

		if ("ADMINISTRATOR".equals(userService.getAuthenticatedUser().getRole().getName()))
			return "redirect:" + "profile";

		return "index";
	}
}

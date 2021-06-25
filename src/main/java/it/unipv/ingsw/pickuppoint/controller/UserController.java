package it.unipv.ingsw.pickuppoint.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.unipv.ingsw.pickuppoint.model.User;
import it.unipv.ingsw.pickuppoint.service.HubService;
import it.unipv.ingsw.pickuppoint.service.UserService;
import it.unipv.ingsw.pickuppoint.service.exception.CustomerAlreadyExistException;
import it.unipv.ingsw.pickuppoint.service.exception.ErrorPickupCode;

@Controller
public class UserController {
	@Autowired
	UserService userService;
	@Autowired
	HubService hubService;

	/**
	 * Questo metodo viene invocato quando il client effettua una richiesta GET alla
	 * root del sito
	 * 
	 * @return home page
	 */
	@RequestMapping("/")
	public String homePage() {
		return "index";
	}

	/**
	 * Questo metodo viene invocato quando il client effettua una richiesta GET a
	 * /profile, subito dopo il login
	 * 
	 * @return pagina html profilo
	 */
	@RequestMapping("/profile")
	public String viewHomePage(Model model) {
		userService.addListOrders(model);
		return "profile";
	}
	
	/**
	 * Questo metodo viene invocato quando il client effettua una richiesta GET a
	 * /viewCustomerOrders. Recupera e visualizza gli ordini del Customer.
	 * 
	 * @param model è un contenitore di attributi che viene inoltrato al client per
	 *              essere visualizzato o manipolato
	 * @return la pagina html della vista degli ordini
	 */
	@RequestMapping("/Orders")
	public String viewCustomerOrders(Model model) {
		userService.addListOrders(model);
		return "viewOrders";
	}

	/**
	 * Questo metodo viene invocato quando il client effettua una richiesta GET a
	 * /register. Viene istanziato un nuovo customer, viene aggiunto al modello che
	 * a sua volta viene inoltrato al client per essere manipolato.
	 * 
	 * @param model è un contenitore di attributi che viene inoltrato al client per
	 *              essere visualizzato o manipolato
	 * @return la pagina html di registrazione
	 */
	@RequestMapping("/register")
	public String register(Model model) {
		User customer = new User();
		model.addAttribute("user", customer);
		return "registration";
	}

	/**
	 * Questo metodo viene invocato quando il client effettua una richiesta POST a
	 * /register. Il client inoltra al server:
	 * 
	 * @param customer      popolato dai sui attributi attraverso input html
	 * @param bindingResult per la verifica di errori di registrazione
	 * @param model         si riferisce all'istanza del Customer, utile nel caso in
	 *                      cui ci fossero degli errori nella registrazione per
	 *                      inoltrare la stessa istanza (ed evitare di crearne
	 *                      un'altra) alla pagina di registrazione
	 * @return In caso di errori ritorna la pagina di registrazione, altrimenti
	 * 		   se l'utente loggato è un admin ritorna al profilo, sennò
	 *         ritorna un reindirizzamento alla pagina root
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
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

		if (userService.getAuthenticatedUser().getRole().getName().equals("ADMINISTRATOR")){
			return "redirect:" + "profile";
		}
		return "redirect:" + "/"; // Ritorna alla schermata di login
	}
	
	
	// Opzionale, ritiro senza account
	@RequestMapping(value = "/pickup", method = RequestMethod.POST)
	public String showEditProductForm(@RequestParam(name = "pickupCode") String pickupCode, Model model) throws ErrorPickupCode {
		try {
			hubService.withdraw(pickupCode);
		} catch (ErrorPickupCode e) {
			System.out.println(e.getMessage());
			model.addAttribute("error", e.getMessage());
			return "/index";
		}
		model.addAttribute("error", "Ritirato con successo");
		return "/";
	}
}

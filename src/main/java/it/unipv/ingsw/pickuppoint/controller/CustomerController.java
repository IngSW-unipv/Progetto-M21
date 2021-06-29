package it.unipv.ingsw.pickuppoint.controller;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.unipv.ingsw.pickuppoint.service.HubService;
import it.unipv.ingsw.pickuppoint.service.UserService;
import it.unipv.ingsw.pickuppoint.service.exception.ErrorPickupCode;
import it.unipv.ingsw.pickuppoint.service.exception.ErrorTrackingCode;

@Controller
public class CustomerController {
	@Autowired
	UserService userService;
	@Autowired
	HubService hubService;

	/**
	 * Invocato quando il client effettua una richiesta GET a /add. Aggiunge un
	 * prodotto nella lista dei prodotti del Customer tramite tracking code;
	 * 
	 * @param tracking code dell'ordine
	 * @return reindirizzamento alla pagina html della vista degli ordini
	 */
	@GetMapping(value = "/add")
	public String addOrder(@RequestParam(name = "tracking") @NotBlank String tracking, Model model) {

		try {
			hubService.addOrderToProfile(tracking);
		} catch (ErrorTrackingCode e) {
			userService.addListOrders(model);
			model.addAttribute("error", e.getMessage());
			return "/profile";
		}
		return "redirect:" + "/Orders";
	}

	/**
	 * Invocato quando il client effettua una richiesta GET a /withdraw/{id};
	 * Permette al Customer di ritirare l'ordine;
	 * 
	 * @param id ordine da ritirare
	 * @return reindirizzamento alla pagina per il recupero e la visualizzazione
	 *         degli ordini del Customer attraverso una richesta GET da parte del
	 *         client
	 * 
	 */
	@GetMapping(value = "/withdraw")
	public String showEditProductForm(@RequestParam(name = "pickupCode") @NotBlank String pickupCode, Model model) {

		try {
			hubService.withdraw(pickupCode);
		} catch (ErrorPickupCode e) {
			userService.addListOrders(model);
			model.addAttribute("error", e.getMessage());
			return "/viewOrders";
		}
		return "redirect:" + "/Orders";
	}
}

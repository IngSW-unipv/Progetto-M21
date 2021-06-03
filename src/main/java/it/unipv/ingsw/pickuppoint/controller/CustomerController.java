package it.unipv.ingsw.pickuppoint.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.unipv.ingsw.pickuppoint.model.User;
import it.unipv.ingsw.pickuppoint.service.HubService;
import it.unipv.ingsw.pickuppoint.service.OrderDetailsService;
import it.unipv.ingsw.pickuppoint.service.UserService;

@Controller
public class CustomerController {
	@Autowired
	UserService userService;
	@Autowired
	OrderDetailsService orderDetailsService;
	@Autowired
	HubService hubService;


	/**
	 * Questo metodo viene invocato quando il client effettua una richiesta GET a
	 * /viewCustomerOrders. Recupera e visualizza gli ordini del Customer.
	 * 
	 * @param model è un contenitore di attributi che viene inoltrato al client per
	 *              essere visualizzato o manipolato
	 * @return la pagina html della vista degli ordini
	 */
	@RequestMapping("/viewCustomerOrders")
	public String viewCustomerOrders(Model model) {
		User user = userService.getAuthenticatedUser();

		// dovrebbe essere user.getOrder
		model.addAttribute("listOrders", orderDetailsService.getCustomerOrders(user.getUserId()));
		return "viewOrders";
	}
	

	/**
	 * Questo metodo viene invocato quando il client effettua una richiesta GET a
	 * /add. Aggiunge un prodotto nella lista dei prodotti del Customer tramite
	 * inserimento del tracking code; Viene aggiunto il riferimento di chiave
	 * esterna (CustomerID) all'interno dell'entità genitore OrderDetails
	 * 
	 * @param tracking code dell'ordine
	 * @return reindirizzamento alla pagina html della vista degli ordini
	 */
	@Transactional
	@RequestMapping(value = "/add")
	public String addOrder(@RequestParam(name = "tracking") int tracking) {
		hubService.addOrder(tracking);
		return "redirect:" + "/viewCustomerOrders";
	}
	

	/**
	 * Questo metodo viene invocato quando il client effettua una richiesta GET a
	 * /withdraw/{id}; Permette al Customer di ritirare l'ordine; Viene settato
	 * DeliveryStatus = WITHDRAW e WithdrawalDate
	 * 
	 * @param id ordine da ritirare
	 * @return reindirizzamento alla pagina per il recupero e la visualizzazione
	 *         degli ordini del Customer attraverso una richesta GET da parte del
	 *         client
	 */
	@RequestMapping("/withdraw/{id}")
	public String showEditProductForm(@PathVariable(name = "id") Long id) {
		hubService.withdraw(id);
		return "redirect:" + "/viewCustomerOrders";
	}
}

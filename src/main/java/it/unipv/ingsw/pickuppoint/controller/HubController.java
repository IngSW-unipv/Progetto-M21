package it.unipv.ingsw.pickuppoint.controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.unipv.ingsw.pickuppoint.model.DeliveryStatus;
import it.unipv.ingsw.pickuppoint.model.User;
import it.unipv.ingsw.pickuppoint.model.entity.OrderDetails;
import it.unipv.ingsw.pickuppoint.service.OrderDetailsService;
import it.unipv.ingsw.pickuppoint.service.UserAuthorization;

@Controller
public class HubController {

	@Autowired
	EntityManager entityManager;
	@Autowired
	OrderDetailsService orderDetailsService;

	/**
	 * Questo metodo viene invocato quando il client effettua una richiesta GET alla
	 * root del sito
	 * 
	 * @return home page
	 */
	@RequestMapping("/")
	public String homePage() {
		return "welcome";
	}

	/**
	 * Questo metodo viene invocato quando il client effettua una richiesta GET a
	 * /login
	 * 
	 * @return pagina di login
	 */
	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	/**
	 * Questo metodo viene invocato quando il client effettua una richiesta GET a
	 * /profile, subito dopo il login
	 * 
	 * @return pagina html profilo
	 */
	@RequestMapping("/profile")
	public String viewHomePage() {
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
	@RequestMapping("/viewCustomerOrders")
	public String viewCustomerOrders(Model model) {

		/**
		 * Grazie a spring security è possibile recuperare l'autentificazione
		 * dell'utente loggato e di conseguenza tutte le sue informazioni
		 */
		User user = ((UserAuthorization) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
				.getUser();

		List<OrderDetails> ordersDetails = orderDetailsService.getCustomerOrders(user.getUserId());
		model.addAttribute("listOrders", ordersDetails);
		return "viewOrders";
	}

	/**
	 * Questo metodo viene invocato quando il client effettua una richiesta GET a
	 * /viewCourierOrders. Recupera e visualizza gli ordini del Courier.
	 * 
	 * @param model è un contenitore di attributi che viene inoltrato al client per
	 *              essere visualizzato o manipolato
	 * @return la pagina html della vista degli ordini
	 */
	@RequestMapping("/viewCourierOrders")
	public String viewCourierOrder(Model model) {
		User user = ((UserAuthorization) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
				.getUser();
		/**
		 * 
		 * 
		 * model.addAttribute("orders",ordersDetails);
		 * 
		 * 
		 * 
		 */
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
		OrderDetails order = orderDetailsService.findByTrackingCode(tracking);

		User user = ((UserAuthorization) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
				.getUser();

		Long orderCustomerId = null;

		try {
			orderCustomerId = order.getCustomer().getUserId();
		} catch (NullPointerException e) {

		}
		if (orderCustomerId != null) {
			System.out.println("ORDINE GIà AGGIUNTO");

		} else {
			/**
			 * Attraverso getReference recuperiamo l'entità di interesse tramite chiave
			 * primaria: utile quando si ha la neccessità di aggiunger/modificare una chiave
			 * esterna
			 */
			User customer = entityManager.getReference(User.class, user.getUserId());

			order.setCustomer(customer);
			entityManager.persist(order);

		}
		return "redirect:" + "/viewCustomerOrders";
	}

	/**
	 * Questo metodo viene invocato quando il client effettua una richiesta GET a
	 * /withdraw/{id}; Permette al Customer di ritirare l'ordine; Viene settato
	 * DeliveryStatus = WITHDRAW
	 * 
	 * @param id ordine da ritirare
	 * @return reindirizzamento alla pagina per il recupero e la visualizzazione
	 *         degli ordini del Customer attraverso una richesta GET da parte del
	 *         client
	 */
	@RequestMapping("/withdraw/{id}")
	public String showEditProductForm(@PathVariable(name = "id") Long id) {
		OrderDetails orderDetails = orderDetailsService.getOrderDetailsById(id);
		orderDetails.getDeliveryDetails().setDeliveryStatus(DeliveryStatus.WITHDRAWN);
		orderDetailsService.save(orderDetails);

		return "redirect:" + "/viewCustomerOrders";
	}
}

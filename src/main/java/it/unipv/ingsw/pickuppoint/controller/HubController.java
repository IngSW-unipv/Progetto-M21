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
import it.unipv.ingsw.pickuppoint.model.entity.Customer;
import it.unipv.ingsw.pickuppoint.model.entity.OrderDetails;
import it.unipv.ingsw.pickuppoint.service.HubService;
import it.unipv.ingsw.pickuppoint.service.OrderDetailsService;
import it.unipv.ingsw.pickuppoint.service.UserAuthorization;

@Controller
public class HubController {

	@Autowired
	EntityManager entityManager;

	@Autowired
	OrderDetailsService orderDetailsService;

	@RequestMapping("/")
	public String viewHomePage() {
		return "index";
	}

	/**
	 * Recupero e visualizzazione ordini Customer
	 * 
	 * Grazie a spring security è possibile recuperare l'autentificazione
	 * dell'utente loggato e di conseguenza tutte le sue informazioni
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/viewCustomerOrders")
	public String viewCustomerOrders(Model model) {
		User user = ((UserAuthorization) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
				.getUser();

		List<OrderDetails> ordersDetails = orderDetailsService.getCustomerOrders(user.getUserId());
		model.addAttribute("listOrders", ordersDetails);

		return "viewOrders";
	}

	/**
	 * Recupero e visualizzazione ordini Courier
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/viewCourierOrders")
	public String viewCourierOrder(Model model) {
		User user = ((UserAuthorization) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
				.getUser();

		List<OrderDetails> ordersDetails = orderDetailsService.getCourierOrders(user.getUserId());

		for (OrderDetails orderDetails : ordersDetails) {
			System.out.println(orderDetails.getProducts());
		}
//		model.addAttribute("orders",ordersDetails);
//		....	
//		....
		return "viewOrders";
	}

	/**
	 * Questo metodo aggiunge un prodotto nella lista dei prodetti del Customer
	 * tramite tracking code; Viene aggiunto il riferimento di chiave esterna
	 * (CustomerID) all'interno dell'entità ordine
	 * 
	 * @param tracking
	 * @return reindirizzamento alla pagina per il recupero e la visualizzazione
	 *         degli ordini del Customer attraverso una richesta GET da parte del
	 *         client
	 */
	@Transactional
	@RequestMapping(value = "/add")
	public String addOrder(@RequestParam(name = "tracking") int tracking) {
		OrderDetails order = orderDetailsService.findByTrackingCode(tracking);
		User user = ((UserAuthorization) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
				.getUser();
		Customer customer = entityManager.getReference(Customer.class, user.getUserId());

		order.setCustomer(customer);
		entityManager.persist(order);

		return "redirect:" + "/viewCustomerOrders";
	}

	/**
	 * Questo modo permette al Customer di ritirare l'ordine; Viene settato
	 * DeliveryStatus = WITHDRAW
	 * 
	 * @param id
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

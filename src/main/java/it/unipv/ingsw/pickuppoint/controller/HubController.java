package it.unipv.ingsw.pickuppoint.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import it.unipv.ingsw.pickuppoint.data.OrderDetailsRepo;
import it.unipv.ingsw.pickuppoint.model.User;
import it.unipv.ingsw.pickuppoint.model.entity.Courier;
import it.unipv.ingsw.pickuppoint.model.entity.Customer;
import it.unipv.ingsw.pickuppoint.model.entity.OrderDetails;
import it.unipv.ingsw.pickuppoint.service.HubService;
import it.unipv.ingsw.pickuppoint.service.UserAuthorization;

@Controller
public class HubController {

	@Autowired
	HubService hubService;
	@Autowired
	OrderDetailsRepo orderDetailsRepo;

	@RequestMapping("/")
	public String viewHomePage() {
		return "index";
	}

	/*
	 * Visualizzazione ordini per Customer e Courier
	 */
	@RequestMapping("/viewOrders")
	public String viewCustomerOrders(Model model) {
		/**
		 * Grazie a spring security è possibile recuperare l'autentificazione
		 * dell'utente loggato e di conseguenza tutte le sue informazioni
		 */
		User user = ((UserAuthorization) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
				.getUser();
//		System.out.println("####"+((UserAuthorization) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAuthorities());
		Long userId = user.getUserId();

		List<OrderDetails> ordersDetails = null;

		if (user instanceof Customer) {
			ordersDetails = orderDetailsRepo.getCustomerOrders(userId);
			for (OrderDetails orderDetails : ordersDetails) {
				System.out.println(orderDetails.getProducts());
			}
		}
		if (user instanceof Courier) {
			ordersDetails = orderDetailsRepo.getCourierOrders(userId);
			for (OrderDetails orderDetails : ordersDetails) {
				System.out.println(orderDetails.getProducts());

			}
		}
//		model.addAttribute("orders",ordersDetails);
//		....		
		return "viewOrders";
	}

//	public Role getCustomerRole(){
//		Role role = roleRepo.findByName("CUSTOMER");
//		return role;
//	}
//
//	public Role getAdministratorRole(){
//		Role role = roleRepo.findByName("ADMINISTRATOR");
//		return role;
//	}
//	public Role getCourierRole(){
//		Role role = roleRepo.findByName("COURIER");
//		return role;
//	}
}

package it.unipv.ingsw.pickuppoint.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.unipv.ingsw.pickuppoint.service.HubService;
import it.unipv.ingsw.pickuppoint.service.UserService;
import it.unipv.ingsw.pickuppoint.service.exception.SlotNotAvailableException;

@Controller
public class CourierController {

	@Autowired
	private UserService userService;
	@Autowired
	private HubService hubService;

	/**
	 * Invocato dal courier quando effettua una richiesta GET a /deliver/id
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping("/deliver/{id}")
	public String deliverOrder(@PathVariable(name = "id") Long id, Model model) {
		try {
			hubService.deliver(id);
		} catch (SlotNotAvailableException e) {
			userService.addListOrders(model);
			model.addAttribute("slotError", e.getMessage());
			return "/viewOrders";
		}
		return "redirect:" + "/Orders";
	}

	/**
	 * Invocato dal courier quando effettua una richiesta GET a /sendBackToHub/id
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/sendBackToHub/{id}")
	public String sendBacktoHub(@PathVariable(name = "id") Long id) {
		hubService.sendBackToHub(id);
		return "redirect:" + "/Orders";
	}
}

package it.unipv.ingsw.pickuppoint.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import it.unipv.ingsw.pickuppoint.service.HubService;
import it.unipv.ingsw.pickuppoint.service.UserService;
import it.unipv.ingsw.pickuppoint.service.exception.SlotNotAvailable;

@Controller
public class CourierController {

	@Autowired
	UserService userService;
	@Autowired
	HubService hubService;

//	/**
//	 * Questo metodo viene invocato quando il client effettua una richiesta GET a
//	 * /Orders. Recupera e visualizza gli ordini del Courier.
//	 * 
//	 * @param model è un contenitore di attributi che viene inoltrato al client per
//	 *              essere visualizzato o manipolato
//	 * @return la pagina html della vista degli ordini
//	 */

	@RequestMapping("/deliver/{id}")
	public String showEditProductFormCourier(@PathVariable(name = "id") Long id, Model model) {
		try {
			hubService.deliver(id);
		} catch (SlotNotAvailable e) {
			userService.addListOrders(model);
			model.addAttribute("slotError", e.getMessage());
			return "/viewOrders";
		}

		return "redirect:" + "/Orders";
	}
	
	@RequestMapping("/sendBackToHub/{id}")
	public String sendBacktoHub(@PathVariable(name = "id") Long id) {

		hubService.sendBackToHub(id);

		return "redirect:" + "/Orders";
	}
	
	
}

package it.unipv.ingsw.pickuppoint.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import it.unipv.ingsw.pickuppoint.service.HubService;
import it.unipv.ingsw.pickuppoint.service.OrderDetailsService;
import it.unipv.ingsw.pickuppoint.service.UserService;

@Controller
public class CourierController {

	@Autowired
	UserService userService;
	@Autowired
	OrderDetailsService orderDetailsService;
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
	public String showEditProductFormCourier(@PathVariable(name = "id") Long id) {
		hubService.deliver(id);
		return "redirect:" + "/Orders";
	}
}

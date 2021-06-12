package it.unipv.ingsw.pickuppoint.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import it.unipv.ingsw.pickuppoint.service.UserService;

@Controller
public class AdministratorController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping("/CourierList")
	public String viewHomePage(Model model) {
		userService.findAllCouriers(model);
		return "courierList";
	}
	
	@RequestMapping("/editCourier/{id}")
	public String showEditCourierForm(@PathVariable(name = "id") Long id, Model model) {
		userService.editUser(model, id);
		return "redirect:" + "/editUser";
	}

}

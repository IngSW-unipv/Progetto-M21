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
	
	@RequestMapping("/UserList")
	public String viewHomePage(Model model) {
		userService.findAllUsers(model);
		return "userList";
	}
	
	@RequestMapping("/editUser/{id}")
	public String showEditUserForm(@PathVariable(name = "id") Long id, Model model) {
		userService.editUser(model, id);
		return "redirect:" + "/editUser";
	}

//	@RequestMapping("/addUser")
//	public String addUser(Model model) {
//		//userService.editUser(model);
//		return "redirect:" + "/editUser";
//	}
	
}

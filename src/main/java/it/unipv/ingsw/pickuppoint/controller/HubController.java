package it.unipv.ingsw.pickuppoint.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import it.unipv.ingsw.pickuppoint.model.entity.Role;
import it.unipv.ingsw.pickuppoint.service.HubService;

@Controller
public class HubController {
	
	@Autowired
	HubService hubService;
	
	
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

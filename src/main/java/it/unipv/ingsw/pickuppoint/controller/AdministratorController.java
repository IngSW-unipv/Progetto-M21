package it.unipv.ingsw.pickuppoint.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import it.unipv.ingsw.pickuppoint.service.AdministratorService;

@Controller
public class AdministratorController {

	@Autowired
	AdministratorService administratorService;
}

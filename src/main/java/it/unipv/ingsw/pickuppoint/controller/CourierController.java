package it.unipv.ingsw.pickuppoint.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import it.unipv.ingsw.pickuppoint.service.CourierService;

@Controller
public class CourierController {

	@Autowired
	CourierService courierService;

}

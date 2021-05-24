package it.unipv.ingsw.pickuppoint.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.unipv.ingsw.pickuppoint.model.entity.Customer;
import it.unipv.ingsw.pickuppoint.service.CustomerService;
import it.unipv.ingsw.pickuppoint.service.exception.CustomerAlreadyExistException;

@Controller
public class CustomerController {
	@Autowired
	private CustomerService customerService;

	
	@RequestMapping("/register")
	public String register(Model model) {
		Customer customer = new Customer();
		model.addAttribute("customer", customer);
		return "registration";
	}

	@ExceptionHandler(CustomerAlreadyExistException.class)
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String userRegistration(@Valid Customer customer, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("customer", customer);
			return "registration";
		}
		try {
			customerService.register(customer);
		} catch (CustomerAlreadyExistException e) {
			bindingResult.rejectValue("email", "customer.email", "An account already exists for this email");
			model.addAttribute("customer", customer);
			return "registration";
		}

		return "redirect:" + "/"; // Ritorna alla schermata di login

	}

	@RequestMapping("/")
	public String viewHomePage() {
//		Test
//		encoder = new BCryptPasswordEncoder();
//		User a = new Administrator();
//		a.setRole(roleRepo.findByName("ADMINIST));
//		a.setUsername("ad2");RATOR"
//		a.setPassword(encoder.encode("ad2"));
//		userRepo.save(a);
		return "index";
	}

}

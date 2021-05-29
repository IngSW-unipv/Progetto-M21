package it.unipv.ingsw.pickuppoint.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.unipv.ingsw.pickuppoint.data.UserRepo;

@Service
public class HubService {


	@Autowired
	UserRepo userRepo;

	@Autowired
	LockerService LockerService;

	@Autowired
	OrderDetailsService orderDetailsService;

}

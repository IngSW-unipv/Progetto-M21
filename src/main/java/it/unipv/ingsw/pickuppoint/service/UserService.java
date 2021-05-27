package it.unipv.ingsw.pickuppoint.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import it.unipv.ingsw.pickuppoint.data.UserRepo;
import it.unipv.ingsw.pickuppoint.model.User;

public class UserService {
	@Autowired UserRepo userRepo;
	
	public List<User> getUserByRole(String name) {
		return userRepo.findByRole_name(name);
	}
}

package it.unipv.ingsw.pickuppoint.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.unipv.ingsw.pickuppoint.data.UserRepo;

@Service
public class CourierService {

	@Autowired
	UserRepo courierRepo;

	/**
	 * @return lista di tutti i corrieri
	 */
//	public List<Courier> getAllCouriers() {
//		return courierRepo.findAll();
//	}

}

package it.unipv.ingsw.pickuppoint.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.unipv.ingsw.pickuppoint.data.CourierRepo;
import it.unipv.ingsw.pickuppoint.model.entity.Courier;

@Service
public class CourierService {

	@Autowired
	CourierRepo courierRepo;

	/**
	 * @return lista di tutti i corrieri
	 */
	public List<Courier> getAllCouriers() {
		return courierRepo.findAll();
	}

}

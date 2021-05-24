package it.unipv.ingsw.pickuppoint.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.unipv.ingsw.pickuppoint.data.LockerAddressRepo;
import it.unipv.ingsw.pickuppoint.data.LockerRepo;
import it.unipv.ingsw.pickuppoint.data.SlotRepo;

@Service
public class LockerService {
	@Autowired
	LockerRepo lockerRepo;

	@Autowired
	SlotRepo slotRepo;
	
	@Autowired
	LockerAddressRepo addressRepo;

}

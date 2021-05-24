package it.unipv.ingsw.pickuppoint.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.unipv.ingsw.pickuppoint.data.DeliveryDetailsRepo;
import it.unipv.ingsw.pickuppoint.data.OrderDetailsRepo;
import it.unipv.ingsw.pickuppoint.data.ProductRepo;
import it.unipv.ingsw.pickuppoint.data.RecipientRepo;

@Service
public class OrderDetailsService {

	@Autowired
	OrderDetailsRepo orderDetailsRepo;

	@Autowired
	ProductRepo productRepo;

	@Autowired
	RecipientRepo recipientRepo;

	@Autowired
	DeliveryDetailsRepo deliveryDetailsRepo;

}

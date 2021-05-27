package it.unipv.ingsw.pickuppoint.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.unipv.ingsw.pickuppoint.data.DeliveryDetailsRepo;
import it.unipv.ingsw.pickuppoint.data.OrderDetailsRepo;
import it.unipv.ingsw.pickuppoint.data.ProductRepo;
import it.unipv.ingsw.pickuppoint.data.RecipientRepo;
import it.unipv.ingsw.pickuppoint.model.entity.OrderDetails;

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

	/**
	 * Ritorna OrderDetails con chiave id se presente, altrimenti
	 * NoSuchElementException
	 * 
	 * @param id
	 * @return OrderDetails by id
	 */
	public OrderDetails getOrderDetailsById(Long id) {
		return orderDetailsRepo.findById(id).orElseThrow();
	}

	public OrderDetails findByTrackingCode(int trackingCode) {
		return orderDetailsRepo.findByTrackingCode(trackingCode);
	}

	public void save(OrderDetails orderDetails) {
		orderDetailsRepo.save(orderDetails);

	}

	public List<OrderDetails> getCourierOrders(Long userId) {
		return orderDetailsRepo.getCourierOrders(userId);
	}

	public List<OrderDetails> getCustomerOrders(Long userId) {
		return orderDetailsRepo.getCustomerOrders(userId);
	}
	
	public List<OrderDetails> getAllOrderDetails(){
		return orderDetailsRepo.findAll();
	}
}

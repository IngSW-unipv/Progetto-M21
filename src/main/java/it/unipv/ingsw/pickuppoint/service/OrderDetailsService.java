package it.unipv.ingsw.pickuppoint.service;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.unipv.ingsw.pickuppoint.data.DeliveryDetailsRepo;
import it.unipv.ingsw.pickuppoint.data.OrderDetailsRepo;
import it.unipv.ingsw.pickuppoint.model.OrderDetails;
import it.unipv.ingsw.pickuppoint.utility.DeliveryStatus;

@Service
public class OrderDetailsService {

	@Autowired
	OrderDetailsRepo orderDetailsRepo;
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

	public OrderDetails findByTrackingCode(String trackingCode) {
		return orderDetailsRepo.findByTrackingCode(trackingCode);
	}
	
	@Transactional
	public void save(OrderDetails orderDetails) {
		orderDetailsRepo.save(orderDetails);
	}

	public List<OrderDetails> getCustomerOrders(Long customerId) {
		return orderDetailsRepo.findByCustomer_userId(customerId);
	}

	public List<OrderDetails> getCourierOrders(Long courierId) {
		return orderDetailsRepo.findByCourier_userId(courierId);
	}
	

	public List<OrderDetails> getAllOrderDetails() {
		return orderDetailsRepo.findAll();
	}
	
	public List <OrderDetails> getAllHubOrders(){
		return orderDetailsRepo.findByDeliveryDetails_deliveryStatus(DeliveryStatus.HUB);
	}
	
	public OrderDetails getOrderByPickupCode(String pickupCode) {
		return orderDetailsRepo.findByPickupCode(pickupCode);
	}
	
	@Transactional
	public Map<Long, Integer> getfindListOfDifferenceDeliverdDateAndCurrentDate(EntityManager em){
		return deliveryDetailsRepo.findListOfDifferenceDeliverdDateAndCurrentDate(em);
	}
	
}

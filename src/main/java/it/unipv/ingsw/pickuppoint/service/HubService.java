package it.unipv.ingsw.pickuppoint.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.unipv.ingsw.pickuppoint.data.SlotRepo;
import it.unipv.ingsw.pickuppoint.model.OrderDetails;
import it.unipv.ingsw.pickuppoint.model.Product;
import it.unipv.ingsw.pickuppoint.model.User;
import it.unipv.ingsw.pickuppoint.service.exception.ErrorPickupCode;
import it.unipv.ingsw.pickuppoint.service.exception.ErrorTrackingCode;
import it.unipv.ingsw.pickuppoint.service.exception.SlotNotAvailable;
import it.unipv.ingsw.pickuppoint.utility.DeliveryStatus;

@Service
public class HubService {

	@Autowired
	OrderDetailsService orderDetailsService;
	@Autowired
	EntityManager entityManager;
	@Autowired
	UserService userService;
	@Autowired
	LockerService lockerService;
	@Autowired
	Date date;
	@Autowired
	SlotRepo slotRepo;
	
	
	public void deliver(Long id) throws SlotNotAvailable{
		OrderDetails orderDetails = orderDetailsService.getOrderDetailsById(id);
		orderDetails.getDeliveryDetails().setDeliveryStatus(DeliveryStatus.DELIVERED);
		orderDetails.getDeliveryDetails().setDataDeliverd(date.getCurrentDataTime());
		lockerService.setSlotDeliver(orderDetails);
		orderDetailsService.save(orderDetails);
	}
	
	@Transactional
	public void withdraw(String pickupCode) throws ErrorPickupCode {
		OrderDetails orderDetails = null;
		orderDetails = orderDetailsService.getOrderByPickupCode(pickupCode);
		
		if (orderDetails == null) 
			throw new ErrorPickupCode("Wrong pickup code, please try again");

		orderDetails.getDeliveryDetails().setDeliveryStatus(DeliveryStatus.WITHDRAWN);
		orderDetails.getDeliveryDetails().setWithdrawalDate(date.getCurrentDataTime());
//		orderDetails.removeProducts();
		orderDetailsService.save(orderDetails);
		removeProducts(orderDetails.getProducts());
		System.out.println("########"+orderDetails.getProducts());
	}
	

	private void removeProducts(List<Product> list) {
		for(Product product: list) {
			System.out.println("@@@@@@@@@@@@@"+ product);
			slotRepo.deleteByProduct(product);
		}
	}

	public void addOrder(String tracking) throws ErrorTrackingCode {
		OrderDetails orderDetails = null;
		orderDetails = orderDetailsService.findByTrackingCode(tracking);
		
		if (orderDetails == null) 
			throw new ErrorTrackingCode("Wrong tracking code, please try again");
		
		User customer = userService.getAuthenticatedUser();
		orderDetails.setCustomer(customer);
		orderDetailsService.assignOrder(orderDetails);
	}
}

package it.unipv.ingsw.pickuppoint.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import it.unipv.ingsw.pickuppoint.model.DeliveryStatus;
import it.unipv.ingsw.pickuppoint.model.User;
import it.unipv.ingsw.pickuppoint.model.entity.OrderDetails;

@Service
public class HubService {

	@Autowired
	OrderDetailsService orderDetailsService;
	@Autowired
	EntityManager entityManager;
	
	public String getCurrentDataTime() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}

	public void deliver(Long id) {
		OrderDetails orderDetails = orderDetailsService.getOrderDetailsById(id);
		orderDetails.getDeliveryDetails().setDeliveryStatus(DeliveryStatus.DELIVERED);
		orderDetails.getDeliveryDetails().setDataDeliverd(getCurrentDataTime());
		orderDetailsService.save(orderDetails);

	}

	public void withdraw(Long id) {
		OrderDetails orderDetails = orderDetailsService.getOrderDetailsById(id);
		orderDetails.getDeliveryDetails().setDeliveryStatus(DeliveryStatus.WITHDRAWN);
		orderDetails.getDeliveryDetails().setWithdrawalDate(getCurrentDataTime());
		orderDetailsService.save(orderDetails);
	}

	public void addOrder(int tracking) {
		OrderDetails order = orderDetailsService.findByTrackingCode(tracking);

		User user = ((UserAuthorization) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
				.getUser();

		Long orderCustomerId = null;

		try {
			orderCustomerId = order.getCustomer().getUserId();
		} catch (NullPointerException e) {

		}
		if (orderCustomerId != null) {
			System.out.println("ORDINE GIà AGGIUNTO");

		} else {
			/**
			 * Attraverso getReference recuperiamo l'entità di interesse tramite chiave
			 * primaria: utile quando si ha la neccessità di aggiunger/modificare una chiave
			 * esterna
			 */
			User customer = entityManager.getReference(User.class, user.getUserId());

			order.setCustomer(customer);
			entityManager.persist(order);
		}
	}
}

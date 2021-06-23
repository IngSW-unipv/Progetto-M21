package it.unipv.ingsw.pickuppoint;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import it.unipv.ingsw.pickuppoint.model.DeliveryDetails;
import it.unipv.ingsw.pickuppoint.model.OrderDetails;
import it.unipv.ingsw.pickuppoint.model.User;
import it.unipv.ingsw.pickuppoint.service.HubService;
import it.unipv.ingsw.pickuppoint.service.OrderDetailsService;
import it.unipv.ingsw.pickuppoint.service.UserService;
import it.unipv.ingsw.pickuppoint.utility.DeliveryStatus;

@Component
public class ScheduledTasks {

	private static final int MAX = 3;

	// Coda di ordini da assegnare
	Deque<OrderDetails> ordersDetailsToAsign = new ArrayDeque<>();
	@Autowired
	UserService userService;
	@Autowired
	OrderDetailsService orderDetailsService;
	@Autowired
	EntityManager entityManager;
	@Autowired
	HubService hubService;

	private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTasks.class);
	

	/**
	 * Il task viene avviato ogni x minuti Gli ordini in attesa di assegnamento
	 * vengono inseriti dentro una Deque
	 */

	@Scheduled(cron = "0 */1 * ? * *")
	private void checkPendingOrders() {
		LOGGER.debug("INIZIO ASSEGNAMENTO ORDINI AI CORRIERI");

		// Tutti gli ordini con status HUB
		List<OrderDetails> ordersDetails = orderDetailsService.getAllHubOrders();
		ordersDetailsToAsign.addAll(ordersDetails);
		checkFreeCourier();
	}

	/**
	 * Controlla periodicamente i corrieri disponibili per l'assegnamento degli ordini
	 */
	public void checkFreeCourier() {
		List<User> couriers = userService.getAllCouriers();

		// Per ogni courier, tutti gli ordini assegnatti
		for (User courier : couriers) {
			List<OrderDetails> ordersDetails = orderDetailsService.getCourierOrders(courier.getUserId());
			int delivering = 0;

			for (OrderDetails orderDetails : ordersDetails) {
				DeliveryDetails deliveryDetails = orderDetails.getDeliveryDetails();
				if (deliveryDetails.getDeliveryStatus() == DeliveryStatus.DELIVERING) {
					delivering++;
				}
			}

			if (delivering < MAX && ordersDetailsToAsign.size() != 0) {
				dynamicAssignment(courier.getUserId(), delivering);
			}
			else if (delivering == MAX) {
				LOGGER.debug("NIENTE DA ASSEGNARE: raggiunto MAX ordini per corriere " + "[ " + MAX + " ]");
			} else if (ordersDetailsToAsign.size() == 0) {
				LOGGER.debug("NIENTE DA ASSEGNARE: nessun ordine in coda");
			}
		}
	}

	/**
	 * Assegnamento dinamico degli ordini
	 * 
	 * @param courier:    corriere a cui assegnare l'ordine
	 * @param delivering: ordini in stato di consegna del corriere
	 */
	private void dynamicAssignment(Long courierId, int delivering) {
		int toAssign = Math.min((MAX - delivering), ordersDetailsToAsign.size());
		User courier = entityManager.getReference(User.class, courierId);

		for (int i = 0; i < toAssign; i++) {
			OrderDetails orderDetails = ordersDetailsToAsign.poll();
			orderDetails.setCourier(courier);
			orderDetails.getDeliveryDetails().setDeliveryStatus(DeliveryStatus.DELIVERING);
			orderDetailsService.assignOrder(orderDetails);

			LOGGER.info("Ordine " + orderDetails.getOrderDetailsId() + " assegnato al COURIER: \t" + courier.getEmail());
		}
	}

	@Scheduled(cron = "0 */1 * ? * *")
	private void checkPendingDelivers() {
		LOGGER.debug("VERIFICA GIORNI MANCANTI");
		HashMap<Long, Integer> checkPendingDeliversMap = (HashMap<Long, Integer>) orderDetailsService
				.getfindListOfDifferenceDeliverdDateAndCurrentDate(entityManager);
		
		for (HashMap.Entry<Long, Integer> entry : checkPendingDeliversMap.entrySet()) {
			if ((entry.getValue() > 2)&&(orderDetailsService.getOrderDetailsById(entry.getKey()).getDeliveryDetails().getDeliveryStatus() != DeliveryStatus.NOT_WITHDRAWN)) {
				
				hubService.setNotWithdrawnState(entry.getKey());
				LOGGER.debug("SET WITHDRAWN STATE TO ORDER: " + entry.getKey());

			}

		}

	}

}

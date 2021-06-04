package it.unipv.ingsw.pickuppoint;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import it.unipv.ingsw.pickuppoint.model.DeliveryStatus;
import it.unipv.ingsw.pickuppoint.model.User;
import it.unipv.ingsw.pickuppoint.model.entity.DeliveryDetails;
import it.unipv.ingsw.pickuppoint.model.entity.OrderDetails;
import it.unipv.ingsw.pickuppoint.service.OrderDetailsService;
import it.unipv.ingsw.pickuppoint.service.UserService;

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

	/**
	 * Il task viene avviato ogni 10 minuti Gli ordini in attesa di assegnamento
	 * vengono inseriti dentro una Deque
	 */

	@Scheduled(cron = "0 */3 * ? * *")
	private void checkPendingOrders() {
		System.out.println("INIZIO ASSEGNAMENTO");

		// Tutti gli ordini con status HUB
		List<OrderDetails> ordersDetails = orderDetailsService.getAllHubOrders();

		// Per ogni ordine push ordine in ordersDetailsToAsign
		for (OrderDetails orderDetails : ordersDetails) {
			ordersDetailsToAsign.push(orderDetails);
		}
		checkFreeCourier();
	}

	/**
	 * Controlla i corrieri disponibili per l'assegnamento degli ordini
	 */
	public void checkFreeCourier() {
		// Tutti i couriers
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
			/**
			 * Se la lista è satura o non ci sono ordini da assegnare
			 */
			else if (delivering == MAX || ordersDetailsToAsign.size() == 0) {
				System.out.println("NIENTE DA ASSEGNARE");
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
			OrderDetails orderDetails = ordersDetailsToAsign.pop();
			orderDetails.setCourier(courier);
			orderDetails.getDeliveryDetails().setDeliveryStatus(DeliveryStatus.DELIVERING);
			orderDetailsService.assignOrder(orderDetails);
		}
	}
}

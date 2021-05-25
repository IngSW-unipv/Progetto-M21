//package it.unipv.ingsw.pickuppoint;
//
//import java.util.ArrayDeque;
//import java.util.Deque;
//import java.util.List;
//import java.util.Set;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import it.unipv.ingsw.pickuppoint.data.CourierRepo;
//import it.unipv.ingsw.pickuppoint.data.OrderDetailsRepo;
//import it.unipv.ingsw.pickuppoint.model.DeliveryStatus;
//import it.unipv.ingsw.pickuppoint.model.entity.Courier;
//import it.unipv.ingsw.pickuppoint.model.entity.DeliveryDetails;
//import it.unipv.ingsw.pickuppoint.model.entity.OrderDetails;
//import it.unipv.ingsw.pickuppoint.service.CourierService;
//
//@Component
//public class ScheduledTasks {
//
//	private static final int MAX = 3;
//
//	Deque<OrderDetails> ordersDetailsToAsign;
//	@Autowired CourierService courierService;	
//	@Autowired OrderDetailsRepo orderDetailsRepo;
//
//	/**
//	 * Il task viene avviato ogni 10 minuti
//	 * Gli ordini in attesa di assegnamento vengono inseriti dentro una Deque
//	 */
//	@Scheduled(cron = "0 */10* ? * *")
//	private void checkPendingOrders() {
//		List<OrderDetails> ordersDetails = orderDetailsRepo.findAll();
//		ordersDetailsToAsign = new ArrayDeque<>();
//		for (OrderDetails orderDetails : ordersDetails) {
//			if (orderDetails.getDeliveryDetails().getDeliveryStatus() == DeliveryStatus.HUB) {
//				ordersDetailsToAsign.push(orderDetails);
//			}
//		}
//		checkFreeCourier();
//	}
//
//	/**
//	 * Controlla i corrieri disponibili per l'assegnamento degli ordini
//	 */
//	public void checkFreeCourier() {
//		List<Courier> couriers = courierService.getAllCouriers();
//
//		for (Courier courier : couriers) {
//			Set<OrderDetails> ordersDetails = courier.getOrderDetails();
//
//			/**
//			 * Se la lista di ordersDetails del corriere non è vuota, conto quanti ordini ha
//			 * in consegna il corriere, dopodichè parte un assegnamento dinamico degli
//			 * ordini
//			 */
//			if (ordersDetails.size() != 0) {
//				int delivering = 0;
//				for (OrderDetails orderDetails : ordersDetails) {
//					DeliveryDetails deliveryDetails = orderDetails.getDeliveryDetails();
//					if (deliveryDetails.getDeliveryStatus() == DeliveryStatus.DELIVERING) {
//						delivering++;
//					}
//					dynamicAssignment(courier, delivering);
//				}
//
//				/**
//				 * PASSO 1 Se la lista è vuota parte un assegnamento statico degli ordini, in
//				 * seguito salta iterazione e passa al prossimo corriere
//				 */
//			} else {
//				staticAssignment(courier);
//				continue; // Non strettamente necessario
//			}
//		}
//	}
//
//	/**
//	 * Assegnazione degli ordini di default al corriere (PASSO 1) Ad ogni corriere
//	 * vengono assegna di default il minimo tra MAX e il numero di elementi presenti
//	 * dentro la Deque (è un controllo per evitare l'assegnamento di un ordine che
//	 * non esiste)
//	 * 
//	 * @param courier: corriere a cui assegnare l'ordine
//	 *
//	 */
//	private void staticAssignment(Courier courier) {
//		for (int i = 0; i < Math.min(MAX, ordersDetailsToAsign.size()); i++) {
//			OrderDetails orderDetails = ordersDetailsToAsign.pop();
//			orderDetails.setCourier(courier);
//			orderDetailsRepo.save(orderDetails);
//		}
//	}
//	
//	/**
//	 * Assegnamento dinamico degli ordini
//	 * 
//	 * @param courier: corriere a cui assegnare l'ordine
//	 * @param delivering: ordini in stato di consegna del corriere
//	 */
//	private void dynamicAssignment(Courier courier, int delivering) {
//		int toAssign = MAX - delivering;
//		for (int i = 0; i < Math.min(toAssign, ordersDetailsToAsign.size()); i++) {
//			OrderDetails orderDetails = ordersDetailsToAsign.pop();
//			orderDetails.setCourier(courier);
//			orderDetailsRepo.save(orderDetails);
//		}
//	}
//}

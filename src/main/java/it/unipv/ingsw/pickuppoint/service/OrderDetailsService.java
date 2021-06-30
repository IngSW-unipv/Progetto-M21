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
	
	/**
	 * Ritorna OrderDetails tramite il suo tracking code
	 * 
	 * @param trackingCode
	 * @return OrderDetails by tracking code
	 */
	public OrderDetails findByTrackingCode(String trackingCode) {
		return orderDetailsRepo.findByTrackingCode(trackingCode);
	}
	
	/**
	 * Salva nel database un orderDetails passato come parametro
	 * 
	 * @param orderDetails
	 */
	@Transactional
	public void save(OrderDetails orderDetails) {
		orderDetailsRepo.save(orderDetails);
	}

	/**
	 * Ritorna una lista di OrderDetails assegnati ad uno specifico
	 * customer, identificato dal suo id.
	 * 
	 * @param customerId
	 * @return List OrderDetails del customer
	 */
	public List<OrderDetails> getCustomerOrders(Long customerId) {
		return orderDetailsRepo.findByCustomer_userId(customerId);
	}

	/**
	 * Ritorna una lista di OrderDetails assegnati ad uno specifico
	 * courier, identificato dal suo id.
	 * 
	 * @param courierId
	 * @return List OrderDetails del courier
	 */
	public List<OrderDetails> getCourierOrders(Long courierId) {
		return orderDetailsRepo.findByCourier_userId(courierId);
	}
	
	/**
	 * Ritorna una lista con tutti gli orderDetails esistenti nel
	 * database.
	 * 
	 * @return List OrderDetails
	 */
	public List<OrderDetails> getAllOrderDetails() {
		return orderDetailsRepo.findAll();
	}
	
	/**
	 * Ritorna una lista con tutti gli orderDetails presenti nell'hub,
	 * cioè quelli il cui stato di consegna è HUB.
	 * 
	 * @return List OrderDetails nell'hub
	 */
	public List <OrderDetails> getAllHubOrders(){
		return orderDetailsRepo.findByDeliveryDetails_deliveryStatus(DeliveryStatus.HUB);
	}
	
	/**
	 * Ritorna OrderDetails tramite il suo pickup code
	 * 
	 * @param pickupCode
	 * @return orderDetails by pickup code
	 */
	public OrderDetails getOrderByPickupCode(String pickupCode) {
		return orderDetailsRepo.findByPickupCode(pickupCode);
	}
	
	/**
	 * Restituisce una mappa di order id con il relativo tempo rimanente
	 * prima che scada il termine di consegna (2 giorni)
	 * 
	 * @param em
	 * @return
	 */
	@Transactional
	public Map<Long, Integer> getfindListOfDifferenceDeliverdDateAndCurrentDate(EntityManager em){
		return deliveryDetailsRepo.findListOfDifferenceDeliverdDateAndCurrentDate(em);
	}
	
}

package it.unipv.ingsw.pickuppoint.service;

import it.unipv.ingsw.pickuppoint.data.SlotRepo;
import it.unipv.ingsw.pickuppoint.model.OrderDetails;
import it.unipv.ingsw.pickuppoint.model.Product;
import it.unipv.ingsw.pickuppoint.model.Slot;
import it.unipv.ingsw.pickuppoint.model.User;
import it.unipv.ingsw.pickuppoint.service.exception.PickupCodeException;
import it.unipv.ingsw.pickuppoint.service.exception.TrackingCodeException;
import it.unipv.ingsw.pickuppoint.service.exception.SlotNotAvailableException;
import it.unipv.ingsw.pickuppoint.utility.DateUtils;
import it.unipv.ingsw.pickuppoint.utility.DeliveryStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;

@Service
public class HubService {

	@Autowired
	private OrderDetailsService orderDetailsService;
	@Autowired
	private UserService userService;
	@Autowired
	private LockerService lockerService;
	@Autowired
	private DateUtils date;
	@Autowired
	private SlotRepo slotRepo;

	private static final Logger LOGGER = LoggerFactory.getLogger(LockerService.class);

	/**
	 * Metodo per la consegna del corriere: Ottiene l'ordine tramite id, setta lo
	 * stato in Delivered, setta la data corrente, salva i prodotti negli slot,
	 * salva l'ordine
	 * 
	 * @param id
	 * @throws SlotNotAvailableException gestione errore slot non disponibile per la consegna
	 */
	public void deliver(Long id) throws SlotNotAvailableException {
		OrderDetails orderDetails = orderDetailsService.getOrderDetailsById(id);
		orderDetails.getDeliveryDetails().setDeliveryStatus(DeliveryStatus.DELIVERED);
		orderDetails.getDeliveryDetails().setDateDelivered(date.getCurrentDataTime());
		lockerService.setSlotDeliver(orderDetails);
		orderDetailsService.save(orderDetails);
	}

	/**
	 * Metodo per il ritiro del customer: ottiene l'ordine tramite pickup code,
	 * verifica il pickup code, setta lo stato in WITHDRAWN, setta la data corrente,
	 * salva l'ordine, rimuove i prodotti dallo slot
	 * 
	 * @param pickupCode
	 * @throws PickupCodeException gestione errore pickup code inserito non corretto
	 */
	@Transactional
	public void withdraw(String pickupCode) throws PickupCodeException {
		OrderDetails orderDetails = null;
		orderDetails = orderDetailsService.getOrderByPickupCode(pickupCode);

		if (orderDetails == null)
			throw new PickupCodeException("Wrong pickup code, please try again");

		orderDetails.getDeliveryDetails().setDeliveryStatus(DeliveryStatus.WITHDRAWN);
		orderDetails.getDeliveryDetails().setWithdrawalDate(date.getCurrentDataTime());
		orderDetailsService.save(orderDetails);
		removeProducts(orderDetails.getProducts());
	}

	/**
	 * Metodo per la rimozione dei prodotti dagli slot
	 * 
	 * @param list di prodotti
	 */
	@Transactional
	private void removeProducts(List<Product> list) {
		for (Product product : list) {
			Slot slot = product.getSlot();
			slot.setEmpty(true);
			slot.removeProduct(product);
			product.removeSlot();
			slotRepo.save(slot);
			LOGGER.info("CUSTOMER: " + userService.getAuthenticatedUser().getEmail() + "\t prodotto "
					+ product.getProductId() + " RITIRATO" + "\t slot: " + slot.getSlotId() + "\t locker: "
					+ slot.getLocker().getLockerId());
		}
	}

	/**
	 * Metodo per aggiugere ordini al profilo del customer: ottiene l'ordine tramite
	 * tracking code, verifica il tracking code, ottiene l'user che ha effettuato la
	 * richiesta, setta il customer in order, salva l'ordine
	 * 
	 * @param tracking
	 * @throws TrackingCodeException
	 */
	public void addOrderToProfile(String tracking) throws TrackingCodeException {
		OrderDetails orderDetails = null;
		orderDetails = orderDetailsService.findByTrackingCode(tracking);

		if (orderDetails == null)
			throw new TrackingCodeException("Wrong tracking code, please try again");

		User customer = userService.getAuthenticatedUser();
		orderDetails.setCustomer(customer);
		orderDetailsService.save(orderDetails);
	}

	@Transactional
	public void setNotWithdrawnState(Long id) {
		OrderDetails orderDetails = orderDetailsService.getOrderDetailsById(id);
		orderDetails.getDeliveryDetails().setDeliveryStatus(DeliveryStatus.NOT_WITHDRAWN);
		orderDetailsService.save(orderDetails);
	}

	@Transactional
	public void sendBackToHub(Long id) {
		OrderDetails orderDetails = orderDetailsService.getOrderDetailsById(id);
		orderDetails.getDeliveryDetails().setDeliveryStatus(DeliveryStatus.HUB);
		orderDetails.setCourier(null);
		orderDetails.getDeliveryDetails().setDateDelivered(null);
		removeProducts(orderDetails.getProducts());
		orderDetailsService.save(orderDetails);
	}
}

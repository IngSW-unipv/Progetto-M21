package it.unipv.ingsw.pickuppoint.service;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import it.unipv.ingsw.pickuppoint.data.OrderDetailsRepo;
import it.unipv.ingsw.pickuppoint.data.SlotRepo;
import it.unipv.ingsw.pickuppoint.model.DeliveryDetails;
import it.unipv.ingsw.pickuppoint.model.Locker;
import it.unipv.ingsw.pickuppoint.model.OrderDetails;
import it.unipv.ingsw.pickuppoint.model.Product;
import it.unipv.ingsw.pickuppoint.model.Recipient;
import it.unipv.ingsw.pickuppoint.model.Slot;
import it.unipv.ingsw.pickuppoint.model.User;
import it.unipv.ingsw.pickuppoint.service.exception.ErrorPickupCode;
import it.unipv.ingsw.pickuppoint.service.exception.ErrorTrackingCode;
import it.unipv.ingsw.pickuppoint.service.exception.JsonFormat;
import it.unipv.ingsw.pickuppoint.service.exception.SlotNotAvailable;
import it.unipv.ingsw.pickuppoint.utility.DeliveryStatus;
import it.unipv.ingsw.pickuppoint.utility.JsonReader;
import it.unipv.ingsw.pickuppoint.utility.ProductSize;

@Service
public class HubService {

	@Autowired
	OrderDetailsService orderDetailsService;
	@Autowired
	UserService userService;
	@Autowired
	LockerService lockerService;
	@Autowired
	Date date;
	@Autowired
	SlotRepo slotRepo;
	@Autowired
	OrderDetailsRepo orderDetailsRepo;
	@Autowired
	JsonReader jsonReader;

	private static final Logger LOGGER = LoggerFactory.getLogger(LockerService.class);

	/**
	 * Metodo per la consegna del corriere: Ottiene l'ordine tramite id, setta lo
	 * stato in Delivered, setta la data corrente, salva i prodotti negli slot,
	 * salva l'ordine
	 * 
	 * @param id
	 * @throws SlotNotAvailable gestione errore slot non disponibile per la consegna
	 */
	public void deliver(Long id) throws SlotNotAvailable {
		OrderDetails orderDetails = orderDetailsService.getOrderDetailsById(id);
		orderDetails.getDeliveryDetails().setDeliveryStatus(DeliveryStatus.DELIVERED);
		orderDetails.getDeliveryDetails().setDataDelivered(date.getCurrentDataTime());
		lockerService.setSlotDeliver(orderDetails);
		orderDetailsService.save(orderDetails);
	}

	/**
	 * Metodo per il ritiro del customer: ottiene l'ordine tramite pickup code,
	 * verifica il pickup code, setta lo stato in WITHDRAWN, setta la data corrente,
	 * salva l'ordine, rimuove i prodotti dallo slot
	 * 
	 * @param pickupCode
	 * @throws ErrorPickupCode gestione errore pickup code inserito non corretto
	 */
	@Transactional
	public void withdraw(String pickupCode) throws ErrorPickupCode {
		OrderDetails orderDetails = null;
		orderDetails = orderDetailsService.getOrderByPickupCode(pickupCode);

		if (orderDetails == null)
			throw new ErrorPickupCode("Wrong pickup code, please try again");

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
	 * @throws ErrorTrackingCode
	 */
	public void addOrderToProfile(String tracking) throws ErrorTrackingCode {
		OrderDetails orderDetails = null;
		orderDetails = orderDetailsService.findByTrackingCode(tracking);

		if (orderDetails == null)
			throw new ErrorTrackingCode("Wrong tracking code, please try again");

		User customer = userService.getAuthenticatedUser();
		orderDetails.setCustomer(customer);
		orderDetailsService.save(orderDetails);
	}

	/**
	 * Metodo per aggiungere ordini nell'HUB: legge json, assegna il json a un array
	 * di json identificati da order, crea un nuovo ordini settando tutti i suoi
	 * campi, salva l'ordine
	 * 
	 * @param multipartFile file caricato da admin
	 * @throws JsonFormat
	 * @throws JSONException
	 * @throws IOException
	 */
	public void addOrders(MultipartFile multipartFile) throws JsonFormat, JSONException, IOException {
		JSONObject json = null;
		try {
			json = jsonReader.readJson(multipartFile);
		} catch (JSONException | IOException | InvalidDataAccessResourceUsageException e) {
			throw new JsonFormat("Wrong Json Format, please try again");
		}

		JSONArray orders = json.getJSONArray("orders");

		for (int i = 0; i < orders.length(); i++) {
//			New Order
			OrderDetails newOrder = new OrderDetails();
			JSONObject order = orders.getJSONObject(i);

//			Order attributes
			String trackingCode = (String) order.get("trackingCode");
			// Da rimuovere
			// String pickupCode = (String) order.get("pickupCode");
			Long lockerId = ((Number) order.get("lockerId")).longValue();
			String sender = (String) order.get("sender");

			Locker newLocker = new Locker(lockerId);

//			RECIPIENT
			JSONObject recipient = order.getJSONObject("recipient");
			String firstName = (String) recipient.get("name");
			String lastName = (String) recipient.get("last");
			String email = (String) recipient.get("email");
			Recipient newRecipient = new Recipient(firstName, lastName, email);

//			PRODUCTS
			JSONArray products = order.getJSONArray("products");
			for (int x = 0; x < products.length(); x++) {
				JSONObject product = products.getJSONObject(x);
				Double weight = ((Number) product.get("weight")).doubleValue();
				ProductSize size = ProductSize.valueOf((String) product.get("size"));
				Product newProduct = new Product(weight, size);
				newOrder.setProducts(newProduct);
			}

//			SET ORDER
			newOrder.setLocker(newLocker);
			newOrder.setTrackingCode(trackingCode);
			// newOrder.setPickupCode(pickupCode);
			newOrder.setSender(sender);
			newOrder.setRecipient(newRecipient);
			newOrder.setDeliveryDetails(new DeliveryDetails());

//			SAVE ORDER
			orderDetailsRepo.save(newOrder);
			LOGGER.info(
					"Ordine " + newOrder.getOrderDetailsId() + " in hub " + newOrder.getDeliveryDetails().getHubDate());
		}
	}

	/**
	 * Meotodo per eliminare i couriers dall'ordine identificato tramite id
	 * 
	 * @param id ordine
	 */
	public void deleteCourier(Long id) {
		for (OrderDetails order : orderDetailsRepo.findByCourier_userId(id)) {
			order.setCourier(null);
		}
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
		orderDetails.getDeliveryDetails().setDataDelivered(null);
		removeProducts(orderDetails.getProducts());
		orderDetailsService.save(orderDetails);
	}
}

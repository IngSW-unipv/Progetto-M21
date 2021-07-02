package it.unipv.ingsw.pickuppoint.service;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonFormat;

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
import it.unipv.ingsw.pickuppoint.model.DeliveryDetails;
import it.unipv.ingsw.pickuppoint.model.OrderDetails;
import it.unipv.ingsw.pickuppoint.model.Product;
import it.unipv.ingsw.pickuppoint.model.Recipient;
import it.unipv.ingsw.pickuppoint.service.exception.JsonFormatException;
import it.unipv.ingsw.pickuppoint.utility.DateUtils;
import it.unipv.ingsw.pickuppoint.utility.DeliveryStatus;
import it.unipv.ingsw.pickuppoint.utility.JsonReader;
import it.unipv.ingsw.pickuppoint.utility.ProductSize;

@Service
public class AdminService {

	@Autowired
	private LockerService lockerService;
	@Autowired
	private OrderDetailsRepo orderDetailsRepo;
	@Autowired
	private DateUtils date;

	private static final Logger LOGGER = LoggerFactory.getLogger(LockerService.class);
	
	/**
	 * Metodo per aggiungere ordini nell'HUB: legge json, json diviso in array
	 * di json identificati da orders, crea un nuovo ordine settando tutti i suoi
	 * campi infine salva l'ordine
	 * 
	 * @param multipartFile file caricato da admin
	 * @throws JsonFormat
	 * @throws JSONException
	 * @throws IOException
	 */
	public void addOrders(MultipartFile multipartFile) throws JsonFormatException, JSONException, IOException {
		JSONObject json = null;
		try {
			json = JsonReader.readJson(multipartFile);
		} catch (JSONException | IOException | InvalidDataAccessResourceUsageException e) {
			throw new JsonFormatException("Wrong Json Format, please try again");
		}

		JSONArray orders = json.getJSONArray("orders");

		for (int i = 0; i < orders.length(); i++) {
//			New Order
			OrderDetails newOrder = new OrderDetails();
			JSONObject order = orders.getJSONObject(i);

//			ORDER ATTRIBUTES
			String trackingCode = (String) order.get("trackingCode");
			Long lockerId = ((Number) order.get("lockerId")).longValue();
			String sender = (String) order.get("sender");

//			RECIPIENT
			JSONObject recipient = order.getJSONObject("recipient");
			String firstName = (String) recipient.get("name");
			String lastName = (String) recipient.get("last");
			Recipient newRecipient = new Recipient(firstName, lastName);

//			PRODUCTS
			JSONArray products = order.getJSONArray("products");
			for (int x = 0; x < products.length(); x++) {
				JSONObject product = products.getJSONObject(x);
				Double weight = ((Number) product.get("weight")).doubleValue();
				ProductSize size = ProductSize.valueOf((String) product.get("size"));
				Product newProduct = new Product(weight, size);
				newOrder.setProducts(newProduct);
			}
			
			DeliveryDetails newDeliveryDetails = new DeliveryDetails(date.getCurrentDataTime(), DeliveryStatus.HUB);

//			SET ORDER
			newOrder.setLocker(lockerService.getLockerById(lockerId));
			newOrder.setTrackingCode(trackingCode);
			newOrder.setSender(sender);
			newOrder.setRecipient(newRecipient);
			newOrder.setDeliveryDetails(newDeliveryDetails);
			newOrder.setPickupCode(Integer.toString((int) (Math.random() * 100000 + 1)));

//			SAVE ORDER
			orderDetailsRepo.save(newOrder);
			LOGGER.info(
					"Ordine " + newOrder.getOrderDetailsId() + " in HUB " + newOrder.getDeliveryDetails().getHubDate());
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

}

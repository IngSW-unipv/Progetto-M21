package it.unipv.ingsw.pickuppoint.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.unipv.ingsw.pickuppoint.data.LockerAddressRepo;
import it.unipv.ingsw.pickuppoint.data.LockerRepo;
import it.unipv.ingsw.pickuppoint.data.SlotRepo;
import it.unipv.ingsw.pickuppoint.model.Locker;
import it.unipv.ingsw.pickuppoint.model.OrderDetails;
import it.unipv.ingsw.pickuppoint.model.Product;
import it.unipv.ingsw.pickuppoint.model.Slot;
import it.unipv.ingsw.pickuppoint.service.exception.SlotNotAvailableException;

@Service
public class LockerService {
	@Autowired
	LockerRepo lockerRepo;
	@Autowired
	SlotRepo slotRepo;
	@Autowired
	LockerAddressRepo addressRepo;
	@Autowired
	UserService userService;

	private static final Logger LOGGER = LoggerFactory.getLogger(LockerService.class);

	/**
	 * Metodo per assegnare i prodotti di un ordine ad uno slot in base al volume del prodotto.
	 * Vengono recuperate una lista con i prodotti dell'ordine e una lista contenente gli slot.
	 * Tramite dei cicli e dei controlli vengono assegnati agli slot. 
	 * 
	 * @param orderDetails
	 * @throws SlotNotAvailableException
	 */
	
	public void setSlotDeliver(OrderDetails orderDetails) throws SlotNotAvailableException {
		List<Product> products = orderDetails.getProducts();
		List<Slot> slots = getLockerSlot(orderDetails.getLocker().getLockerId());

//		Volume totale prodotti dell'ordine
		double totVolume = totVolumeProduct(products);

		Long slotId = allInOneSlot(slots, totVolume);

		for (Product product : products) {

			if (slotId != null) {
				Slot slot = slotRepo.findBySlotId(slotId);
				product.setSlot(slot);
				slot.addProduct(product);
				slot.setEmpty(false);
				LOGGER.info("COURIER: " + userService.getAuthenticatedUser().getEmail() + "\t \t prodotto "
						+ product.getProductId() + " CONSEGNATO" + "\t slot: " + slot.getSlotId() + "\t locker: "
						+ slot.getLocker().getLockerId());
			}

			else {
				double productVolume = product.getVolume();

				for (Slot slot : slots) {
					double slotVolume = slot.getVolume();

					if (slot.isEmpty() && product.getSlot() == null) {
						if (slotVolume >= productVolume) {
							slot.addProduct(product);
							slot.setEmpty(false);
							LOGGER.info("COURIER: " + userService.getAuthenticatedUser().getEmail() + "\t \t prodotto "
									+ product.getProductId() + " CONSEGNATO" + "\t slot: " + slot.getSlotId()
									+ "\t locker: " + slot.getLocker().getLockerId());
						}
					}
				}

				if (product.getSlot() == null) {
					throw new SlotNotAvailableException(
							"NON è POSSIBILE CONSEGNARE L'ORDINE " + product.getOrderId() + " SLOT NON DISPONIBILI");
				}
			}
		}
	}
	
	/**
	 * Metodo per verificare se tutti i prodotti possono essere inseriti in uno slot
	 * 
	 * @param slots
	 * @param totVolume
	 * @return
	 */

	private Long allInOneSlot(List<Slot> slots, double totVolume) {
		Long slotId = null;
		for (Slot slot : slots) {
			if (Math.abs(slot.getVolume()) >= Math.abs(totVolume) && slot.isEmpty())
				slotId = slot.getSlotId();
		}
		return slotId;
	}
	
	/**
	 * Metodo per controllare il volume totale dei prodotti.
	 * 
	 * @param products
	 * @return
	 */

	private double totVolumeProduct(List<Product> products) {
		double totVolume = 0;
		for (Product product : products) {
			totVolume += product.getVolume();
		}
		return totVolume;
	}
	
	/**
	 * Metodo per ottenere dal DB una lista di slot appartenenti ad un locker.
	 * 
	 * @param id
	 * @return
	 */

	public List<Slot> getLockerSlot(Long id) {
		return slotRepo.findByLockerId(id);
	}
	
	/**
	 * Metodo per ottenere dal DB una lista di tutti i locker
	 * 
	 * @return
	 */
	
	public List <Locker> getAllLocker(){
		return lockerRepo.findAll();
	}
	
	/**
	 * Metodo che restituisce un oggetto locker in base all'id
	 * 
	 * @param lockerId
	 * @return
	 */
	
	public Locker getLockerById(Long lockerId) {
		return lockerRepo.findByLockerId(lockerId);
	}
}
package it.unipv.ingsw.pickuppoint.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.unipv.ingsw.pickuppoint.data.LockerAddressRepo;
import it.unipv.ingsw.pickuppoint.data.LockerRepo;
import it.unipv.ingsw.pickuppoint.data.SlotRepo;
import it.unipv.ingsw.pickuppoint.model.OrderDetails;
import it.unipv.ingsw.pickuppoint.model.Product;
import it.unipv.ingsw.pickuppoint.model.Slot;
import it.unipv.ingsw.pickuppoint.service.exception.SlotNotAvailable;

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

	public void setSlotDeliver(OrderDetails orderDetails) throws SlotNotAvailable {
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
							LOGGER.info("COURIER: " + userService.getAuthenticatedUser().getEmail() + "\t prodotto "
									+ product.getProductId() + " CONSEGNATO" + "\t slot: " + slot.getSlotId()
									+ "\t locker: " + slot.getLocker().getLockerId());
						}
					}
				}

				if (product.getSlot() == null) {
					throw new SlotNotAvailable(
							"NON è POSSIBILE CONSEGNARE L'ORDINE " + product.getOrderId() + " SLOT NON DISPONIBILI");
				}
			}
		}
	}

	private Long allInOneSlot(List<Slot> slots, double totVolume) {
		Long slotId = null;
		for (Slot slot : slots) {
			if (Math.abs(slot.getVolume()) >= Math.abs(totVolume) && slot.isEmpty())
				slotId = slot.getSlotId();
		}
		return slotId;
	}

	private double totVolumeProduct(List<Product> products) {
		double totVolume = 0;
		for (Product product : products) {
			totVolume += product.getVolume();
		}
		return totVolume;
	}

	public List<Slot> getLockerSlot(Long id) {
		return slotRepo.findByLockerId(id);
	}
}
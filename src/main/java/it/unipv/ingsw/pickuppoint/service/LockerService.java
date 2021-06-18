package it.unipv.ingsw.pickuppoint.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.unipv.ingsw.pickuppoint.data.LockerAddressRepo;
import it.unipv.ingsw.pickuppoint.data.LockerRepo;
import it.unipv.ingsw.pickuppoint.data.SlotRepo;
import it.unipv.ingsw.pickuppoint.model.Locker;
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

	public void setSlotDeliver(OrderDetails orderDetails) throws SlotNotAvailable {
		Locker locker = orderDetails.getLocker();
		List<Product> products = orderDetails.getProducts();
		Collections.sort(products, new Comparator<Product>() {

			@Override
			public int compare(Product o1, Product o2) {
				if (o1.getVolume() > o2.getVolume()) {
					return 1;
				} else if (o1.getVolume() < o2.getVolume()) {
					return -1;
				} else {
					return 0;
				}
			}

		});

		List<Slot> slotList = slotRepo.findByLockerId(locker.getLockerId());

		Collections.sort(slotList, new Comparator<Slot>() {

			@Override
			public int compare(Slot o1, Slot o2) {
				if (o1.getVolume() > o2.getVolume()) {
					return 1;
				} else if (o1.getVolume() < o2.getVolume()) {
					return -1;
				} else {
					return 0;
				}
			}

		});

		String error = "";

		for (Product product : products) {

			boolean check = false;

			for (int i = 0; i < slotList.size(); i++) {

				if ((slotList.get(i).isEmpty()) && (slotList.get(i).getVolume()  >= product.getVolume())) {
					slotList.get(i).setEmpty(false);
					slotList.get(i).setProduct(product);
					i = slotList.size();

					check = true;
				}

			}

			if (check == false)
				error = "Impossibile consegnare ordine " + product.getOrderId() + "\n";
		}

		if (error.length() != 0) {
			throw new SlotNotAvailable(error);
		}

	}

}

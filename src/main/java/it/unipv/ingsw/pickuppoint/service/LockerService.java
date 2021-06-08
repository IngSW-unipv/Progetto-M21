package it.unipv.ingsw.pickuppoint.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.unipv.ingsw.pickuppoint.data.LockerAddressRepo;
import it.unipv.ingsw.pickuppoint.data.LockerRepo;
import it.unipv.ingsw.pickuppoint.data.SlotRepo;
import it.unipv.ingsw.pickuppoint.model.entity.Locker;
import it.unipv.ingsw.pickuppoint.model.entity.OrderDetails;
import it.unipv.ingsw.pickuppoint.model.entity.Product;
import it.unipv.ingsw.pickuppoint.model.entity.Slot;

@Service
public class LockerService {
	@Autowired
	LockerRepo lockerRepo;

	@Autowired
	SlotRepo slotRepo;
	
	@Autowired
	LockerAddressRepo addressRepo;
	
	
	public void setSlotDeliver(OrderDetails orderDetails) {
		Locker locker = orderDetails.getLocker();
		List<Product> products = orderDetails.getProducts();
		Collections.sort(products, new Comparator<Product>() {

			@Override
			public int compare(Product o1, Product o2) {
				if(o1.getHeight() > o2.getHeight()) {
					return -1;
				} else if (o1.getHeight() < o2.getHeight()) {
					return 1;
				} else {
					return 0;
				}
			}
			
		});
		
		System.out.println(products.toString());
		
		List<Slot> slotList = slotRepo.findByLockerId(locker.getLockerId());
		
		Collections.sort(slotList, new Comparator<Slot>() {

			@Override
			public int compare(Slot o1, Slot o2) {
				if(o1.getHeight() > o2.getHeight()) {
					return 1;
				} else if (o1.getHeight() < o2.getHeight()) {
					return -1;
				} else {
					return 0;
				}
			}
			
		});
		
		
		for(Product product : products) {
			
			boolean check = false;
			
			for(int i = 0; i < slotList.size(); i++) {
			
				if((slotList.get(i).isEmpty())&&(slotList.get(i).getHeight() >= product.getHeight())) {
					slotList.get(i).setEmpty(false);
					slotList.get(i).setProduct(product);
					i = slotList.size();
					
					check = true;	
				}
			
			}
			
			if (check == false) System.out.println("IMPOSSIBILE CONSEGNARE");
			
		}
		
	
	}
	

}

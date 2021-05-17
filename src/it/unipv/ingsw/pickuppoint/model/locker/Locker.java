package it.unipv.ingsw.pickuppoint.model.locker;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import it.unipv.ingsw.pickuppoint.model.delivery.Product;

public class Locker {
	private int lockerId;
	private LockerAddress lockerAddress;
	private Map<Integer, Slot> lockerNumberToSlot;

	/**
	 * 
	 * @param lockerId
	 * @param city
	 * @param address
	 * @param postalCode
	 */
	public Locker(int lockerId, String city, String address, int postalCode) {
		this.lockerId = lockerId;
		lockerAddress = new LockerAddress(city, address, postalCode);
		lockerNumberToSlot = new HashMap<Integer, Slot>();
	}

	/**
	 * 
	 * @param slotSize
	 * @return slotId
	 */
	public int getSlotId(SlotSize slotSize) {
		for (Slot slot : lockerNumberToSlot.values()) {
			if (slot.isEmpty() && slot.getSize() == slotSize) {
				return slot.getSlotId();
			}
		}
		return -1;
	}

	public Map<Integer, Slot> getLockerNumberToSlot() {
		return lockerNumberToSlot;
	}

	public int getLockerNumber() {
		return lockerId;
	}

	public LockerAddress getLockerAddress() {
		return lockerAddress;
	}
}

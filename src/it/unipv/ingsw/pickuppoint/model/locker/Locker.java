package it.unipv.ingsw.pickuppoint.model.locker;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Locker {
	private int lockerId;
	private LockerAddress lockerAddress;
	private Map<Integer, Slot> lockerNumberToSlot;

	public Locker(int lockerId, String city, String address, int postalCode) {
		this.lockerId = lockerId;
		lockerAddress = new LockerAddress(city, address, postalCode);
		lockerNumberToSlot = new HashMap<Integer, Slot>();
	}
	
	public boolean isAvailable(SlotSize slotSize) {
		for (Entry<Integer, Slot> me : lockerNumberToSlot.entrySet()) {
			Slot slot = me.getValue();
			if (slot.isEmpty() && slot.getSize() == slotSize) {
				return true;
			}
		}
		return false;
	}
	
//	/**
//	 * @param required slot size
//	 * @return slot key
//	 */
//	public Integer getSlotId(SlotSize slotSize) {
//		for (Entry<Integer, Slot> me : lockerNumberToSlot.entrySet()) {
//			Slot slot = me.getValue();
//			
//			if (slot.isEmpty() && slot.getSize() == slotSize) {
//				return me.getKey();
//			}
//		}
//		return -1;
//	}

	public int getLockerNumber() {
		return lockerId;
	}

	public LockerAddress getLockerAddress() {
		return lockerAddress;
	}
}

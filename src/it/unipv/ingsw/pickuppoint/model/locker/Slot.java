package it.unipv.ingsw.pickuppoint.model.locker;

import it.unipv.ingsw.pickuppoint.model.delivery.Product;

public class Slot {
	private int slotId;
	private int height;
	private int width;
	private int length;
	private boolean isEmpty;
	private Product product;
	
	private SlotSize size;

	public Slot(SlotSize size) {
		this.size = size;
		initSize();
	}

	private void initSize() {
		this.height = size.getHeight();
		this.width = size.getWidth();
		this.length = size.getLength();
	}

	public boolean isEmpty() {
		return isEmpty;
	}
	
	public int getSlotId() {
		return slotId;
	}

	public Product getProduct() {
		return product;
	}

	public SlotSize getSize() {
		return size;
	}
}

package it.unipv.ingsw.pickuppoint.model.locker;

public class Slot {
	private int height;
	private int width;
	private int length;
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

	@Override
	public String toString() {
		return "Slot [altezza=" + height + ", larghezza=" + width + ", lunghezza=" + length + ", size=" + size + "]";
	}
}

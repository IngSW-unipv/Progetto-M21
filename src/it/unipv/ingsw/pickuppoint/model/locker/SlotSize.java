package it.unipv.ingsw.pickuppoint.model.locker;

public enum SlotSize {
	BIG(50, 50, 50), MEDIUM(20, 20, 20), SMALL(10, 10, 10);

	private int length;
	private int width;
	private int height;

	SlotSize(int length, int width, int height) {
		this.length = length;
		this.width = width;
		this.height = height;
	}

	public int getLength() {
		return length;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}

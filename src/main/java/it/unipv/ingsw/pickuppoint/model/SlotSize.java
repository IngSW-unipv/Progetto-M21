package it.unipv.ingsw.pickuppoint.model;

public enum SlotSize {
	BIG(50, 50, 50, "BIG"), MEDIUM(20, 20, 20, "MEDIUM"), SMALL(10, 10, 10, "SMALL");

	private int length;
	private int width;
	private int height;

	SlotSize(int length, int width, int height, String size) {
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

package it.unipv.ingsw.pickuppoint.utility;

public enum ProductSize {
	BIG(50, 50, 50, "BIG"), MEDIUM(20, 20, 20, "MEDIUM"), SMALL(10, 10, 10, "SMALL");

	private double length;
	private double width;
	private double height;
	private String name;

	ProductSize(int length, int width, int height, String size) {
		this.length = length;
		this.width = width;
		this.height = height;
	}

	ProductSize(String size) {
		this.name = size;
	}

	public double getLength() {
		return length;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public double getVolume() {
		return height * width * length;
	}

	public String getName() {
		return name;
	}
}

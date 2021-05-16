package it.unipv.ingsw.pickuppoint.model.locker;

public class LockerAddress {
	private String city;
	private String address;
	private int postalCode;
	
	public LockerAddress(String city, String address, int postalCode) {
		this.city = city;
		this.address = address;
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}

	public String getAddress() {
		return address;
	}

	public int getPostalCode() {
		return postalCode;
	}
}

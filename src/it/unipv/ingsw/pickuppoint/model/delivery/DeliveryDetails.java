package it.unipv.ingsw.pickuppoint.model.delivery;

public class DeliveryDetails {
	private String arrivalDataHub;
	private String deliveryDate;
	private String dataDeliverd;
	private String pickupCode;
	private int slotId;
	private DeliveryStatus deliveryStatus;

	public void setSlotId(int slotId) {
		this.slotId = slotId;
	}
	
	public void setDeliverStatus(DeliveryStatus deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}
}

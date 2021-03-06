package it.unipv.ingsw.pickuppoint.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Random;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import it.unipv.ingsw.pickuppoint.data.OrderDetailsRepo;
import it.unipv.ingsw.pickuppoint.data.SlotRepo;
import it.unipv.ingsw.pickuppoint.model.DeliveryDetails;
import it.unipv.ingsw.pickuppoint.model.OrderDetails;
import it.unipv.ingsw.pickuppoint.model.User;
import it.unipv.ingsw.pickuppoint.service.exception.PickupCodeException;
import it.unipv.ingsw.pickuppoint.service.exception.TrackingCodeException;
import it.unipv.ingsw.pickuppoint.service.exception.SlotNotAvailableException;
import it.unipv.ingsw.pickuppoint.utility.DateUtils;
import it.unipv.ingsw.pickuppoint.utility.DeliveryStatus;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class HubServiceTest {

	@MockBean
	private OrderDetailsService orderDetailsServiceMock;
	@MockBean
	private UserService userServiceMock;
	@MockBean
	private LockerService lockerServiceMock;
	@MockBean
	private DateUtils dateMock;
	@MockBean
	private SlotRepo slotRepoMock;
	@MockBean
	private OrderDetailsRepo orderDetailsRepoMock;
	@MockBean
	private OrderDetails orderDetailsMock;
	@InjectMocks
	private HubService hubService;

	private Long id = new Random().nextLong();;

	/**
	 * This test checks the deliver function by asserting that the deliveryStatus is
	 * DELIVERED.
	 * 
	 * @throws SlotNotAvailableException
	 */
	@Test
	@DisplayName("this tests the delivery status in the deliver method")
	void testDeliveryStatus() throws SlotNotAvailableException {

		DeliveryDetails deliveryDetails = new DeliveryDetails();
		Mockito.when(orderDetailsServiceMock.getOrderDetailsById(id)).thenReturn(orderDetailsMock);
		Mockito.when(orderDetailsMock.getDeliveryDetails()).thenReturn(deliveryDetails);

		hubService.deliver(id);
		assertEquals(DeliveryStatus.DELIVERED, deliveryDetails.getDeliveryStatus());
	}

	/**
	 * This test checks the deliver function by asserting that the the date is
	 * correct.
	 * 
	 * @throws SlotNotAvailableException
	 */
	@Test
	@DisplayName("this tests the delivery date in the deliver method")
	void testDeliveryDate() throws SlotNotAvailableException {

		DeliveryDetails deliveryDetails = new DeliveryDetails();
		Mockito.when(orderDetailsServiceMock.getOrderDetailsById(id)).thenReturn(orderDetailsMock);
		Mockito.when(orderDetailsMock.getDeliveryDetails()).thenReturn(deliveryDetails);

		hubService.deliver(id);
		assertEquals(dateMock.getCurrentDataTime(), deliveryDetails.getDateDelivered());
	}

	/**
	 * This test checks the withdraw function by asserting that DeliveryStatus is
	 * WITHDRAWN.
	 */
	@Test
	@DisplayName("this tests the withdrawn status in the withdraw method")
	void testWithdrawnStatus() {
		DeliveryDetails deliveryDetails = new DeliveryDetails();
		Mockito.when(orderDetailsServiceMock.getOrderByPickupCode(id.toString())).thenReturn(orderDetailsMock);
		Mockito.when(orderDetailsMock.getDeliveryDetails()).thenReturn(deliveryDetails);

		try {
			hubService.withdraw(id.toString());
		} catch (PickupCodeException e) {
			fail("FAILURE MESSAGE: Order does not exist, unexpected exception");
		}
		assertEquals(DeliveryStatus.WITHDRAWN, deliveryDetails.getDeliveryStatus());
	}

	/**
	 * This test checks that the exception in the withdraw function is thrown.
	 */
	@Test
	@DisplayName("this tests the exception in the withdraw method")
	void testWithdrawException() {
		DeliveryDetails deliveryDetails = new DeliveryDetails();
		Mockito.when(orderDetailsServiceMock.getOrderByPickupCode(id.toString())).thenReturn(null);
		Mockito.when(orderDetailsMock.getDeliveryDetails()).thenReturn(deliveryDetails);
		assertThrows(PickupCodeException.class, () -> {
			hubService.withdraw(id.toString());
		});
	}

	/**
	 * This test checks the sendBackToHub function by asserting that the delivery
	 * date is null.
	 */
	@Test
	@DisplayName("this tests the delivery date in the sendBackToHub method")
	void testSendBackToHubDeliveryDate() {
		DeliveryDetails deliveryDetails = new DeliveryDetails();
		Mockito.when(orderDetailsServiceMock.getOrderDetailsById(id)).thenReturn(orderDetailsMock);
		Mockito.when(orderDetailsMock.getDeliveryDetails()).thenReturn(deliveryDetails);

		hubService.sendBackToHub(id);
		assertEquals(null, deliveryDetails.getDateDelivered());
	}

	/**
	 * This test checks the sendBackToHub function by asserting that the
	 * DeliveryStatus is HUB.
	 */
	@Test
	@DisplayName("this tests the delivery status in the sendBackToHub method")
	void testSendBackToHubDeliveryStatus() {
		DeliveryDetails deliveryDetails = new DeliveryDetails();
		Mockito.when(orderDetailsServiceMock.getOrderDetailsById(id)).thenReturn(orderDetailsMock);
		Mockito.when(orderDetailsMock.getDeliveryDetails()).thenReturn(deliveryDetails);

		hubService.sendBackToHub(id);
		assertEquals(DeliveryStatus.HUB, deliveryDetails.getDeliveryStatus());
	}

	/**
	 * This test checks the addOrderToProfile function by checking that the user is
	 * assigned correctly.
	 */
	@Test
	@DisplayName("this tests the user assignment in the addOrderToProfile method")
	void testAddOrderToProfileAssignment() {
		OrderDetails orderDetails = new OrderDetails();
		User user = new User();
		Mockito.when(orderDetailsServiceMock.findByTrackingCode(id.toString())).thenReturn(orderDetails);
		Mockito.when(userServiceMock.getAuthenticatedUser()).thenReturn(user);
		try {
			hubService.addOrderToProfile(id.toString());
		} catch (TrackingCodeException e) {
			fail("FAILURE MESSAGE: Order does not exist, unexpected exception");
		}
		assertEquals(user, orderDetails.getCustomer());
	}

	/**
	 * This test checks that the exception in the addOrderToProfile function is
	 * thrown.
	 */
	@Test
	@DisplayName("this tests the exception in the addOrderToProfile method")
	void testAddOrderToProfileException() {
		User user = new User();
		Mockito.when(orderDetailsServiceMock.findByTrackingCode(id.toString())).thenReturn(null);
		Mockito.when(userServiceMock.getAuthenticatedUser()).thenReturn(user);
		assertThrows(TrackingCodeException.class, () -> {
			hubService.addOrderToProfile(id.toString());
		});
	}

}
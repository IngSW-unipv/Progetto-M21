package it.unipv.ingsw.pickuppoint.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;
import java.util.List;

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
import it.unipv.ingsw.pickuppoint.service.exception.ErrorPickupCode;
import it.unipv.ingsw.pickuppoint.service.exception.ErrorTrackingCode;
import it.unipv.ingsw.pickuppoint.service.exception.SlotNotAvailable;
import it.unipv.ingsw.pickuppoint.utility.DeliveryStatus;
import it.unipv.ingsw.pickuppoint.model.Product;
import it.unipv.ingsw.pickuppoint.model.User;

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
	private Date dateMock;
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
	 * @throws SlotNotAvailable
	 */
	@Test
	@DisplayName("this tests the delivery status in the deliver method")
	void testDeliveryStatus() throws SlotNotAvailable {

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
	 * @throws SlotNotAvailable
	 */
	@Test
	@DisplayName("this tests the delivery date in the deliver method")
	void testDeliveryDate() throws SlotNotAvailable {

		DeliveryDetails deliveryDetails = new DeliveryDetails();
		Mockito.when(orderDetailsServiceMock.getOrderDetailsById(id)).thenReturn(orderDetailsMock);
		Mockito.when(orderDetailsMock.getDeliveryDetails()).thenReturn(deliveryDetails);

		hubService.deliver(id);
		assertEquals(dateMock.getCurrentDataTime(), deliveryDetails.getDataDelivered());
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
		} catch (ErrorPickupCode e) {
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
		assertThrows(ErrorPickupCode.class, () -> {
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
		assertEquals(null, deliveryDetails.getDataDelivered());
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
		} catch (ErrorTrackingCode e) {
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
		assertThrows(ErrorTrackingCode.class, () -> {
			hubService.addOrderToProfile(id.toString());
		});
	}

}
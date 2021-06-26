package it.unipv.ingsw.pickuppoint.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import it.unipv.ingsw.pickuppoint.service.HubService;
import it.unipv.ingsw.pickuppoint.service.UserService;

@ExtendWith(MockitoExtension.class)
class CourierControllerTest {
		
	private Random rd = new Random();
	
	@Mock
	private UserService userServiceMock = Mockito.mock(UserService.class);
	@Mock
	private HubService hubServiceMock = Mockito.mock(HubService.class);
	@InjectMocks
	private CourierController courierControllerMock = new CourierController();

	@Test
	@DisplayName("this tests the showEditProductFormCourier method and should run")
	void testShowEditProductFormCourier() {
		Long id = rd.nextLong();
		String result = courierControllerMock.showEditProductFormCourier(id, null);
		assertEquals("redirect:" + "/Orders",result);
	}

	@Test
	@DisplayName("this tests the sendBacktoHub method and should run")
	void testSendBacktoHub() {
		Long id = rd.nextLong();
		String result = courierControllerMock.sendBacktoHub(id);
		assertEquals("redirect:" + "/Orders",result);
	}
	
	@Test
	@DisplayName("this tests the sendBacktoHub method and should fail")
	void testSendBacktoHubShouldFail() {
		Long id = rd.nextLong();
		String result = courierControllerMock.sendBacktoHub(id);
		assertEquals("this is a failure message",result);
	}

}

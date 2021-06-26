package it.unipv.ingsw.pickuppoint.controller;

import static org.junit.jupiter.api.Assertions.*;

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
class CustomerControllerTest {
	
	@Mock
	private UserService userServiceMock = Mockito.mock(UserService.class);
	@Mock
	private HubService hubServiceMock = Mockito.mock(HubService.class);
	@InjectMocks
	private CustomerController customerControllerMock = new CustomerController();
	
	@Test
	@DisplayName("this tests the addOrder method and should run")
	void testAddOrder() {
		String result = customerControllerMock.addOrder("TRACKINGCODE", null);
		assertEquals("redirect:" + "/Orders", result);
	}
	
	@Test
	@DisplayName("this tests the addOrder method and should fail")
	void testAddOrderShouldFail() {
		String result = customerControllerMock.addOrder("TRACKINGCODE", null);
		assertEquals("/profile", result);
	}

	@Test
	@DisplayName("this tests the showEditProductForm method and should run")
	void testShowEditProductForm() {
		String result = customerControllerMock.showEditProductForm("PICKUPCODE", null);
		assertEquals("redirect:" + "/Orders", result);
	}

}

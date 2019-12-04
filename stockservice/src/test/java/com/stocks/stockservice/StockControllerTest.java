package com.stocks.stockservice;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.stocks.stockservice.model.StockServiceUser;
import com.stocks.stockservice.service.UserServiceImpl;

public class StockControllerTest {
	
	@InjectMocks 
	UserServiceImpl userController;
	
	@Mock
	UserServiceImpl userService;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetUser() {
		StockServiceUser user = getMockUser();
 		when(userService.getUser((long) 1)).thenReturn(user);
 		
 		userService.getUser((long) 1);
 		
		assertEquals("mock user",user.getUserName());
		
	}

	private StockServiceUser getMockUser() {
		StockServiceUser user = new StockServiceUser("mock user", "email@testemail.com", "1235", "user address", "1234567", "5624436", "User Bank Name", 3000, "ADMIN", "pwd");
		return user;
	}
}

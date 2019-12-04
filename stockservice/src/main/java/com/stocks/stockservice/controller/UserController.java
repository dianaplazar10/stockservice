package com.stocks.stockservice.controller;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stocks.stockservice.exceptionhandlers.CustomRuntimeException;
import com.stocks.stockservice.model.StockServiceUser;
import com.stocks.stockservice.model.UserRole;
import com.stocks.stockservice.service.UserService;

@RestController
public class UserController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@GetMapping(value = "/hello")
	public String getResponse() {
		return "Hello";
	}
	
	@GetMapping(value="/users/{userId}")
	public ResponseEntity<StockServiceUser> getUser(@PathVariable("userId") String userId) {
		StockServiceUser user = userService.getUser(Long.parseLong(userId));
		if(user==null) {
			throw new CustomRuntimeException("No user present with the given userId : "+userId);
		} 
		return new ResponseEntity<StockServiceUser>(user,HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/users/{userId}")
	public ResponseEntity<Void> deleteUser(@PathVariable("userId") String userId) {
		try {
			userService.removeUser(Long.parseLong(userId));
		} catch (Exception ex) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.noContent().build(); 
	}
	
	@PostMapping(value="/users",consumes = {MediaType.APPLICATION_JSON_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<StockServiceUser> createUser(@Valid @RequestBody StockServiceUser user) {
		if(!isRoleValid(user.getRole())) {
			throw new CustomRuntimeException("Invalid Role. Valid roles are: "
												+ Arrays.asList(UserRole.values()));
		}
		StockServiceUser usr = userService.createUser(user);
		return new ResponseEntity<StockServiceUser>(usr,HttpStatus.CREATED);
	}

	private boolean isRoleValid(String reqUsrRole) {
		Set<String> roleNamesSet = Stream.of(UserRole.values()).map(Enum::name)
				.collect(Collectors.toSet());
		return roleNamesSet.contains(reqUsrRole);
	}

}

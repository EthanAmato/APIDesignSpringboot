package com.tts.apidesignlab.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tts.apidesignlab.models.User;
import com.tts.apidesignlab.repositories.UserRepository;

@RestController
public class UserController {

	@Autowired
	UserRepository repository;

	//Working on Postman
	@GetMapping("/users")
	public  ResponseEntity<List<User>> getUsers(@RequestParam(value = "state", required = false) String state) {
		if (state != null) {
			return new ResponseEntity<>((List<User>) repository.findByState(state), HttpStatus.OK);
		}
		return new ResponseEntity<>((List<User>) repository.findAll(), HttpStatus.OK);
	}

	//Working on Postman
	@GetMapping(path = "/users/{id}")
	public ResponseEntity<Optional<User>> getUserById(@PathVariable(value = "id") @Valid Long id) {
		Optional<User> user = repository.findById(id);
		if (!user.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(repository.findById(id), HttpStatus.OK);
	}

	//Working on Postman
	@PostMapping("/users")
	public ResponseEntity<Void> createUser(@RequestBody @Valid User user, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return new ResponseEntity<> (HttpStatus.BAD_REQUEST);
		}
		repository.save(new User(user.getId(), user.getFirstName(), user.getLastName(), user.getState()));
		return new ResponseEntity<> (HttpStatus.CREATED); 
	}
	
	
	//Working on Postman
	@PutMapping("/users/{id}")
	public ResponseEntity<Void> updateUser(@PathVariable(value = "id") @Valid Long id, 
										   @RequestBody @Valid User user, 
										   BindingResult bindingResult) {
		Optional<User> updatingUser = repository.findById(id);
		if(!updatingUser.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		if(bindingResult.hasErrors()) {
			return new ResponseEntity<> (HttpStatus.BAD_REQUEST);
		}
		repository.save(new User(id, user.getFirstName(), user.getLastName(), user.getState()));
		
		return new ResponseEntity<> (HttpStatus.OK); 
	}

	//Working on Postman
	@DeleteMapping("/users/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable(value = "id") @Valid Long id) {
		Optional<User> user = repository.findById(id);
		if (!user.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		repository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	

}

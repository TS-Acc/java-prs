package com.maxtrain.bootcamp.prs.user;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserRepository userRepo;
	
	@GetMapping("{username}/{password}")
	public ResponseEntity<User> getByUsernameAndPassword(@PathVariable String username, @PathVariable String password) {
		Optional<User> usernameAndPassword = userRepo.findByUsernameAndPassword(username, password);
		if(usernameAndPassword.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(usernameAndPassword.get(), HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<Iterable<User>> getUsers() {
		Iterable<User> users = userRepo.findAll();
		return new ResponseEntity<Iterable<User>>(users, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<User> getUser(@PathVariable int id) {
		Optional<User> user = userRepo.findById(id);
		if(user.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user.get(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<User> postUser(@RequestBody User user) {
		User newUser = userRepo.save(user);
		return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity putUser(@PathVariable int id, @RequestBody User user) {
		if(user.getId() != id) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		userRepo.save(user);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity deleteUser(@PathVariable int id) {
		Optional<User> userToDelete = userRepo.findById(id);
		if(userToDelete.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		userRepo.delete(userToDelete.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}
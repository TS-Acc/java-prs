package com.maxtrain.bootcamp.prs.request;

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
@RequestMapping("api/requests")
public class RequestController {

	@Autowired
	private RequestRepository requestRepo;
	
	@GetMapping("reviews/{userId}")
	public ResponseEntity<Iterable<Request>> getRequestsInReview(@PathVariable int userId) {
		Iterable<Request> requestsInReview = requestRepo.findByStatusAndUserIdNot("REVIEW", userId);
		return new ResponseEntity<Iterable<Request>>(requestsInReview, HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<Iterable<Request>> getRequests() {
		Iterable<Request> requests = requestRepo.findAll();
		return new ResponseEntity<Iterable<Request>>(requests, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Request> getRequest(@PathVariable int id) {
		Optional<Request> request = requestRepo.findById(id);
		if(request.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Request>(request.get(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Request> postRequest(@RequestBody Request request) {
		Request newRequest = requestRepo.save(request);
		return new ResponseEntity<Request>(newRequest, HttpStatus.CREATED);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("review/{id}")
	public ResponseEntity reviewRequest(@PathVariable int id) {
		Optional<Request> requestToReview = requestRepo.findById(id);
		if(requestToReview.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		if(requestToReview.get().getTotal() <= 50) {
			requestToReview.get().setStatus("APPROVED");
		}
		else {
			requestToReview.get().setStatus("REVIEW");
		}
		putRequest(requestToReview.get().getId(), requestToReview.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("approve/{id}")
	public ResponseEntity approveRequest(@PathVariable int id) {
		Optional<Request> requestToApprove = requestRepo.findById(id);
		if(requestToApprove.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		requestToApprove.get().setStatus("APPROVED");
		putRequest(requestToApprove.get().getId(), requestToApprove.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("reject/{id}")
	public ResponseEntity rejectRequest(@PathVariable int id) {
		Optional<Request> requestToReject = requestRepo.findById(id);
		if(requestToReject.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		requestToReject.get().setStatus("REJECTED");
		putRequest(requestToReject.get().getId(), requestToReject.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity putRequest(@PathVariable int id, @RequestBody Request request) {
		if(request.getId() != id) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		requestRepo.save(request);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity deleteRequest(@PathVariable int id) {
		Optional<Request> requestToDelete = requestRepo.findById(id);
		if(requestToDelete.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		requestRepo.delete(requestToDelete.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}

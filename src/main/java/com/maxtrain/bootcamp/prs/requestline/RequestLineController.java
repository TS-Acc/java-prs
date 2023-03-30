package com.maxtrain.bootcamp.prs.requestline;

import com.maxtrain.bootcamp.prs.product.Product;
import com.maxtrain.bootcamp.prs.product.ProductRepository;
import com.maxtrain.bootcamp.prs.request.Request;
import com.maxtrain.bootcamp.prs.request.RequestRepository;
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
@RequestMapping("api/requestlines")
public class RequestLineController {

	@Autowired
	private RequestLineRepository requestLineRepo;
	
	@Autowired
	private RequestRepository requestRepo;
	
	@Autowired
	private ProductRepository productRepo;
	
	private boolean recalculateRequestTotal(int requestId) {
		Optional<Request> requestToRecalc = requestRepo.findById(requestId);
		if(requestToRecalc.isEmpty()) {
			return false;
		}
		Iterable<RequestLine> reqLinesByReqId = requestLineRepo.findByRequestId(requestToRecalc.get().getId());
		double total = 0;
		for(RequestLine requestLine : reqLinesByReqId) {
			if(requestLine.getProduct().getName() == null) {
				Product product = productRepo.findById(requestLine.getProduct().getId()).get();
				requestLine.setProduct(product);
			}
			double addToTotal = requestLine.getProduct().getPrice() * requestLine.getQuantity();
			total += addToTotal;
		}
		requestToRecalc.get().setTotal(total);
		requestRepo.save(requestToRecalc.get());
		return true;
		
	}
	
	@GetMapping
	public ResponseEntity<Iterable<RequestLine>> getRequestLines() {
		Iterable<RequestLine> requestLines = requestLineRepo.findAll();
		return new ResponseEntity<Iterable<RequestLine>>(requestLines, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<RequestLine> getRequestLine(@PathVariable int id) {
		Optional<RequestLine> requestLine = requestLineRepo.findById(id);
		if(requestLine.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<RequestLine>(requestLine.get(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<RequestLine> postRequestLine(@RequestBody RequestLine requestLine) {
		RequestLine newRequestLine = requestLineRepo.save(requestLine);
		recalculateRequestTotal(requestLine.getRequest().getId());
		return new ResponseEntity<RequestLine>(newRequestLine, HttpStatus.CREATED);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity putRequestLine(@PathVariable int id, @RequestBody RequestLine requestLine) {
		if(requestLine.getId() != id) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		requestLineRepo.save(requestLine);
		recalculateRequestTotal(requestLine.getRequest().getId());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity deleteRequestLine(@PathVariable int id) {
		Optional<RequestLine> requestLineToDelete = requestLineRepo.findById(id);
		if(requestLineToDelete.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		requestLineRepo.delete(requestLineToDelete.get());
		recalculateRequestTotal(requestLineToDelete.get().getRequest().getId());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}

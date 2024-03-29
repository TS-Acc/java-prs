package com.maxtrain.bootcamp.prs.product;

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
@RequestMapping("api/products")
public class ProductController {

	@Autowired
	private ProductRepository productRepo;
	
	@GetMapping
	public ResponseEntity<Iterable<Product>> getProducts() {
		Iterable<Product> products = productRepo.findAll();
		return new ResponseEntity<Iterable<Product>>(products, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Product> getProduct(@PathVariable int id) {
		Optional<Product> product = productRepo.findById(id);
		if(product.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Product>(product.get(), HttpStatus.OK);
	}
	
	@GetMapping("partnbr/{partNbr}")
	public ResponseEntity<Product> getProductByPartNbr(@PathVariable String partNbr) {
		Optional<Product> productByPartNbr = productRepo.findByPartNbr(partNbr);
		if(productByPartNbr.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Product>(productByPartNbr.get(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Product> postProduct(@RequestBody Product product) {
		Product newProduct = productRepo.save(product);
		return new ResponseEntity<Product>(newProduct, HttpStatus.CREATED);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity putProduct(@PathVariable int id, @RequestBody Product product) {
		if(product.getId() != id) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		productRepo.save(product);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity deleteProduct(@PathVariable int id) {
		Optional<Product> productToDelete = productRepo.findById(id);
		if(productToDelete.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		productRepo.delete(productToDelete.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}

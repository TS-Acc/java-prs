package com.maxtrain.bootcamp.prs.vendor;

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
@RequestMapping("/api/vendors")
public class VendorController {

	@Autowired
	private VendorRepository vendorRepo;
	
	@GetMapping
	public ResponseEntity<Iterable<Vendor>> getVendors() {
		Iterable<Vendor> vendors = vendorRepo.findAll();
		return new ResponseEntity<Iterable<Vendor>>(vendors, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Vendor> getVendor(@PathVariable int id) {
		Optional<Vendor> vendor = vendorRepo.findById(id);
		if(vendor.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Vendor>(vendor.get(), HttpStatus.OK);
	}
	
	@GetMapping("code/{code}")
	public ResponseEntity<Vendor> getVendorByCode(@PathVariable String code) {
		Optional<Vendor> vendorByCode = vendorRepo.findByCode(code);
		if(vendorByCode.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Vendor>(vendorByCode.get(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Vendor> postVendor(@RequestBody Vendor vendor) {
		Vendor newVendor = vendorRepo.save(vendor);
		return new ResponseEntity<Vendor>(newVendor, HttpStatus.CREATED);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity putVendor(@PathVariable int id, @RequestBody Vendor vendor) {
		if(vendor.getId() != id) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		vendorRepo.save(vendor);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity deleteVendor(@PathVariable int id) {
		Optional<Vendor> vendorToDelete = vendorRepo.findById(id);
		if(vendorToDelete.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		vendorRepo.delete(vendorToDelete.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}

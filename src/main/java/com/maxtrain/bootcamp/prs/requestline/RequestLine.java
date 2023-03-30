package com.maxtrain.bootcamp.prs.requestline;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.maxtrain.bootcamp.prs.product.Product;
import com.maxtrain.bootcamp.prs.request.Request;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="RequestLines")
public class RequestLine {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@JsonBackReference
	@ManyToOne(optional=false)
	@JoinColumn(name="requestId", columnDefinition="INT")
	private Request request;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="productId", columnDefinition="INT")
	private Product product;
	
	@Column(columnDefinition="INT NOT NULL DEFAULT 1")
	private int quantity;
	
	public RequestLine() {}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Request getRequest() {
		return request;
	}
	public void setRequest(Request request) {
		this.request = request;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}	
	
}

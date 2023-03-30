package com.maxtrain.bootcamp.prs.request;

import com.maxtrain.bootcamp.prs.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import jakarta.persistence.OneToMany;
import com.maxtrain.bootcamp.prs.requestline.RequestLine;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.List;

@Entity
@Table(name="Requests")
public class Request {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(length=80, nullable=false)
	private String description;
	
	@Column(length=80, nullable=false)
	private String justification;
	
	@Column(length=80, nullable=true)
	private String rejectionReason;
	
	@Column(columnDefinition="VARCHAR(20) NOT NULL DEFAULT 'Pickup'")
	private String deliveryMode;
	
	@Column(columnDefinition="VARCHAR(10) NOT NULL DEFAULT 'NEW'")
	private String status;
	
	@Column(columnDefinition="DECIMAL(11,2) NOT NULL DEFAULT 0")
	private double total;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="vendorId", columnDefinition="INT")
	private User user;
	
	@JsonManagedReference
	@OneToMany(mappedBy="request")
	private List<RequestLine> requestLines;
	
	public Request() {}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getJustification() {
		return justification;
	}
	public void setJustification(String justification) {
		this.justification = justification;
	}
	public String getRejectionReason() {
		return rejectionReason;
	}
	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}
	public String getDeliveryMode() {
		return deliveryMode;
	}
	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public List<RequestLine> getRequestLines() {
		return requestLines;
	}
	
	public void setRequestLines(List<RequestLine> requestLines) {
		this.requestLines = requestLines;
	}
	
}

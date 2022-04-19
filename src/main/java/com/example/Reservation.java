package com.example;

import java.time.LocalDate;

public class Reservation {

private Integer id;
	
	private Integer planId;
	
	private LocalDate checkinDate;
	
	private Integer stayDays;
	
	private Integer numOfGuest;
	
	private Integer payMethod;
	
	private Integer totalPrice;
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPlanId() {
		return planId;
	}

	public void setPlanId(Integer planId) {
		this.planId = planId;
	}

	public LocalDate getCheckinDate() {
		return checkinDate;
	}

	public void setCheckinDate(LocalDate checkinDate) {
		this.checkinDate = checkinDate;
	}

	public Integer getStayDays() {
		return stayDays;
	}

	public void setStayDays(Integer stayDays) {
		this.stayDays = stayDays;
	}

	public Integer getNumOfGuest() {
		return numOfGuest;
	}

	public void setNumOfGuest(Integer numOfGuest) {
		this.numOfGuest = numOfGuest;
	}

	public Integer getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(Integer payMethod) {
		this.payMethod = payMethod;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Override
	public String toString() {
		return "Reservation [id=" + id + ", planId=" + planId + ", checkinDate=" + checkinDate + ", stayDays="
				+ stayDays + ", numOfGuest=" + numOfGuest + ", payMethod=" + payMethod + ", totalPrice=" + totalPrice
				+ "]";
	}
}

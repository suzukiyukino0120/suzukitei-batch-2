package com.example;

import java.time.LocalDate;

public class Sales {

	private LocalDate date;

	private Integer totalSales;
	
	private Integer numOfReservations;
	
	private Integer average;
	
	private Integer salesOfCash;
	
	private Integer salesOfCredit;
	
	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Integer getTotalSales() {
		return totalSales;
	}

	public void setTotalSales(Integer totalSales) {
		this.totalSales = totalSales;
	}

	public Integer getNumOfReservations() {
		return numOfReservations;
	}

	public void setNumOfReservations(Integer numOfReservations) {
		this.numOfReservations = numOfReservations;
	}

	public Integer getAverage() {
		return average;
	}

	public void setAverage(Integer average) {
		this.average = average;
	}

	public Integer getSalesOfCash() {
		return salesOfCash;
	}

	public void setSalesOfCash(Integer salesOfCash) {
		this.salesOfCash = salesOfCash;
	}

	public Integer getSalesOfCredit() {
		return salesOfCredit;
	}

	public void setSalesOfCredit(Integer salesOfCredit) {
		this.salesOfCredit = salesOfCredit;
	}

	@Override
	public String toString() {
		return "Sales [date=" + date + ", totalSales=" + totalSales + ", numOfReservations=" + numOfReservations
				+ ", average=" + average + ", salesOfCash=" + salesOfCash + ", salesOfCredit=" + salesOfCredit + "]";
	}
}

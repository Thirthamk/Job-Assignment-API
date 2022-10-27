package com.nology.demo.job;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import  com.nology.demo.temp.temp;

public class JobsDTO {
	@NotNull
	private String name;
	
	@NotNull
	private LocalDate startDate;
	@NotNull
	private LocalDate endDate;
	public Long tId;
	
	public Long getTempId() {
		return tId;
	}
	public void setTempId(Long tempId) {
		this.tId = tempId;
	}
	public temp getTemp() {
		return temp;
	}
	public void setTemp(temp temp) {
		this.temp = temp;
	}
	public temp temp;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	
	

}

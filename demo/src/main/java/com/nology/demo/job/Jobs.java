package com.nology.demo.job;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import  com.nology.demo.temp.temp;

@Entity
public class Jobs {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private LocalDate startDate;
	private LocalDate endDate;
	private Long tId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "temp_id")
	@JsonBackReference
	private temp temp;
	
	public Jobs(String name, LocalDate startDate, LocalDate endDate) {
		this.setName(name);
		this.setStartDate(startDate);
		this.setEndDate(endDate);
	}
	public Jobs() {
		
	}
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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

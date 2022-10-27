package com.nology.demo.temp;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.nology.demo.job.Jobs;

public class tempDTO {
	@NotNull
	private String firstName;
	@NotNull
	private String lastName;
	
	public List<Jobs> jobArr;
	
	public List<Jobs> getJobArr() {
		return jobArr;
	}
	public void setJobArr(List<Jobs> jobArr) {
		this.jobArr = jobArr;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	

}

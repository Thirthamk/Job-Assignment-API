package com.nology.demo.job;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/Jobs")
public class JobController {
	@Autowired
	private JobService JobS;
	
	@GetMapping
	public List<Jobs> getJobs(@RequestParam(required = false) Boolean bool){
		System.out.println(bool);
		if (bool == null) return JobS.all();
		return JobS.getAllJobsTF(bool);
	
	}
	
	@PostMapping
	public ResponseEntity<Jobs> saveJobs(@Valid @RequestBody JobsDTO Jobs) {
		if(Jobs.getStartDate().isAfter(Jobs.getEndDate())) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		Jobs Job = JobS.create(Jobs);
		return new ResponseEntity<>(Job, HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Optional<Jobs>> getJobs(@PathVariable  Long id){
		Optional<Jobs> Jobs = JobS.getJobs(id);
		if (Jobs.isEmpty()){
			return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(Jobs,HttpStatus.OK);
		}
	@PatchMapping("/{jobId}")
	public ResponseEntity<Jobs> JobsPatch(@PathVariable Long jobId,@RequestBody JobsDTO JobData){
		Jobs Jobs = JobS.jobUpdate(jobId, JobData);
		if(Jobs == null) {
			return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(Jobs,HttpStatus.OK);
		
	}
	
	@DeleteMapping("/{id}")
	public void jobDel(@PathVariable Long id) {
		JobS.jobDel(id);
		}
	
	
}

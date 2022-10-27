package com.nology.demo.temp;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/temp")
public class tempController {
	@Autowired
	private tempService tempS;
	
	@GetMapping
	public ResponseEntity<List<temp>> getAllTemps(@RequestParam(required = false) Long jobId){
		if (jobId == null) {
			List<temp> temp = tempS.all();
			if (temp == null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			return new ResponseEntity<>(temp, HttpStatus.OK);
		}
		else {
			List<temp> temp = tempS.getAllAvailableTemps(jobId);
			if (temp == null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			return new ResponseEntity<>(temp, HttpStatus.OK);
		}
	}
	
	
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public temp savetemp(@Valid @RequestBody tempDTO temp) {
		return tempS.create(temp);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Optional<temp>> getTemp(@PathVariable  Long id){
		Optional<temp> temp = tempS.getTemp(id);
		if (temp.isEmpty()){
			return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(temp,HttpStatus.OK);
		}
	
	@PatchMapping("/{tempId}")
	public ResponseEntity<temp> tempPatch(@PathVariable Long tempId,@RequestBody tempDTO tempData){
		temp temp = tempS.tempUpdate(tempId, tempData);
		if(temp == null) {
			return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(temp,HttpStatus.OK);
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteTemp(@PathVariable Long id) {
		String temp = tempS.deleteTemp(id);
		if (temp == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
	}
	
	
	
	
}

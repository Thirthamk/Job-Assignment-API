package com.nology.demo.job;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nology.demo.temp.temp;
import com.nology.demo.temp.tempRepository;

@Service
@Transactional
public class JobService {
	@Autowired
	private JobRepository Jrepository;
	
	@Autowired
	private tempRepository Trepository;
	
	public List<Jobs> all(){
		return Jrepository.findAll();	
	}
	public List<Jobs> getAllJobsTF(Boolean bool) {
		List<Jobs> all = all();
		List<Jobs> filtered = new ArrayList<>();
		for (Jobs job: all) {
			if( bool == true) {
				if (job.getTemp() != null) {
					filtered.add(job);
				}
			}
			if(bool == false) {
				if (job.getTemp() == null) {
					filtered.add(job);
				}
			}
		}
		return filtered;
	}
	public Jobs create(JobsDTO Jobs) {
		Jobs j = new Jobs(Jobs.getName(),Jobs.getEndDate(), Jobs.getEndDate());
		return Jrepository.save(j);
	}
	
	public Optional<Jobs> getJobs(Long id){
		return Jrepository.findById(id);
	}

	public Jobs jobUpdate(Long id, JobsDTO jobData) {
		Optional<Jobs> Jobs = getJobs(id);
		
		if (Jobs.isEmpty())return null;
		Jobs existentJobs = Jrepository.findById(id).get();
		if (jobData.getName()!= null &&  !jobData.getName().equals("")) {
			existentJobs.setName(jobData.getName());
		}
		if (jobData.getStartDate() != null) {
			existentJobs.setStartDate(jobData.getStartDate());
		}
		if (jobData.getEndDate() != null) {
			existentJobs.setEndDate(jobData.getEndDate());
		}
		if(jobData.getTempId() != null) {
			existentJobs.setTempId(jobData.getTempId());
		}

		Long tempId = existentJobs.getTempId();
		Optional<temp> fetchedTemp = Trepository.findById(tempId);
		if(fetchedTemp.isEmpty()) return null;
		
		temp existentTemp = Trepository.findById(tempId).get();
		
		if(existentTemp.getJobArr().size() == 0) {
			existentJobs.setTemp(existentTemp);
			return this.Jrepository.save(existentJobs);
		}
		LocalDate aS,aF,bS,bF;
		aS = existentJobs.getStartDate();
		aF = existentJobs.getEndDate();

		ArrayList<Jobs> jobArr = new ArrayList<>();
		for (Jobs job : existentTemp.getJobArr()) {
			jobArr.add(job);
		}
		Collections.sort(jobArr, new Comparator<Jobs>() {
			public int compare(Jobs a, Jobs b) {
				return a.getStartDate().compareTo(b.getStartDate());
			}
		});
		
			bS = jobArr.get(0).getStartDate();
			bF = jobArr.get(jobArr.size() - 1).getEndDate();
			
			if (aF.isBefore(bS)) {
				existentJobs.setTemp(existentTemp);
				return this.Jrepository.save(existentJobs);
			}
			if (bF.isBefore(aS)) {
				existentJobs.setTemp(existentTemp);
				return this.Jrepository.save(existentJobs);
			}
			
		for (int i = 0; i < jobArr.size() - 1; i++) {
		
			bF = jobArr.get(i).getEndDate();
			bS = jobArr.get(i + 1).getStartDate();
			if (bF.isBefore(aS) && aF.isBefore(bS)) {
				existentJobs.setTemp(existentTemp);
				return this.Jrepository.save(existentJobs);
			}
		}
		return Jrepository.save(existentJobs);
	}
	
	public void jobDel(Long id) {
		Jrepository.deleteById(id);
	}
	

}

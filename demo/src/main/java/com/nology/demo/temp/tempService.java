package com.nology.demo.temp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nology.demo.job.JobRepository;
import com.nology.demo.job.Jobs;

@Service
@Transactional
public class tempService {
	@Autowired
	private tempRepository Trepository;
	
	@Autowired
	private JobRepository Jrepository;
	
	public List<temp> all(){
		return Trepository.findAll();	
	}
    public List<temp> getAllAvailableTemps (Long jobId) {
		
		
		
		Optional<Jobs> fetchedJob = Jrepository.findById(jobId);
		
		if(fetchedJob.isEmpty()) return null;
		
		Jobs existentJob = Jrepository.findById(jobId).get();
		
		ArrayList<temp> tempList = new ArrayList<>();
		
		for (temp temp: all()) {
			

			if(temp.getJobArr().size() == 0) {
				tempList.add(temp);
				continue;
			}
			LocalDate aS,aF,bS,bF;
			aS = existentJob.getStartDate();
			aF = existentJob.getEndDate();
			

			ArrayList<Jobs> jobArr = new ArrayList<>();
			for (Jobs job : temp.getJobArr()) {
				jobArr.add(job);
				continue;
			}
			Collections.sort(jobArr, new Comparator<Jobs>() {
				public int compare(Jobs a, Jobs b) {
					return a.getStartDate().compareTo(b.getStartDate());
				}
			});
			

			bS = jobArr.get(0).getStartDate();
			bF = jobArr.get(jobArr.size() - 1).getEndDate();
			if (aF.isBefore(bS)) {
				tempList.add(temp);
				continue;
			}
			if (bF.isBefore(aS)) {
				tempList.add(temp);
				continue;
			}
			
			for (int i = 0; i < jobArr.size() - 1; i++) {
				bF = jobArr.get(i).getEndDate();
				bS = jobArr.get(i + 1).getStartDate();
				if (bF.isBefore(aS) && aF.isBefore(bS)) {
				tempList.add(temp);
				continue;
				}
			}
		}
		return tempList;
	}
	
	
	public temp create(tempDTO tempData) {
		temp t = new temp(tempData.getFirstName(), tempData.getLastName());
		return Trepository.save(t);
	}
	
	public Optional<temp> getTemp(Long id){
		return Trepository.findById(id);
	}
	
	public temp tempUpdate(Long tempId, tempDTO tempData) {
		Optional<temp> temp = getTemp(tempId);
		
		if (temp.isEmpty())return null;
		temp existentTemp = Trepository.findById(tempId).get();
		if (tempData.getFirstName()!= null &&  !tempData.getFirstName().equals("")) {
			existentTemp.setFirstName(tempData.getFirstName());
		}
		if (tempData.getLastName()!= null &&  !tempData.getLastName().equals("")) {
			existentTemp.setLastName(tempData.getLastName());
		}
		return Trepository.save(existentTemp);
	}
	
	public String deleteTemp (Long id) {
		Optional<temp> fetchedTemp = getTemp(id);
		if(fetchedTemp.isEmpty()) return null;
		Trepository.deleteById(id);
		return "";
	}
}

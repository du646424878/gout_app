package org.uestc.gout.repos;

import java.io.Serializable;

import org.springframework.data.repository.CrudRepository;
import org.uestc.gout.model.PatientDetail;

public interface PatientDetailRepo extends CrudRepository<PatientDetail, Serializable> {
	
	PatientDetail findByPatientid(int patientId);
	
}

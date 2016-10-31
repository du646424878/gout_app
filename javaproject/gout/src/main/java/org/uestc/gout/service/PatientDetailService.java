package org.uestc.gout.service;

import org.uestc.gout.model.PatientDetail;
import org.uestc.gout.util.SERVICE_RESULT;

public interface PatientDetailService {

	PatientDetail findPatientDetailByPatientId(int id);

	SERVICE_RESULT updatePatientDetail(PatientDetail pd);

}

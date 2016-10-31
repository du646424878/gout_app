package org.uestc.gout.service.impl;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uestc.gout.dao.PatientDetailMapper;
import org.uestc.gout.model.Page;
import org.uestc.gout.model.PatientDetail;
import org.uestc.gout.repos.PatientDetailRepo;
import org.uestc.gout.repos.UserRepo;
import org.uestc.gout.service.PatientDetailService;
import org.uestc.gout.util.SERVICE_RESULT;

@Service
public class PatientDetaliServiceImpl implements PatientDetailService {
	private static Logger logger = Logger.getLogger(PatientDetaliServiceImpl.class.getName());
	@Autowired
	UserRepo userRepo;
	@Autowired
	PatientDetailRepo patientDetailRepo;

	@Autowired
	PatientDetailMapper patientDetailMapper;

	@Override
	public PatientDetail findPatientDetailByPatientId(int id) {
		return patientDetailRepo.findByPatientid(id);
	}
	
	@Override
	public SERVICE_RESULT updatePatientDetail(PatientDetail pd) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 分页获取患者详情表
	 * <p>
	 * 
	 * 
	 * @return
	 */
	public List<Map> getpatientsdetailbypage(Page pageParam) {
		int start = pageParam.getStart() >= 0 ? pageParam.getStart() : 0;
		int end = pageParam.getEnd() > start ? pageParam.getEnd() : start + 10;
		String ordercol = pageParam.getOrdercol() != null ? pageParam.getOrdercol() : "userid";
		String order = pageParam.getOrder() != null ? pageParam.getOrder() : "desc";
		String keyword = pageParam.getKeyword() != null ? "%" + pageParam.getKeyword() + "%" : "%%";
		logger.info(String.format("Invoked with param: %s %s %s %s %s", start, end, ordercol, order, keyword));
		return patientDetailMapper.getpatientsdetail(start, end, ordercol, order, keyword);
	}

	public Integer getpatientdetailcount(String keyword) {
		Integer result = null;
		try {
			result = patientDetailMapper.getpatientcount(keyword);
		} catch (Exception e) {
			e.printStackTrace();
			result = -1;
		}
		return result;
	}
}

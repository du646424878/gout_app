package org.uestc.gout.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.uestc.gout.model.PatientDetail;

@Mapper
public interface PatientDetailMapper {

	/**
	 * 关联查询患者详情表
	 * 
	 * @param start
	 *            开始index
	 * @param end
	 *            结束index
	 * @param ordercol
	 *            排序列名
	 * @param order
	 *            排序种类 desc 或 asc
	 * @param keyword
	 *            检索关键字
	 * @return
	 */
	@Select("SELECT u1.userid, u1.usertypeid, u1.username, patientdetail.realname ,u2.username as 'doctorname', gender, height, weight, age, nation, nativeplace, job, phonenumber, email, firstvisitdate, isrelativegout FROM user as u1 inner join patientdetail on u1.userid = patientdetail.patientid left join user as u2 on u2.userid = patientdetail.docterid "
			+ "where u1.usertypeid = 10  and patientdetail.realname like #{keyword} order by #{ordercol} ${order} limit #{start} , #{end}")
	List<Map> getpatientsdetail(@Param("start") int start, @Param("end") int end, @Param("ordercol") String ordercol,
			@Param("order") String order, @Param("keyword") String keyword);

	/**
	 * 根据关键字检索患者(详情关联表)条数
	 * 
	 * @param keyword
	 *            关键字
	 * @return
	 */
	@Select("select u1.userid, u1.usertypeid, u1.username, patientdetail.realname ,u2.username as 'doctorname', gender, height, weight, age, nation, nativeplace, job, phonenumber, email, firstvisitdate, isrelativegout FROM user as u1 inner join patientdetail on u1.userid = patientdetail.patientid left join user as u2 on u2.userid = patientdetail.docterid "
			+ "where u1.usertypeid = 10  and patientdetail.realname like #{keyword}")
	Integer getpatientcount(@Param("keyword") String keyword);
}
package org.uestc.gout.dao;

import org.uestc.gout.model.Monthyassay;

public interface MonthyassayMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table monthyassay
     *
     * @mbggenerated Thu Apr 21 14:35:38 CST 2016
     */
    int deleteByPrimaryKey(Integer assayid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table monthyassay
     *
     * @mbggenerated Thu Apr 21 14:35:38 CST 2016
     */
    int insert(Monthyassay record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table monthyassay
     *
     * @mbggenerated Thu Apr 21 14:35:38 CST 2016
     */
    int insertSelective(Monthyassay record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table monthyassay
     *
     * @mbggenerated Thu Apr 21 14:35:38 CST 2016
     */
    Monthyassay selectByPrimaryKey(Integer assayid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table monthyassay
     *
     * @mbggenerated Thu Apr 21 14:35:38 CST 2016
     */
    int updateByPrimaryKeySelective(Monthyassay record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table monthyassay
     *
     * @mbggenerated Thu Apr 21 14:35:38 CST 2016
     */
    int updateByPrimaryKey(Monthyassay record);
}
package org.uestc.gout.dao;

import org.uestc.gout.model.Messgae;

public interface MessgaeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table message
     *
     * @mbggenerated Thu Apr 21 14:35:38 CST 2016
     */
    int deleteByPrimaryKey(Integer msgid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table message
     *
     * @mbggenerated Thu Apr 21 14:35:38 CST 2016
     */
    int insert(Messgae record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table message
     *
     * @mbggenerated Thu Apr 21 14:35:38 CST 2016
     */
    int insertSelective(Messgae record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table message
     *
     * @mbggenerated Thu Apr 21 14:35:38 CST 2016
     */
    Messgae selectByPrimaryKey(Integer msgid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table message
     *
     * @mbggenerated Thu Apr 21 14:35:38 CST 2016
     */
    int updateByPrimaryKeySelective(Messgae record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table message
     *
     * @mbggenerated Thu Apr 21 14:35:38 CST 2016
     */
    int updateByPrimaryKey(Messgae record);
}
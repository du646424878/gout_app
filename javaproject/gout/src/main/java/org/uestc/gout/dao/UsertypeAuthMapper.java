package org.uestc.gout.dao;

import org.uestc.gout.model.UsertypeAuth;

public interface UsertypeAuthMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usertype_auth
     *
     * @mbggenerated Thu Apr 21 14:35:39 CST 2016
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usertype_auth
     *
     * @mbggenerated Thu Apr 21 14:35:39 CST 2016
     */
    int insert(UsertypeAuth record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usertype_auth
     *
     * @mbggenerated Thu Apr 21 14:35:39 CST 2016
     */
    int insertSelective(UsertypeAuth record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usertype_auth
     *
     * @mbggenerated Thu Apr 21 14:35:39 CST 2016
     */
    UsertypeAuth selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usertype_auth
     *
     * @mbggenerated Thu Apr 21 14:35:39 CST 2016
     */
    int updateByPrimaryKeySelective(UsertypeAuth record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usertype_auth
     *
     * @mbggenerated Thu Apr 21 14:35:39 CST 2016
     */
    int updateByPrimaryKey(UsertypeAuth record);
}
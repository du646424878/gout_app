package org.uestc.gout.model;

import java.math.BigDecimal;
import java.util.Date;

public class PatientDetail {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column patientdetail.id
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column patientdetail.patientid
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    private Integer patientid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column patientdetail.docterid
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    private Integer docterid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column patientdetail.realname
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    private String realname;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column patientdetail.gender
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    private Byte gender;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column patientdetail.idcardnumber
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    private String idcardnumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column patientdetail.height
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    private BigDecimal height;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column patientdetail.weight
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    private BigDecimal weight;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column patientdetail.age
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    private Byte age;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column patientdetail.nation
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    private String nation;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column patientdetail.nativeplace
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    private String nativeplace;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column patientdetail.job
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    private String job;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column patientdetail.phonenumber
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    private String phonenumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column patientdetail.email
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    private String email;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column patientdetail.firstvisitdate
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    private Date firstvisitdate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column patientdetail.isrelativegout
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    private Byte isrelativegout;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column patientdetail.id
     *
     * @return the value of patientdetail.id
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column patientdetail.id
     *
     * @param id the value for patientdetail.id
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column patientdetail.patientid
     *
     * @return the value of patientdetail.patientid
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    public Integer getPatientid() {
        return patientid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column patientdetail.patientid
     *
     * @param patientid the value for patientdetail.patientid
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    public void setPatientid(Integer patientid) {
        this.patientid = patientid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column patientdetail.docterid
     *
     * @return the value of patientdetail.docterid
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    public Integer getDocterid() {
        return docterid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column patientdetail.docterid
     *
     * @param docterid the value for patientdetail.docterid
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    public void setDocterid(Integer docterid) {
        this.docterid = docterid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column patientdetail.realname
     *
     * @return the value of patientdetail.realname
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    public String getRealname() {
        return realname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column patientdetail.realname
     *
     * @param realname the value for patientdetail.realname
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column patientdetail.gender
     *
     * @return the value of patientdetail.gender
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    public Byte getGender() {
        return gender;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column patientdetail.gender
     *
     * @param gender the value for patientdetail.gender
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    public void setGender(Byte gender) {
        this.gender = gender;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column patientdetail.idcardnumber
     *
     * @return the value of patientdetail.idcardnumber
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    public String getIdcardnumber() {
        return idcardnumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column patientdetail.idcardnumber
     *
     * @param idcardnumber the value for patientdetail.idcardnumber
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    public void setIdcardnumber(String idcardnumber) {
        this.idcardnumber = idcardnumber == null ? null : idcardnumber.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column patientdetail.height
     *
     * @return the value of patientdetail.height
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    public BigDecimal getHeight() {
        return height;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column patientdetail.height
     *
     * @param height the value for patientdetail.height
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column patientdetail.weight
     *
     * @return the value of patientdetail.weight
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    public BigDecimal getWeight() {
        return weight;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column patientdetail.weight
     *
     * @param weight the value for patientdetail.weight
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column patientdetail.age
     *
     * @return the value of patientdetail.age
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    public Byte getAge() {
        return age;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column patientdetail.age
     *
     * @param age the value for patientdetail.age
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    public void setAge(Byte age) {
        this.age = age;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column patientdetail.nation
     *
     * @return the value of patientdetail.nation
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    public String getNation() {
        return nation;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column patientdetail.nation
     *
     * @param nation the value for patientdetail.nation
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    public void setNation(String nation) {
        this.nation = nation == null ? null : nation.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column patientdetail.nativeplace
     *
     * @return the value of patientdetail.nativeplace
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    public String getNativeplace() {
        return nativeplace;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column patientdetail.nativeplace
     *
     * @param nativeplace the value for patientdetail.nativeplace
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    public void setNativeplace(String nativeplace) {
        this.nativeplace = nativeplace == null ? null : nativeplace.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column patientdetail.job
     *
     * @return the value of patientdetail.job
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    public String getJob() {
        return job;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column patientdetail.job
     *
     * @param job the value for patientdetail.job
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    public void setJob(String job) {
        this.job = job == null ? null : job.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column patientdetail.phonenumber
     *
     * @return the value of patientdetail.phonenumber
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    public String getPhonenumber() {
        return phonenumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column patientdetail.phonenumber
     *
     * @param phonenumber the value for patientdetail.phonenumber
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber == null ? null : phonenumber.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column patientdetail.email
     *
     * @return the value of patientdetail.email
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column patientdetail.email
     *
     * @param email the value for patientdetail.email
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column patientdetail.firstvisitdate
     *
     * @return the value of patientdetail.firstvisitdate
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    public Date getFirstvisitdate() {
        return firstvisitdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column patientdetail.firstvisitdate
     *
     * @param firstvisitdate the value for patientdetail.firstvisitdate
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    public void setFirstvisitdate(Date firstvisitdate) {
        this.firstvisitdate = firstvisitdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column patientdetail.isrelativegout
     *
     * @return the value of patientdetail.isrelativegout
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    public Byte getIsrelativegout() {
        return isrelativegout;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column patientdetail.isrelativegout
     *
     * @param isrelativegout the value for patientdetail.isrelativegout
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    public void setIsrelativegout(Byte isrelativegout) {
        this.isrelativegout = isrelativegout;
    }
}
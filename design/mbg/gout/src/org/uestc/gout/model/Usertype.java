package org.uestc.gout.model;

public class Usertype {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column usertype.id
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column usertype.usertypename
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    private String usertypename;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column usertype.id
     *
     * @return the value of usertype.id
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column usertype.id
     *
     * @param id the value for usertype.id
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column usertype.usertypename
     *
     * @return the value of usertype.usertypename
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    public String getUsertypename() {
        return usertypename;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column usertype.usertypename
     *
     * @param usertypename the value for usertype.usertypename
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    public void setUsertypename(String usertypename) {
        this.usertypename = usertypename == null ? null : usertypename.trim();
    }
}
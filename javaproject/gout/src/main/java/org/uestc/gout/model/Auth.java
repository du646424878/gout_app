package org.uestc.gout.model;

public class Auth {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column auth.id
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column auth.apiname
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    private String apiname;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column auth.url
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    private String url;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column auth.id
     *
     * @return the value of auth.id
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column auth.id
     *
     * @param id the value for auth.id
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column auth.apiname
     *
     * @return the value of auth.apiname
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    public String getApiname() {
        return apiname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column auth.apiname
     *
     * @param apiname the value for auth.apiname
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    public void setApiname(String apiname) {
        this.apiname = apiname == null ? null : apiname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column auth.url
     *
     * @return the value of auth.url
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    public String getUrl() {
        return url;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column auth.url
     *
     * @param url the value for auth.url
     *
     * @mbggenerated Wed May 04 20:10:01 CST 2016
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }
}
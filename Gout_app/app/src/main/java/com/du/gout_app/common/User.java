package com.du.gout_app.common;

import java.util.Date;

public class User {

    private Integer userid;

    private Integer usertypeid;

    private String username;

    private String token;

    private Date registerdate;

    private String password;

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
    
    public Integer getUsertypeid() {
        return usertypeid;
    }


    public void setUsertypeid(Integer usertypeid) {
        this.usertypeid = usertypeid;
    }


    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }


    public String getToken() {
        return token;
    }


    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }


    public Date getRegisterdate() {
        return registerdate;
    }

 
    public void setRegisterdate(Date registerdate) {
        this.registerdate = registerdate;
    }

  
    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }
}
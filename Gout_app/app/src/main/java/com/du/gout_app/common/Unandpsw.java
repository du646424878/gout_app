package com.du.gout_app.common;


public class Unandpsw {
public String username;
public String password;
  public Unandpsw(String un,String psw){
	  this.username=un;
	  this.password=psw;
	  
  }
 public String getusername(){
	 return username;
 }
 public String getpassword(){
	 return password;
 }
 public void setusername(String un){
	 this.username =un;
 }
 public void setpassword(String psw){
	 this.password =psw;
 }
}

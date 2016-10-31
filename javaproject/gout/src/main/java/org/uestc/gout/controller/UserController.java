package org.uestc.gout.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.annotations.ListIndexBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.uestc.gout.model.Page;
import org.uestc.gout.model.PatientDetail;
import org.uestc.gout.model.ServerMessage;
import org.uestc.gout.model.User;
import org.uestc.gout.service.impl.PatientDetaliServiceImpl;
import org.uestc.gout.service.impl.UserServiceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;

@Controller
@RequestMapping(value = "/admin/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserController {
	@Autowired
	UserServiceImpl userService;
	@Autowired
	PatientDetaliServiceImpl patientDetailService;

	/**
	 * 返回当前用户信息
	 * 
	 * @param request
	 *            自动注入
	 * @param response
	 *            自动注入
	 * @param session
	 *            自动注入
	 * @return
	 */
	@RequestMapping("/currentuser")
	@ResponseBody
	public String currnetsuer(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		ServerMessage<User> m = new ServerMessage<>();
		m.setMessage((User) session.getAttribute("user"));
		m.setSuccess(true);
		m.setType("object");
		return JSON.toJSONString(m, SerializerFeature.BrowserCompatible);
	}

	@RequestMapping("/getpatientsdetailbypage")
	@ResponseBody
	public String getpatientsdetailbypage(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		ServerMessage<List<Map>> m = new ServerMessage<>();
		Page pageParam = JSON.parseObject(request.getParameter("jsondata"), Page.class);
		m.setMessage(patientDetailService.getpatientsdetailbypage(pageParam));
		m.setSuccess(true);
		m.setType("object list");
		return JSON.toJSONString(m, SerializerFeature.BrowserCompatible);
	}
	
	/*不知道怎么实现检测无权限
	  传过来的参数是jsondata还是直接在request中的username*/
	@RequestMapping("/getuser")
	@ResponseBody
	public String getuser(HttpServletRequest request, HttpServletResponse response,
			HttpSession session){
		ServerMessage<String> m=new ServerMessage<>();
		User param = JSON.parseObject(request.getParameter("jsondata"), User.class);
		User user=null;
		user=userService.findUserByName(param.getUsername());
		if(user!=null){//返回查找到的用户信息
			m.setMessage(JSON.toJSONString(user,SerializerFeature.BrowserCompatible));
			m.setType("object");
			m.setSuccess(true);
		}else {//查找不到用户返回错误信息
			m.setMessage("no such user");
			m.setType("err message");
			m.setSuccess(false);
		}
		return JSON.toJSONString(m,SerializerFeature.BrowserCompatible);
	}
	
	@RequestMapping("/allusers")
	@ResponseBody
	public String allusers(HttpServletRequest request, HttpServletResponse response,
			HttpSession session){
		ServerMessage<List<User>> mes= new ServerMessage<>();
		List<User> list=userService.findAllUser();
		mes.setMessage(list);
		mes.setSuccess(true);
		mes.setType("obejctlist");
		return JSON.toJSONString(mes,SerializerFeature.BrowserCompatible);
	}
	
	@RequestMapping("/getpatientdetail")
	@ResponseBody
	public String getpatientdetail(HttpServletRequest request, HttpServletResponse response,
			HttpSession session){
		ServerMessage<String> m=new ServerMessage<>();
		JSONObject json= JSON.parseObject(request.getParameter("jsondata"));
		int patientid=json.getInteger("patientid");
		PatientDetail mes=null;
		mes=patientDetailService.findPatientDetailByPatientId(patientid);
		if(mes!=null){
			m.setMessage(JSON.toJSONString(mes,SerializerFeature.BrowserCompatible));
			m.setType("object");
			m.setSuccess(true);
		}else {
			m.setMessage("no such patient");
			m.setType("err message");
			m.setSuccess(false);
		}
		return JSON.toJSONString(m,SerializerFeature.BrowserCompatible);
	}
	
	//移动端  参数不知道从哪里来  session么？和getpatientdetail有什么区别呢
	@RequestMapping("/getcurrentpatientdetail")
	@ResponseBody
	public String getcurrentpatientdetail(HttpServletRequest request, HttpServletResponse response,
			HttpSession session){
		
		return null;
	}
}

package org.uestc.gout.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.uestc.gout.model.ServerMessage;
import org.uestc.gout.model.User;
import org.uestc.gout.service.impl.UserServiceImpl;
import org.uestc.gout.util.SERVICE_RESULT;
import org.uestc.gout.util.Util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

@Controller
public class RootController {

	@Autowired
	private UserServiceImpl userService;
	

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public String login(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		ServerMessage<String> resultmessage = new ServerMessage<String>();
		User param = JSON.parseObject(request.getParameter("jsondata"), User.class);
		SERVICE_RESULT service_result = userService.validateUsernameAndUnencrptPassword(param);
		// 如果成功了 就更新token 并将该值返回
		if (service_result == SERVICE_RESULT.SUCCESS) {
			String token = userService.updateUserToken(param.getUsername());
			// 更新session信息
			if (session != null) {
				session.setAttribute("user", userService.findUserByName(param.getUsername()));
				session.setAttribute("islogin", true);
				session.setAttribute("isinit", true);
			}
			resultmessage.setMessage(token);
			resultmessage.setSuccess(true);
			resultmessage.setType("token");
		}
		// 如果失败了 返回失败的原因
		else {
			resultmessage.setType("err message");
			resultmessage.setMessage(Util.beautyServiceResultStr(service_result));
			resultmessage.setSuccess(false);
		}
		return JSON.toJSONString(resultmessage, SerializerFeature.BrowserCompatible);
	}

	@RequestMapping("/signin")
	@ResponseBody
	public String signin(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		ServerMessage<Object> resultmessage = new ServerMessage<>();
		User param = Util.fromRequestReadJsondataObject(request, User.class);
		SERVICE_RESULT service_result = userService.createNewPatient(param);
		if (service_result == SERVICE_RESULT.SUCCESS) {
			resultmessage.setMessage(userService.findUserByName(param.getUsername()));
			resultmessage.setSuccess(true);
			resultmessage.setType("object");
		} else {
			resultmessage.setType("err message");
			resultmessage.setMessage(Util.beautyServiceResultStr(service_result));
			resultmessage.setSuccess(false);
		}
		return JSON.toJSONString(resultmessage, SerializerFeature.BrowserCompatible);
	}
	
	@RequestMapping(value = "/hasuser",method = RequestMethod.POST)
	@ResponseBody
	public String hasuser(HttpServletRequest request, HttpServletResponse response){
		ServerMessage<String> m=new ServerMessage<>();
		User user=null;
		User param=JSON.parseObject(request.getParameter("jsondata"),User.class);
		user=userService.findUserByName(param.getUsername());
		if(user==null){
			m.setMessage("false");
			m.setType("boolean hasuser");
			m.setSuccess(true);
		}else{
			m.setMessage("true");
			m.setType("boolean hasuser");
			m.setSuccess(true);
		}
		return JSON.toJSONString(m, SerializerFeature.BrowserCompatible);
	}
}

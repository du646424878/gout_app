package org.uestc.gout.filters;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.uestc.gout.model.ServerMessage;
import org.uestc.gout.model.User;
import org.uestc.gout.service.impl.UserServiceImpl;

import com.alibaba.fastjson.JSON;

/**
 * 权限过滤器
 * <p>
 * 实现对于/amdin/* 路径下的拦截<br>
 * 实现token验证<br>
 * 实现session初始化<br>
 * 由于这些功能之后会被Spring Security框架代替<br>
 * 就不写FilterChain 简单实现以下
 * 
 * @author suntao
 *
 */
@Component
public class AuthFilter implements Filter {
	@Autowired
	private UserServiceImpl userService;

	public void setUserService(UserServiceImpl userService) {
		this.userService = userService;
	}

	@Bean
	public FilterRegistrationBean TokenFilterRegistration(UserServiceImpl userService) {
		FilterRegistrationBean bean = new FilterRegistrationBean();
		AuthFilter filter = new AuthFilter();
		filter.setUserService(userService);
		bean.setFilter(filter);
		bean.addUrlPatterns("/*");
		return bean;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		HttpServletResponse res = (HttpServletResponse) response;
		// 初始化session
		if (session.getAttribute("isinit") == null) {
			session.setAttribute("islogin", false);
			session.setAttribute("isinit", true);
			session.setAttribute("user", null);
		}
		// 读取token进行登录
		String token = req.getParameter("token");
		if (token != null) {
			User user = userService.findUserByToken(token);
			System.out.println(token);
			if (userService == null)
				System.err.println("userService is null");
			if (user != null) {
				session.setAttribute("islogin", true);
				session.setAttribute("isinit", true);
				session.setAttribute("user", user);
			}
		}
		// 根据路径进行拦截
		if (req.getRequestURI().startsWith("/admin")) {
			Boolean islogin = (Boolean) session.getAttribute("islogin");
			if (islogin != null && islogin == true) {

			} else {
				PrintWriter pr = res.getWriter();
				pr.write(JSON.toJSONString(new ServerMessage<String>("no auth", false, "err message")));
				pr.flush();
				pr.close();
				return;
			}
		}
		chain.doFilter(request, response);

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}

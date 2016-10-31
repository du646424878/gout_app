package org.uestc.gout.service;

import java.util.List;

import org.uestc.gout.model.User;
import org.uestc.gout.util.SERVICE_RESULT;

public interface UserService {

	SERVICE_RESULT createNewUser(User u);

	SERVICE_RESULT validateUsernameAndUnencrptPassword(User param);

	List<User> getUserListByType(int usertype);

	User getUserByUsername(String username);
	
	List<User> findAllUser();
	/**
	 * 更新一个用户的密码
	 * <p>
	 * 验证旧密码<br>
	 * 传入的都是明文<br>
	 * 用户的token需要被重置
	 * 
	 * @param username
	 * @param oldpassword
	 * @param newpassword
	 * @return
	 */
	SERVICE_RESULT updatePassword(String username, String oldpassword, String newpassword);

}

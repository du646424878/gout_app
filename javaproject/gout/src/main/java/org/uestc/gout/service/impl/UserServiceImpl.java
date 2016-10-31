package org.uestc.gout.service.impl;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uestc.gout.dao.UserMapper;
import org.uestc.gout.model.PatientDetail;
import org.uestc.gout.model.User;
import org.uestc.gout.repos.UserRepo;
import org.uestc.gout.service.UserService;
import org.uestc.gout.util.SERVICE_RESULT;
import org.uestc.gout.util.Util;

@Service
public class UserServiceImpl implements UserService {
	private static Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserRepo userRepo;

	public Boolean login(String username, String password) {
		return userMapper.login(username, password);
	}

	public User findUserByName(String username) {
		return userRepo.findByUsername(username);
	}
	
	public List<User> findAllUser() {
		return userRepo.findAll();
	}
	
	/**
	 * 由token查找用户
	 * <p>
	 * 如果token为空或者为null<br>
	 * 返回null
	 * 
	 * @param token
	 * @return
	 */
	public User findUserByToken(String token) {
		if (token == null || token.trim().isEmpty())
			return null;
		else
			return userRepo.findByToken(token);
	}

	public SERVICE_RESULT validateUsernameAndUnencrptPassword(User param) {
		SERVICE_RESULT result = SERVICE_RESULT.UNEXPTED_ERROR;
		try {
			if (param.getUsername() != null && param.getPassword() != null) {
				User dbrecord = userRepo.findByUsername(param.getUsername());
				if (dbrecord != null) {
					if (dbrecord.getPassword().equals(Util.strEncrypt(param.getPassword()))) {
						result = SERVICE_RESULT.SUCCESS;
					} else {
						result = SERVICE_RESULT.PASSWORD_ERROR;
					}
				} else {
					result = SERVICE_RESULT.NO_SUCH_USER;
				}
			} else {
				result = SERVICE_RESULT.PARAM_ERROR;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public String updateUserToken(String username) {
		String result = null;
		User beupdateduser = userRepo.findByUsername(username);
		if (beupdateduser != null) {
			result = Util.generateUUID();
			beupdateduser.setToken(result);
			userRepo.save(beupdateduser);
		}
		return result;
	}

	@Override
	public SERVICE_RESULT createNewUser(User u) {
		SERVICE_RESULT result = SERVICE_RESULT.UNEXPTED_ERROR;
		try {
			if (u.getUsername() != null && u.getPassword() != null) {
				if (findUserByName(u.getUsername()) == null) {
					User created = userRepo.save(u);
					if (created != null)
						result = SERVICE_RESULT.SUCCESS;
				} else {
					result = SERVICE_RESULT.BEING_ADDED_USER_HAS_EXISTED;
				}
			} else {
				result = SERVICE_RESULT.PARAM_ERROR;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public SERVICE_RESULT createNewPatient(User u) {
		u.setUsertypeid(10);// 这里以后需要调整
		return createNewUser(u);
	}

	@Override
	public List<User> getUserListByType(int usertype) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SERVICE_RESULT updatePassword(String username, String oldpassword, String newpassword) {
		// TODO Auto-generated method stub
		return null;
	}

}

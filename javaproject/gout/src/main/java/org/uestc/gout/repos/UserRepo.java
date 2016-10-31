package org.uestc.gout.repos;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uestc.gout.model.PatientDetail;
import org.uestc.gout.model.User;

public interface UserRepo extends JpaRepository<User, Serializable> {
	User findByUsername(String name);

	User findByToken(String token);
	
	List<User> findAll();
}

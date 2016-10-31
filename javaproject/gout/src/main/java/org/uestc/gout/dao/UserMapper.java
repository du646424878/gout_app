package org.uestc.gout.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.uestc.gout.model.User;

@Mapper
public interface UserMapper {

	int deleteByPrimaryKey(Integer userid);

	@Insert("insert user()")
	int insert(User record);

	int insertSelective(User record);

	@Select("select * from user where userid = #{userid}")
	User selectByPrimaryKey(Integer userid);

	int updateByPrimaryKeySelective(User record);



	int updateByPrimaryKey(User record);

	@Select("select * from user where username = #{username} and password = MD5(#{password})")
	Boolean login(@Param("username") String username, @Param("password") String password);

	@Select("select * from user where username = #{username}")
	Boolean selectByName(@Param("username") String username);

	@Update(" update user set token=#{token} where username=#{username}")
	Boolean changeTokenByName(@Param("username") String username, @Param("token") String token);
}
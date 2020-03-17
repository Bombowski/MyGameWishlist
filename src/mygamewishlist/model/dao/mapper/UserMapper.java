package mygamewishlist.model.dao.mapper;

import org.apache.ibatis.annotations.Param;

import mygamewishlist.model.pojo.User;

public interface UserMapper {

	public User getUserByEmail(@Param("email") String email);
	
	public void addUser(User usr);
}

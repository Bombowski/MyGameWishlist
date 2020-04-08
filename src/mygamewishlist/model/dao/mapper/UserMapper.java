package mygamewishlist.model.dao.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import mygamewishlist.model.pojo.db.User;

public interface UserMapper {

	public User getUserByEmail(@Param("email") String email);
	
	public void addUser(User usr);
	
	public ArrayList<User> getUsersWithList();
}

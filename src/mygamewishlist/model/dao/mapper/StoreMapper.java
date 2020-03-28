package mygamewishlist.model.dao.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import mygamewishlist.model.pojo.db.Store;

public interface StoreMapper {

	public ArrayList<Store> getStores();
	
	public ArrayList<String> getStoreNames();
	
	public Store getStoreByName(@Param("name") String name);
}

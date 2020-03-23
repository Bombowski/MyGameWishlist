package mygamewishlist.model.dao.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import mygamewishlist.model.pojo.db.Store;

public interface ShopMapper {

	public ArrayList<Store> getStores();
	
	public String getStoreUrlById(@Param("id") int id);
}

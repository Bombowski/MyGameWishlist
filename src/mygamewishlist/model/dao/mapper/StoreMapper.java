package mygamewishlist.model.dao.mapper;

import java.util.ArrayList;

import mygamewishlist.model.pojo.db.Store;

public interface StoreMapper {

	public ArrayList<Store> getStores();
	
	public ArrayList<String> getStoreName();
}

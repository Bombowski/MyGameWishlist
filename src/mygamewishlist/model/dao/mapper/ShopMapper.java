package mygamewishlist.model.dao.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import mygamewishlist.model.pojo.db.Shop;

public interface ShopMapper {

	public ArrayList<Shop> getShops();
	
	public String getShopUrlById(@Param("id") int id);
}

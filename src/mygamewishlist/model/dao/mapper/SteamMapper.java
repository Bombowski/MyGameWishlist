package mygamewishlist.model.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import mygamewishlist.model.pojo.SteamGame;

public interface SteamMapper {

	public Integer getGameIdsByName(@Param("name") String name);
	
	public void addGame(List<SteamGame> list);
}

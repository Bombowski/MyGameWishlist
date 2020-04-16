package mygamewishlist.model.dao.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import mygamewishlist.model.pojo.ScrapedGame;
import mygamewishlist.model.pojo.db.TimelineGame;
import mygamewishlist.model.pojo.db.TimelineGameDetailed;

public interface TimelineMapper {

	public void add2Timeline(@Param("sg") ScrapedGame sg, @Param("time") String time);
	
	public ArrayList<TimelineGame> getTimelineByUrl(@Param("url") String url);
	
	public ArrayList<TimelineGameDetailed> getTimelineByUrlDetailed(@Param("url") String url);
}

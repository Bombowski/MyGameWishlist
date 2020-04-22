package mygamewishlist.model.dao.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import mygamewishlist.model.pojo.db.Review;
import mygamewishlist.model.pojo.db.ReviewList;

public interface ReviewMapper {

	public ArrayList<ReviewList> getReviewList(@Param("idUser") int idUser);
	
	public ArrayList<ReviewList> getReviewListNotLogged();
	
	public void addOrUpdateReview(Review rev);
	
	public void deleteReview(@Param("idUser") int idUser, @Param("idGame") int idGame);
}

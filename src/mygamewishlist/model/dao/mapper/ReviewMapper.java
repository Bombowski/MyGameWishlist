package mygamewishlist.model.dao.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import mygamewishlist.model.pojo.ReviewList;
import mygamewishlist.model.pojo.db.Review;

public interface ReviewMapper {

	public ArrayList<ReviewList> getReviewList(@Param("idUser") int idUser);
	
	public ArrayList<ReviewList> getReviewListNotLogged();
	
	public void updateReview(Review rev);
	
	public void deleteReview(@Param("idUser") int idUser, @Param("idGame") int idGame);
}

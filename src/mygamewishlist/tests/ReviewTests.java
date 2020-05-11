package mygamewishlist.tests;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import mygamewishlist.model.pojo.db.Review;
import mygamewishlist.model.pojo.db.ReviewList;

public class ReviewTests {

	private static Review rev;
	
	@BeforeClass
	public static void init() {
		rev = new Review();
		rev.setIdGame(1);
		rev.setIdUser(1);
		rev.setRating(7);
		rev.setReview("awesome review");
	}
	
	@Test
	public void getReviewList() {
		assertEquals(true, Tests.cq_ejb.getReviewList().size() > 0);
	}

	@Test
	public void getReviewListNotLogged() {
		assertEquals(true, Tests.cq_ejb.getReviewList().size() > 0);
	}

	@Test
	public void addOrUpdateReview() {
		Tests.cq_ejb.addOrUpdateReview(rev);
		for (ReviewList rl : Tests.cq_ejb.getReviewList()) {
			if (rl.getIdGame() == rev.getIdGame() && rl.getUserRating() == rev.getRating()) {
				assertEquals(true, true);
				return;
			}				
		}
	}

	@Test
	public void deleteReview() {
		Tests.cq_ejb.deleteReview(rev.getIdUser(), rev.getIdGame());
		for (ReviewList rl : Tests.cq_ejb.getReviewList()) {
			if (rl.getIdGame() == rev.getIdGame() && rl.getUserRating() == rev.getRating()) {
				assertEquals(true, false);
				return;
			}				
		}
	}	
}

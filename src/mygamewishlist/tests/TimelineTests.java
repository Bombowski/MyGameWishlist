package mygamewishlist.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TimelineTests {

	@Test
	public void getTimelineByUrl() {
		assertEquals(true, Tests.cq_ejb.getTimelineByUrl("/app/20920").size() > 1);
	}

	@Test
	public void getTimelineByUrlDetailed() {
		assertEquals(true, Tests.cq_ejb.getTimelineByUrlDetailed("/app/20920").size() > 1);		
	}
}

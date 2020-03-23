package mygamewishlist.model.ejb.scraping;

import java.io.IOException;
import java.util.ArrayList;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import mygamewishlist.model.pojo.MyLogger;
import mygamewishlist.model.pojo.ScrapedGame;

@Stateless
@LocalBean
public class ScrapingEJB {
	
	private MyLogger LOG = MyLogger.getLOG();

	public String replaceSpaces(String str) {
		return str.replace(" ", "+");
	}
	
	public String[] splitSpacesReplaceCommasEuros(String price) {
		String prices[] = price.split(" ");

		for (int j = 0; j < prices.length; j++) {						
			prices[j] = prices[j].replace(",", ".");
			prices[j] = prices[j].replace("€", "");
		}
		
		return prices;
	}
	
	public ArrayList<ScrapedGame> getSteamGames(String name, String storeUrl) throws IOException {
		Document doc = Jsoup.connect(new StringBuilder()
					.append(storeUrl)
					.append("search/?term=")
					.append(name)
					.toString()
				)
				.get();
		
		Elements ele = doc.select(".search_result_row");
		ArrayList<ScrapedGame> games = new ArrayList<ScrapedGame>();
		
		for (int i = 0; i < ele.size(); i++) {
			String img = ele.get(i).select("img").attr("src");
			String url = ele.get(i).attr("href");
			String fullName = ele.get(i).select(".title").text();
			double defaultP;
			double currentP;
			double discount = 0;
			
			String price = ele.get(i).select(".search_price").text();
			
			if (price.equals("")) {
				continue;
			} else if (price.equals("Free To Play") || price.equals("Free")) {
				currentP = defaultP = 0;
			} else if(price.equals("Free Demo")) {
				currentP = defaultP = -1;
			} else {
				String[] prices = splitSpacesReplaceCommasEuros(price);
				
				if (prices.length == 2) {
					defaultP = Double.parseDouble(prices[0]);
					currentP = Double.parseDouble(prices[1]);
					discount = Double.parseDouble(ele.get(i)
							.select(".search_discount")
							.text()
							.replace("%", ""));
				} else {
					currentP = defaultP = Double.parseDouble(prices[0]);
				}
			}
			
			games.add(new ScrapedGame(url, fullName, img, defaultP, currentP, discount));
		}
		
		return games;
	}
	
	public ArrayList<ScrapedGame> getGameG2A(String name) {
		return new ArrayList<ScrapedGame>();
	}
	
	public ArrayList<ScrapedGame> getGameInstantGaming(String name) {
		return new ArrayList<ScrapedGame>();
	}
}

package mygamewishlist.model.scraping;

import java.io.IOException;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * @author Patryk
 *
 * Class that contains functions used by all of the scraping classes
 */
public class ScrapingFunctions {
	
	/**
	 * Replaces all of the commas by dots, euros by empty string, and
	 * percante by empty string.
	 * 
	 * @param price String
	 * @return String
	 */
	protected static String replaceCommasEurosPercent(String price) {
		return price.replace(",", ".").replace("€", "").replace("%", "");
	}
	
	/**
	 * Concatenates url and name, and then returns the document
	 * 
	 * @param url String
	 * @param name String
	 * @return Document
	 * @throws IOException
	 * 
	 * 2020-05-13 22:00:04 [EjbTimerPool - 13] DEBUG o.a.i.d.pooled.PooledDataSource -
				Returned connection 1820295244 to pool.
2020-05-13 22:00:04 [EjbTimerPool - 13] DEBUG FILE_ERROR -
				WishListGame2Scrap [urlStore=https://www.instant-gaming.com/en, urlGame=/268-buy-key-gogcom-the-witcher-3-wild-hunt, storeName=Instant Gaming, gameName=The Witcher 3: Wild Hunt, img=https://s2.gaming-cdn.com/images/products/268/271x377/the-witcher-3-wild-hunt-cover.jpg, defaultPrice=30.0, currentPrice=11.99, minPrice=-1.0, maxPrice=-1.0]
2020-05-13 22:00:05 [EjbTimerPool - 13] ERROR FILE_ERROR -
				Mark invalid - IOException ScrapingInstantGaming
2020-05-13 22:00:05 [EjbTimerPool - 13] ERROR FILE_ERROR -
				org.jsoup.parser.CharacterReader.rewindToMark(CharacterReader.java:148)
				org.jsoup.parser.Tokeniser.consumeCharacterReference(Tokeniser.java:192)
				org.jsoup.parser.TokeniserState$38.read(TokeniserState.java:759)
				org.jsoup.parser.Tokeniser.read(Tokeniser.java:59)
				org.jsoup.parser.TreeBuilder.runParser(TreeBuilder.java:55)
				org.jsoup.parser.TreeBuilder.parse(TreeBuilder.java:47)
				org.jsoup.parser.Parser.parseInput(Parser.java:35)
				org.jsoup.helper.DataUtil.parseInputStream(DataUtil.java:170)
				org.jsoup.helper.HttpConnection$Response.parse(HttpConnection.java:835)
				org.jsoup.helper.HttpConnection.get(HttpConnection.java:287)
				mygamewishlist.model.scraping.ScrapingFunctions.getDoc(ScrapingFunctions.java:40)
				mygamewishlist.model.scraping.ScrapingInstantGaming.getGame(ScrapingInstantGaming.java:136)
				mygamewishlist.model.ejb.ScrapingEJB.getGame(ScrapingEJB.java:111)
				mygamewishlist.model.ejb.TimersEJB.chkPrices(TimersEJB.java:113)
				java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
				java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
				java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
				java.base/java.lang.reflect.Method.invoke(Method.java:566)
				org.apache.openejb.core.interceptor.ReflectionInvocationContext$Invocation.invoke(ReflectionInvocationContext.java:205)
				org.apache.openejb.core.interceptor.ReflectionInvocationContext.proceed(ReflectionInvocationContext.java:186)
				org.apache.openejb.monitoring.StatsInterceptor.record(StatsInterceptor.java:191)
				org.apache.openejb.monitoring.StatsInterceptor.AroundTimeout(StatsInterceptor.java:155)
				java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
				java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
				java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
				java.base/java.lang.reflect.Method.invoke(Method.java:566)
				org.apache.openejb.core.interceptor.ReflectionInvocationContext$Invocation.invoke(ReflectionInvocationContext.java:205)
				org.apache.openejb.core.interceptor.ReflectionInvocationContext.proceed(ReflectionInvocationContext.java:186)
				org.apache.openejb.core.interceptor.InterceptorStack.invoke(InterceptorStack.java:85)
				org.apache.openejb.core.stateless.StatelessContainer._invoke(StatelessContainer.java:252)
				org.apache.openejb.core.stateless.StatelessContainer.invoke(StatelessContainer.java:212)
				org.apache.openejb.core.timer.EjbTimerServiceImpl.ejbTimeout(EjbTimerServiceImpl.java:800)
				org.apache.openejb.core.timer.EjbTimeoutJob.execute(EjbTimeoutJob.java:39)
				org.apache.openejb.quartz.core.JobRunShell.run(JobRunShell.java:202)
				java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128)
				java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)
				java.base/java.lang.Thread.run(Thread.java:834)
2020-05-13 22:00:05 [EjbTimerPool - 13] DEBUG FILE_ERROR -
				WishListGame2Scrap [urlStore=https://www.instant-gaming.com/en, urlGame=/789-buy-key-gogcom-the-witcher-2-assassins-of-kings-enhanced-edition, storeName=Instant Gaming, gameName=The Witcher 2: Assassins of Kings Enhanced Edition, img=https://s1.gaming-cdn.com/images/products/789/157x218/the-witcher-2-assassins-of-kings-enhanced-edition-cover.jpg, defaultPrice=20.0, currentPrice=2.99, minPrice=-1.0, maxPrice=-1.0]
2020-05-13 22:00:06 [EjbTimerPool - 13] DEBUG FILE_ERROR -
				ScrapedGame [urlStore=null, urlGame=null, fullName=null, img=null, storeName=null, defaultPrice=20.0, currentPrice=2.99, currentDiscount=85.0]
2020-05-13 22:00:06 [EjbTimerPool - 13] DEBUG o.a.i.d.pooled.PooledDataSource -
				Checked out connection 1916081672 from pool.
	 */
	protected static Document getDoc(String url, String name) throws IOException {
		return Jsoup.connect(new StringBuilder()
				.append(url)
				.append(name)
				.toString())
			.execute().bufferUp().parse();
	}
	
	/**
	 * Concatenates url and name, and then adds a coockie with a name of
	 * ckName, and value of ckValue.
	 * 
	 * @param url String
	 * @param name String
	 * @param ckName String
	 * @param ckValue String
	 * @return Document
	 * @throws IOException
	 */
	protected static Document getDocCookie(String url, String name, String ckName, String ckValue) throws IOException {
		return Jsoup.connect(new StringBuilder()
				.append(url)
				.append(name)
				.toString())
			.cookie(ckName, ckValue)	
			.get();
	}
	
	/**
	 * Concatenates url and name, and then adds the map coockies as a
	 * list of coockies.
	 * 
	 * @param url String
	 * @param name String 
	 * @param cookies HashMap<String, String>
	 * @return Document
	 * @throws IOException
	 */
	protected static Document getDocCookie(String url, String name, HashMap<String, String> cookies) throws IOException {
		return Jsoup.connect(new StringBuilder()
				.append(url)
				.append(name)
				.toString())
			.cookies(cookies)
			.get();
	}
	
	/**
	 * if the string provided is null, a 0 is returned, else str
	 * 
	 * @param str String
	 * @return String
	 */
	protected static String ifnull0(String str) {
		return str == null ? "0" : str;
	}
}

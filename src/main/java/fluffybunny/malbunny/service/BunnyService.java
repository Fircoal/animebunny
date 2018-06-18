package fluffybunny.malbunny.service;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fluffybunny.malbunny.dao.BunnyDao;
import fluffybunny.malbunny.entity.Anime;
import fluffybunny.malbunny.entity.Entry;
import fluffybunny.malbunny.entity.Grouping;
import fluffybunny.malbunny.entity.MalBunnyUser;
import fluffybunny.malbunny.entity.Previous;
import fluffybunny.malbunny.entity.PreviousIndex;
import fluffybunny.malbunny.entity.Profile;
import fluffybunny.malbunny.utils.RankComporator;
import fluffybunny.malbunny.utils.UtilityBunny;
import fluffybunny.malbunny.xmlParsing.ParseUser;

/**
 * Hello world!
 *
 */
@Service("BunnyService")
public class BunnyService {

	@Autowired
	@Qualifier("BunnyDao")
	BunnyDao dao;
	
	@Autowired
	@Qualifier("AsyncService")
	AsyncService aser;

	private MalBunnyUser activeUser = null;

	public void getUserDetails(String username) {
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\firco\\Downloads\\Music VSTs + Plugins\\chromedriver.exe");
		// Initialize browser
		WebDriver driver = UtilityBunny.newChrome();

		JavascriptExecutor jse = (JavascriptExecutor) driver;

		// Open Google
		driver.get("https://myanimelist.net/animelist/" + username);

		int uid = Integer.parseInt(driver.findElement(By.xpath("/html/body")).getAttribute("data-owner-id").toString());
		Profile user = new Profile();
		int[] ratingPool = new int[11];
		user.setId(uid);
		user.setName(username);
		int scoreCount = 0;
		String animePath = "//*[@id='list-container']/div[3]/div/table/tbody";
		List<WebElement> animeRows = driver.findElements(By.xpath(animePath));

		for (int i = 280; i < animeRows.size(); i = i + 300) {
			jse.executeScript(
					"window.scrollTo(0,Math.max(document.documentElement.scrollHeight,document.body.scrollHeight,document.documentElement.clientHeight));");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			animeRows = driver.findElements(By.xpath(animePath));
			System.out.println(i + " " + animeRows.size());

		}

		List<Entry> entries = new ArrayList<Entry>();

		for (int i = 2; i < animeRows.size() + 1; i++) {

			// *[@id="list-container"]/div[3]/div/table/tbody[2]/tr[1]/td[1]

			String status = driver.findElement(By.xpath(animePath + "[" + i + "]/tr[1]/td[1]")).getAttribute("class")
					.toString().substring(12);

			if (status.equals("plantowatch")) {
				break;
			}
			int id = Integer.parseInt(driver.findElement(By.xpath(animePath + "[" + i + "]/tr[1]/td[8]"))
					.getAttribute("id").toString().substring(5));
			Entry entry = dao.getEntry(uid, id);
			entry.setUid(uid);
			entry.setStatus(status);
			entry.setName(driver.findElement(By.xpath(animePath + "[" + i + "]/tr[1]/td[4]/a")).getText());
			entry.setHref(driver.findElement(By.xpath(animePath + "[" + i + "]/tr[1]/td[4]/a")).getAttribute("href"));
			String scoreStr = driver.findElement(By.xpath(animePath + "[" + i + "]/tr[1]/td[5]/a")).getText();
			int rating = (scoreStr.equals("-")) ? 0 : Integer.parseInt(scoreStr);
			// scoreCount += (rating + 9)/10;
			ratingPool[rating] = ratingPool[rating] + 1;
			entry.setScore(rating);
			entry.setProgress(
					driver.findElement(By.xpath(animePath + "[" + i + "]/tr[1]/td[7]/div/span[1]")).getText());
			entry.setId(id);
			// *[@id="list-container"]/div[3]/div/table/tbody[2]/tr[1]/td[7]/div/span[1]

			// *[@id="tags-36840"]
			System.out.println(entry);

			System.out.println(entry.getHref());

			entries.add(entry);
			
			
		}

		setUpUserStats(entries, ratingPool, scoreCount, user);
	}
	
	public void setUpUserStats(List<Entry> entries, int[] ratingPool, int scoreCount, Profile user ) {
		List<Anime> aniList = getAnimeInfoAndUpdate(entries);
		

		entries.sort((x, y) -> x.getId() - y.getId());
		Map<Integer, Integer> orderMap = new HashMap<>();
		aniList.sort((x, y) -> x.getRanking() - y.getRanking());
		for (int i = 0; i < entries.size(); i++) {
			orderMap.put(entries.get(i).getId(), i);
		}

		double[] percents = new double[11];
		double sum = 0;
		percents[0] = 0;
		for (int i = 1; i < 11; i++) {
			percents[i] = ratingPool[i] / 2 + sum;
			sum += ratingPool[i];
			scoreCount += ratingPool[i];
			System.out.println(percents[i] + " " + sum + " " + scoreCount);
		}

		double currCount = scoreCount;
		System.out.println(currCount);
		for (int i = 0; i < aniList.size(); i++) {
			System.out.println(aniList.get(i) + " " + orderMap.get(aniList.get(i).getId()));
			Entry ent = entries.get(orderMap.get(aniList.get(i).getId()));
			ent.setMalRank(i + 1);
			ent.setAnime(aniList.get(i));
			if (!user.isIsranked()) {
				ent.setPer(percents[ent.getScore()] / scoreCount);
			}
			if (ent.getScore() > 0) {
				ent.setMalPer((currCount) / scoreCount);
				currCount--;
			}
		}

		performMalStats(user, entries);

		user.setAverage(entries.stream().mapToInt(x -> x.getScore()).filter(x -> x > 0).average().getAsDouble());
		dao.setEntries(entries);
		dao.setProfile(user);

		// Close browser

	}

	private void performMalStats(Profile user, List<Entry> entries) {
		entries.sort((x, y) -> x.getId() - y.getId());
		int size = UtilityBunny.popGuide.length;
		// aniList.stream().mapToInt(i -> i.getPop()).max().getAsInt()/500+1;
		int[][] popTable = new int[13][size];
		int[][] scoreTable = new int[11][100];
		double[] avgByMal = new double[100];
		double[] malByScr = new double[11];
		int[] countMal = new int[100];
		int[] countScr = new int[11];
		for (int i = 0; i < entries.size(); i++) {
			Entry ent = entries.get(i);
			Anime ani = ent.getAnime();
			int score = ent.getScore();
			int scoreRange = (int) (ani.getScore() * 10);
			popTable[score][UtilityBunny.getArrayBucket(UtilityBunny.popGuide, ani.getPop())]++;
			scoreTable[score][scoreRange]++;
			avgByMal[scoreRange] += ent.getScore();
			countMal[scoreRange]++;
			malByScr[score] += ani.getScore();
		}
		for (int i = 0; i < 100; i++) {
			avgByMal[i] = avgByMal[i] / countMal[i];
		}
		for (int i = 0; i < 11; i++) {
			malByScr[i] = malByScr[i] / countScr[i];
		}
		for (int i = 0; i < UtilityBunny.popGuide.length; i++) {
			for (int k = 1; k < 11; k++) {
				popTable[11][i] += k * popTable[k][i];
				popTable[12][i] += popTable[k][i];
			}
			try {
				popTable[11][i] = popTable[11][i] * 100 / popTable[12][i];
			} catch (ArithmeticException e) {
				popTable[11][i] = 0;
			}
		}

		user.setAvgByMal(avgByMal);
		user.setMalByScr(malByScr);
		user.setPopTable(popTable);
		user.setScoreTable(scoreTable);
	}

	public List<Entry> getEntries(int uid) {
		List<Entry> entries = dao.getEntries(uid,false);
		List<Anime> aniList = getAnimeInfo(entries);
		setAnime(entries, aniList);
		return entries;
	}

	public Profile loadDetails(String username) {
		Profile user = dao.getProfile(username);
		if (user == null) {
			return null;
			//getUserDetails(username);
			//user = dao.getProfile(username);
		}
		List<Entry> entries = dao.getEntries(user.getId(),false);
		List<Anime> aniList = getAnimeInfo(entries);
		setAnime(entries, aniList);
		Collections.sort(entries);
		performMalStats(user, entries);
		Map<Integer, Integer> idmap = new HashMap<>();
		for (int i = 0; i < aniList.size(); i++) {
			idmap.put(aniList.get(i).getId(), i);
		}
		user.setAnime(aniList);
		user.setEntries(entries);
		return user;
	}

	public void registerUser(MalBunnyUser user) {
		dao.register(user);
	}

	private void setAnime(List<Entry> entries, List<Anime> aniList) {
		entries.sort((x, y) -> x.getId() - y.getId());
		aniList.sort((x, y) -> x.getId() - y.getId());
		for (int i = 0; i < entries.size(); i++) {
			entries.get(i).setAnime(aniList.get(i));
		}

	}

	private List<Anime> getAnimeInfo(List<Entry> entries) {
		List<Anime> animeList = new ArrayList<>();
		for (Entry entry : entries) {
			Anime ani = dao.getAnimeById(entry.getId());
			animeList.add(ani);
		}
		return animeList;
	}

	public List<Anime> getAnimeInfoAndUpdate(List<Entry> entries) {
		List<Anime> animeList = new ArrayList<>();
		List<Entry> updateAnime = new ArrayList<>();
		for (Entry entry : entries) {
			Anime ani = dao.getAnimeById(entry.getId());
			LocalDate refreshperiod = LocalDate.now();
			refreshperiod = refreshperiod.minusWeeks(2);
			if(ani.getDateSaved() == null || ani.getDateSaved().isBefore(refreshperiod)) {
				updateAnime.add(entry);
			}
			animeList.add(ani);
		}
		//AsyncService aservice = new AsyncService(dao);
		aser.logAnimeInfo(updateAnime);
		return animeList;
	}

	

	public void logPreviousRecord(String data, LocalDate date) {
		HashMap<String, Integer> hash = new HashMap<String, Integer>();
		int malId = activeUser.getMalid();
		Profile prof = dao.getProfile(malId);
		logPreviousRecord(data, hash, malId, prof, date);
	}

	public PreviousIndex logPreviousRecord(String data, HashMap<String, Integer> hash, int malId, Profile prof,
			LocalDate date) {
		PreviousIndex prevdex = new PreviousIndex(data.split("\n"), hash, malId, dao);
		dao.savePrevious(prevdex);
		return prevdex;
	}

	public void combine(String data) {
		HashMap<String, Integer> hash = new HashMap<String, Integer>();
		int malId = activeUser.getMalid();
		Profile prof = dao.getProfile(malId);
		prof.setIsranked(true);
		PreviousIndex prevdex = logPreviousRecord(data, hash, malId, prof, LocalDate.now());
		List<Entry> entries = dao.getEntries(malId,false);
		for (Entry entry : entries) {
			System.out.println(entry);
			Integer num = hash.get(entry.getName());
			if (num != null) {
				Previous prev = prevdex.getPrevList().get(num);
				entry.setRank(prev.getRank());
				entry.setDraft(prev.getRank());
				System.out.println("Setto");
			}
		}
		dao.setProfile(prof);
		dao.setEntries(entries);
	}

	public void setActiveUser(String username) {
		activeUser = dao.loadUser(username);
	}

	public void eraseActiveUser() {
		activeUser = null;
	}

	public Profile loadDetails() {
		Profile user = dao.getProfile(activeUser.getMalid());
		ArrayList<Entry> entries = dao.getEntries(user.getId(),false);;
		List<Anime> aniList = getAnimeInfo(entries);
		// Collections.sort(entries);
		user.setEntries(entries);
		setAnime(entries, aniList);
		performMalStats(user, entries);
		return user;
	}

	public void combine(String data, String username) {
		// combine(data,dao.loadUser(username).getMalid());
	}

	public void saveDraft(List<Integer> order) {
		int malId = activeUser.getMalid();
		List<Entry> entries = dao.getEntries(malId,false);;
		Map<Integer, Integer> indexMap = new HashMap<>();
		for (int i = 0; i < entries.size(); i++) {
			indexMap.put(entries.get(i).getId(), i);
		}
		for (int i = 0; i < order.size(); i++) {
			entries.get(indexMap.get(order.get(i))).setDraft(i + 1);
		}
		dao.setEntries(entries);
	}

	public void snapshot(String username) {
		Profile user = dao.getProfile(username);
		ArrayList<Entry> entries = dao.getEntries(user.getId(),false);;
		user.setEntries(entries);
		PreviousIndex past = dao.getLastRanking(user.getId());
		List<Previous> prevList = past.getPrevList();
		Map<Integer, Integer> prevHash = new HashMap<>();
		for (int i = 0; i < prevList.size(); i++) {
			prevHash.put(prevList.get(i).getId(), i);
		}
		int k = 0;
		entries.sort(new RankComporator());
		for (int i = 0; i < entries.size(); i++) {
			Entry ent = entries.get(i);
			if (prevHash.get(ent.getId()) != null) {
				Previous prev = prevList.get(prevHash.get(ent.getId()));
				ent.setPrevPer(prev.getPer());
				ent.setPrevMalPer(prev.getMalPer());
				ent.setPrevRank(prev.getRank() + k);
				ent.setPrevScore(prev.getScore());
			} else {
				if (ent.getRank() > 0) {
					k++;
				}
				ent.setPrevPer(0);
				ent.setPrevMalPer(0);
				ent.setPrevRank(0);
				ent.setPrevScore(0);
			}
		}
		PreviousIndex prevDex = new PreviousIndex(user);
		dao.savePrevious(prevDex);
	}

	public void xmlCreate(String username) {
		Profile user = dao.getProfile(username);
		ArrayList<Entry> entries = dao.getEntries(user.getId(),false);;
		setAnime(entries, getAnimeInfo(entries));
		try {
			String tab = "\t";
			PrintWriter xmlWriter = new PrintWriter(new File("animelist.xml"));
			xmlWriter.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			xmlWriter.println(
					"<!-- Created by XML Export feature at MyAnimeList.net Programmed by Xinil Last updated 5/27/2008 -->");
			xmlWriter.println("<ns2:myAnimeList xmlns:ns2=\"myanimelist\"");
			xmlWriter.println(tab + "<myinfo>");
			tab = tab + "\t";
			xmlWriter.println(tab + "<user_id>" + user.getId() + "</user_id>");
			xmlWriter.println(tab + "<user_name>" + username + "</user_name>");
			xmlWriter.println(tab + "<user_export_type>1</user_export_type>");
			xmlWriter.println(tab + "<user_total_anime>" + entries.size() + "</user_total_anime>");
			xmlWriter.println(tab + "<user_total_watching>"
					+ entries.stream().filter(i -> i.getStatus().equals("watching")).count()
					+ "</user_total_watching>");
			xmlWriter.println(tab + "<user_total_completed>"
					+ entries.stream().filter(i -> i.getStatus().equals("completed")).count()
					+ "</user_total_completed>");
			xmlWriter.println(tab + "<user_total_onhold>"
					+ entries.stream().filter(i -> i.getStatus().equals("onhold")).count() + "</user_total_onhold>");
			xmlWriter.println(tab + "<user_total_dropped>"
					+ entries.stream().filter(i -> i.getStatus().equals("dropped")).count() + "</user_total_dropped>");
			xmlWriter.println(tab + "<user_total_plantowatch>"
					+ entries.stream().filter(i -> i.getStatus().equals("plantowatch")).count()
					+ "</user_total_plantowatch>");
			xmlWriter.println("\t</myinfo>");
			for (Entry ent : entries) {
				Anime ani = ent.getAnime();
				xmlWriter.println("\t<anime>");
				xmlWriter.println(tab + "<series_animedb_id>" + ent.getId() + "</series_animedb_id>");
				xmlWriter.println(tab + "<series_title>");
				xmlWriter.println(tab + "\t<![CDATA[");
				xmlWriter.println(tab + tab + ent.getName());
				xmlWriter.println(tab + "\t]]>");
				xmlWriter.println(tab + "</series_title>");
				xmlWriter.println(tab + "<series_type>" + ani.getType() + "</series_type>");
				xmlWriter.println(tab + "<series_episodes>" + ani.getEpisodes() + "</series_episodes>");
				xmlWriter.println(tab + "<my_id>0</my_id>");
				xmlWriter.println(tab + "<my_watched_episodes>" + ent.getProgress().split("/")[0].trim()
						+ "</my_watched_episodes>");
				xmlWriter.println(tab + "<my_start_date>0000-00-00</my_start_date>");
				xmlWriter.println(tab + "<my_finish_date>0000-00-00</my_finish_date>");
				xmlWriter.println(tab + "<my_rated/>");
				xmlWriter.println(tab + "<my_score>" + ent.getScore() + "</my_score>");
				xmlWriter.println(tab + "<my_dvd/>");
				xmlWriter.println(tab + "<my_storage/>");
				xmlWriter.println(tab + "<my_status>" + ent.getStatus() + "</my_status>");
				xmlWriter.println(tab + "<my_comments>");
				xmlWriter.println(tab + "\t<![CDATA[");
				xmlWriter.println(tab + "\t]]>");
				xmlWriter.println(tab + "</my_comments>");
				xmlWriter.println(tab + "<my_times_watched>0</my_times_watched>");
				xmlWriter.println(tab + "<my_rewatch_value/>");
				xmlWriter.println(tab + "<my_tags>");
				xmlWriter.println(tab + "\t<![CDATA[");
				xmlWriter.println(tab + "\t]]>");
				xmlWriter.println(tab + "</my_tags>");
				xmlWriter.println(tab + "<my_rewatching>0</my_rewatching>");
				xmlWriter.println(tab + "<my_rewatching_ep>0</my_rewatching_ep>");
				xmlWriter.println(tab + "<update_on_import>0</update_on_import>");
				xmlWriter.println("\t</anime>");
				System.out.println(ent.getName());
			}
			xmlWriter.println("</ns2:myAnimeList>");
			xmlWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveRanking(List<Integer> order) {
		int malId = activeUser.getMalid();
		List<Entry> entries = dao.getEntries(malId,false);;
		List<Anime> aniList = getAnimeInfo(entries);
		setAnime(entries, aniList);
		Map<Integer, Integer> indexMap = new HashMap<>();
		for (int i = 0; i < entries.size(); i++) {
			indexMap.put(entries.get(i).getId(), i);
		}
		int size = order.size();
		for (int i = 0; i < size; i++) {
			Entry ent = entries.get(indexMap.get(order.get(i)));
			ent.setRank(i + 1);
			ent.setDraft(i + 1);
			ent.setPer(((double) size - 0.5 - i) / size);
		}

		entries.sort((x, y) -> (x.getRank() > 0)
				? ((y.getRank() > 0) ? (int) (-x.getAnime().getScore() * 100 + y.getAnime().getScore() * 100) : -1)
				: (y.getRank() > 0) ? 1 : 0);
		int i = 1;
		for (Entry ent : entries) {
			if (ent.getRank() > 0) {
				ent.setMalRank(i);
				ent.setMalPer((double) (size + 0.5 - i++) / size);
			}
		}

		dao.setEntries(entries);

		snapshot(activeUser.getUsername());

	}

	public Profile getFullProfile(String username) {
		Profile user = dao.getProfile(username);
		ArrayList<Entry> entries = dao.getEntries(user.getId(),false);;
		setAnime(entries, getAnimeInfo(entries));
		user.setEntries(entries);
		return user;
	}

	public List<Grouping> setCatergory(String catergory, String username) {
		Profile user = getFullProfile(username);
		List<Grouping> groups = new ArrayList<>();
		Map<String, List<Entry>> cats = null;
		switch(catergory) {
		case "source": cats= user.getEntries().stream().filter(x -> x.getAnime().getSource() != null)
				.collect(Collectors.groupingBy(x -> x.getAnime().getSource()));
				break;
		case "type":
			cats= user.getEntries().stream().filter(x -> x.getAnime().getType() != null)
			.collect(Collectors.groupingBy(x -> x.getAnime().getType()));
			break;
		case "rating":
			cats= user.getEntries().stream().filter(x -> x.getAnime().getRating() != null)
			.collect(Collectors.groupingBy(x -> x.getAnime().getRating()));
			break;
		case "studio":
		case "producers":
		case "genres": 
			cats = getGroupingWithMultiple(user,catergory);
			break;
		case "favorites": 
			cats = getGroupingByRangeInt(user,catergory, 10, 25, 50, 100, 250, 500, 1000, 2500, Integer.MAX_VALUE);
			break;
		case "episodes":
			cats = getGroupingByRangeInt(user,catergory, 1, 3, 8, 16, 30, 57, 80, 110, 160, 250, 500, Integer.MAX_VALUE);
			break;
		case "duration":
			cats = getGroupingByRangeDub(user,catergory, 2, 8, 15, 30, 60, 150, Double.MAX_VALUE);
			break;
		case "pop":
			cats = getGroupingByRangeInt(user,catergory, 50, 100, 200, 500, 1000, 2000, 3000, 4000, 5000, 7500, 10000, Integer.MAX_VALUE);
			break;
		case "score":
			cats = getGroupingByRangeDub(user,catergory, 4, 5, 5.5, 6, 6.5, 7, 7.25, 7.5, 7.75, 8, 8.25, 8.5, 9, Double.MAX_VALUE);
			break;
		}
		
		
		Collection<List<Entry>> ccats = cats.values();
		List<Integer> sizes = new ArrayList<Integer>();
		for (List<Entry> catList : ccats) {
			sizes.add((int) catList.stream().filter(x -> x.getScore() == 0 & x.getRank() == 0).count());
		}
		int countMed = (int) Math.round(sizes.stream().mapToInt(x -> x.intValue()).average().getAsDouble());
		for (String catName : cats.keySet()) {
			groups.add(new Grouping(catName, cats.get(catName), countMed, user.getAverage()));
		}
		groups.removeIf(x -> x.getMean() == 0);
		groups.sort((x,y) -> (int) (y.getWeighted()*1000 - x.getWeighted()*1000));
		return groups;

	}

	private Map<String, List<Entry>> getGroupingByRangeDub(Profile user, String catergory, double... range) {
		List<Entry> entries = user.getEntries();
		List<Double> entDubs = null;
		Map<String, List<Entry>> cats = new HashMap<>();
		switch(catergory) {
			case "duration":
				entDubs = entries.stream().map(x -> x.getAnime().getDuration()).collect(Collectors.toList());
				break;
			case "score":
				entDubs = entries.stream().map(x -> x.getAnime().getScore()).collect(Collectors.toList());
				break;
			default:
				return null;
		}
		for(int i = -1; i < range.length-1; i++) {
			if(i == -1) {
				cats.put("0 - " + range[i+1], new ArrayList<Entry>());
			} else if (i == range.length-2) {
				cats.put(range[i] + "++", new ArrayList<Entry>());
			} else {
				cats.put(range[i] + " - " + range[i+1], new ArrayList<Entry>());
			}
			
		}
		for(int k = 0; k < entries.size(); k++) {
			Entry ent = entries.get(k);
			Double dub = entDubs.get(k);
			for(int i = 0; i < range.length; i++) {
				if(dub < range[i]) {
					if(i == 0) {
						cats.get("0 - " + range[i]).add(ent);
					} else if (i == range.length-1) {
						cats.put(range[i-1] + "++", new ArrayList<Entry>());
					} else {
						cats.get(range[i-1] + " - " + range[i]).add(ent);
					}
					break;
				}
			}
		}
		return cats;
	}
	
	private Map<String, List<Entry>> getGroupingByRangeInt(Profile user, String catergory, int... range) {
		List<Entry> entries = user.getEntries();
		List<Integer> entInts = null;
		Map<String, List<Entry>> cats = new HashMap<>();
		switch(catergory) {
			case "favorites": 
				entInts = entries.stream().map(x -> x.getAnime().getFavorites()).collect(Collectors.toList());
				break;
			case "episodes":
				entInts = entries.stream().map(x -> x.getAnime().getEpisodes()).collect(Collectors.toList());
				break;
			case "pop":
				entInts = entries.stream().map(x -> x.getAnime().getPop()).collect(Collectors.toList());
				break;
			default:
				return null;
		}
		for(int i = -1; i < range.length-1; i++) {
			if(i == -1) {
				cats.put("0 - " + range[i+1], new ArrayList<Entry>());
			} else if (i == range.length-2) {
				cats.put(range[i] + "++", new ArrayList<Entry>());
			} else {
				cats.put(range[i] + " - " + range[i+1], new ArrayList<Entry>());
			}
			
		}
		for(int k = 0; k < entries.size(); k++) {
			Entry ent = entries.get(k);
			Integer dub = entInts.get(k);
			for(int i = 0; i < range.length; i++) {
				if(dub < range[i]) {
					if(i == 0) {
						cats.get("0 - " + range[i]).add(ent);
					} else if (i == range.length-1) {
						cats.put(range[i-1] + "++", new ArrayList<Entry>());
					} else {
						cats.get(range[i-1] + " - " + range[i]).add(ent);
					}
					break;
				}
			}
		}
		return cats;
	}

	private Map<String, List<Entry>> getGroupingWithMultiple(Profile user, String catergory) {
		List<Entry> entries = user.getEntries();
		Map<String, List<Entry>> cats = new HashMap<>();
		for(Entry ent : entries) {
			List<String> strs = null;
			switch (catergory) {
			case "studio":
				strs = ent.getAnime().getStudio();
				break;
			case "producers":
				strs = ent.getAnime().getProducers();
				break;
			case "genres": 
				strs = ent.getAnime().getGenres();
				break;
			}
			if(strs != null) {
				for(int i = 0; i < strs.size(); i++) {
					if(cats.get(strs.get(i)) == null) {
						List<Entry> ents = new ArrayList<Entry>();
						ents.add(ent);
						cats.put(strs.get(i), ents);
					} else {
						List<Entry> ents = cats.get(strs.get(i));
						ents.add(ent);
					}
				}
			}
		}
		return cats;
	}

	public String parseUserXml(String data) {
		Profile user = ParseUser.parseAndConvert(data, dao);
		// Initialize browser
		int[] ratingPool = new int[11];
		List<Entry> entries = user.getEntries();
		
		int count = (int) entries.stream().filter(e -> e.getScore() > 0).count();
		for(Entry ent : entries) {
			int rating = ent.getScore();
			ratingPool[rating] = ratingPool[rating] + 1;
		}
		setUpUserStats(entries, ratingPool, count, user);
		return user.getUsername();
	}

	public String prevReset() {
		int malId = activeUser.getMalid();
		Profile user = dao.getProfile(malId);
		PreviousIndex past = dao.getLastRanking(user.getId());
		List<Previous> prevList = past.getPrevList();
		List<Entry> entries = dao.getEntries(malId,false);;
		Map<Integer,Integer> orderMap = new HashMap<>();
		for (int i = 0; i < entries.size(); i++) {
			orderMap.put(entries.get(i).getId(), i);
		}
		for(Previous prev : prevList) {
			Entry ent = entries.get(orderMap.get(prev.getId()));
			ent.setMalPer(prev.getMalPer());
			ent.setMalRank(prev.getMalRank());
			ent.setPer(prev.getPer());
			ent.setRank(prev.getRank());
			ent.setProgress(prev.getProgress());
			ent.setScore(prev.getScore());
			ent.setStatus(prev.getStatus());
			ent.setPrevMalPer(prev.getChaMalPer() + ent.getMalPer());
			ent.setPrevRank((int)prev.getChaNum() + ent.getRank());
			ent.setPrevPer(prev.getChaPer() + ent.getPer());
		}
		dao.setEntries(entries);
		dao.setProfile(user);
		return user.getName();
	}

}
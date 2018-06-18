package fluffybunny.malbunny.xmlParsing;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import fluffybunny.malbunny.dao.BunnyDao;
import fluffybunny.malbunny.entity.Entry;
import fluffybunny.malbunny.entity.MalBunnyUser;
import fluffybunny.malbunny.entity.Profile;



public class ParseUser {
	
	public static Profile parseAndConvert(String data, BunnyDao dao) { 
		JAXBContext xmlparser;
		try {
			data = data.replace("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r\n" + 
					"		<!--\r\n" + 
					"		 Created by XML Export feature at MyAnimeList.net\r\n" + 
					"		 Programmed by Xinil\r\n" + 
					"		 Last updated 5/27/2008\r\n" + 
					"		-->\r\n" + 
					"", "").trim();
			xmlparser = JAXBContext.newInstance(MyAnimeList.class);
			//xmlparser = JAXBContext.newInstance("mal");
			Unmarshaller um = xmlparser.createUnmarshaller();
			MyAnimeList mal = (MyAnimeList) um.unmarshal(new StringReader(data));
			return convert(mal, dao);	
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Profile convert(MyAnimeList mal, BunnyDao dao) {
		Profile user = new Profile();
		MyInfo info = mal.getMyinfo();
		int userId = Integer.parseInt(info.getUser_id());
		user.setId(userId);
		user.setName(info.getUser_name());
		user.setWatching(Integer.parseInt(info.getUser_total_watching()));
		user.setCompleted(Integer.parseInt(info.getUser_total_completed()));
		user.setOnHold(Integer.parseInt(info.getUser_total_onhold()));
		user.setDropped(Integer.parseInt(info.getUser_total_dropped()));
		user.setPtw(Integer.parseInt(info.getUser_total_plantowatch()));
		user.setId(Integer.parseInt(info.getUser_id()));
		List<Anime> animus = mal.getAnime();
		user.setEntries(new ArrayList<Entry>());
		for(Anime animu : animus) {
			System.out.println(animu);
			Entry ent = dao.getEntry(userId, animu.getSeries_animedb_id());
			ent.setName(animu.getSeries_title());
			ent.setProgress(animu.getMy_watched_episodes());
			ent.setScore(animu.getMy_score());
			ent.setStatus(animu.getMy_status());
			ent.setHref("https://myanimelist.net/anime/"+ent.getId());
			user.getEntries().add(ent);
		}
		
		return user;
	}

}

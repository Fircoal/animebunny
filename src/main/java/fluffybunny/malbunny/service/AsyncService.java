package fluffybunny.malbunny.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.google.common.primitives.Doubles;

import fluffybunny.malbunny.dao.BunnyDao;
import fluffybunny.malbunny.entity.Anime;
import fluffybunny.malbunny.entity.Entry;
import fluffybunny.malbunny.utils.UtilityBunny;

@Component("AsyncService")
public class AsyncService {
	BunnyDao dao;
	public static Deque<Entry> toAddQueue = new ConcurrentLinkedDeque<>();
	
	
	public AsyncService(BunnyDao dao) {
		this.dao = dao;
	}
	
	
	@Async
	public void logAnimeInfo(List<Entry> updateAnime) {
		for (Entry entry : updateAnime) {
			if(!toAddQueue.stream().anyMatch(e -> e.getId() == entry.getId())) {
				toAddQueue.offer(entry);
			}
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		updateAnimeInfo();
	}
	
	


	private void updateAnimeInfo() {
		WebDriver driver = UtilityBunny.newChrome();
		while(toAddQueue.size() > 0) {
			Entry ent = toAddQueue.poll();
			try {
				getAnimeInfo(driver,ent.getHref(),ent.getId());
			} catch (Exception e) {
				e.printStackTrace();
				toAddQueue.offer(ent);
			}
		}
		driver.close();
	}


	public void getAnimeInfo(WebDriver driver, String link, int id) throws Exception{

		driver.get(link);

		By namePath = By.xpath("/html/body/div[1]/div[3]/div[3]/div[1]/h1/span");
		By scorePath = By.xpath(
				"//*[@id=\'content\']/table/tbody/tr/td[2]/div[1]/table/tbody/tr[1]/td/div[1]/div[1]/div[1]/div[1]/div[1]");
		By rankingPath = By.xpath(
				"//*[@id='content']/table/tbody/tr/td[2]/div[1]/table/tbody/tr[1]/td/div[1]/div[1]/div[1]/div[1]/div[2]/span[1]/strong");
		By popPath = By.xpath(
				"//*[@id='content']/table/tbody/tr/td[2]/div[1]/table/tbody/tr[1]/td/div[1]/div[1]/div[1]/div[1]/div[2]/span[2]/strong");
		By durPath = By.xpath("//*[@id=\'content\']/table/tbody/tr/td[1]/div/div[19]");
		By picPath = By.xpath("/html/body/div[1]/div[3]/div[3]/div[2]/table/tbody/tr/td[1]/div/div[1]/a/img");
		By seasonPath = By.xpath(
				"/html/body/div[1]/div[3]/div[3]/div[2]/table/tbody/tr/td[2]/div[1]/table/tbody/tr[1]/td/div[1]/div[1]/div[1]/div[1]/div[3]/span[1]/a");
		By typePath = By.xpath(
				"/html/body/div[1]/div[3]/div[3]/div[2]/table/tbody/tr/td[2]/div[1]/table/tbody/tr[1]/td/div[1]/div[1]/div[1]/div[1]/div[3]/span[2]/a");
		By studioPath = By.xpath(
				"/html/body/div[1]/div[3]/div[3]/div[2]/table/tbody/tr/td[2]/div[1]/table/tbody/tr[1]/td/div[1]/div[1]/div[1]/div[1]/div[3]/span[3]/a");
		By infoHeader = By.xpath("/html/body/div[1]/div[3]/div[3]/div[2]/table/tbody/tr/td[1]/div/div/span");
		By infoPaths = By.xpath("/html/body/div[1]/div[3]/div[3]/div[2]/table/tbody/tr/td[1]/div/div");

		System.out.println(driver.findElement(scorePath).getText() + " " + driver.findElement(rankingPath).getText()
				+ " " + driver.findElement(popPath).getText());

		Anime anime = new Anime();
		anime.setId(id);
		anime.setHref("https://myanimelist.net/anime/" + id);
		anime.setName(driver.findElement(namePath).getText());
		String scoreText = driver.findElement(scorePath).getText();
		String rankText = driver.findElement(rankingPath).getText().substring(1);
		anime.setScore((scoreText.equals("N/A")) ? 0 : Double.parseDouble(scoreText));
		anime.setRanking((rankText.equals("/A")) ? 0 : Integer.parseInt(rankText));
		anime.setPop(Integer.parseInt(driver.findElement(popPath).getText().substring(1)));

		List<WebElement> infoHeaders = driver.findElements(infoHeader);
		List<WebElement> info = driver.findElements(infoPaths);

		for (int i = 0; i < info.size(); i++) {
			String str[] = info.get(i).getText().split(":");
			if(str.length < 2) {
				continue;
			}
			String header = str[0].trim();
			String detail = str[1].trim();
			System.out.println(info.get(i).getText() + " " + header + " " + detail);
			switch (header) {
			case "Episodes":
				if(detail.equals("Unknown")) {
					anime.setEpisodes(0);
				} else {
					anime.setEpisodes(Integer.parseInt(detail));
				}
				break;
			case "Status":
				anime.setStatus(detail);
				break;
			case "Aired": // anime.setStatus(detail);
				break;
			case "Broadcast": // anime.setStatus(detail);
				break;
			case "Producers":
			case "Licensors":
				String[] producers = detail.split(",");
				anime.getProducers().addAll(Arrays.stream(producers).map(k -> k.trim()).collect(Collectors.toList()));
				break;
			case "Studios":
				anime.setStudio(Arrays.stream(detail.split(",")).map(k -> k.trim()).collect(Collectors.toList()));
				break;
			case "Source":
				anime.setSource(detail);
				break;
			case "Genres":
				anime.setGenres(Arrays.stream(detail.split(",")).map(k -> k.trim()).collect(Collectors.toList()));
				break;
			case "Duration":
				anime.setDuration(detail);
				break;
			case "Rating":
				anime.setRating(detail);
				break;
			case "Favorites":
				anime.setFavorites(Integer.parseInt(detail.replace(",", "")));
				break;
			}
		}
		driver.findElement(
				By.xpath("/html/body/div[1]/div[3]/div[3]/div[2]/table/tbody/tr/td[2]/div[1]/div[1]/ul/li[6]/a"))
				.click();
		//*[@id="content"]/table/tbody/tr/td[2]/div[1]/table/tbody/tr[1]/td[1]
		//String scrperpath = "/html/body/div[1]/div[3]/div[3]/div[2]/table/tbody/tr/td[2]/div[1]/table[1]/tbody/tr[$$$]/td[2]/div/span";
		//String scrpath = "/html/body/div[1]/div[3]/div[3]/div[2]/table/tbody/tr/td[2]/div[1]/table[1]/tbody/tr[$$$]/td[2]/td[1]";
		List<WebElement> tableRows = driver.findElements(By.xpath("/html/body/div[1]/div[3]/div[3]/div[2]/table/tbody/tr/td[2]/div[1]/table[1]/tbody/tr"));
		double[] scrper = new double[11];
		for(int i = 0; i < tableRows.size(); i++) {
			String scrText = tableRows.get(i).findElement(By.xpath(".//td[1]")).getText();
			if(!scrText.equals("")) {
				int scr = Integer.parseInt(scrText);
				scrper[scr] = Double.parseDouble(tableRows.get(i).findElement(By.xpath("//td[2]/div/span")).getText().split("%")[0].trim());
			}
		}
		scrper[0] = 0;
		anime.setScorePers(Doubles.asList(scrper));
		anime.setDateSaved(LocalDate.now());

		System.out.println(anime);

		dao.updateAnime(anime);

		//return anime;
	}

}

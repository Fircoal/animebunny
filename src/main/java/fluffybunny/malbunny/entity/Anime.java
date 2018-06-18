package fluffybunny.malbunny.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name="Anime")
public class Anime implements Serializable{
	
	@Id
	int id;
	String name;
	String href;
	double score;
	int ranking;
	int pop;
	LocalDate startdate;
	LocalDate enddate;
	String season;
	String type;
	@ElementCollection
	List<String> studio;
	int episodes;
	String status;
	String source;
	@ElementCollection
	List<String> genres;
	@ElementCollection
	List<String> producers;
	double duration;
	String rating;
	int favorites;
	String picLink;
	@ElementCollection
	List<Double> scorePers;

	
	private LocalDate dateSaved;
	
	
	
	public List<String> getProducers() {
		if(producers == null) {
			producers = new ArrayList<String>();
		}
		return producers;
	}
	public void setProducers(List<String> producers) {
		this.producers = producers;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getScore() {
		return score;
	}
	public String displayScore() {
		return score + "";
	}
	public void setScore(double score) {
		this.score = score;
	}
	public int getRanking() {
		return ranking;
	}
	public String displayRanking() {
		return ranking + "";
	}
	public void setRanking(int ranking) {
		this.ranking = ranking;
	}
	public int getPop() {
		return pop;
	}
	public String displayPop() {
		return pop + "";
	}
	public void setPop(int pop) {
		this.pop = pop;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getDateSaved() {
		return dateSaved;
	}
	public void setDateSaved(LocalDate dateSaved) {
		this.dateSaved = dateSaved;
	}
	public LocalDate getStartdate() {
		return startdate;
	}
	public void setStartdate(LocalDate startdate) {
		this.startdate = startdate;
	}
	public LocalDate getEnddate() {
		return enddate;
	}
	public void setEnddate(LocalDate enddate) {
		this.enddate = enddate;
	}
	public String getSeason() {
		return season;
	}
	public void setSeason(String season) {
		this.season = season;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<String> getStudio() {
		return studio;
	}
	public void setStudio(List<String> studio) {
		this.studio = studio;
	}
	public int getEpisodes() {
		return episodes;
	}
	public void setEpisodes(int episodes) {
		this.episodes = episodes;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public List<String> getGenres() {
		return genres;
	}
	public void setGenres(List<String> genres) {
		this.genres = genres;
	}
	public double getDuration() {
		return duration;
	}
	public void setDuration(double duration) {
		this.duration = duration;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public int getFavorites() {
		return favorites;
	}
	public void setFavorites(int favorites) {
		this.favorites = favorites;
	}
	public String getPicLink() {
		return picLink;
	}
	public void setPicLink(String picLink) {
		this.picLink = picLink;
	}
	public List<Double> getScorePers() {
		return scorePers;
	}
	public void setScorePers(List<Double> scorePers) {
		this.scorePers = scorePers;
	}
	@Override
	public String toString() {
		return "Anime [id=" + id + ", name=" + name + ", href=" + href + ", score=" + score + ", ranking=" + ranking
				+ ", pop=" + pop + ", start=" + startdate + ", end=" + enddate + ", season=" + season + ", type=" + type
				+ ", studio=" + studio + ", episodes=" + episodes + ", status=" + status + ", source=" + source
				+ ", genres=" + genres + ", duration=" + duration + ", rating=" + rating + ", favorites=" + favorites
				+ ", picLink=" + picLink + ", scorePers=" + scorePers + ", dateSaved=" + dateSaved
				+ "]";
	}
	public void setDuration(String detail) {
		detail = detail.replace(" per ep.", "");
		String[] durStrs = detail.split(" ");
		for(int i = 1; i < durStrs.length; i++) {
			if(durStrs[i].contains("min")) {
				duration = duration + Double.parseDouble(durStrs[i-1]);
			} else if (durStrs[i].contains("sec")) {
				duration = duration + Double.parseDouble(durStrs[i-1])/60;
			} else if (durStrs[i].contains("hr")){
				duration = duration + Integer.parseInt(durStrs[i-1])*60;
			}
		}
		
		
	}	

}

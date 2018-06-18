package fluffybunny.malbunny.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name="Profile")
public class Profile implements Serializable{
	
	@Id
	int id;
	String username;
	boolean isranked;
	int[] ratingPool;
	
	private LocalDate dateSaved;

	double average;

	int watching;
	int completed;
	int onHold;
	int dropped;
	int ptw;
	int ranked;
	int ranted;
	double mavg;

	double change;
	double malchange;
	
	@Transient
	int[][] popTable;
	@Transient
	int[][] scoreTable;
	@Transient
	double[] avgByMal;
	@Transient
	double[] malByScr;
	
	@Transient
	List<Entry> entries;
	@Transient
	List<Anime> anime;
	
	
	public LocalDate getDateSaved() {
		return dateSaved;
	}
	public void setDateSaved(LocalDate dateSaved) {
		this.dateSaved = dateSaved;
	}
	public double getAverage() {
		return average;
	}
	public void setAverage(double average) {
		this.average = average;
	}
	public int getWatching() {
		return watching;
	}
	public void setWatching(int watching) {
		this.watching = watching;
	}
	public int getCompleted() {
		return completed;
	}
	public void setCompleted(int completed) {
		this.completed = completed;
	}
	public int getOnHold() {
		return onHold;
	}

	public void setOnHold(int onHold) {
		this.onHold = onHold;
	}
	public int getDropped() {
		return dropped;
	}
	public void setDropped(int dropped) {
		this.dropped = dropped;
	}
	public int getPtw() {
		return ptw;
	}
	public void setPtw(int ptw) {
		this.ptw = ptw;
	}
	public int getRanked() {
		return ranked;
	}
	public void setRanked(int ranked) {
		this.ranked = ranked;
	}
	public int getRanted() {
		return ranted;
	}
	public void setRanted(int ranted) {
		this.ranted = ranted;
	}
	public double getMavg() {
		return mavg;
	}
	public void setMavg(double mavg) {
		this.mavg = mavg;
	}
	public double getChange() {
		return change;
	}
	public void setChange(double change) {
		this.change = change;
	}
	public double getMalchange() {
		return malchange;
	}
	public void setMalchange(double malchange) {
		this.malchange = malchange;
	}
	public void setIsranked(boolean isranked) {
		this.isranked = isranked;
	}
	public List<Entry> getEntries() {
		return entries;
	}
	public void setEntries(List<Entry> entries) {
		this.entries = entries;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return username;
	}
	public void setName(String name) {
		this.username = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public boolean isIsranked() {
		return isranked;
	}
	public void setRanked(boolean isranked) {
		this.isranked = isranked;
	}
	public int[] getRatingPool() {
		return ratingPool;
	}
	public void setRatingPool(int[] ratingPool) {
		this.ratingPool = ratingPool;
	}
	public int[][] getPopTable() {
		return popTable;
	}
	public void setPopTable(int[][] popTable) {
		this.popTable = popTable;
	}
	public int[][] getScoreTable() {
		return scoreTable;
	}
	public void setScoreTable(int[][] scoreTable) {
		this.scoreTable = scoreTable;
	}
	public double[] getAvgByMal() {
		return avgByMal;
	}
	public void setAvgByMal(double[] avgByMal) {
		this.avgByMal = avgByMal;
	}
	public double[] getMalByScr() {
		return malByScr;
	}
	public void setMalByScr(double[] malByScr) {
		this.malByScr = malByScr;
	}
	public List<Anime> getAnime() {
		return anime;
	}
	public void setAnime(List<Anime> anime) {
		this.anime = anime;
	}
}

package fluffybunny.malbunny.entity;

import java.time.LocalDate;

import javax.persistence.Id;
import javax.persistence.Transient;

public class OutputEntry {
	public int rank;
	public String status;
	public String name;
	public String href;
	public int score;
	public double per;
	public double malPer;
	public double chaNum;
	public double chaScr;
	public double chaPer;
	public double chaMalPer;
	public double malScore;
	public int ranking;
	public int pop;

	public OutputEntry() {
		
	}
	
	public OutputEntry(Entry ent) {
		rank = ent.getRank();
		status = ent.getStatus();
		name = ent.getName();
		href = ent.getHref();
		score = ent.getScore();
		per = ent.getPer();
		malPer = ent.getMalPer();
		chaNum = ent.getChaNum();
		chaScr = ent.getChaScr();
		chaPer = ent.getChaPer();
		chaMalPer = ent.getChaMalPer();
		Anime ani = ent.getAnime();
		malScore = ani.getScore();
		ranking = ani.getRanking();
		pop = ani.getPop();
	}
	public double getMalScore() {
		return malScore;
	}
	
	public void setScore(double score) {
		this.malScore = score;
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
	public String getStatus() {
		return status;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public double getPer() {
		return per;
	}

	public void setPer(double per) {
		this.per = per;
	}

	public double getMalPer() {
		return malPer;
	}

	public void setMalPer(double malPer) {
		this.malPer = malPer;
	}

	

	public String displayRank() {
		if (rank == 0) {
			return "-";
		} else {
			return rank + "";
		}

	}

	public String displayScore() {
		if (score == 0) {
			return "-";
		} else {
			return score + "";
		}
	}

	public String displayPer() {
		return String.format("%1$,.2f", per * 100) + "%";
	}

	public String displayMalPer() {
		return String.format("%1$,.2f", malPer * 100) + "%";
	}

	public double getChaNum() {
		return chaNum;
	}

	public void setChaNum(double chaNum) {
		this.chaNum = chaNum;
	}

	public double getChaScr() {
		return chaScr;
	}

	public void setChaScr(double chaScr) {
		this.chaScr = chaScr;
	}

	public double getChaPer() {
		return chaPer;
	}

	public void setChaPer(double chaPer) {
		this.chaPer = chaPer;
	}

	public double getChaMalPer() {
		return chaMalPer;
	}

	public void setChaMalPer(double chaMalPer) {
		this.chaMalPer = chaMalPer;
	}

	public void setMalScore(double malScore) {
		this.malScore = malScore;
	}



}

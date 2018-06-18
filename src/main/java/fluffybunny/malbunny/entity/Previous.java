package fluffybunny.malbunny.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Previous")
public class Previous implements Serializable{
	
	int rank;
	String status;
	String name;
	int score;
	String progress;
	double mal;
	int malRank;
	double per;
	double malPer;
	double chaNum;
	double chaPer;
	double chaMalPer;
	@Id
	int id;
	@Id
	int rid;
	
	
	public Previous(int rank, String status, String name, int score, String progress, double per, double malPer,
			double chaNum, double chaPer, double chaMalPer, int id, int rid) {
		super();
		this.rank = rank;
		this.status = status;
		this.name = name;
		this.score = score;
		this.progress = progress;
		this.per = per;
		this.malPer = malPer;
		this.chaNum = chaNum;
		this.chaPer = chaPer;
		this.chaMalPer = chaMalPer;
		this.id = id;
		this.rid = rid;
	}
	
	public Previous() {
		
	}

	public Previous(Entry ent, int rid) {
		this.rank = ent.getRank();
		this.status = ent.getStatus();
		this.name = ent.getName();
		this.score = ent.getScore();
		this.progress = ent.getProgress();
		this.per = ent.getPer();
		this.malPer = ent.getMalPer();
		this.chaNum = score - ent.getPrevScore();
		this.chaPer = per - ent.getPrevPer();
		this.chaMalPer = malPer - ent.getPrevMalPer();
		this.id = ent.getId();
		this.rid = rid;
	}

	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public String getStatus() {
		return status;
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
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getProgress() {
		return progress;
	}
	public void setProgress(String progress) {
		this.progress = progress;
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
	public double getChaNum() {
		return chaNum;
	}
	public void setChaNum(double chaNum) {
		this.chaNum = chaNum;
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}
	public double getMal() {
		return mal;
	}

	public void setMal(double mal) {
		this.mal = mal;
	}

	public int getMalRank() {
		return malRank;
	}

	public void setMalRank(int malRank) {
		this.malRank = malRank;
	}
	
	@Override
	public String toString() {
		return "Previous [rank=" + rank + ", status=" + status + ", name=" + name + ", score=" + score + ", progress="
				+ progress + ", mal=" + mal + ", malRank=" + malRank + ", per=" + per + ", malPer=" + malPer
				+ ", chaNum=" + chaNum + ", chaPer=" + chaPer + ", chaMalPer=" + chaMalPer + ", id=" + id + ", rid="
				+ rid + "]";
	}


}

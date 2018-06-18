package fluffybunny.malbunny.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "Entry")
public class Entry implements Serializable, Comparable<Entry> {
	int rank;
	String status;
	String name;
	String href;
	int score;
	String progress;
	int malRank;
	double per;
	double malPer;
	int prevRank;
	int prevScore;
	double prevPer;
	double prevMalPer;
	String picLink;
	int draft;
	@Id
	int id;
	@Id
	int uid;
	@Transient
	Anime anime;

	public Anime getAnime() {
		return anime;
	}

	public void setAnime(Anime anime) {
		this.anime = anime;
	}

	public String getPicLink() {
		return picLink;
	}

	public void setPicLink(String picLink) {
		this.picLink = picLink;
	}
	
	public String getStatus() {
		return status;
	}

	public int getUid() {
		return uid;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public void setUid(int uid) {
		this.uid = uid;
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

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public int getId() {
		return id;
	}

	public void setId(int i) {
		this.id = i;
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

	public int getPrevRank() {
		return prevRank;
	}

	public void setPrevRank(int prevRank) {
		this.prevRank = prevRank;
	}

	public int getPrevScore() {
		return prevScore;
	}

	public void setPrevScore(int prevScore) {
		this.prevScore = prevScore;
	}

	public double getPrevPer() {
		return prevPer;
	}

	public void setPrevPer(double prevPer) {
		this.prevPer = prevPer;
	}

	public double getPrevMalPer() {
		return prevMalPer;
	}

	public void setPrevMalPer(double prevMalPer) {
		this.prevMalPer = prevMalPer;
	}

	public int getDraft() {
		return draft;
	}

	public void setDraft(int draft) {
		this.draft = draft;
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

	public String displayDraft() {
		if (draft == 0) {
			return "-";
		} else {
			return draft + "";
		}
	}

	public String displayPer() {
		return String.format("%1$,.2f", per * 100) + "%";
	}

	public String displayMalPer() {
		return String.format("%1$,.2f", malPer * 100) + "%";
	}

	public String displayChaNum() {
		if (prevRank == 0) {
			return "NEW";
		}
		int chaNum = prevRank - rank;
		if (chaNum > 0) {
			return "+" + chaNum;
		} else if (chaNum == 0) {
			return "-";
		} else {
			return chaNum + "";
		}
	}

	public String displayChaScr() {
		if (prevScore == 0) {
			return "NEW";
		}
		int chaScr = score - prevScore;
		if (chaScr > 0) {
			return "+" + chaScr;
		} else if (chaScr == 0) {
			return "-";
		} else {
			return chaScr + "";
		}
	}

	public String displayChaPer() {
		if (prevPer == 0) {
			return "NEW";
		}
		double chaPer = per - prevPer;
		if (chaPer > 0) {
			return "+" + String.format("%1$,.2f", chaPer * 100) + "%";
		} else if (chaPer == 0) {
			return "-";
		} else {
			return String.format("%1$,.2f", chaPer * 100) + "%";
		}
	}

	public String displayChaMalPer() {
		if (prevMalPer == 0) {
			return "NEW";
		}
		double chaMalPer = malPer - prevMalPer;
		if (chaMalPer > 0) {
			return "+" + String.format("%1$,.2f", chaMalPer * 100) + "%";
		} else if (chaMalPer == 0) {
			return "-";
		} else {
			return String.format("%1$,.2f", chaMalPer * 100) + "%";
		}
	}

	public int getMalRank() {
		return malRank;
	}

	public void setMalRank(int malRank) {
		this.malRank = malRank;
	}

	@Override
	public int compareTo(Entry ent) {
		// System.out.println(toString());
		// System.out.println("vs");
		// System.out.println(ent.toString());
		int comp = rank - ent.rank;
		// System.out.println("Rank Comp: " + comp);
		if (ent.rank == 0 || rank == 0) {
			comp = ent.score - score;
			// System.out.println("Score Comp: " + comp);
		}
		return comp;
	}

	@Override
	public String toString() {
		return "Entry [rank=" + rank + ", status=" + status + ", name=" + name + ", href=" + href + ", score=" + score
				+ ", progress=" + progress + ", malRank=" + malRank + ", per=" + per + ", malPer=" + malPer
				+ ", prevRank=" + prevRank + ", prevScore=" + prevScore + ", prevPer=" + prevPer + ", prevMalPer="
				+ prevMalPer + ", draft=" + draft + ", id=" + id + ", uid=" + uid + ", anime=" + anime + "]";
	}

	public double getChaNum() {
		if(prevRank == 0) {
			return 0;
		}
		return prevRank - rank + .001;
	}
	
	public double getChaScr() {
		if(prevScore == 0) {
			return 0;
		}
		return score - prevScore + .001;
	}
	
	public double getChaPer() {
		if(prevPer == 0) {
			return 0;
		}
		return per - prevPer  + .001;
	}
	
	public double getChaMalPer() {
		if(prevMalPer == 0) {
			return 0;
		}
		return  malPer - prevMalPer + .001;
	}
	
	

}

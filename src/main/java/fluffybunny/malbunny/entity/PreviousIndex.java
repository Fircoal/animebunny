package fluffybunny.malbunny.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import fluffybunny.malbunny.dao.BunnyDao;
import fluffybunny.malbunny.utils.UtilityBunny;

@Entity
@Table(name="PreviousIndex")
public class PreviousIndex implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	int rid;
	int uid;


	private LocalDate dateSaved;

	double average;

	int watching;
	int completed;
	int onHold;
	int dropped;
	int ptw;
	int ranked;
	double mavg;

	double change;
	double malchange;
	

	@Transient
	List<Previous> prevList;

	public PreviousIndex(String[] lineData, Map<String, Integer> animeHash, int uid, BunnyDao dao) {
		this.uid = uid;
		average = 0;
		mavg = 0;
		Map<String, Integer> headerHash = getHeaders(lineData[0].split("\t"));
		System.out.println(headerHash);
		prevList = new ArrayList<Previous>();
		int errorCount = -1;
		for (int i = 1; i < lineData.length; i++) {
			try {
				System.out.println(headerHash);
				System.out.println(lineData[i]);
				String[] line = lineData[i].split("\t");
				Previous prev = new Previous();
				prev.setName(UtilityBunny.fixString(getPrevStringValue(line, headerHash, "name")));
				int idn = getPrevIntValue(line, headerHash, "id");
				int id = (idn==0) ? dao.getAnimeByName(prev.getName()).getId() : idn;
				prev.setId(id);
				prev.setRid(rid);
				prev.setRank(getPrevIntValue(line, headerHash, "rank"));
				prev.setStatus(getPrevStringValue(line, headerHash, "status"));
				int score = getPrevIntValue(line, headerHash, "score");
				double mal = getPrevDoubleValue(line, headerHash, "mal");
				prev.setScore(score);
				prev.setProgress(getPrevStringValue(line, headerHash, "progress"));
				prev.setPer(getPrevDoubleValue(line, headerHash, "per"));
				prev.setMal(mal);
				prev.setMalRank(getPrevIntValue(line, headerHash, "malNum"));
				prev.setMalPer(getPrevDoubleValue(line, headerHash, "malPer"));
				prev.setChaNum(getPrevDoubleValue(line, headerHash, "chaNum"));
				prev.setChaPer(getPrevDoubleValue(line, headerHash, "chaPer"));
				prev.setChaMalPer(getPrevDoubleValue(line, headerHash, "chaMalPer"));
				System.out.println(prev);
				prevList.add(prev);
				animeHash.put(prev.getName(), (i-1));
				if (prev.getRank() > 0) {
					ranked++;
					average += score;
					mavg += mal;
				}
				System.out.println(average + "  " + mavg);
				String stat = prev.getStatus();
				if(stat != null) {
					switch (stat) {
					case "watching":
						watching++;
						break;
					case "completed":
						completed++;
						break;
					case "onhold":
						onHold++;
						break;
					case "dropped":
						dropped++;
						break;
					case "ptw":
						ptw++;
						break;
					}
				}
			} catch (Exception e) {
				System.out.println("Exception made. Skipping Line.");
				Previous prev = new Previous();
				prev.setId(errorCount--);
				prevList.add(prev);
				e.printStackTrace();
			}
		}
		
		average = average/(lineData.length-1);
		mavg = mavg/(lineData.length-1);

	}
	
	public PreviousIndex(Profile prof) {
		this.average = prof.getAverage();
		this.change = prof.getChange();
		this.completed = prof.getCompleted();
		this.dateSaved = LocalDate.now();
		this.dropped = prof.getDropped();
		this.malchange = prof.getMalchange();
		this.mavg = prof.getMavg();
		this.onHold = prof.getOnHold();
		this.ptw = prof.getPtw();
		this.ranked = prof.getRanked();
		this.uid = prof.getId();
		this.watching = prof.getWatching();
		this.prevList = new ArrayList<Previous>();
		List<Entry> entries = prof.getEntries();
		for(Entry ent : entries) {
			prevList.add(new Previous(ent,rid));
		}
	}
	
	public PreviousIndex() {
		super();
	}

	public double getPrevDoubleValue(String[] line, Map<String, Integer> headerHash, String value) {
		if (headerHash.get(value) == null) {
			return 0;
		} else {
			System.out.println(line[headerHash.get(value)].trim());
			System.out.println(line[headerHash.get(value)].trim().equals("NEW"));
			if(line[headerHash.get(value)].trim().equals("") || line[headerHash.get(value)].trim().equals("NEW")) {
				return Double.MAX_VALUE;
			}
			if(line[headerHash.get(value)].contains("%")) {
				return Double.parseDouble(line[headerHash.get(value)].replace("%", ""))/100;
			}
			return Double.parseDouble(line[headerHash.get(value)]);
		}
	}

	public int getPrevIntValue(String[] line, Map<String, Integer> headerHash, String value) {
		if (headerHash.get(value) == null) {
			return 0;
		} else {
			if(line[headerHash.get(value)].trim().equals("") || line[headerHash.get(value)].trim().equals("NEW")) {
				return Integer.MAX_VALUE;
			}
			return Integer.parseInt(line[headerHash.get(value)]);
		}
	}

	public String getPrevStringValue(String[] line, Map<String, Integer> headerHash, String value) {
		if (headerHash.get(value) == null) {
			return null;
		} else {
			return line[headerHash.get(value)].trim();
		}
	}

	private Map<String, Integer> getHeaders(String[] headers) {
		Map<String, Integer> headerHash = new HashMap<>();
		headerHash.put("rank", null);
		headerHash.put("id", null);
		headerHash.put("status", null);
		headerHash.put("name", null);
		headerHash.put("score", null);
		headerHash.put("progress", null);
		headerHash.put("per", null);
		headerHash.put("mal", null);
		headerHash.put("malNum", null);
		headerHash.put("malPer", null);
		headerHash.put("chaNum", null);
		headerHash.put("chaPer", null);
		headerHash.put("chaMalPer", null);
		for (int i = 0; i < headers.length; i++) {
			headerSet(i, headers[i].toLowerCase().trim(), headerHash);
		}
		return headerHash;
	}

	private void headerSet(int i, String header, Map<String, Integer> headerHash) {
		switch (header) {
		case "#":
		case "rank":
			headerHash.put("rank", i);
			break;
		case "anime":
		case "name":
			headerHash.put("name", i);
			break;
		case "scr":
		case "score":
			headerHash.put("score", i);
			break;
		case "mal":
		case "mal score":
			headerHash.put("mal", i);
			break;
		case "m#":
		case "mal ranking":
			headerHash.put("malNum", i);
			break;
		case "%":
		case "percent":
			headerHash.put("per", i);
			break;
		case "m%":
		case "mal percent":
			headerHash.put("malPer", i);
			break;
		case "cha#":
		case "rank change": 
			headerHash.put("chaNum", i);
			break;
		case "cha%":
		case "percent change":
			headerHash.put("chaPer", i);
			break;
		case "chm%":
		case "mal percent change":
			headerHash.put("chaMalPer", i);
			break;
		}

	}

	public int getRid() {
		return rid;
	}

	public void setRid(int rid) {
		this.rid = rid;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

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

	public List<Previous> getPrevList() {
		return prevList;
	}

	public void setPrevList(List<Previous> prevList) {
		this.prevList = prevList;
	}

}

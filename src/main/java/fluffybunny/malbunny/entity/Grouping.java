package fluffybunny.malbunny.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import fluffybunny.malbunny.utils.DefaultOutputEntryComporator;
import fluffybunny.malbunny.utils.RankComporator;

public class Grouping {
	
	public String name;
	List<OutputEntry> entries;
	public double weighted;
	public double mean;
	public double median;
	public int ratesize;
	public double rankwei;
	public double rankmean;
	public double rankmedian;
	public int ranksize;
	
/*	public Grouping(String catName, List<OutputEntry> list, int weiNum, double average) {
		name = catName;
		getScoreStats(list, weiNum + 5, average);
		getRankStats(list, weiNum + 5, average);
		list.sort((x,y) -> x.getRank() - y.getRank());
		entries = list;
	}*/
	
	public Grouping(String catName, List<Entry> list, int weiNum, double average) {
		entries = list.stream().map(x -> new OutputEntry(x)).collect(Collectors.toList());
		name = catName;
		getScoreStats(entries, weiNum + 5, average);
		getRankStats(entries, weiNum + 5, average);
		entries.sort(new DefaultOutputEntryComporator());
	}
	
	public void getScoreStats(List<OutputEntry> list, int weiNum, double average) {
		list = list.stream().filter(x -> x.getScore() > 0).collect(Collectors.toList());
		list.sort((x,y) -> y.getScore() - x.getScore());
		ratesize = list.size();
		if(ratesize > 0) {
		median = (ratesize%2==0) ? (list.get(ratesize/2).getScore() + list.get((ratesize-1)/2).getScore())/2 : list.get(ratesize/2).getScore();
		mean = list.stream().mapToInt(x -> x.getScore()).average().getAsDouble();
		weighted = (ratesize > weiNum) ? mean : ((weiNum-ratesize)*average + ratesize*mean)/weiNum;
		} else {
			median = 0;
			mean = 0;
			weighted = 0;
		}
	}

	public void getRankStats(List<OutputEntry> list, int weiNum, double average) {
		list = list.stream().filter(x -> x.getRank() > 0).collect(Collectors.toList());
		list.sort((x,y) -> x.getRank() - y.getRank());
		ranksize = list.size();
		if(ranksize > 0) {
		rankmedian = (ranksize%2==0) ? (list.get(ranksize/2).getRank() + list.get((ranksize-1)/2).getRank())/2 : list.get(ranksize/2).getRank();
		rankmean = list.stream().mapToInt(x -> x.getRank()).average().getAsDouble();
		rankwei = (ranksize > weiNum) ? rankmean : (weiNum-ranksize)*average + ranksize*rankmean;
		} else {
				rankmedian = 0;
				rankmean = 0;
				rankwei = 0;
			}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<OutputEntry> getEntries() {
		return entries;
	}

	public void setEntries(List<OutputEntry> entries) {
		this.entries = entries;
	}

	public double getWeighted() {
		return weighted;
	}

	public void setWeighted(double weighted) {
		this.weighted = weighted;
	}

	public double getMean() {
		return mean;
	}

	public void setMean(double mean) {
		this.mean = mean;
	}

	public double getMedian() {
		return median;
	}

	public void setMedian(double median) {
		this.median = median;
	}

	public double getRankwei() {
		return rankwei;
	}

	public void setRankwei(double rankwei) {
		this.rankwei = rankwei;
	}

	public double getRankmean() {
		return rankmean;
	}

	public void setRankmean(double rankmean) {
		this.rankmean = rankmean;
	}

	public double getRankmedian() {
		return rankmedian;
	}

	public void setRankmedian(double rankmedian) {
		this.rankmedian = rankmedian;
	}

	public int getRatesize() {
		return ratesize;
	}

	public void setRatesize(int ratesize) {
		this.ratesize = ratesize;
	}

	public int getRanksize() {
		return ranksize;
	}

	public void setRanksize(int ranksize) {
		this.ranksize = ranksize;
	}
	
	
}

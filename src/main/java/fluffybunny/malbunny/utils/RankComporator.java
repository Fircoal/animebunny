package fluffybunny.malbunny.utils;

import java.util.Comparator;

import fluffybunny.malbunny.entity.Entry;

public class RankComporator implements Comparator<Entry>{

	@Override
	public int compare(Entry arg0, Entry arg1) {
		return arg0.getRank() - arg1.getRank();
	}

}

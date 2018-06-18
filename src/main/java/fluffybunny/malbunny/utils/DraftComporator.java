package fluffybunny.malbunny.utils;

import java.util.Comparator;

import fluffybunny.malbunny.entity.Entry;

public class DraftComporator implements Comparator<Entry>{

	@Override
	public int compare(Entry arg0, Entry arg1) {
		int comp = arg1.getScore() - arg0.getScore();
		if(comp == 0) {
			comp = arg0.getDraft() - arg1.getDraft();
		}
		return comp;
	}

}

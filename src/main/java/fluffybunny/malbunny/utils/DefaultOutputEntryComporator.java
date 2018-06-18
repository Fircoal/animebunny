package fluffybunny.malbunny.utils;

import java.util.Comparator;

import fluffybunny.malbunny.entity.Entry;
import fluffybunny.malbunny.entity.OutputEntry;

public class DefaultOutputEntryComporator implements Comparator<OutputEntry>{

	@Override
	public int compare(OutputEntry arg0, OutputEntry arg1) {
		int comp = arg1.getScore() - arg0.getScore();
		if(comp == 0) {
			return arg0.getRank() - arg1.getRank();
		}
		return comp;
	}

}

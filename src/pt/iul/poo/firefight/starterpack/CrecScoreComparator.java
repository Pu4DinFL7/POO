package pt.iul.poo.firefight.starterpack;

import java.util.Comparator;

public class CrecScoreComparator implements Comparator<User> {

	@Override
	public int compare(User o1, User o2) {
		return o1.getScore() - o2.getScore(); 
	}

}

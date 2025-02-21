package pt.iul.poo.firefight.starterpack;

import java.util.Comparator;

public class ScoreComparator implements Comparator<User> {

	@Override
	public int compare(User o1, User o2) {
		return o2.getScore() - o1.getScore();
	}

}

package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Point2D;

public class Ground extends Terrain {

	private static final int LIFE = -1;
	private static final double PROB = 0;

	public Ground(Point2D position) {
		super(position, "land", 1, PROB, LIFE, false);
	}

}

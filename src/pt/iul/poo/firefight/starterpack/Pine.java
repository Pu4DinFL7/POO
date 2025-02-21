package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Point2D;

public class Pine extends Terrain implements Burnable {

	private static final int LIFE = 10;
	private static final double PROB = 0.40;

	public Pine(Point2D position) {
		super(position, "pine", 1, PROB, LIFE, false);

	}

}

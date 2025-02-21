package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Point2D;

public class Grass extends Terrain implements Burnable {

	private static final int LIFE = 3;
	private static final double PROB = 0.15;

	public Grass(Point2D position) {
		super(position, "grass", 1, PROB, LIFE, false);

	}

}

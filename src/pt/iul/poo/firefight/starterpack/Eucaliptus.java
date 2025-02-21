package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Point2D;

public class Eucaliptus extends Terrain implements Burnable {

	private static final int LIFE = 5;
	private static final double PROB = 0.50;

	public Eucaliptus(Point2D position) {
		super(position, "eucaliptus", 1, PROB, LIFE, false);

	}

}

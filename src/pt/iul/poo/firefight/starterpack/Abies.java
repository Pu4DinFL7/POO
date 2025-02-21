package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Point2D;

public class Abies extends Terrain implements Burnable {

	private static final double PROB = 0.05;
	private static final int LIFE = 20;

	public Abies(Point2D position) {
		super(position, "abies",  1, PROB, LIFE, false);
	}
}

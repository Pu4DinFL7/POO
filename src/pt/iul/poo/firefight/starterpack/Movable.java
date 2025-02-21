package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Point2D;

public interface Movable {

	public void move();
	public boolean canMoveTo(Point2D position);

}

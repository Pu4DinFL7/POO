package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;
import java.awt.event.KeyEvent;

public class Plane extends GameElement implements Movable {

	private static GameEngine game = GameEngine.getInstance();
	private static final int CLEARFIRE = 1000;

	public Plane(Point2D position) {
		super(position, "plane", 4);
		if (!game.hasNoFire(position)) {
			Terrain t = (Terrain) game.getGameElement(p -> p instanceof Terrain, getPosition());

			new Water(t.getPosition(), "water", 3, KeyEvent.VK_DOWN);
			Fire f = (Fire) game.getGameElement(b -> b instanceof Fire, t.getPosition());
			game.removeObject(f);
			game.setScore(CLEARFIRE);

		}

	}

	@Override
	public void move() {
		changePosition(Direction.UP);

		if (canMoveTo(getPosition()) && !game.hasNoFire(getPosition())) {

			Terrain t = (Terrain) game.getGameElement(p -> p instanceof Terrain, getPosition());

			new Water(t.getPosition(), "water", 3, KeyEvent.VK_DOWN);
			Fire f = (Fire) game.getGameElement(b -> b instanceof Fire, t.getPosition());
			game.removeObject(f);
			game.setScore(CLEARFIRE);

		} else if (!canMoveTo(getPosition())) {

			game.removeObject(this);
		}

	}
@Override

	public boolean canMoveTo(Point2D p) {

		if (p.getX() < 0)
			return false;
		if (p.getY() < 0)
			return false;
		if (p.getX() >= GameEngine.GRID_WIDTH)
			return false;
		if (p.getY() >= GameEngine.GRID_HEIGHT)
			return false;

		return true;
	}

}
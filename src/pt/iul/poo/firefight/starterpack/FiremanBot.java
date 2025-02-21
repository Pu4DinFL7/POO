package pt.iul.poo.firefight.starterpack;

import java.awt.event.KeyEvent;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class FiremanBot extends Fireman {

	private static GameEngine game = GameEngine.getInstance();

	public FiremanBot(Point2D position) {
		super(position, "firemanbot");
	}

	@Override
	public void move() {

		Direction randDir = Direction.random();
		Point2D newPosition = super.getPosition().plus(randDir.asVector());

		if (randDir == Direction.RIGHT) {
			super.setName("firemanbot_right");
		}

		if (randDir == Direction.LEFT) {
			super.setName("firemanbot_left");
		}
		if (canMoveTo(newPosition) && game.hasNoFire(newPosition)) {
			setPosition(newPosition);

		} else if (canMoveTo(newPosition) && !game.hasNoFire(newPosition)) {
			clearFire();

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
		if ((game.getGameElement(b -> b instanceof Vehicle || b instanceof FiremanBot || b instanceof Fireman, p) != null)) {
			return false;
		}

		return true;
	}

}
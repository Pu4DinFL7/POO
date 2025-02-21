package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;
import java.awt.event.KeyEvent;

public class Bulldozer extends GameElement implements Vehicle {

	private static GameEngine game = GameEngine.getInstance();
	private static final int CLEARTERRAIN = -200;

	public Bulldozer(Point2D position) {
		super(position, "bulldozer", 3);
	}

	public void drive() {

		int key = ImageMatrixGUI.getInstance().keyPressed();

		if (Direction.isDirection(key)) {

			Direction dir = Direction.directionFor(key);

			// verifica se ele se consegue mover para a posicao seguinte
			if (canMoveTo(super.getPosition().plus(dir.asVector()))
					&& game.hasNoFire(super.getPosition().plus(dir.asVector()))) {
				dirVehicle(key);

				if (game.isBurnable(super.getPosition().plus(dir.asVector()))) {
					Terrain t = (Terrain) game.getGameElement(o -> o instanceof Terrain,
							super.getPosition().plus(dir.asVector()));
					game.removeObject(t);
					game.setScore(CLEARTERRAIN);
					new Ground(super.getPosition().plus(dir.asVector()));

				}
				super.changePosition(dir);

			}

		}

	}

	public void getOutVehicle() {

		game.getFireman().setLayer(3);
		game.getFireman().setIsInBulldozer(false);

	}

	public void getInVehicle() {

		if (game.getFireman().getIsInBulldozer() == false) {

			game.getFireman().setIsInBulldozer(true);

			game.getFireman().setLayer(0);

		}

	}

	public void dirVehicle(int key) {
		switch (key) {
		case KeyEvent.VK_DOWN:
			super.setName("bulldozer_down");
			break;

		case KeyEvent.VK_UP:
			super.setName("bulldozer_up");

			break;

		case KeyEvent.VK_LEFT:
			super.setName("bulldozer_left");

			break;

		case KeyEvent.VK_RIGHT:
			super.setName("bulldozer_right");

			break;

		default:
			break;

		}
	}

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
package pt.iul.poo.firefight.starterpack;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pt.iul.ista.poo.gui.ImageMatrixGUI;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

// Esta classe de exemplo esta' definida de forma muito basica, sem relacoes de heranca
// Tem atributos e metodos repetidos em relacao ao que está definido noutras classes 
// Isso sera' de evitar na versao a serio do projeto

public class Fireman extends GameElement implements Movable {
	private static GameEngine game = GameEngine.getInstance();
	private boolean isInBulldozer = false;
	private boolean isInFireTruck = false;
	private boolean existsPlane = false;
	private Bulldozer bulldozer;
	private FireTruck firetruck;
	private Plane plane;
	private static final int CLEARFIRE = 1000;
	private static final int CALLPLANE = -400;

	public Fireman(Point2D position) {
		super(position, "fireman", 3);
	}

	public Fireman(Point2D position, String name) {
		super(position, name, 3);
	}

	// Move numa direcao aleatoria

	@Override
	public void move() {

		int key = ImageMatrixGUI.getInstance().keyPressed();
		List<GameElement> vehicleList = game.select(b -> b instanceof Vehicle);

		if (Direction.isDirection(key)) {
			Direction dir = Direction.directionFor(key);

			if (canMoveTo(super.getPosition().plus(dir.asVector()))
					&& game.hasNoFire(super.getPosition().plus(dir.asVector()))) {
				dirFireman(key);

				if (isInBulldozer) {
					bulldozer.drive();

				}

				else if (isInFireTruck) {

					firetruck.drive();

				}

				super.changePosition(dir);

			} else if (canMoveTo(super.getPosition().plus(dir.asVector()))
					&& !game.hasNoFire(super.getPosition().plus(dir.asVector()))) {

				if (isInBulldozer == false && isInFireTruck == false) {

					clearFire();

				} else if (isInBulldozer == false && isInFireTruck == true) {

					firetruck.fireTruckClearFire();

				}

			}

			for (GameElement b : vehicleList) {

				if (b.getPosition().equals(super.getPosition()) && !isInBulldozer && !isInFireTruck) {
					if (b instanceof Bulldozer) {

						bulldozer = (Bulldozer) b;
						bulldozer.getInVehicle();

					}

					if (b.getPosition().equals(super.getPosition()) && !isInFireTruck && !isInBulldozer) {
						if (b instanceof FireTruck) {

							firetruck = (FireTruck) b;
							firetruck.getInVehicle();
						}
					}
				}
			}
		}
		if (key == KeyEvent.VK_ENTER && isInBulldozer == true) {
			bulldozer.getOutVehicle();
			setIsInBulldozer(false);

		} else if (key == KeyEvent.VK_ENTER && isInFireTruck == true) {
			firetruck.getOutVehicle();
			setIsInFireTruck(false);

		}

		if (key == KeyEvent.VK_P) {
			game.setScore(CALLPLANE);

			int temp = 0;
			int occurrences = 0;
			int maxOccur = 0;
			List<Fire> fireList = game.select(f -> f instanceof Fire);
			List<Integer> list = new ArrayList<>();

			for (int i = 0; i < fireList.size(); i++) {
				list.add(fireList.get(i).getPosition().getX());

				for (int j = 0; j < GameEngine.GRID_WIDTH; j++) {
					occurrences = Collections.frequency(list, j);

					if (temp < occurrences) {
						temp = occurrences;
						maxOccur = j;
					}
				}
			}

			plane = new Plane(new Point2D(maxOccur, GameEngine.GRID_HEIGHT - 1));
			setPlane(true);

		}

	}

	public boolean getPlaneExistence() {
		return existsPlane;
	}

	public void setPlane(boolean exists) {
		existsPlane = exists;
	}

	public boolean getIsInBulldozer() {
		return isInBulldozer;
	}

	public void setIsInBulldozer(boolean enter) {
		isInBulldozer = enter;
	}

	public boolean getIsInFireTruck() {
		return isInFireTruck;
	}

	public void setIsInFireTruck(boolean enter) {
		isInFireTruck = enter;
	}

	public void clearFire() {
		int key = ImageMatrixGUI.getInstance().keyPressed();
		// posicao seguinte onde esta o bombeiro
		if (Direction.isDirection(key)) {
			Direction dir = Direction.directionFor(key);
			if (!game.hasNoFire(super.getPosition().plus(dir.asVector()))) {

				Terrain t = (Terrain) game.getGameElement(p -> p instanceof Terrain,
						getPosition().plus(dir.asVector()));

				new Water(t.getPosition(), "water", 3, key);
				Fire f = (Fire) game.getGameElement(b -> b instanceof Fire, t.getPosition());
				game.removeObject(f);
				game.setScore(CLEARFIRE);

			}
		}
	}

	public void dirFireman(int key) {
		switch (key) {
		case KeyEvent.VK_LEFT:
			super.setName("fireman_left");

			break;

		case KeyEvent.VK_RIGHT:
			super.setName("fireman_right");

			break;

		default:
			break;

		}
	}

	@Override
	// Verifica se a posicao p esta' dentro da grelha de jogo
	public boolean canMoveTo(Point2D p) {

		if (p.getX() < 0)
			return false;
		if (p.getY() < 0)
			return false;
		if (p.getX() >= GameEngine.GRID_WIDTH)
			return false;
		if (p.getY() >= GameEngine.GRID_HEIGHT)
			return false;
		if (isInBulldozer || isInFireTruck) {
			if ((game.getGameElement(b -> b instanceof Vehicle || b instanceof FiremanBot, p) != null)) {
				return false;
			}
		}

		return true;
	}

}
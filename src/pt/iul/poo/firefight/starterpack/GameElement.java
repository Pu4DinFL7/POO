package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public abstract class GameElement implements ImageTile {

	private String name;
	private Point2D position;
	private int layer;
	private static GameEngine game = GameEngine.getInstance();

	public GameElement(Point2D position, String name, int layer) {

		this.position = position;
		this.name = name;
		this.layer = layer;
		game.addObject(this);

	}

	@Override
	public String toString() {
		return "GameElement [name=" + name + ", position=" + position + ", layer=" + layer + "]";
	}

	public void changePosition(Direction dir) {
		position = position.plus(dir.asVector());

	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Point2D getPosition() {
		return position;
	}

	public void setPosition(Point2D position) {
		this.position = position;

	}

	public int getLayer() {
		return layer;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}

}
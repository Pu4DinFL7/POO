package pt.iul.poo.firefight.starterpack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import pt.iul.ista.poo.utils.Point2D;

public class Fire extends GameElement {

	private static GameEngine game = GameEngine.getInstance();

	public Fire(Point2D position) {
		super(position, "fire", 3);
	}

	public static void propagate() {
		List<Point2D> points = new ArrayList<>();
		List<Fire> fireList = game.select(p -> p instanceof Fire);
		for (Fire f : fireList) {
			points.addAll(f.getPosition().getNeighbourhoodPoints());
		}
		List<Point2D> aux = points.stream().distinct().collect(Collectors.toList());
		for (int i = 0; i < aux.size(); i++) {
			Point2D p2d = aux.get(i);
			if (!(game.select(p -> p instanceof Movable && p.getPosition().equals(p2d))).isEmpty()) {
				continue;
			}

			if (canMoveTo(p2d) && Terrain.isTerrain(p2d)) {
				Terrain t = (Terrain) game.getGameElement(b -> b instanceof Terrain, p2d);
				double x = Math.random();
				if (t.getProb() > x && t.getBurned() == false) {
					new Fire(p2d);
					t.setBurned(true);
				}
			}
		}
	}

	public static boolean canMoveTo(Point2D p) {
		if (p.getX() < 0)
			return false;
		if (p.getY() < 0)
			return false;
		if (p.getX() >= GameEngine.GRID_WIDTH)
			return false;
		if (p.getY() >= GameEngine.GRID_HEIGHT)
			return false;
		if (!game.select(f -> f instanceof Fire && f.getPosition().equals(p)).isEmpty())
			return false;
		if (!game.select(w -> w instanceof Water && w.getPosition().equals(p)).isEmpty()) {

			return false;
		}

		return true;
	}

}

package pt.iul.poo.firefight.starterpack;

import java.util.List;

import pt.iul.ista.poo.utils.Point2D;

public abstract class Terrain extends GameElement {

	private static GameEngine game = GameEngine.getInstance();
	private double PROB;
	private int LIFE;
	private boolean burned;

	public Terrain(Point2D position, String name, int layer, double prob, int life, boolean burned) {
		super(position, name, layer);
		this.burned = burned;
		PROB = prob;
		LIFE = life;
	}

	public static boolean isTerrain(Point2D p) {
		List<Terrain> terrainList = game.select(t -> t instanceof Terrain);
		for (Terrain t : terrainList) {
			if (t.getPosition().equals(p)) {
				return true;
			}
		}
		return false;
	}

	public static void lifeIsntFair() {
		List<Fire> fireList = game.select(p -> p instanceof Fire);
		for (Fire f : fireList) {
			if (Terrain.isTerrain(f.getPosition())) {

				Terrain terrain = (Terrain) game.getGameElement(b -> b instanceof Terrain, f.getPosition());

				int i = terrain.getLife();
				int t = game.getGUI().keyPressed();
				if (t > 0 && i > 0) {
					i--;
					terrain.setLife(i);
				}
				if (i == 0) {
					terrain.burnt(terrain.getName());
					game.removeObject(game.getGameElement(o -> o instanceof Fire, terrain.getPosition()));
				}
			}
		}
	}

	public void burnt(String name) {
		switch (name) {
		case "pine":
			super.setName("burntpine");

			break;

		case "eucaliptus":
			super.setName("burnteucaliptus");

			break;

		case "grass":
			super.setName("burntgrass");
			break;

		case "abies":
			super.setName("burntabies");
			break;

		case "fuelbarrel": {
			new Ground(super.getPosition());
			super.setName("explosion");
			 FuelBarrel.explosion(super.getPosition());
			

		}
		default:
			break;
		}

	}

	public double getProb() {
		return PROB;
	}

	public int getLife() {
		return LIFE;
	}

	public void setLife(int i) {
		LIFE = i;

	}

	public boolean getBurned() {
		return burned;
	}

	public void setBurned(boolean burned) {
		this.burned = burned;
	}

}

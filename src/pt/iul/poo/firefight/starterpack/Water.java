package pt.iul.poo.firefight.starterpack;

import java.awt.event.KeyEvent;

import pt.iul.ista.poo.utils.Point2D;

public class Water extends GameElement {

	public Water(Point2D position, String name, int layer, int key) {
		super(position, name, layer);
		createWater(key);

	}

	public void createWater(int key) {
		switch (key) {
		case KeyEvent.VK_DOWN:
			super.setName("water_down");
			break;

		case KeyEvent.VK_UP:
			super.setName("water_up");

			break;

		case KeyEvent.VK_LEFT:
			super.setName("water_left");

			break;

		case KeyEvent.VK_RIGHT:
			super.setName("water_right");

			break;

		default:
			break;

		}
	}
}

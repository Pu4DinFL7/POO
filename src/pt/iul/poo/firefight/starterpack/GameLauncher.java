package pt.iul.poo.firefight.starterpack;

import java.io.FileNotFoundException;

import pt.iul.ista.poo.gui.ImageMatrixGUI;

public class GameLauncher {
	public static void main(String[] args) throws FileNotFoundException {

		ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
		gui.setSize(GameEngine.GRID_WIDTH, GameEngine.GRID_HEIGHT);
		gui.go();
		GameEngine game = GameEngine.getInstance();

		gui.registerObserver(game);

		ReadFile.readFile("levels/level6.txt");

	}
}
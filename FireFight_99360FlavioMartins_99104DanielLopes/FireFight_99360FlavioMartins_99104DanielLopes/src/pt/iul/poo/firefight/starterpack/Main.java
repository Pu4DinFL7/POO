package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.gui.ImageMatrixGUI;

public class Main {
	public static void main(String[] args) {
		ImageMatrixGUI gui = ImageMatrixGUI.getInstance();

		// Cria uma instancia de GameEngine e depois inicia o jogo
		// Podera' vir a ficar diferente caso defina GameEngine como solitao
		
		GameEngine game = new GameEngine();
		//gui.registerObserver(game); //temos de fazer sempre isto tudo
		game.start();
		
	}
}

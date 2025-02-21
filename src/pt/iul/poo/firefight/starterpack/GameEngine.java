package pt.iul.poo.firefight.starterpack;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.observer.Observed;
import pt.iul.ista.poo.observer.Observer;
import pt.iul.ista.poo.utils.Point2D;

// Note que esta classe e' um exemplo - nao pretende ser o inicio do projeto, 
// embora tambem possa ser usada para isso.
//
// No seu projeto e' suposto haver metodos diferentes.
// 
// As coisas que comuns com o projeto, e que se pretendem ilustrar aqui, sao:
// - GameEngine implementa Observer - para  ter o metodo update(...)  
// - Configurar a janela do interface grafico (GUI):
//        + definir as dimensoes
//        + registar o objeto GameEngine ativo como observador da GUI
//        + lancar a GUI
// - O metodo update(...) e' invocado automaticamente sempre que se carrega numa tecla
//
// Tudo o mais podera' ser diferente!

public class GameEngine implements Observer {

	// Dimensoes da grelha de jogo
	public static final int GRID_HEIGHT = 10;
	public static final int GRID_WIDTH = 10;
	private static GameEngine INSTANCE;

	private ImageMatrixGUI gui = ImageMatrixGUI.getInstance(); // Referencia para ImageMatrixGUI (janela de interface
	// com o utilizador)
	private static List<ImageTile> tileList = new ArrayList<>(); // Lista de imagens
	private Fireman fireman; // Referencia para o bombeiro
	private int score = 0;
	private int count = 1;

	public static GameEngine getInstance() {
		if (INSTANCE == null)
			INSTANCE = new GameEngine();
		return INSTANCE;
	}

	public ImageMatrixGUI getGUI() {
		return gui;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int newScore) {
		score += newScore;
	
		if(score < 0) {
			score = 0;
		}
	}

	public Fireman getFireman() {
		return fireman;
	}

	public boolean hasNoFire(Point2D point) {
		List<Fire> fireList = select(p -> p instanceof Fire && p.getPosition().equals(point));
		return fireList.isEmpty();

	}

	public void makePlanesMove() {
		List<Plane> planeList = new ArrayList<>();
		for (ImageTile it : tileList) {
			if (it instanceof Plane) {
				planeList.add((Plane) it);
			}
		}

		for (Plane p : planeList) {
			p.move();
			p.move();
		}
	}

	public GameElement getGameElement(Predicate<GameElement> pred, Point2D point) {
		for (ImageTile it : tileList) {
			if (pred.test((GameElement) it) && it.getPosition().equals(point)) {
				return (GameElement) it;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> select(Predicate<GameElement> pred) {
		List<T> list = new ArrayList<>();
		for (ImageTile it : tileList) {
			if (pred.test((GameElement) it)) {
				list.add((T) it);
			}
		}
		return list;

	}

	// O metodo update() e' invocado sempre que o utilizador carrega numa tecla
	// no argumento do metodo e' passada um referencia para o objeto observado
	// (neste caso seria a GUI)
	@Override
	public void update(Observed source) {


		clearWaters();
		clearBurntFuelBarrel();
		

		if (fireman.getPlaneExistence()) {
			makePlanesMove();
		}
		

		fireman.move();
		
		Fire.propagate();
		

		if(!select(fb -> fb instanceof FiremanBot).isEmpty()) {
			List<FiremanBot> firemanBotList = select(fb -> fb instanceof FiremanBot);
			for(FiremanBot fb : firemanBotList) {
				fb.move();
			}
			
		}
		

		Terrain.lifeIsntFair();
		
		gui.setStatusMessage("Score: " + score);
		

		if (select(o -> o instanceof Fire).isEmpty() && select(w -> w instanceof Water).isEmpty()) {
			
			changeFiles();	
			gui.setStatusMessage("Score: " + score);
		}

		gui.update();
		
	}
	
	public void clearBurntFuelBarrel() {
		List<FuelBarrel> fuelBarrelList = select(f -> f instanceof FuelBarrel && f.getName().equals("explosion"));
		for (FuelBarrel f : fuelBarrelList) {
			removeObject(f);	
		}

	}

	public void clearWaters() {
		List<Water> waterList = select(w -> w instanceof Water);
		for (Water w : waterList) {
			removeObject(w);
		}

	}

	public void addObject(GameElement it) {
		tileList.add(it);
		gui.addImage(it);
	}

	public void removeObject(GameElement ge) {
		tileList.remove(ge);
		gui.removeImage(ge);
	}

	public void setFireman(Fireman f) {
		this.fireman = f;
	}

	public boolean isBurnable(Point2D point) {
		for (ImageTile it : tileList) {
			if (it.getPosition().equals(point) && it instanceof Burnable) {
				return true;

			}
		}
		return false;
	}

	public void changeFiles() {
		int countNumberLines = 0;
		List<User> userList = new ArrayList<>();
		List<User> aux = new ArrayList<>();

		try {
			String userName = JOptionPane.showInputDialog("Username: ");
			File file = new File("ScoreBoard" + count + ".txt");

			file.createNewFile();

			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()) {
				String[] s1 = sc.nextLine().split(":");
				String name = s1[0];
				int scores = Integer.parseInt(s1[1]);


				User u = new User(name, scores);
				userList.add(u);
				countNumberLines++;
			}
			sc.close();
			userList.sort(new CrecScoreComparator());

			if (userList.size() >= 5) {

				//userList.forEach(o -> System.out.println(o));
				if (userList.get(0).getScore() < score) {

					userList.remove(0);
					userList.add(new User(userName, score));

				}


			}else {
				userList.add(new User(userName, score));
			}

			userList.sort(new ScoreComparator());
			File file2 = new File("ScoreBoard" + count + ".txt");
			FileWriter writer = new FileWriter(file2);
			for (User o : userList) {
				writer.write(o.getName() + ":" + o.getScore() + System.lineSeparator());

			}

			writer.close();
			count++;
			score = 0;

		} catch (FileNotFoundException e) {
			System.err.println("Couldn't open the file");
		} catch (IOException e) {
			System.err.println("Couldn't write in the file");
		}
		gui.clearImages();

		try {
			tileList.clear();
			ReadFile.readFile("levels/level" + count + ".txt");

		} catch (FileNotFoundException e) {
			gui.dispose();
			gui.setMessage("We are very sorry... Theres's no more levels left for you to conquer at the moment! :(");
		
		}

	}

}
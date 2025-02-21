package pt.iul.poo.firefight.starterpack;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import pt.iul.ista.poo.utils.Point2D;

public class ReadFile {
    
    private static GameEngine game = GameEngine.getInstance();

    public static void readFile(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        String aux;
        Scanner sc = new Scanner(file);
        int x = 0;
        int y = 0;
        while (sc.hasNextLine()) {
            aux = sc.nextLine();
            // itera sobre uma linha do ficheiro para saber o caracter que esta em cada
            // posicao
            if (x < GameEngine.GRID_WIDTH && y < GameEngine.GRID_HEIGHT) {
                for (int i = 0; i < aux.length(); i++) {
                    if (x < GameEngine.GRID_WIDTH) {
                        
                        drawTerrain(x, y, aux.charAt(i));
                        x++;
                    }
                    if (x >= GameEngine.GRID_WIDTH) {
                        x = 0;
                        y++;
                    }
                }
            } else {
                String[] str = aux.split(" ");
                String name = str[0];
                int positionX = Integer.parseInt(str[1]);
                int positionY = Integer.parseInt(str[2]);
                drawObject(name, positionX, positionY);
            }
        }
        sc.close();
        
    }
    
    public static void drawObject(String s, int x, int y) {
        switch (s) {
        case "Fireman":
            Fireman fireman = new Fireman(new Point2D(x, y));
            game.setFireman(fireman);
            break;

        case "Bulldozer":
            
            new Bulldozer(new Point2D(x,y));
            
            break;

        case "Fire":
            new Fire(new Point2D(x, y));
        
            break;
        case "FiremanBot":
            
            new FiremanBot(new Point2D(x,y));
            
            break;
            
        case "FireTruck":
            
            new FireTruck(new Point2D(x,y));
            
            break;
            
        default:

            break;
        }
    }
    
    
    public static void drawTerrain(int x, int y, char c) {
        

        switch (c) {
        case 'p':
            new Pine(new Point2D(x, y));
            
            break;
        case 'e': {
            
            new Eucaliptus(new Point2D(x, y));
            
            break;
        }

        case 'm': {
            new Grass(new Point2D(x, y));
            
            break;
        }

        case '_': {
            new Ground(new Point2D(x, y));
            
            break;
        }
        
        case 'a': {
             new Abies(new Point2D(x, y));
            
            break;
        }
        
        case 'b': {
             new FuelBarrel(new Point2D(x, y));
            
            break;
        }

        default:
        
            break;
        }
    }
}
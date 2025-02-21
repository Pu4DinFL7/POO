package pt.iul.poo.firefight.starterpack;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;
import pt.iul.ista.poo.utils.Vector2D;

public class FireTruck extends GameElement implements Vehicle {

    private static GameEngine game = GameEngine.getInstance();

    public FireTruck(Point2D position) {
        super(position, "firetruck", 3);

    }

    public void drive() {

        int key = ImageMatrixGUI.getInstance().keyPressed();

        if (Direction.isDirection(key)) {

            Direction dir = Direction.directionFor(key);
            
            // verifica se ele se consegue mover para a posicao seguinte
            if (canMoveTo(super.getPosition().plus(dir.asVector()))
                    && game.hasNoFire(super.getPosition().plus(dir.asVector()))) {
                dirVehicle(key);
                
                super.changePosition(dir);
            }
                
        }
    }    

    public void getInVehicle() {
        if (game.getFireman().getIsInFireTruck() == false) {
            game.getFireman().setIsInFireTruck(true);
            game.getFireman().setLayer(0);
        }
    }

    public void getOutVehicle() {
        game.getFireman().setLayer(3);
        game.getFireman().setIsInFireTruck(false);
    }

    public void dirVehicle(int key) {
        switch (key) {
        case KeyEvent.VK_LEFT:
            super.setName("firetruck_left");

            break;

        case KeyEvent.VK_RIGHT:
            super.setName("firetruck_right");

            break;

        default:
            break;

        }
    }

    public void fireTruckClearFire() {
        int key = ImageMatrixGUI.getInstance().keyPressed();
        // posicao seguinte onde esta o bombeiro
        Direction dir = Direction.directionFor(key);
        
        if (!game.hasNoFire(super.getPosition().plus(dir.asVector()))) {
             List <Point2D> aux = super.getPosition().getFrontRect(dir, 3, 2);
             
             for(Point2D p : aux) {
                 if((Fire)game.getGameElement(f -> f instanceof Fire, p) instanceof Fire) {
                     new Water(p, "water", 3, key);
                     Fire f = (Fire) game.getGameElement(b -> b instanceof Fire,p);
                     game.removeObject(f);
                 }
             }

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
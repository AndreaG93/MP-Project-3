package thread.thread.updater;

import android.graphics.Rect;
import java.util.Vector;
import game.Game;
import sprite.Fruit;
import sprite.Ghost;
import sprite.PacMan;
import sprite.PowerUp;
import sprite.Sprite;
import thread.AbstractThread;

/**
 * This class is used to update postion and detect collision
 */
public class PositionUpdaterThread extends AbstractThread {

    private Game myGame;

    private PacMan myPacMan;
    private PowerUp myPowerUp;
    private Vector<Ghost> myGhostVector = new Vector<>();
    private Vector<Fruit> myFruitVector = new Vector<>();

    public PositionUpdaterThread(Game arg0) {
        this.myGame = arg0;
    }

    @Override
    public void run() {

        while (isRunning) {

            try {
                sleep(50);

                while (isPaused) {
                    yield();
                }

                // Get Lock
                Game.getMyLock().lock();

                this.myGhostVector.clear();
                this.myFruitVector.clear();
                this.myPacMan = null;
                this.myPowerUp = null;

                // Populate vectors
                for (Sprite obj : this.myGame.getMySpriteVector()) {

                    if (obj.getClass().getName().equals(Ghost.class.getName()))
                        this.myGhostVector.add((Ghost) obj);
                    else if (obj.getClass().getName().equals(Fruit.class.getName()))
                        this.myFruitVector.add((Fruit) obj);
                    else if (obj.getClass().getName().equals(PacMan.class.getName()))
                        this.myPacMan = (PacMan) obj;
                    else if (obj.getClass().getName().equals(PowerUp.class.getName()))
                        this.myPowerUp = (PowerUp) obj;
                }

                if (this.myPacMan != null) {
                    // Check collision with ghost
                    for (Ghost myGhost : this.myGhostVector) {
                        if (Rect.intersects(this.myPacMan.getBound(), myGhost.getBound())) {
                            this.myGame.updateAfterEnemyCollision(myGhost);
                        }
                    }

                    // Check collision with fruits
                    for (Fruit myFruit : this.myFruitVector) {
                        if (Rect.intersects(this.myPacMan.getBound(), myFruit.getBound())) {
                            this.myGame.updateAfterFruitCollision(myFruit);
                        }
                    }

                    // Check collision with powerUP
                    if (this.myPowerUp != null) {

                        if (Rect.intersects(this.myPacMan.getBound(), this.myPowerUp.getBound())) {
                            this.myGame.updateAfterPowerUpCollision(this.myPowerUp);
                        }
                    }
                }

                // Update position
                for (Sprite obj : this.myGame.getMySpriteVector())
                    obj.updatePosition(this.myGame.getWidth(), this.myGame.getHeight());


            } catch (InterruptedException e) {
                e.printStackTrace();
                this.stopThread();
            } finally {

                // Release Lock
                Game.getMyLock().unlock();
            }
        }
    }
}

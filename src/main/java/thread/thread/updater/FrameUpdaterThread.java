package thread.thread.updater;

import java.util.Vector;

import game.Game;
import game.GamePointsToDraw;
import sprite.Sprite;
import thread.AbstractThread;

public class FrameUpdaterThread extends AbstractThread {

    private Game myGame;
    private Vector<Sprite> myCollection = new Vector<>();

    public FrameUpdaterThread(Game arg0) {
        this.myGame = arg0;
    }

    @Override
    public void run() {

        while (isRunning) {

            try {
                sleep(100);

                while (isPaused) {
                    yield();
                }

                // Get Lock
                Game.getMyLock().lock();

                for (Sprite obj : this.myGame.getMySpriteVector())
                    myCollection.add(obj);

                for (Sprite obj : this.myCollection)
                {
                    int i = this.myGame.getMySpriteVector().indexOf(obj);
                    this.myGame.getMySpriteVector().get(i).updateFrame();
                }

                myCollection.clear();

                // Release Lock
                Game.getMyLock().unlock();


            } catch (InterruptedException e) {
                e.printStackTrace();
                this.stopThread();
            }
        }
    }
}




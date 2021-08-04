package thread.thread.updater;

import game.Game;
import thread.AbstractThread;

public class BackgroundPositionUpdaterThread extends AbstractThread {

    private Game myGame;

    public BackgroundPositionUpdaterThread(Game arg0) {
        this.myGame = arg0;
    }

    public void run() {

        while (isRunning) try {
            sleep(50);

            while (isPaused) {
                yield();
            }

            // Perform position background update...
            this.myGame.getMyGameBackground().setPositionBackground(this.myGame.getWidth());

        } catch (InterruptedException e) {
            e.printStackTrace();
            this.stopThread();
        }
    }
}

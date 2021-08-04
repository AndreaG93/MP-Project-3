package thread.thread.updater;

import java.util.Vector;
import game.Game;
import game.GamePlayerStatus;
import game.GamePointsToDraw;
import thread.AbstractThread;

public class TimerUpdaterThread extends AbstractThread {

    private Game myGame;
    private Vector<GamePointsToDraw> myCollection = new Vector<>();

    public TimerUpdaterThread(Game arg0) {
        this.myGame = arg0;
    }

    public void run() {
        while (isRunning) {

            try {

                while (isPaused) {
                    yield();
                }

                Thread.sleep(1000);

                // add one second...
                this.myGame.getMyStatistics().addSecondPlayTime();

                // Get Lock
                Game.getMyLock().lock();

                for(GamePointsToDraw obj : this.myGame.getMyGamePointsToDrawVector())
                {
                    obj.subOneSecond();
                    if (obj.getSecondsPermanence() == 0)
                        myCollection.add(obj);
                }

                this.myGame.getMyGamePointsToDrawVector().removeAll(myCollection);
                myCollection.clear();

                // Check if player is entered in INVINCIBLE status
                if(this.myGame.getMyGamePlayerStatus() == GamePlayerStatus.INVINCIBLE)
                {
                    this.myGame.subOneSecondToInvincibleStatusTimer();

                    if(this.myGame.getInvincibleStatusTimer() == 3 || this.myGame.getInvincibleStatusTimer() == 2 || this.myGame.getInvincibleStatusTimer() == 1)
                    {
                        this.myGame.createTextInvincibleTimerRemain();
                    }

                    if(this.myGame.getInvincibleStatusTimer() == 0)
                    {
                        this.myGame.updateAfterInvincibleTimerExpired();
                    }
                }

                // Release Lock
                Game.getMyLock().unlock();

            } catch (InterruptedException e) {
                e.printStackTrace();
                this.stopThread();
            }
        }
    }
}


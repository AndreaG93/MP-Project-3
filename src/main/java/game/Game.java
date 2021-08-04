package game;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.sql.Time;
import java.util.Vector;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import activities.MainActivity;
import activities.ResultActivity;
import game.difficulty.Easy;
import game.difficulty.GameDifficulty;
import game.difficulty.Hard;
import game.difficulty.Normal;
import game.difficulty.VeryHard;
import game.gesture.GestureDetector;
import game.gesture.GestureObserver;
import rg.pac_space.R;
import sprite.Fruit;
import sprite.PacManDeath;
import sprite.PowerUp;
import sprite.Sprite;
import sprite.Ghost;
import sprite.PacMan;
import statistics.Statistics;
import thread.DrawerThread;
import thread.thread.updater.BackgroundPositionUpdaterThread;
import thread.thread.updater.FrameUpdaterThread;
import thread.thread.updater.PositionUpdaterThread;
import thread.thread.updater.TimerUpdaterThread;
import utility.DialogBuilder;
import utility.IntentHelper;
import utility.Support;

import static java.lang.Thread.sleep;

public class Game extends SurfaceView implements GestureObserver, SurfaceHolder.Callback {

    public static Lock getMyLock() {
        return myLock;
    }

    static private Lock myLock = new ReentrantLock();

    private GameDifficulty myGameDifficulty;

    private Context myContext;

    // Represents status
    private GameStatus myStatus;

    // Represents player status
    private GamePlayerStatus myGamePlayerStatus;

    // Represents a timer
    private int invincibleStatusTimer;

    // Represents player's life
    private int life = 2;

    // Represents player statistics
    private Statistics myStatistics;

    // Represents background
    private GameBackground myGameBackground;

    // Represent player detector
    private PacMan myPacMan;

    /***********************************************************************************************
     * Vectors
     */

    // This vector is used to store Sprites object
    private Vector<Sprite> mySpriteVector = new Vector<>();

    // This vector is used to store pointsToDraw object
    private Vector<GamePointsToDraw> myGamePointsToDrawVector = new Vector<>();

    /***********************************************************************************************
     * Thread
     */

    // This thread is used to update all sprite's position and to check collisions
    private PositionUpdaterThread myPositionUpdaterThread;

    // This thread is used to animate sprites.
    private FrameUpdaterThread myFrameUpdaterThread;

    // This thread is used as a timer
    private TimerUpdaterThread myTimerUpdaterThread;

    // This thread is used to animate background.
    private BackgroundPositionUpdaterThread myBackgroundPositionUpdaterThread;

    // This thread is used to draw.
    private DrawerThread myDrawerThread;

    /***********************************************************************************************
     * Music
     */
    private GameMusic gameMusic;
    private GameMusic pauseSoundEffect;

    /**
     * Constructs a newly allocated {@code Game} object.
     *
     * @param arg0 Represents a {@code Context} object.
     * @param arg1 Represents a {@code GestureDetector} object.
     */
    public Game(Context arg0, GestureDetector arg1) {
        super(arg0);
        this.myContext = arg0;
        this.myGameBackground = new GameBackground();
        this.myStatus = GameStatus.LOADING;

        // Loading sounds
        this.gameMusic = new GameMusic(myContext, R.raw.sunset, true);
        this.pauseSoundEffect = new GameMusic(myContext, R.raw.pause, false);

        // Loading game difficulty
        SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(myContext);

        switch (Integer.valueOf(sharedPreference.getString("chosenGameDifficulty", "1"))) {
            case 0: {
                this.myGameDifficulty = new Easy();
                break;
            }
            case 1: {
                this.myGameDifficulty = new Normal();
                break;
            }
            case 2: {
                this.myGameDifficulty = new Hard();
                break;
            }
            case 3: {
                this.myGameDifficulty = new VeryHard();
                break;
            }
        }

        // Loading player and statistics
        this.myStatistics = new Statistics(myGameDifficulty);

        // Loading threads
        this.myTimerUpdaterThread = new TimerUpdaterThread(this);
        this.myFrameUpdaterThread = new FrameUpdaterThread(this);
        this.myBackgroundPositionUpdaterThread = new BackgroundPositionUpdaterThread(this);
        this.myDrawerThread = new DrawerThread(this, BitmapFactory.decodeResource(myContext.getResources(), R.drawable.pacman_icon));
        this.myPositionUpdaterThread = new PositionUpdaterThread(this);

        // Thread starts... (paused)
        this.myTimerUpdaterThread.start();
        this.myFrameUpdaterThread.start();
        this.myBackgroundPositionUpdaterThread.start();
        this.myDrawerThread.start();
        this.myPositionUpdaterThread.start();

        // Register as 'GestureObserver'
        arg1.addObserver(this);

        // Register as SurfaceHolder.Callback
        this.getHolder().addCallback(this);
    }

    /***********************************************************************************************
     * Getter methods.
     **********************************************************************************************/

    public Context getMyContext() {
        return myContext;
    }

    public GameStatus getMyStatus() {
        return myStatus;
    }

    public Vector<Sprite> getMySpriteVector() {
        return mySpriteVector;
    }

    public GameBackground getMyGameBackground() {
        return myGameBackground;
    }

    public Statistics getMyStatistics() {
        return myStatistics;
    }

    public int getLife() {
        return life;
    }

    public Vector<GamePointsToDraw> getMyGamePointsToDrawVector() {
        return myGamePointsToDrawVector;
    }

    public GamePlayerStatus getMyGamePlayerStatus() {
        return myGamePlayerStatus;
    }

    public int getInvincibleStatusTimer() {
        return invincibleStatusTimer;
    }

    /***********************************************************************************************
     * Surface events
     **********************************************************************************************/

    /**
     * This method is called immediately before a surface is being destroyed.
     *
     * @param holder Represents a {@code SurfaceHolder} object.
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        this.gameMusic.releaseResource();
        this.pauseSoundEffect.releaseResource();

        // Stop all thread...
        this.myTimerUpdaterThread.stopThread();
        this.myFrameUpdaterThread.stopThread();
        this.myBackgroundPositionUpdaterThread.stopThread();
        this.myDrawerThread.stopThread();
        this.myPositionUpdaterThread.stopThread();
    }

    /**
     * This is called immediately after the surface is first created.
     *
     * @param holder Represents a {@code SurfaceHolder} object.
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        // Start 'DrawerThread' and 'BackgroundPositionUpdaterThread'
        this.myDrawerThread.setThreadPause(false);
        this.myBackgroundPositionUpdaterThread.setThreadPause(false);

        // Start music if enabled
        this.gameMusic.play();
    }

    /**
     * This is called immediately after any structural changes (format or size) have been made to the surface.
     *
     * @param holder Represents a {@code SurfaceHolder} object.
     * @param format Represents an {@code int}.
     * @param width  Represents an {@code int}.
     * @param height Represents an {@code int}.
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        Resources rs = this.myContext.getResources();

        // Loading bitmaps
        Bitmap playerBitmap = BitmapFactory.decodeResource(rs, R.drawable.pacman_sprite);
        Bitmap ghostBitmap = BitmapFactory.decodeResource(rs, R.drawable.ghost_sprite);
        Bitmap fruitBitmap = BitmapFactory.decodeResource(rs, R.drawable.fruit_sprite);
        Bitmap powerUpBitmap = BitmapFactory.decodeResource(rs, R.drawable.power_up_sprite);

        // Loading bitmap background and resize it
        Bitmap tempGameBackground = BitmapFactory.decodeResource(rs, R.drawable.background);
        this.myGameBackground.setBackground(Support.getResizedBitmap(tempGameBackground, height, width));

        // Loading sprite...
        // Add enemies
        for (int i = 1; i <= this.myGameDifficulty.getNumberOfEnemies(); i++) {
            this.mySpriteVector.add(new Ghost(ghostBitmap));
        }

        // Add fruits
        for (int i = 1; i <= this.myGameDifficulty.getNumberOfFruits(); i++) {
            this.mySpriteVector.add(new Fruit(fruitBitmap));
        }

        // Add power up
        this.mySpriteVector.add(new PowerUp(powerUpBitmap));

        // Loading player
        this.myPacMan = new PacMan(playerBitmap);
        this.myPacMan.updateBitmap(height, width);

        // Resize bitmap enemies sprite
        for (Sprite obj : this.mySpriteVector)
            obj.updateBitmap(height, width);

        // Reset Game
        resetGame();
    }

    /***********************************************************************************************
     * Game event
     **********************************************************************************************/

    /**
     * This method is used to END game and open 'Result' activity
     */
    private void endGame() {

        try {
            sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Add one object
        IntentHelper myIntentHelper = IntentHelper.getInstance();
        myIntentHelper.addObjectForKey(this.myStatistics, "statisticsObject");
        Support.openNewActivityWithoutHistory(myContext, ResultActivity.class);
    }

    /**
     * This method is used to RESET game
     */
    private void resetGame() {

        // Pause threads...
        this.myTimerUpdaterThread.setThreadPause(true);
        this.myFrameUpdaterThread.setThreadPause(true);
        this.myPositionUpdaterThread.setThreadPause(true);

        // Get Lock
        Game.getMyLock().lock();

        // Add a new player sprite
        this.mySpriteVector.add(this.myPacMan);

        // Reset sprite positions
        for (Sprite obj : this.mySpriteVector)
            obj.resetPositionAndSpeed(this.getHeight(), this.getMeasuredWidth());

        // Get Lock
        Game.getMyLock().unlock();

        this.myStatus = GameStatus.STOPPED;
    }

    /**
     * This method is used to PAUSE game.
     */
    public void pauseGame() {
        if (!(this.myStatus == GameStatus.PAUSED)) {
            this.myStatus = GameStatus.PAUSED;

            this.myPositionUpdaterThread.setThreadPause(true);
            this.myTimerUpdaterThread.setThreadPause(true);
            this.myFrameUpdaterThread.setThreadPause(true);

            this.gameMusic.pause();
            this.pauseSoundEffect.play();

            DialogInterface.OnClickListener resumeGameEvent = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    // reset sounds
                    gameMusic.play();
                    pauseSoundEffect.restart();

                    startGame();
                    dialog.dismiss();
                }
            };

            DialogInterface.OnClickListener resetGameEvent = new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    // reset sounds
                    gameMusic.play();
                    pauseSoundEffect.restart();

                    mySpriteVector.remove(myPacMan);
                    resetGame();

                    // Reset player's statistics
                    life = 2;
                    myStatistics.setFruits(0);
                    myStatistics.setGameScore(0);
                    myStatistics.setSurvivalTime(new Time(0));
                    dialog.dismiss();
                }
            };

            DialogInterface.OnClickListener backToMainMenuEvent = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Support.openNewActivityWithoutHistory(myContext, MainActivity.class);
                    dialog.dismiss();
                }
            };


            DialogBuilder.pauseDialog(myContext, resumeGameEvent, backToMainMenuEvent, resetGameEvent);
        }
    }

    /**
     * This method is used to START game.
     */
    private void startGame() {
        this.myStatus = GameStatus.RUNNING;

        this.myTimerUpdaterThread.setThreadPause(false);
        this.myFrameUpdaterThread.setThreadPause(false);
        this.myPositionUpdaterThread.setThreadPause(false);
    }

    /**
     * This method is used after death animation
     *
     * @param sprite Represents a {@code Sprite} object.
     */
    public void updateGameAfterDeathAnimation(Sprite sprite) {

        // Get Lock
        Game.getMyLock().lock();
        this.mySpriteVector.remove(sprite);
        // Release Lock
        Game.getMyLock().unlock();

        resetGame();
        this.life--;

        if (this.life == 0) {
            this.myStatus = GameStatus.GAME_OVER;
            endGame();
        }
    }

    /***********************************************************************************************
     * Collisions
     **********************************************************************************************/

    /**
     * This method is used when a enemy-player collision is captured.
     */
    public void updateAfterEnemyCollision(Sprite obj) {

        // Get Lock
        Game.getMyLock().lock();

        // Collision happen when player is invincible
        if (this.myGamePlayerStatus == GamePlayerStatus.INVINCIBLE) {

            this.myStatistics.addOneKilledEnemy();
            this.myGamePointsToDrawVector.add(new GamePointsToDraw(obj.getPosX(), obj.getPosY(), String.valueOf(this.myStatistics.getGameDifficulty().getDifficultyPoints())));
            obj.resetPositionAndSpeed(this.getHeight(), this.getWidth());


        } else {
            int x = this.myPacMan.getPosX();
            int y = this.myPacMan.getPosY();

            Bitmap bitmap = BitmapFactory.decodeResource(this.myContext.getResources(), R.drawable.pacman_death);
            PacManDeath myPacManDeath = new PacManDeath(bitmap, this);
            myPacManDeath.updateBitmap(this.getHeight(), this.getWidth());

            myPacManDeath.setPosX(x);
            myPacManDeath.setPosY(y);

            this.mySpriteVector.add(myPacManDeath);
            this.mySpriteVector.remove(this.myPacMan);
        }

        // Release Lock
        Game.getMyLock().unlock();
    }

    /**
     * This method is used when a fruit-player collision is captured.
     */
    public void updateAfterFruitCollision(Sprite obj) {
        this.myStatistics.addOneFruit();
        this.myGamePointsToDrawVector.add(new GamePointsToDraw(obj.getPosX(), obj.getPosY(), String.valueOf(myStatistics.getGameDifficulty().getFruitsPoints())));
        obj.resetPositionAndSpeed(this.getHeight(), this.getWidth());
    }


    /**
     * This method is used when a invincible timer expired
     */
    public void updateAfterInvincibleTimerExpired() {
        Bitmap invulnerableGhost = BitmapFactory.decodeResource(myContext.getResources(), R.drawable.ghost_sprite);

        // Get Lock
        Game.getMyLock().lock();

        this.myGamePointsToDrawVector.add(new GamePointsToDraw(this.myPacMan.getPosX(), this.myPacMan.getPosY(), this.myContext.getString(R.string.str_normal_status)));
        this.myGamePlayerStatus = GamePlayerStatus.NORMAL;

        for (Sprite obj : this.mySpriteVector) {

            if(obj.getClass().getName().equals(Ghost.class.getName()))
            {
                obj.setMyBitmap(invulnerableGhost);
                obj.updateBitmap(this.getHeight(), this.getWidth());
            }

        }

        // Release Lock
        Game.getMyLock().unlock();

    }

    /**
     * This method is used when a powerUP-player collision is captured.
     */
    public void updateAfterPowerUpCollision(Sprite sprite) {

        myGamePointsToDrawVector.add(new GamePointsToDraw(sprite.getPosX(), sprite.getPosY(), myContext.getString(R.string.str_invincible_status)));
        sprite.resetPositionAndSpeed(this.getHeight(), this.getWidth());

        Bitmap vulnerableGhost = BitmapFactory.decodeResource(myContext.getResources(), R.drawable.ghost_sprite_vulnerabile);

        // Get Lock
        Game.getMyLock().lock();

        this.invincibleStatusTimer = 5;
        this.myGamePlayerStatus = GamePlayerStatus.INVINCIBLE;

        for (Sprite obj : this.mySpriteVector) {
            if(obj.getClass().getName().equals(Ghost.class.getName()))
            {
                obj.setMyBitmap(vulnerableGhost);
                obj.updateBitmap(this.getHeight(), this.getWidth());
            }
        }

        // Release Lock
        Game.getMyLock().unlock();
    }

    public void subOneSecondToInvincibleStatusTimer() {
        this.invincibleStatusTimer--;
    }

    public void createTextInvincibleTimerRemain()
    {
        this.myGamePointsToDrawVector.add(new GamePointsToDraw(this.myPacMan.getPosX(), this.myPacMan.getPosY(), String.valueOf(this.invincibleStatusTimer)));
    }


    /***********************************************************************************************
     * Gestures
     **********************************************************************************************/

    /**
     * This method is used to after ON_FLING GESTURE
     *
     * @param arg0 - Represents a {@code double}.
     */
    @Override
    public void updateAfterOnFling(double arg0) {

        if (this.myStatus == GameStatus.RUNNING)
            myPacMan.updateSpeed(arg0);
    }

    /**
     * This method is used to after SINGLE TAP GESTURE
     */
    @Override
    public void updateAfterOnSingleTapUp() {
        if (this.myStatus == GameStatus.STOPPED)
            startGame();
        if (this.myStatus == GameStatus.PAUSED)
            startGame();
    }

    /**
     * This method is used after DOUBLE TAP GESTURE
     */
    @Override
    public void updateAfterDoubleTap() {

        if (this.myStatus == GameStatus.RUNNING)
            myPacMan.stopPlayer();
    }
}
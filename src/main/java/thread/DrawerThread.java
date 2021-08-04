package thread;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.SurfaceHolder;
import game.Game;
import game.GamePointsToDraw;
import game.GameStatus;
import rg.pac_space.R;
import sprite.Sprite;
import statistics.Statistics;

public class DrawerThread extends AbstractThread {

    private static final String MY_TIME_FORMAT = "mm:ss";
    private Canvas myCanvas;
    private Game myGame;
    private Paint myTextPaint;

    // Bitmap
    private Bitmap lifeIconBitmap;

    /**
     * Constructs a newly allocated {@code DrawerThread} object.
     *
     * @param arg0           Represents a {@code Game} object.
     * @param lifeIconBitmap Represents a {@code Bitmap} object.
     */
    public DrawerThread(Game arg0, Bitmap lifeIconBitmap) {

        this.myGame = arg0;
        this.lifeIconBitmap = lifeIconBitmap;

        this.myTextPaint = new Paint();
        myTextPaint.setColor(Color.WHITE);
        myTextPaint.setAntiAlias(true);
        myTextPaint.setTextSize(50);
        myTextPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
    }

    @Override
    public void run() {

        SurfaceHolder mySurfaceHolder = null;

        while (isRunning) {

            while (isPaused) {
                yield();
            }

                try {
                    mySurfaceHolder = this.myGame.getHolder();

                    if (mySurfaceHolder != null) {

                        // This instruction return a "Canvas" object that is used to draw into the surface. It can be "null".
                        this.myCanvas = mySurfaceHolder.lockCanvas();


                        if (this.myCanvas != null) {

                            // First draw background
                            drawBackground();

                            // GAME RUNNING
                            if (this.myGame.getMyStatus() == GameStatus.RUNNING || this.myGame.getMyStatus() == GameStatus.PAUSED) {
                                drawSprite();
                                drawPoints();
                            }

                            // GAME STOPPED
                            if (this.myGame.getMyStatus() == GameStatus.STOPPED) {
                                int posX = this.myGame.getWidth() / 3;
                                int posY = this.myGame.getHeight() / 2;

                                this.myCanvas.drawText(myGame.getContext().getString(R.string.str_tapToStart), posX, posY, this.myTextPaint);
                            }

                            // GAME OVER
                            if (this.myGame.getMyStatus() == GameStatus.GAME_OVER) {
                                int posX = this.myGame.getWidth() / 3;
                                int posY = this.myGame.getHeight() / 2;

                                this.myCanvas.drawText(myGame.getContext().getString(R.string.str_gameOver), posX, posY, this.myTextPaint);
                            }

                            drawHUD();
                        }
                    }

                } catch (IllegalArgumentException e) {
                    Log.e(String.valueOf(Thread.currentThread().getId()) + ":", "Error during lockCanvas():\n" + e.getMessage());
                } finally {
                    if (myCanvas != null && mySurfaceHolder != null) {
                        try {
                            // Finish editing pixels in the surface.
                            // After this call, the surface's current pixels will be shown on the screen
                            mySurfaceHolder.unlockCanvasAndPost(myCanvas);

                        } catch (IllegalArgumentException e) {
                            Log.e(String.valueOf(Thread.currentThread().getId()) + ":", "Error during unlockCanvasAndPost():\n" + e.getMessage());
                        }
                    }
                }
            }

    }

    /***********************************************************************************************
     * Drawing method
     */

    /**
     * This method is used to draw background.
     */
    private void drawBackground() {
        this.myGame.getMyGameBackground().drawBackground(this.myCanvas, this.myGame.getMeasuredWidth());
    }

    /**
     * This method is used to draw time game.
     */
    private void drawHUD() {

        Statistics statistics = this.myGame.getMyStatistics();

        int posY = this.lifeIconBitmap.getHeight() * 2;

        this.myCanvas.drawBitmap(this.lifeIconBitmap, this.lifeIconBitmap.getWidth(), this.lifeIconBitmap.getHeight(), null);



        // Life
        String var0 = " x " + String.valueOf(myGame.getLife()) + " - ";

        // Fruits
        String var1 = this.myGame.getMyContext().getString(R.string.str_fruit) + ": " + String.valueOf(statistics.getFruits()) + " - ";

        // Time
        String var2 = this.myGame.getMyContext().getString(R.string.str_time) + ": " + (String.valueOf(DateFormat.format(MY_TIME_FORMAT, statistics.getSurvivalTime())));

        this.myCanvas.drawText(var0 + var1 + var2, this.lifeIconBitmap.getWidth()*2, posY, this.myTextPaint);
    }

    /**
     * This method is used to draw {@code Sprite} object..
     */
    private void drawSprite() {

        // Get Lock
        Game.getMyLock().lock();

            // Draw enemies Sprite
            for (Sprite obj : this.myGame.getMySpriteVector())
                obj.draw(this.myCanvas);

        // Release Lock
        Game.getMyLock().unlock();
    }

    /**
     * This method is used to draw {@code GamePointsToDraw} object..
     */
    private void drawPoints() {

        // Get Lock
        Game.getMyLock().lock();

        // Draw enemies Sprite
        for (GamePointsToDraw obj : this.myGame.getMyGamePointsToDrawVector()) {
            this.myCanvas.drawText(obj.getPointsText(), obj.getPosX(), obj.getPosY(), obj.getMyTextPaint());
            obj.updatePosY();
            obj.updateTextPaint();
        }


        // Release Lock
        Game.getMyLock().unlock();
    }



}

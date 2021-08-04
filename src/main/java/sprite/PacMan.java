package sprite;

import android.graphics.Bitmap;

public class PacMan extends Sprite {

    public PacMan(Bitmap arg1) {
        this.BMP_ROWS = 4;
        this.BMP_COLUMNS = 2;
        this.myBitmap = arg1;

        spriteInitialization();
    }

    /**
     * This method is used to reset position and speed.
     *
     * @param surfaceHeight Represents an {@code int}.
     * @param surfaceWidth  Represents an {@code int}.
     */
    @Override
    public void resetPositionAndSpeed(int surfaceHeight, int surfaceWidth) {
        this.posY = surfaceHeight / 2;
        this.posX = 2 * this.spriteWidth;

        this.xSpeed = 0;
        this.ySpeed = 0;
    }

    /**
     * This method is used to update position of current {@code Sprite} object.
     *
     * @param surfaceWidth  Represents a {@code int}.
     * @param surfaceHeight Represents a {@code int}.
     */
    @Override
    public void updatePosition(int surfaceWidth, int surfaceHeight) {

        if (this.posX + this.xSpeed + this.spriteWidth < surfaceWidth && this.posX + this.xSpeed + this.spriteWidth > 0)
            posX = posX + (int) xSpeed;

        if (this.posY + this.ySpeed + this.spriteHeight < surfaceHeight && this.posY + this.ySpeed + this.spriteWidth > 0)
            posY = posY + (int) ySpeed;
    }


    /**
     * This method is used to set 'xSpeed' and 'ySpeed' to zero.
     */
    public void stopPlayer() {
        this.xSpeed = 0;
        this.ySpeed = 0;
    }

    /**
     * This method update {@code xSpeed} and {@code ySpeed} after a game.observer.gesture
     *
     * @param arg0 - Represents a {@code double}.
     */
    public void updateSpeed(double arg0) {
        this.xSpeed = Math.cos(Math.toRadians(arg0)) * 10;
        this.ySpeed = -Math.sin(Math.toRadians(arg0)) * 10;
    }
}

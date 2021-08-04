package sprite;

import android.graphics.Bitmap;

import java.util.Random;

public class Ghost extends Sprite {

    public Ghost(Bitmap arg1) {
        this.BMP_ROWS = 4;
        this.BMP_COLUMNS = 2;
        this.myBitmap = arg1;

        spriteInitialization();
    }

    /**
     * This method is used to reset position and speed of current {@code Sprite} object.
     *
     * @param surfaceHeight Represents an {@code int}.
     * @param surfaceWidth  Represents an {@code int}.
     */
    @Override
    public void resetPositionAndSpeed(int surfaceHeight, int surfaceWidth) {
        this.posY = new Random().nextInt(surfaceHeight);
        this.posX = surfaceWidth + (2 * this.spriteWidth);

        this.xSpeed = -(new Random().nextInt(30 - 10) + 10);
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
        if (this.posX + this.xSpeed < surfaceWidth && this.posX + this.xSpeed > 0)
            posX = posX + (int) xSpeed;
        else {
            posX = surfaceWidth - 1;
            posY = new Random().nextInt(surfaceHeight);
        }
    }
}

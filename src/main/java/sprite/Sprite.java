package sprite;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import utility.Support;

public abstract class Sprite {

    protected Bitmap myBitmap;
    protected int BMP_ROWS;
    protected int BMP_COLUMNS;

    protected int currentFrame = 0;
    protected int animationRow = 0;

    protected int spriteWidth;
    protected int spriteHeight;

    protected int posX;
    protected int posY;

    protected double xSpeed;
    protected double ySpeed;

    protected void spriteInitialization() {
        this.spriteWidth = this.myBitmap.getWidth() / this.BMP_COLUMNS;
        this.spriteHeight = this.myBitmap.getHeight() / this.BMP_ROWS;
    }

    /**
     * This method is used to get bound of a {@code Sprite} object.
     *
     * @return Represents an {@code Rect} object.
     */
    public Rect getBound() {
        return new Rect(posX, posY, posX + this.spriteWidth, posY + this.spriteHeight);
    }

    /**
     * This method is used to update position of current {@code Sprite} object.
     *
     * @param limitWidth  Represents a {@code int}.
     * @param limitHeight Represents a {@code int}.
     */
    public abstract void updatePosition(int limitWidth, int limitHeight);


    /**
     * This method is used to reset position and speed of current {@code Sprite} object.
     *
     * @param surfaceHeight Represents an {@code int}.
     * @param surfaceWidth  Represents an {@code int}.
     */
    public abstract void resetPositionAndSpeed(int surfaceHeight, int surfaceWidth);


    /**
     * This method is used to update current frame of current {@code Sprite} object.
     */
    public void updateFrame() {

        double rad = Math.atan2(this.ySpeed, this.xSpeed) + Math.PI;
        double angle = (rad * 180 / Math.PI + 180) % 360;

        if (Support.inRange(angle, 45, 135)) {
            animationRow = 0;
        } else if (Support.inRange(angle, 0, 45) || Support.inRange(angle, 315, 360)) {
            animationRow = 2;
        } else if (Support.inRange(angle, 225, 315)) {
            animationRow = 3;
        } else {
            animationRow = 1;
        }

        if (this.currentFrame == BMP_COLUMNS - 1)
            this.currentFrame = 0;
        else
            currentFrame++;
    }

    /**
     * This method is used to draw current {@code Sprite} object.
     *
     * @param arg0 Represents a {@code Canvas} object.
     */
    public void draw(Canvas arg0) {
        int srcX = currentFrame * spriteWidth;
        int srcY = animationRow * spriteHeight;

        Rect src = new Rect(srcX, srcY, srcX + spriteWidth, srcY + spriteHeight);
        Rect dst = new Rect(posX, posY, posX + spriteWidth, posY + spriteHeight);

        arg0.drawBitmap(myBitmap, src, dst, null);
    }

    /**
     * This method is used to update {@code Bitmap} object when surface change.
     *
     * @param newSurfaceHeight Represents a {@code int}.
     * @param newSurfaceWidth  Represents a {@code int}.
     */
    public void updateBitmap(double newSurfaceHeight, double newSurfaceWidth) {

        double aspectRatio = newSurfaceWidth / newSurfaceHeight;

        double scaledX = this.myBitmap.getWidth() * aspectRatio * this.BMP_ROWS;
        double scaledY = this.myBitmap.getHeight() * aspectRatio * this.BMP_COLUMNS;

        this.myBitmap = Support.getResizedBitmap(this.myBitmap, (int) scaledX, (int) scaledY);

        this.spriteWidth = this.myBitmap.getWidth() / this.BMP_COLUMNS;
        this.spriteHeight = this.myBitmap.getHeight() / this.BMP_ROWS;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setMyBitmap(Bitmap myBitmap) {
        this.myBitmap = myBitmap;
    }

}

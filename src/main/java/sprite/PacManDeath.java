package sprite;

import android.graphics.Bitmap;

import game.Game;
import utility.Support;

public class PacManDeath extends Sprite {

    private Game myGame;

    public PacManDeath(Bitmap arg0, Game arg1) {
        this.BMP_ROWS = 1;
        this.BMP_COLUMNS = 11;
        this.myBitmap = arg0;

        this.animationRow = 0;
        this.xSpeed = 0;
        this.ySpeed = 0;
        this.myGame = arg1;

        spriteInitialization();
    }

    @Override
    public void updateFrame() {

        if (this.currentFrame == BMP_COLUMNS - 1)
            this.myGame.updateGameAfterDeathAnimation(this);

        currentFrame++;
    }

    @Override
    public void updatePosition(int limitWidth, int limitHeight) {
    }

    @Override
    public void resetPositionAndSpeed(int surfaceHeight, int surfaceWidth) {
    }
}


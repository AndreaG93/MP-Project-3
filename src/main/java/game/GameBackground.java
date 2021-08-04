package game;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import utility.Support;

public class GameBackground {
    // This bitmap represents game background
    private Bitmap background;

    // Represents the position of background
    private int positionBackground;

    /**
     * This method is used to set a new Background
     *
     * @param background - Represents an {@code Bitmap} object.
     */
    public void setBackground(Bitmap background) {
        this.background = background;
    }

    /**
     * This method update the position of current background.
     *
     * @param surfaceWidth - Represents an {@code int}.
     */
    public void setPositionBackground(int surfaceWidth) {
        if (this.positionBackground == surfaceWidth)
            this.positionBackground = 1;
        else
            this.positionBackground++;
    }

    /**
     * This method is used to draw background.
     *
     * @param arg0         - Represents a {@code Canvas} object.
     * @param surfaceWidth - Represents an {@code int}.
     */
    public void drawBackground(Canvas arg0, int surfaceWidth) {
        if (this.background != null) {
            arg0.drawBitmap(this.background, this.positionBackground, 0, null);
            arg0.drawBitmap(this.background, this.positionBackground - surfaceWidth, 0, null);
        }
    }


}

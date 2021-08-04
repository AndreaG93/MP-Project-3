package game;

import android.graphics.Paint;
import android.graphics.Typeface;

public class GamePointsToDraw {

    private final String pointsText;
    private Paint myTextPaint = new Paint();
    private int secondsPermanence = 2;
    private int posX;
    private int posY;

    // Colors
    private final int R = 255;
    private final int G = 255;
    private final int B = 255;
    private int A = 255;

    /**
     * Constructs a newly allocated {@code GamePointsToDraw} object.
     *
     * @param posX   Represents an {@code int}.
     * @param posY   Represents an {@code int}.
     * @param text Represents an {@code int}.
     */
    public GamePointsToDraw(int posX, int posY, String text) {
        this.posX = posX;
        this.posY = posY;
        this.pointsText = text;

        myTextPaint.setARGB(A, R, G, B);
        myTextPaint.setAntiAlias(true);
        myTextPaint.setTextSize(50);
        myTextPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
    }

    /**
     * This method is used to update color about how to draw point text
     */
    public void updateTextPaint() {
        if (A - 5 < 0)
            return;
        A = A - 5;
        myTextPaint.setARGB(A, R, G, B);
    }

    /**
     * This method is used to update position about where to draw point text
     */
    public void updatePosY() {
        this.posY = this.posY - 1;
    }

    /**
     * This method is used to update permanence time
     */
    public void subOneSecond() {
        this.secondsPermanence--;
    }

    /***********************************************************************************************
     * Getter and setter methods.
     **********************************************************************************************/

    public String getPointsText() {
        return pointsText;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getSecondsPermanence() {
        return secondsPermanence;
    }

    public Paint getMyTextPaint() {
        return myTextPaint;
    }
}

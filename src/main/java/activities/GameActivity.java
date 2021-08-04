package activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceView;

import game.Game;
import game.gesture.GestureDetector;

public class GameActivity extends AppCompatActivity {

    private GestureDetector myGestureDetector;
    private Game myGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.myGestureDetector = new GestureDetector(this);
        //SurfaceView surfaceView = new SurfaceView(this);
        this.myGame = new Game(this, this.myGestureDetector);
        setContentView(this.myGame);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.myGestureDetector.getEvent(event);

        // Be sure to call the superclass implementation
        return super.onTouchEvent(event);
    }


    /**
     * This method is used to display a dialog when user touch back button
     *
     * @param event Represents a {@code KeyEvent} object.
     * @return Represents a {@code boolean}
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        if (event.getAction() == KeyEvent.ACTION_DOWN)
            this.myGame.pauseGame();
        return true;
    }
}

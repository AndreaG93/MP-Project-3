package game.gesture;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.view.MotionEvent;

import utility.Support;
import android.view.GestureDetector.*;

public class GestureDetector extends AbstractGestureDetector implements OnGestureListener, OnDoubleTapListener {

    private GestureDetectorCompat myGestureDetectorCompat;

    public GestureDetector(Context arg0) {
        this.myGestureDetectorCompat = new GestureDetectorCompat(arg0, this);
        this.myGestureDetectorCompat.setOnDoubleTapListener(this);
    }

    public void getEvent(MotionEvent event) {
        this.myGestureDetectorCompat.onTouchEvent(event);
    }

    /**
     *
     * @param e1
     * @param e2
     * @param velocityX
     * @param velocityY
     * @return
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        float x1 = e1.getX();
        float y1 = e1.getY();

        float x2 = e2.getX();
        float y2 = e2.getY();

        notifyOnFling(Support.getAngle(x1, y1, x2, y2));
        return true;
    }

    /**
     *
     * @param e
     * @return
     */
    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        notifyDoubleTap();
        return true;
    }

    /**
     *
     * @param event
     * @return
     */
    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        notifyOnSingleTapUp();
        return true;
    }


    @Override
    public boolean onDown(MotionEvent event) {
        return true;
    }

    @Override
    public void onLongPress(MotionEvent event) {
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent event) {
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }
}

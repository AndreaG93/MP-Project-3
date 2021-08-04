package game.gesture;

public interface GestureObserver {

    void updateAfterOnFling(double arg0);

    void updateAfterOnSingleTapUp();

    void updateAfterDoubleTap();
}

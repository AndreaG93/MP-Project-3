package game.gesture;

import java.util.Vector;

public abstract class AbstractGestureDetector {
    private Vector<GestureObserver> listOfObservers = new Vector<>();

    /**
     * This method is used to register a new game.observer.
     *
     * @param arg0 - Represents an {@link GestureObserver} object.
     */
    public void addObserver(GestureObserver arg0) {
        listOfObservers.add(arg0);
    }

    protected void notifyOnFling(double arg0) {
        for (GestureObserver object : listOfObservers) {
            object.updateAfterOnFling(arg0);
        }
    }

    protected void notifyOnSingleTapUp() {
        for (GestureObserver object : listOfObservers) {
            object.updateAfterOnSingleTapUp();
        }
    }

    protected void notifyDoubleTap() {
        for (GestureObserver object : listOfObservers) {
            object.updateAfterDoubleTap();
        }
    }
}

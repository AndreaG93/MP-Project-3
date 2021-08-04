package thread;

public abstract class AbstractThread extends Thread {

    protected boolean isRunning = true;
    protected boolean isPaused = true;

    public void setThreadPause(boolean arg0) {
        this.isPaused = arg0;
    }

    public void stopThread() {
        this.isRunning = false;
    }
}

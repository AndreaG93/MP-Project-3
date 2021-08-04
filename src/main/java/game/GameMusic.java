package game;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;

public class GameMusic {

    private MediaPlayer gameMusic;
    private SharedPreferences mySharedPreferences;

    /**
     * Constructs a newly allocated {@code GameMusic} object.
     *
     * @param arg0 Represents a {@code Context} object.
     * @param arg1 Represents an {@code int}.
     */
    public GameMusic(Context arg0, int arg1, boolean loop) {
        this.mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(arg0);
        this.gameMusic = MediaPlayer.create(arg0, arg1);
        this.gameMusic.setLooping(loop);
    }

    /**
     * This method is used to starts or resume playback.
     */
    public void play() {
        if (mySharedPreferences.getBoolean("EnableAudio", true))
            this.gameMusic.start();
    }

    /**
     * This method is used to pauses playback.
     */
    public void pause() {
        gameMusic.pause();
    }

    /**
     * This method is used to restart playback.
     */
    public void restart() {
        gameMusic.pause();
        gameMusic.seekTo(0);
    }

    /**
     * This method is used to releaseResource and release playback.
     */
    public void releaseResource() {
        gameMusic.release();
    }
}

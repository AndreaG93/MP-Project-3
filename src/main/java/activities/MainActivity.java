package activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import activities.setting.SettingActivity;
import rg.pac_space.R;


public class MainActivity extends AppCompatActivity {

    /**
     * This method is used to initialize current activity.
     */
    private void initializationActivity() {

        final Context myContext = this.getApplicationContext();

        /**
         * Start Button initialization
         */
        Button startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(myContext, GameActivity.class));
            }
        });

        /**
         * Statistics Button initialization
         */
        Button scoreButton = (Button) findViewById(R.id.scoreButton);
        scoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(myContext, ScoreActivity.class));
            }
        });

        /**
         * setting Button initialization
         */
        Button settingButton = (Button) findViewById(R.id.settingButton);
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(myContext, SettingActivity.class));
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializationActivity();


    }
}

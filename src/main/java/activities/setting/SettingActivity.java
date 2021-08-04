package activities.setting;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;

import java.util.Vector;

public class SettingActivity extends Activity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static Vector<SettingObserver> observer = new Vector<SettingObserver>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingFragment()).commit();
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        for (SettingObserver object : observer) {
            object.update();
        }
    }

    public static void registerToObserverList(SettingObserver arg0) {
        observer.add(arg0);
    }
}

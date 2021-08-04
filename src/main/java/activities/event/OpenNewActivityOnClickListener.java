package activities.event;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

/**
 * The {@code OpenNewActivityOnClickListener} is used to open a new Activity.
 *
 * @version 1.0
 */
public class OpenNewActivityOnClickListener implements View.OnClickListener, DialogInterface.OnClickListener {

    private Context myContext;
    private Class targetActivity;

    public OpenNewActivityOnClickListener(Context arg0, Class arg1) {
        this.myContext = arg0;
        this.targetActivity = arg1;
    }

    private void startNewActivity() {
        myContext.startActivity(new Intent(this.myContext, this.targetActivity));
    }


    @Override
    public void onClick(View view) {
        startNewActivity();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        startNewActivity();
    }
}
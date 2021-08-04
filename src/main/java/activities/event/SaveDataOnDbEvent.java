package activities.event;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;

import activities.GameActivity;
import activities.ScoreActivity;
import rg.pac_space.R;
import statistics.StatisticsDatabase;
import utility.Support;

public class SaveDataOnDbEvent implements DialogInterface.OnClickListener {

    private Context myContext;
    private int score;
    private EditText myEditText;

    public SaveDataOnDbEvent(Context arg0, EditText arg1, int arg2)
    {
        this.myContext = arg0;
        this.myEditText = arg1;
        this.score = arg2;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

        String myPlayerName = this.myEditText.getText().toString();
        if (myPlayerName.isEmpty())
            myPlayerName = this.myContext.getString(R.string.str_standardPlayer);

        StatisticsDatabase.getInstance(this.myContext).insert(myPlayerName, score);
        Support.openNewActivityWithoutHistory(myContext, ScoreActivity.class);
    }
}

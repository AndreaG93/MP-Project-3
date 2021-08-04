package utility;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import rg.pac_space.R;

public class DialogBuilder {

    /**
     * This method is used to build and show a {@code pauseDialog} object.
     *
     * @param arg0 - Represents a {@code Context} object.
     * @return - A {@code Dialog} object.
     */
    public static void pauseDialog(Context arg0, DialogInterface.OnClickListener arg1, DialogInterface.OnClickListener arg2, DialogInterface.OnClickListener arg3) {

        // Constructs a new allocated "Builder" object
        AlertDialog.Builder builder = new AlertDialog.Builder(arg0);
        builder.setTitle(R.string.str_pause);
        builder.setCancelable(false);
        builder.setIcon(arg0.getDrawable(R.drawable.ghost_icon));

        // setting ok button...
        builder.setNegativeButton(R.string.str_resume, arg1);
        builder.setPositiveButton(R.string.str_backToMainMenu, arg2);
        builder.setNeutralButton(R.string.str_retry, arg3);

        // Create dialog and show
        builder.create().show();
    }


}
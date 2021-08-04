package utility;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;

public class Support {

    public static void openNewActivityWithoutHistory(Context myContext, Class myClass) {
        Intent myIntent = new Intent(myContext, myClass);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        myContext.startActivity(myIntent);
    }

    /**
     * This method is used to resize a specified {@code Bitmap} object.
     *
     * @param arg0        Represents a {@code Bitmap} object.
     * @param finalHeight Represents an {@code int}.
     * @param finalWidth  Represents an {@code int}.
     * @return A {@code Bitmap} object.
     */
    public static Bitmap getResizedBitmap(Bitmap arg0, int finalHeight, int finalWidth) {
        int width = arg0.getWidth();
        int height = arg0.getHeight();
        float scaleWidth = ((float) finalWidth) / width;
        float scaleHeight = ((float) finalHeight) / height;

        // Create a matrix for the manipulation
        Matrix matrix = new Matrix();

        // Resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);

        // Recreate the new Bitmap
        return Bitmap.createBitmap(arg0, 0, 0, width, height, matrix, false);
    }

    /**
     * @param angle an angle
     * @param init  the initial bound
     * @param end   the final bound
     * @return returns true if the given angle is in the interval [init, end).
     */
    public static boolean inRange(double angle, float init, float end) {
        return (angle >= init) && (angle < end);
    }

    /**
     * Finds the angle between two points in the plane (x1,y1) and (x2, y2)
     * The angle is measured with 0/360 being the X-axis to the right, angles
     * increase counter clockwise.
     *
     * @param x1 the posX position of the first point
     * @param y1 the posY position of the first point
     * @param x2 the posX position of the second point
     * @param y2 the posY position of the second point
     * @return the angle between two points
     */
    public static double getAngle(float x1, float y1, float x2, float y2) {

        double rad = Math.atan2(y1 - y2, x2 - x1) + Math.PI;
        return (rad * 180 / Math.PI + 180) % 360;
    }
}

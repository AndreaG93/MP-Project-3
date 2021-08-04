package statistics;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Vector;

public class StatisticsDatabase extends SQLiteOpenHelper {

    private static StatisticsDatabase myInstance = null;

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "Statistics.db";
    private static final String TABLE_NAME = "STATISTICS";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_NAME = "NAME";
    private static final String COLUMN_SCORE = "SCORE";

    /**
     * This method is used to get an instance of {@code StatisticsDatabase} class.
     *
     * @param arg0 - Represents a {@code Context} object.
     * @return A {@code StatisticsDatabase} object.
     */
    public static StatisticsDatabase getInstance(Context arg0) {
        if (myInstance == null) myInstance = new StatisticsDatabase(arg0);
        return myInstance;
    }

    /**
     * Constructs a newly allocated {@code StatisticsDatabase} object.
     *
     * @param context - Represents a {@code Context} object.
     */
    private StatisticsDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This method is called when the database is created for the first time.
     *
     * @param arg0 - Represents a {@code SQLiteDatabase} object.
     */
    @Override
    public void onCreate(SQLiteDatabase arg0) {
        String SQL = String.format("CREATE TABLE %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s INTEGER)", TABLE_NAME, COLUMN_ID, COLUMN_NAME, COLUMN_SCORE);
        arg0.execSQL(SQL);
    }

    /**
     * Called in the event that the application code contains a more recent database version number reference.
     *
     * @param arg0       - Represents a {@code SQLiteDatabase} object.
     * @param oldVersion - Represents an {@code int}.
     * @param newVersion - Represents an {@code int}.
     */
    @Override
    public void onUpgrade(SQLiteDatabase arg0, int oldVersion, int newVersion) {

        String SQL = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        arg0.execSQL(SQL);
        onCreate(arg0);
    }

    /**
     * This method is used to insert a new record;
     *
     * @param name  - Represents a {@code String} object.
     * @param score - Represents an {@code int} object.
     */
    public void insert(String name, int score) {
        ContentValues var = new ContentValues();
        var.put(COLUMN_NAME, name);
        var.put(COLUMN_SCORE, score);
        this.getWritableDatabase().insertOrThrow(TABLE_NAME, null, var);
    }

    /**
     * This method is user to get a {@code Vector} object that contain {@code Statistics} object.
     * {@code nRows} is used to specified how many elements this vector is made up of;
     * If {@code nRows} is -1, this method returns all available {@code Statistics} objects.
     *
     * @param nRows - Represents an {@code int} object.
     * @return A {@code Vector<>} object.
     */
    public Vector<Statistics> get(int nRows) {

        // Create SQL String
        String SQL = String.format("SELECT * FROM %s ORDER BY %s DESC", TABLE_NAME, COLUMN_SCORE);

        // Initialization new Vector<> object
        Vector<Statistics> myVector = new Vector<>();

        // Initialization new Cursor object
        Cursor cur = this.getReadableDatabase().rawQuery(SQL, null);

        // Initialization of a counter
        int x = 0;

        if (nRows == -1)
            nRows = cur.getCount();

        // Get data
        while (cur.moveToNext() && x < nRows) {
            Statistics obj = new Statistics(null);
            obj.setPlayerName(cur.getString(cur.getColumnIndex(COLUMN_NAME)));
            obj.setGameScore(cur.getInt(cur.getColumnIndex(COLUMN_SCORE)));
            myVector.add(obj);
            x++;
        }


        // Closes the Cursor, releasing all of its resources and making it completely invalid.
        cur.close();
        return myVector;
    }


    /**
     * This method is used to get max score from DB.
     *
     * @return An {@code int}.
     */
    public int getMaxScore() {
        int x = -1;

        // Create SQL String
        String SQL = String.format("SELECT MAX(%s) FROM %s", COLUMN_SCORE, TABLE_NAME);

        // Initialization new Cursor object
        Cursor cur = this.getReadableDatabase().rawQuery(SQL, null);

        // Get data
        while (cur.moveToNext()) {
            x = cur.getInt(0);
        }

        // Closes the Cursor, releasing all of its resources and making it completely invalid.
        cur.close();
        return x;
    }
}

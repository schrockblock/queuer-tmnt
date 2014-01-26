package com.tmnt.queuer.databases;

/**
 * Created by billzito on 1/25/14.
 */

        import android.content.Context;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.util.Log;

public class ProjectOpenHelper extends SQLiteOpenHelper {

    public static final String TABLE_PROJECTS = "projects";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SERVER_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_COLOR = "color";
    public static final String COLUMN_CREATED = "created_at";
    public static final String COLUMN_UPDATED = "updated_at";
    public static final String COLUMN_HIDDEN = "hidden";

    private static final String DATABASE_NAME = "projects.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_PROJECTS + "(" + COLUMN_ID
            + " integer primary key autoincrement, "
            + COLUMN_SERVER_ID + " integer, "
            + COLUMN_TITLE + " text not null, "
            + COLUMN_COLOR + " integer, "
            + COLUMN_HIDDEN + " integer, "
            + COLUMN_CREATED + " integer, "
            + COLUMN_UPDATED + " integer"
            + ");";

    public ProjectOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(ProjectOpenHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROJECTS);
        onCreate(db);
    }
}

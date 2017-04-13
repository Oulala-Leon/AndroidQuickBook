package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tankul on 13/04/17.
 */

public class Database_Helper extends SQLiteOpenHelper {

    public Database_Helper(Context context) {
        super(context, Database.DB_NAME, null, Database.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + Database.Book.TABLE + " ( " +
                Database.Book._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Database.Book.PAGE_CONTENTS + " TEXT NOT NULL , " +
                Database.Book.TEXT_TYPE + " INTEGER);";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + Database.Book.TABLE);
        onCreate(db);
    }
}

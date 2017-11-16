package com.matylionak.valery.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "task";
    private static final int VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE task('ID' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'title' TEXT, 'description' TEXT, 'progress' INTEGER,'startTime' TEXT," +
                "'startDate' TEXT, 'endTime' TEXT, 'endDate' TEXT, 'state' INTEGER, 'estimated' INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

package com.example.tabataapplication.DatabaseHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "timerDatabase.db";
    private static final int SCHEMA = 1;
    //table names
    static final String PHASES_TABLE = "phases";
    static final String SEQUENCE_TABLE = "sequences";
    //columns names
    public static final String PHASE_ID = "_id";
    public static final String FK_SEQUENCE_ID = "_id_sequence";
    public static final String ACTION_NAME = "actionName";
    public static final String TIME = "time";
    public static final String DESCRIPTION = "description";

    public static final String SETS_AMOUNT = "setsAmount";
    public static final String SEQUENCE_ID = "_id";
    public static final String TITLE = "title";
    public static final String COLOUR = "colour";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + SEQUENCE_TABLE + " (" + SEQUENCE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TITLE + " TEXT, " + SETS_AMOUNT + " INTEGER," + COLOUR + " INTEGER);");

        db.execSQL("CREATE TABLE " + PHASES_TABLE + " (" + PHASE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FK_SEQUENCE_ID + " INTEGER,"
                + ACTION_NAME + " TEXT, " + TIME + " INTEGER, " + DESCRIPTION + " TEXT, " +
                "FOREIGN KEY(" + FK_SEQUENCE_ID + ") REFERENCES " + SEQUENCE_TABLE + "(" + SEQUENCE_ID + "));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SEQUENCE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PHASES_TABLE);
        onCreate(db);
    }
}

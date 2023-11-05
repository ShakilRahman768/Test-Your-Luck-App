package com.example.testyourluck;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "CoinDatabase";
    private static final int DATABASE_VERSION = 1;

    // Table and column names
    private static final String COINS_TABLE = "coins_table";
    private static final String COLUMN_COIN_COUNT = "coin_count";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the coins table
        String createCoinsTableQuery = "CREATE TABLE " + COINS_TABLE +
                " (" + COLUMN_COIN_COUNT + " INTEGER)";
        db.execSQL(createCoinsTableQuery);

        // Insert initial data (starting with 0 coins)
        String insertInitialDataQuery = "INSERT INTO " + COINS_TABLE +
                " (" + COLUMN_COIN_COUNT + ") VALUES (0)";
        db.execSQL(insertInitialDataQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the old table and recreate it for simplicity in this example
        db.execSQL("DROP TABLE IF EXISTS " + COINS_TABLE);
        onCreate(db);
    }

    public void updateCoins(int newCoinCount) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE " + COINS_TABLE + " SET " + COLUMN_COIN_COUNT + " = " + newCoinCount);
        db.close();
    }

    public int getCoinCount() {
        SQLiteDatabase db = getReadableDatabase();
        int coinCount = 0;
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_COIN_COUNT + " FROM " + COINS_TABLE, null);
        if (cursor.moveToFirst()) {
            coinCount = cursor.getInt(cursor.getColumnIndex(COLUMN_COIN_COUNT));
        }
        cursor.close();
        db.close();
        return coinCount;
    }
}

package com.veriqus.savoirvivre;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import static android.icu.text.MessagePattern.ArgType.SELECT;


public class DatabaseOpenHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "dat.db";
    private static final int DATABASE_VERSION = 1;
    public static final int DATv = DATABASE_VERSION;

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        setForcedUpgrade();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
    }

}

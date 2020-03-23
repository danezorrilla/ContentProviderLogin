package com.bb.contentproviderlogin.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.bb.contentproviderlogin.model.Guest;

public class GuestDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "guest.db";
    public static final String TABLE_NAME = "guest";
    public static final String COLUMN_GUEST_ID = "guest_id";
    public static final String COLUMN_GUEST_NAME = "guest_name";
    public static final String COLUMN_GUEST_ROOM_NUMBER = "guest_room_number";
    public static final int DB_VERSION = 1;

    public GuestDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_GUEST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_GUEST_NAME + " TEXT, " + COLUMN_GUEST_ROOM_NUMBER + " TEXT)";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String updateTable = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(updateTable);
        onCreate(db);
    }

    public void addNewGuest(Guest guest){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_GUEST_NAME, guest.getGuestName());
        contentValues.put(COLUMN_GUEST_ROOM_NUMBER, guest.getGuestRoomNumber());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME, null, contentValues);
        db = null;
    }

    public Cursor readAllGuest(){
        Cursor allGuest = getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME, null, null);
        return allGuest;
    }
}

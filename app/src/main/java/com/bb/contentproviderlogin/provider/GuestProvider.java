package com.bb.contentproviderlogin.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bb.contentproviderlogin.database.GuestDatabaseHelper;

public class GuestProvider extends ContentProvider{
    private String authority = "com.bb.contentproviderlogin.provider.GuestProvider";
    private String url = "content://"+authority+"/"+ GuestDatabaseHelper.TABLE_NAME;

    private static final int SINGLE_GUEST = 1;
    private static final int ALL_GUEST = 2;

    private UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private GuestDatabaseHelper databaseHelper = null;

    @Override
    public boolean onCreate() {
        uriMatcher.addURI(authority, "guests/#", SINGLE_GUEST);
        uriMatcher.addURI(authority, "guests", ALL_GUEST);

        try{
            databaseHelper = new GuestDatabaseHelper(getContext());
        } catch(Exception e){
            e.printStackTrace();
        }
        return (databaseHelper != null);
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor guestCursor = null;

        switch(uriMatcher.match(uri)){
            case ALL_GUEST:
                guestCursor = databaseHelper.readAllGuest();
                break;
        }
        return guestCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}

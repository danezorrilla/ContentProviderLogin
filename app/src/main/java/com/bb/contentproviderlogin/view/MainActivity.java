package com.bb.contentproviderlogin.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.bb.contentproviderlogin.R;
import com.bb.contentproviderlogin.adapter.GuestAdapter;
import com.bb.contentproviderlogin.database.GuestDatabaseHelper;
import com.bb.contentproviderlogin.model.Guest;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private List<Guest> guestList = new ArrayList<Guest>();


    private GuestDatabaseHelper guestDatabaseHelper;

    @BindView(R.id.login_userName)
    EditText guestNameEditText;

    @BindView(R.id.login_userRoom)
    EditText guestRoomNumberEditText;

    @BindView(R.id.hotel_recyclerView)
    RecyclerView hotelGuestList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        guestDatabaseHelper = new GuestDatabaseHelper(this);
        readFromContentProvider();
        readDatabase();
    }

    @OnClick(R.id.login_Login)
    public void AddNewGuest(View view){
        String guestName = guestNameEditText.getText().toString();
        String guestRoomNumber = guestRoomNumberEditText.getText().toString();

        Guest newGuest = new Guest(guestName, guestRoomNumber);
        guestDatabaseHelper.addNewGuest(newGuest);

        guestNameEditText.setText("");
        guestRoomNumberEditText.setText("");

        readDatabase();
    }

    private void readFromContentProvider(){
        String uri = "content://com.bb.contentproviderlogin.provider.GuestProvider/guests";

        ContentResolver contentResolver = getContentResolver();

        Cursor guestCursor= contentResolver.query(Uri.parse(uri), null, null, null,null);

        guestCursor.moveToPosition(-1);
        guestList.clear();
        Log.d("TAG_H", "Checking: " + guestList );

        while(guestCursor.moveToNext()){

            String guestName = guestCursor.getString(guestCursor.getColumnIndex(GuestDatabaseHelper.COLUMN_GUEST_NAME));
            String guestRoomNum = guestCursor.getString(guestCursor.getColumnIndex(GuestDatabaseHelper.COLUMN_GUEST_ROOM_NUMBER));

            guestList.add(new Guest(guestName, guestRoomNum));
            Log.d("TAG_X", "From Provider: " + guestName);
        }

        guestCursor.close();
    }

    private void readDatabase(){
        Cursor guestCursor = guestDatabaseHelper.readAllGuest();
        guestCursor.move(-1);
        guestList.clear();

        while(guestCursor.moveToNext()){
            String guestName = guestCursor.getString(guestCursor.getColumnIndex(GuestDatabaseHelper.COLUMN_GUEST_NAME));
            String guestRoomNum = guestCursor.getString(guestCursor.getColumnIndex(GuestDatabaseHelper.COLUMN_GUEST_ROOM_NUMBER));

            guestList.add(new Guest(guestName, guestRoomNum));
        }

        updateAdapter();
        guestCursor.close();
    }

    private void updateAdapter(){
        GuestAdapter guestAdapter = new GuestAdapter(guestList);
        hotelGuestList.setLayoutManager(new LinearLayoutManager(this));
        hotelGuestList.setAdapter(guestAdapter);
    }

}

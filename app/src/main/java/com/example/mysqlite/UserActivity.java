package com.example.mysqlite;
import androidx.appcompat.app.AppCompatActivity;


import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity {
    List<User> userList;
    ListView recyclerView;
    UserDbHelper mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        mDatabase = new UserDbHelper(this);

        recyclerView = findViewById(R.id.recyclerview);
        userList = new ArrayList<>();
        loadUsersFromDatabase();

    }

    private void loadUsersFromDatabase() {

        Cursor cursor = mDatabase.getAllUser();
        if (cursor.moveToFirst()) {
            do {
                userList.add(new User(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();



        UserAdapter userAdapter = new UserAdapter(this,R.layout.list_layout_user,userList,mDatabase);
        recyclerView.setAdapter(userAdapter);
    }

}


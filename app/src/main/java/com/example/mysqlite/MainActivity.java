package com.example.mysqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String DATABASE_NAME = "myuserdatabase";

    EditText editFirstName;
    EditText editLastName;
    EditText editSalary;
    UserDbHelper mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatabase = new UserDbHelper(this);

        editFirstName = findViewById(R.id.first_name);
        editLastName = findViewById(R.id.last_name);
        editSalary = findViewById(R.id.salary);

        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.viewusers).setOnClickListener(this);
    }

    private void addUser() {
        String firstname = editFirstName.getText().toString().trim();
        String lastname = editLastName.getText().toString().trim();
        String salary = editSalary.getText().toString();


        if (firstname.isEmpty()) {
            editFirstName.setError("Name can't be empty");
            editFirstName.requestFocus();
            return;
        }
        if (lastname.isEmpty()) {
            editLastName.setError("Name can't be empty");
            editLastName.requestFocus();
            return;
        }

        if (salary.isEmpty()) {
            editSalary.setError("Salary can't be empty");
            editSalary.requestFocus();
            return;
        }
        if (mDatabase.addUser(firstname, lastname, Integer.parseInt(salary)))
        {  Toast.makeText(this, "User Added", Toast.LENGTH_SHORT).show();
        }
     
        else
            Toast.makeText(this, "Could not User", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                addUser();
                break;
            case R.id.viewusers:
                startActivity(new Intent(this, UserActivity.class));

                break;
        }
    }

}

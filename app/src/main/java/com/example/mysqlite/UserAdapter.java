package com.example.mysqlite;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

public class UserAdapter extends ArrayAdapter<User>{


   Context context;
    int listLayoutRes;
    List<User> userList;
    UserDbHelper mDatabase;


    public UserAdapter(Context context, int listLayoutRes, List<User> userList, UserDbHelper mDatabase) {
        super(context,listLayoutRes,userList);
        this.context = context;
        this.listLayoutRes = listLayoutRes;
        this.userList=userList;
        this.mDatabase = mDatabase;
    }




    @NonNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(listLayoutRes, null);

        TextView textViewFirstname = view.findViewById(R.id.textViewFirstname);
        TextView textViewLastname = view.findViewById(R.id.textViewLastname);
        TextView textViewSalary = view.findViewById(R.id.textViewsalary);


        final User user =  userList.get(position);

        textViewFirstname.setText(user.getfirstname());
        textViewLastname.setText(user.getlastname());
        textViewSalary.setText(String.valueOf(user.getsalary()));

        final ImageButton buttonDelete = view.findViewById(R.id.buttonDeleteEmployee);
        ImageButton buttonEdit = view.findViewById(R.id.buttonEditEmployee);



        view.findViewById(R.id.buttonDeleteEmployee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteEmployee(user);
            }

            private void deleteEmployee(final User employee) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Are you sure?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //calling the delete method from the database manager instance
                        if (mDatabase.deleteUser(employee.getId()))
                            loadEmployeesFromDatabaseAgain();
                    }

                    private void loadEmployeesFromDatabaseAgain() {
                        Cursor cursor = mDatabase.getAllUser();


                        userList.clear();
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
                        notifyDataSetChanged();


                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        view.findViewById(R.id.buttonEditEmployee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUser(user);
            }
        });
        return view;

    }



    private void updateUser(final User user) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_update_user, null);
        builder.setView(view);

        final AlertDialog dialog = builder.create();
        dialog.show();

        final EditText editTextFirst = view.findViewById(R.id.efirstname);
        final EditText editTextLast = view.findViewById(R.id.elastname);
        final EditText editTextSal = view.findViewById(R.id.esalary);

        editTextFirst.setText(user.getfirstname());
        editTextLast.setText(user.getlastname());
        editTextSal.setText(String.valueOf(user.getsalary()));


        view.findViewById(R.id.buttonUpdateUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String First = editTextFirst.getText().toString().trim();
                String Last = editTextLast.getText().toString().trim();
                String Salary = editTextSal.getText().toString();

                if (First.isEmpty()) {
                    editTextFirst.setError("Name can't be blank");
                    editTextFirst.requestFocus();
                    return;
                }
                if (Last.isEmpty()) {
                    editTextLast.setError("Name can't be blank");
                    editTextLast.requestFocus();
                    return;
                }
                if (Salary.isEmpty()) {
                    editTextSal.setError("Salary can't be blank");
                    editTextSal.requestFocus();
                    return;
                }

                if (mDatabase.updateUser(user.getId(), First, Last, Integer.valueOf(Salary))) {
                    Toast.makeText(context, "User Updated", Toast.LENGTH_SHORT).show();
                    loadEmployeesFromDatabaseAgain();

                    dialog.dismiss();


                }
            }


        });
    }

    private void loadEmployeesFromDatabaseAgain() {
        Cursor cursor = mDatabase.getAllUser();

        userList.clear();
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
        notifyDataSetChanged();
    }

}

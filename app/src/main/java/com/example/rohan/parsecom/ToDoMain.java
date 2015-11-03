package com.example.rohan.parsecom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class ToDoMain extends AppCompatActivity {
    EditText editTextUsername, editTextPassword;
    Button buttonLogin, buttonSignup;
    public final String USER_NAME = "username";
    public static final int SIGNUP = 1212;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_main);

        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextUsername = (EditText) findViewById(R.id.editTextEmail);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonSignup = (Button) findViewById(R.id.buttonSignup);

      //  ParseUser.logOut();

        final ParseUser currentUser = ParseUser.getCurrentUser();

        if (currentUser == null) {
            buttonLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkUserName()) {
                        ParseUser.logInInBackground(editTextUsername.getText().toString(), editTextPassword.getText().toString(),
                                new LogInCallback() {
                                    @Override
                                    public void done(ParseUser user, ParseException e) {
                                        if (user != null) {
                                            Log.d("as", "User Foud: " + user.getUsername());
                                            Intent i = new Intent(ToDoMain.this, ToDoList.class);
//                                            i.putExtra(USER_NAME, currentUser.getUsername());
                                            startActivity(i);
                                        } else {
                                            Toast.makeText(ToDoMain.this, "Invalid Creds", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }
            });
        } else {
            Log.d("As", "Current Usr " + currentUser.getUsername());
            Intent i = new Intent(ToDoMain.this, ToDoList.class);
//            i.putExtra(USER_NAME, currentUser.getUsername());
            finish();
            startActivity(i);

        }

        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("asp","Getting control back");
                Intent i = new Intent(ToDoMain.this,SignUp.class);
//                finish();
                startActivityForResult(i,SIGNUP);
            }
        });




    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_to_do_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean checkUserName() {
        if (editTextUsername.getText().toString().trim().equals("") || editTextPassword.getText().toString().trim().equals("")) {
//                    editTextUsername.setError("Enter a valid details");
            Toast.makeText(ToDoMain.this, "Enter a Valid Values", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Log.d("as", "User creds: " + editTextPassword.getText().toString() + " , " + editTextUsername.getText().toString());
            return true;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SIGNUP) {
            if (resultCode == RESULT_OK) {
                  ParseUser.logOut();
                Log.d("Signout", "Need to Logout");
            }
        }
    }
}
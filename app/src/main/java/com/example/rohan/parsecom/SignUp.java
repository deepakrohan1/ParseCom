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

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.List;

public class SignUp extends AppCompatActivity {

    EditText editTextName, editTextEmail, editTextPassword, editTextCPassword;
    Button buttonOk, buttonCancel;
    String username, password, confirmPassword, emailMain;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z]+\\.+[a-zA-Z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextCPassword = (EditText) findViewById(R.id.editTextCPassword);



        buttonOk = (Button) findViewById(R.id.buttonSignup);
        buttonCancel = (Button) findViewById(R.id.buttonCancel);

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = editTextName.getText().toString().trim();
                password = editTextPassword.getText().toString().trim();
                confirmPassword = editTextCPassword.getText().toString().trim();
                emailMain = editTextEmail.getText().toString().trim();

                ParseQuery<ParseUser> user1 = ParseUser.getQuery();
                user1.whereEqualTo("username", emailMain);
                user1.findInBackground(new FindCallback<ParseUser>() {

                    @Override
                    public void done(List<ParseUser> objects, ParseException e) {
                        Log.d("as","check size" +objects.size());
                        if (objects.size() > 0) {
                            Log.d("as","check size" +objects.size());
                            Toast.makeText(SignUp.this, "UserExists", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d("as", username + " , " + password + " , " + emailMain + " , " + confirmPassword);
                            if (username.equals("") || password.equals("") || emailMain.equals("")) {
                                Toast.makeText(SignUp.this, "Enter a valid Details", Toast.LENGTH_SHORT).show();
                            } else if(!emailMain.matches(emailPattern)){
                                Toast.makeText(SignUp.this, "Enter a Valid Email", Toast.LENGTH_SHORT).show();
                            }else if (!confirmPassword.equals(password)) {
                                Toast.makeText(SignUp.this, "Both Passwords aren't same", Toast.LENGTH_SHORT).show();
                            }else {
                                ParseUser user = new ParseUser();
                                user.setUsername(emailMain);
                                user.setPassword(password);
                                user.put("name", username);
                                user.signUpInBackground(new SignUpCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        if (e == null) {
                                            Log.d("as", "Successful");
//                                            finish();
                                            Toast.makeText(SignUp.this,"User Created Successfully",Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(SignUp.this, ToDoMain.class);
                                            finish();
                                            startActivity(i);
                                        } else {
                                            Log.d("as", "Failed");
                                        }
                                    }
                                });
                            }
                        }
                    }
                });
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
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
}
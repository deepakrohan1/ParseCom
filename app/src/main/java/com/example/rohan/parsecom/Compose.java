package com.example.rohan.parsecom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;
public class Compose extends AppCompatActivity {
    Button buttonSend;
    EditText editTextContent;
    public static final String USER_KEY="user_key";
    public static final String BLOG_CONTENT="blog_content";
    public static final String NAME_KEY="name_key";
    String userfirstname ="";
    ArrayList<String> usernames = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        final ParseUser currentUser = ParseUser.getCurrentUser();
        buttonSend = (Button)findViewById(R.id.buttonSend);
        editTextContent = (EditText)findViewById(R.id.textField);

        ParseQuery<ParseUser> user1 = ParseUser.getQuery();
        user1.whereEqualTo("username", currentUser.getUsername());
        user1.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                for(ParseUser p: objects) {
                    usernames.add(p.getString("name"));
                }
                userfirstname = usernames.get(0);
            }
        });

        buttonSend.setOnClickListener(new View.OnClickListener() {


            String username = currentUser.getUsername();


            @Override
            public void onClick(View v) {
                String content = editTextContent.getText().toString();
                if (content.equals("")) {
                    Toast.makeText(Compose.this, "Enter a Valid Content", Toast.LENGTH_SHORT).show();
                } else {
                    ParseObject parseObject = new ParseObject("BlogObj");
                    parseObject.put(USER_KEY, username);
                    parseObject.put(BLOG_CONTENT, content);
                    parseObject.put(NAME_KEY, userfirstname);
                    parseObject.saveInBackground();

                    Intent i = new Intent(Compose.this,ToDoList.class);
                    finish();
                    startActivity(i);

//                    finish();
                }
            }
        });


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




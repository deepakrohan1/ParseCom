package com.example.rohan.parsecom;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ToDoList extends AppCompatActivity {
    String username= "user";
    AlertDialog alertDialog;
    String note="";
    public static final String USER_KEY="user";
    public static final String NOTE_KEY="note";
    ArrayList<Post> toDoNotes = new ArrayList<>();
    ListView listView;
    public static PostAdapter adapter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        listView = (ListView)findViewById(R.id.listView);

        ParseUser currentUser = ParseUser.getCurrentUser();
        if(currentUser != null){
            username =currentUser.getUsername();
            Log.d("as", "The current user to write: " + username);

            ParseQuery<ParseObject> noteObj = ParseQuery.getQuery("BlogObj");
            noteObj.addDescendingOrder("createdAt");
            toDoNotes.clear();
            noteObj.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null) {
                        Log.d("rt",objects.size()+", "+username);
                        if (objects.size() > 0) {
                            for (ParseObject p : objects) {
                                Post currentPost = new Post();
                                currentPost.setContent(p.getString("blog_content"));
                                currentPost.setDate(p.getCreatedAt());
                                currentPost.setObjectId(p.getObjectId());
                                Log.d("cmeOn", "Date is " + p.getObjectId());
                                currentPost.setFirstLast(p.getString("name_key"));
                                currentPost.setUserName(p.getString("user_key"));
                                toDoNotes.add(currentPost);

                            }
                            displayNotes(toDoNotes);
                        } else {
                            Toast.makeText(ToDoList.this, "Empty List", Toast.LENGTH_SHORT).show();
                        }

                    } else {

                    }
                }
            });
        }else if(currentUser == null){
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_to_do_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_compose:
                Intent i = new Intent(ToDoList.this, Compose.class);
                startActivity(i);
                return true;
            case R.id.action_logout:
                ParseUser.logOut();
                Intent j = new Intent(ToDoList.this, ToDoMain.class);
                finish();
                startActivity(j);
                return true;
            case R.id.action_refresh:
                ParseQuery<ParseObject> noteObj = ParseQuery.getQuery("BlogObj");
                noteObj.addDescendingOrder("createdAt");
                toDoNotes.clear();
                noteObj.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e == null) {
                            Log.d("rt", objects.size() + ", " + username);
                            if (objects.size() > 0) {
                                for (ParseObject p : objects) {
                                    Post currentPost = new Post();
                                    currentPost.setContent(p.getString("blog_content"));
                                    Log.d("parseDemo", "About to get date");
                                    currentPost.setDate(p.getCreatedAt());
                                    currentPost.setObjectId(p.getObjectId());
                                    Log.d("parseDemo", "Date is " + p.getObjectId());
                                    currentPost.setFirstLast(p.getString("name_key"));
                                    currentPost.setUserName(p.getString("user_key"));
                                    toDoNotes.add(currentPost);
                                }
                                displayNotes(toDoNotes);
                            } else {
                                Toast.makeText(ToDoList.this, "Empty List", Toast.LENGTH_SHORT).show();
                            }

                        } else {

                        }
                    }
                });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void displayNotes(ArrayList<Post> s){
        Log.d("as", s.toString());

        adapter = new PostAdapter(ToDoList.this, R.layout.message_layout,s, username);
        listView.setAdapter(adapter);
    }
}

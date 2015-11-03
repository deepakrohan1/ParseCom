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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        listView = (ListView)findViewById(R.id.listView);
//        if(getIntent().getExtras() != null){
//            username = getIntent().getExtras().toString();
//            Log.d("as","The user to write TODO: ");
//        }
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
                                Log.d("parseDemo", "About to get date");
                                currentPost.setDate(p.getCreatedAt());
                                Log.d("parseDemo", "Date is " + p.getCreatedAt());
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
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_to_do_list, menu);
        return true;
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        ParseQuery<ParseObject> noteObj2 = ParseQuery.getQuery("BlogObj");
//        noteObj2.addDescendingOrder("createdAt");
//        toDoNotes.clear();
//        noteObj2.findInBackground(new FindCallback<ParseObject>() {
//            @Override
//            public void done(List<ParseObject> objects, ParseException e) {
//                if (e == null) {
//                    Log.d("rt", objects.size() + ", " + username);
//                    if (objects.size() > 0) {
//                        for (ParseObject p : objects) {
//                            Post currentPost = new Post();
//                            currentPost.setContent(p.getString("blog_content"));
//                            Log.d("parseDemo", "About to get date");
//                            currentPost.setDate(p.getCreatedAt());
//                            Log.d("parseDemo", "Date is " + p.getCreatedAt());
//                            currentPost.setFirstLast(p.getString("name_key"));
//                            currentPost.setUserName(p.getString("user_key"));
//                            toDoNotes.add(currentPost);
//                        }
//                        displayNotes(toDoNotes);
//                    } else {
//                        Toast.makeText(ToDoList.this, "Empty List", Toast.LENGTH_SHORT).show();
//                    }
//
//                } else {
//
//                }
//            }
//        });
//    }



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
                finish();
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
                                    Log.d("parseDemo", "Date is " + p.getCreatedAt());
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

        PostAdapter adapter = new PostAdapter(ToDoList.this, R.layout.message_layout,s, username);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                ImageView trash = (ImageView) view.findViewById(R.id.imageViewDelete);
                trash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ParseQuery<ParseObject> query = ParseQuery.getQuery("BlogObj");
                        query.whereEqualTo("blog_content", toDoNotes.get(position).getContent());
                        Log.d("parseDemo", "Content being deleted is " + toDoNotes.get(position).getContent());
                        query.findInBackground(new FindCallback<ParseObject>() {
                            public void done(List<ParseObject> invites, ParseException e) {
                                if (e == null) {
                                    // iterate over all messages and delete them
                                    for (ParseObject invite : invites) {
                                        invite.deleteInBackground();
                                    }

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
                                                        Log.d("parseDemo", "About to get date");
                                                        currentPost.setDate(p.getCreatedAt());
                                                        Log.d("parseDemo", "Date is " + p.getCreatedAt());
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

                                } else { //Handle condition here

                                }
                            }
                        });

                    }
                });
            }
        });
    }
}

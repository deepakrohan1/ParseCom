//Assignment In Class 7
//Group8C_InClass07
//Deepak Rohan Sekar
//Justin Campbell
//Erik Crosby
package com.example.rohan.parsecom;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class PostAdapter extends ArrayAdapter<Post> {
    Context mContext;
    int mResource;
    List<Post> mObjects;
    String thisUser;
    ArrayList<Post> toDoNotes = new ArrayList<>();
//    ToDoList toDoList = null;


    public PostAdapter(Context context, int resource, List<Post> objects, String userName) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
        this.mObjects = objects;
        this.thisUser = userName;
//        toDoList = new ToDoList();


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder holder;
        Log.d("parseDemo", "USername : " + thisUser);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource, parent, false);
        }
        Post p = mObjects.get(position);

        TextView textViewUserName = (TextView) convertView.findViewById(R.id.textViewUser);
        textViewUserName.setText(p.getFirstLast());

        TextView textViewContent = (TextView) convertView.findViewById(R.id.textViewBody);
        textViewContent.setText(p.getContent());

        TextView textViewDate = (TextView) convertView.findViewById(R.id.textViewDate);
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(p.getDate());
        textViewDate.setText(date);


        Log.d("parseDemo", "thisUSer = " + thisUser);
        Log.d("parseDemo", "getUserName = " + p.getUserName());

        if (thisUser.equals(p.getUserName())) {

            ImageView trash = (ImageView) convertView.findViewById(R.id.imageViewDelete);
            trash.setVisibility(View.VISIBLE);
            trash.setEnabled(true);
            trash.setImageDrawable(mContext.getResources().getDrawable(R.drawable.trash));
            trash.setTag(position);
            trash.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             Log.d("Clicked", "Inside OnClic");
                                             int xy = (int) v.getTag();
                                             Post blogContent = mObjects.get(xy);
                                             Log.d("Clicked", "test" + blogContent.toString());
                                             Toast.makeText(mContext, "Removing the Content", Toast.LENGTH_SHORT).show();

                                             ParseQuery<ParseObject> query = ParseQuery.getQuery("BlogObj");
                                             query.whereEqualTo("blog_content", blogContent.getContent());
                                             Log.d("parseDemo", "Content being deleted is " + blogContent.getContent());
                                             query.findInBackground(new FindCallback<ParseObject>() {
                                                 public void done(List<ParseObject> invites, ParseException e) {
                                                     if (e == null) {
                                                         // iterate over all messages and delete them
                                                         for (ParseObject invite : invites) {
                                                             invite.deleteInBackground();
                                                         }
                                                         callMethod();

                                                     }
                                                 }
                                             });


                                         }
                                     }

            );

            //trash.setId(position);
        } else {
            ImageView trash = (ImageView) convertView.findViewById(R.id.imageViewDelete);
            trash.setEnabled(false);
            trash.setVisibility(View.INVISIBLE);
        }


        return convertView;
    }

    public void callMethod(){
        ParseQuery<ParseObject> noteObj = ParseQuery.getQuery("BlogObj");
        noteObj.addDescendingOrder("createdAt");
//        toDoNotes.clear();
        noteObj.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
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
                        ((ToDoList) mContext).displayNotes(toDoNotes);
                    }
                }
            }

        });
    }
}

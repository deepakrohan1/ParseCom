//Assignment In Class 7
//Group8C_InClass07
//Deepak Rohan Sekar
//Justin Campbell
//Erik Crosby
package com.example.rohan.parsecom;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class PostAdapter extends ArrayAdapter<Post> {
    Context mContext;
    int mResource;
    List<Post> mObjects;
    String thisUser;


    public PostAdapter(Context context, int resource, List<Post> objects, String userName) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
        this.mObjects = objects;
        this.thisUser = userName;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

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
        textViewDate.setText(p.getDate().toString());


        Log.d("parseDemo", "thisUSer = " + thisUser);
        Log.d("parseDemo", "getUserName = " + p.getUserName());

        if (thisUser.equals(p.getUserName())) {

            ImageView trash = (ImageView) convertView.findViewById(R.id.imageViewDelete);
            trash.setVisibility(View.VISIBLE);
            trash.setEnabled(true);
            trash.setImageDrawable(mContext.getResources().getDrawable(R.drawable.trash));
            //trash.setId(position);
        } else {
            ImageView trash = (ImageView) convertView.findViewById(R.id.imageViewDelete);
            trash.setEnabled(false);
            trash.setVisibility(View.INVISIBLE);
        }


        return convertView;
    }
}

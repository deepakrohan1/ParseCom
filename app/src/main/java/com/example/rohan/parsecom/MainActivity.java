package com.example.rohan.parsecom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        final ParseObject parseObject = new ParseObject("StudentObject");
//        parseObject.put("name", "John G");
//        parseObject.put("college", "UNCC");
//        parseObject.put("hobbies","driving, coding");
//        parseObject.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(ParseException e) {
//                if (e == null) {
////                        Log.d("as", object.getString("name") + " : " + object.getString("college") + " : " +
////                                object.getString("hobbies"));
//                    Log.d("as", parseObject.getObjectId());
//                } else {
//                    // something went wrong
//                }
//            }
//        });

//        ParseUser user = new ParseUser();
//        user.setUsername("deepakrohan");
//        user.setEmail("deepakrohan@hotmail.com");
//        user.setPassword("123456");
//        user.put("name","Deepak Rohan");
//        user.signUpInBackground(new SignUpCallback() {
//            @Override
//            public void done(ParseException e) {
//                if(e == null){
//                    Log.d("as","Successful");
//                }else{
//                    Log.d("as","Failed");
//                }
//            }
//        });
//        ParseUser.logInInBackground("deepakrohan", "123456", new LogInCallback() {
//            @Override
//            public void done(ParseUser user, ParseException e) {
//                if(user != null){
//                        Log.d("as",user.getEmail());
//
//                }else{
//                                        Log.d("as","failed");
//
//                }
//            }
//        });
        ParseUser.requestPasswordResetInBackground("deepakrohan@hotmail.com", new RequestPasswordResetCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null){
                    Log.d("As","Email sent");
                }else{

                }
            }
        });
//
//        ParseQuery<ParseObject> query = ParseQuery.getQuery("StudentObject");
//        query.whereEqualTo("college", "UNCC");
//        query.findInBackground(new FindCallback<ParseObject>() {
//            public void done(List<ParseObject> scoreList, ParseException e) {
//                if (e == null) {
//                    for(ParseObject p : scoreList) {
//                        Log.d("score", "Retrieved " + p.getString("name") );
//                    }
//                } else {
//                    Log.d("score", "Error: " + e.getMessage());
//                }
//            }
//        });


//            String objectId = parseObject.getObjectId();

//            ParseQuery<ParseObject> query = ParseQuery.getQuery("StudentObject");
//            query.getInBackground(objectId, new GetCallback<ParseObject>() {
//                public void done(ParseObject object, ParseException e) {
//                    if (e == null) {
////                        Log.d("as", object.getString("name") + " : " + object.getString("college") + " : " +
////                                object.getString("hobbies"));
//                        Log.d("as",object.getObjectId());
//                    } else {
//                        // something went wrong
//                    }
//                }
//            });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

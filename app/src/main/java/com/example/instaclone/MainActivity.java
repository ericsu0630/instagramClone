package com.example.instaclone;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Save data to Parse server
//        ParseObject tweets = new ParseObject("Scores");
//        tweets.put("username", "Sam");
//        tweets.put("score", 25);
//
//        tweets.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(ParseException e) {
//                if(e == null){
//                    Log.i("Success!", "The score was uploaded!");
//                }else{
//                    e.printStackTrace();
//                    Log.i("Failed!", "something went wrong!");
//                }
//            }
//        });

        //Get data from Parse server
//        ParseQuery<ParseObject> query = ParseQuery.getQuery("tweets");
//        query.getInBackground("m7VU85V5kA", new GetCallback<ParseObject>() {
//            @Override
//            public void done(ParseObject object, ParseException e) {
//                if(e == null && object!= null){
//                    object.put("tweet", "Dylan's mom!");
//                    object.saveInBackground();
//                    Log.i("username",String.valueOf(object.getString("username")));
//                    Log.i("tweet",String.valueOf(object.getString("tweet")));
//                }else{
//                    Log.i("Error","Parse get failed!");
//                }
//            }
//        });

        //Get more than 1 object from Parse server
//        ParseQuery<ParseObject> query = ParseQuery.getQuery("tweets");
//        query.findInBackground(new FindCallback<ParseObject>() {
//            @Override
//            public void done(List<ParseObject> objects, ParseException e) {
//                if(e == null && objects.size()>0){
//                    for(ParseObject object : objects){
//                        Log.i("username", String.valueOf(object.getString("username")));
//                        Log.i("tweet", String.valueOf(object.getString("tweet")));
//                    }
//                }else{
//                    Log.i("Error", "get failed");
//                }
//            }
//        });

        //Search for a particular object using field data
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Scores");
        query.whereEqualTo("score", 19);
        //query.setLimit(1); //gets the first one
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null && objects.size()>0){
                    for(ParseObject object : objects){
                        int score = object.getInt("score");
                        score+=1;
                        object.put("score", score);
                        object.saveInBackground();
                    }
                }else{
                    Log.i("Error", "get failed");
                }
            }
        });

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }
}
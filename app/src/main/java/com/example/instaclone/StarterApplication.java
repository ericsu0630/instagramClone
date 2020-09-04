package com.example.instaclone;

import android.app.Application;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

public class StarterApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        //http://54.238.88.134/apps
        //Username, password: user, 0Stl3l8eU0Lu
        // Add your initialization code here
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("myappID")
                .clientKey("0Stl3l8eU0Lu")
                .server("http://54.238.88.134/parse/")
                .build()
        );

//        ParseObject exampleObject = new ParseObject("ExampleObject");
//        exampleObject.put("myString","Ben");
//        exampleObject.put("myNumber", "156");
//        exampleObject.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(ParseException e) {
//                if(e == null){
//                    //Ok
//                    Log.i("Parse Success!", "data has been saved");
//                }else{
//                    Log.i("Error", "There was a Parse exception!");
//                }
//            }
//        });

        ParseUser.enableAutomaticUser();

        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);

    }
}

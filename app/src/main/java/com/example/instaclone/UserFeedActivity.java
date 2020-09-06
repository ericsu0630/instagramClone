package com.example.instaclone;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.util.List;

public class UserFeedActivity extends AppCompatActivity {

    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feed);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        if(username != null) {
            setTitle(username + "'s Photos");
            Log.i("Clicked user", username);
            linearLayout = findViewById(R.id.linearLayout);

            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Images");
            query.whereEqualTo("username", username);
            query.orderByDescending("createdAt");
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null && objects.size() > 0) {

                        for (ParseObject image : objects) {
                            ParseFile parseFile = (ParseFile) image.get("image");
                            if (parseFile != null) {
                                parseFile.getDataInBackground(new GetDataCallback() {
                                    @Override
                                    public void done(byte[] data, ParseException e) {
                                        if (e == null && data != null) {
                                            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                            ImageView imageView = new ImageView(getApplicationContext());
                                            imageView.setLayoutParams(new ViewGroup.LayoutParams(
                                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                                    ViewGroup.LayoutParams.WRAP_CONTENT
                                            ));
                                            imageView.setPadding(0, 10, 0, 10);
                                            imageView.setImageBitmap(bitmap);
                                            linearLayout.addView(imageView);
                                        } else {
                                            e.printStackTrace();
                                            Toast.makeText(UserFeedActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            } else {
                                Log.i("Error", "Null image");
                            }
                        }
                    } else if (e != null) {
                        Log.i("Error2", e.toString());
                        Toast.makeText(UserFeedActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Log.i("Error", "get image failed");
                    }
                }
            });
        }
    }
}
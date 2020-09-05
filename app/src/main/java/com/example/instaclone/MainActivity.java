package com.example.instaclone;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity {

    EditText username, password, password2, email;
    Button login, submit;
    TextView signUp, loginText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.usernameEditText);
        password = findViewById(R.id.passwordEditText);
        password2 = findViewById(R.id.passwordEditText2);
        email = findViewById(R.id.emailEditText);
        login = findViewById(R.id.loginButton);
        submit = findViewById(R.id.submit);
        signUp = findViewById(R.id.signupTextView);
        loginText = findViewById(R.id.backToLoginTextView);
        ParseUser.logOut();

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }

    public void login(View view) {
        ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e == null){
                    Log.i("Success!", user.getUsername()+" has logged in.");
                    Toast.makeText(MainActivity.this, user.getUsername()+" has logged in.", Toast.LENGTH_SHORT).show();
                }else{
                    Log.i("Error1", e.toString());
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void submit(View view) {
        if(email.getText().toString().isEmpty()){
            Log.i("Error", "email empty");
            Toast.makeText(this, "email required", Toast.LENGTH_SHORT).show();
        }else if (password.getText().toString().equals(password2.getText().toString())) { //if passwords match
                final ParseUser newUser = new ParseUser();
                newUser.setUsername(username.getText().toString());
                newUser.setPassword(password.getText().toString());
                newUser.setEmail(email.getText().toString());
                newUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Log.i("Success", newUser.getUsername() + " has been added.");
                            Toast.makeText(MainActivity.this, "Sign up successful!", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.i("Error2", e.toString());
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        } else {
            Log.i("Error", "Passwords do not match");
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
        }
    }


    public void signUp(View view){
        login.setVisibility(View.GONE);
        signUp.setVisibility(View.GONE);
        email.setVisibility(View.VISIBLE);
        password2.setVisibility(View.VISIBLE);
        submit.setVisibility(View.VISIBLE);
        loginText.setVisibility(View.VISIBLE);
    }

    public void backToLogin(View view){
        login.setVisibility(View.VISIBLE);
        signUp.setVisibility(View.VISIBLE);
        email.setVisibility(View.GONE);
        password2.setVisibility(View.GONE);
        submit.setVisibility(View.GONE);
        loginText.setVisibility(View.GONE);
    }

}
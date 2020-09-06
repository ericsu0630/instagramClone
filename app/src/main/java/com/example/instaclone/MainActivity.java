package com.example.instaclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity implements View.OnKeyListener{

    EditText username, password, password2, email;
    Button login, submit;
    TextView signUp, loginText;
    boolean loginMode = true;

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) { //Do something when a key is pressed
        if(i == KeyEvent.KEYCODE_ENTER  && keyEvent.getAction() == KeyEvent.ACTION_DOWN){
            if(view.getTag().toString().equals("password") && loginMode){
                Log.i("KEY EVENT", "password1");
                login(login); //mimics 'LOGIN' key pressed
                return true;
            }else if(view.getTag().toString().equals("password again") && !loginMode){
                Log.i("KEY EVENT", "password2");
                submit(submit); //mimics 'SUBMIT' key pressed
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Inst(er)gram");
        username = findViewById(R.id.usernameEditText);
        password = findViewById(R.id.passwordEditText);
        password2 = findViewById(R.id.passwordEditText2);
        email = findViewById(R.id.emailEditText);
        login = findViewById(R.id.loginButton);
        submit = findViewById(R.id.submit);
        signUp = findViewById(R.id.signupTextView);
        loginText = findViewById(R.id.backToLoginTextView);

        password.setOnKeyListener(this);
        password2.setOnKeyListener(this);

        try{
            if(ParseUser.getCurrentUser().getUsername() != null){
                showUsers();
            }
        }catch(Exception e){
            Log.i("Error", e.toString());
        }

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }

    public void login(View view) { //login with user details
        ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(user != null && e == null){
                    Log.i("Success!", user.getUsername()+" has logged in.");
                    //Toast.makeText(MainActivity.this, user.getUsername()+" has logged in.", Toast.LENGTH_SHORT).show();
                    showUsers();
                }else{
                    Log.i("Error1", e.toString());
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void submit(View view) { //sign up with new user data
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
                            //Toast.makeText(MainActivity.this, "Sign up successful!", Toast.LENGTH_SHORT).show();
                            showUsers();
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

    //Switch between sign up and login modes
    public void signUp(View view){
        loginMode = false; //switched to sign up mode
        login.setVisibility(View.GONE);
        signUp.setVisibility(View.GONE);
        email.setVisibility(View.VISIBLE);
        password2.setVisibility(View.VISIBLE);
        submit.setVisibility(View.VISIBLE);
        loginText.setVisibility(View.VISIBLE);
    }
    public void backToLogin(View view){
        loginMode = true;
        login.setVisibility(View.VISIBLE);
        signUp.setVisibility(View.VISIBLE);
        email.setVisibility(View.GONE);
        password2.setVisibility(View.GONE);
        submit.setVisibility(View.GONE);
        loginText.setVisibility(View.GONE);
    }

    public void closeKeyboard(View view){ //hides they keyboard when user clicks on background
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getApplicationWindowToken(),0);
    }

    public void showUsers(){
        Intent intent = new Intent(getApplicationContext(),UserListActivity.class);
        startActivity(intent);
    }

}
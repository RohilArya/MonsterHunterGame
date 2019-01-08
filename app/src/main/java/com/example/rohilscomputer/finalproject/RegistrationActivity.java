package com.example.rohilscomputer.finalproject;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.List;

public class RegistrationActivity extends AppCompatActivity {
    private userDBHelper myDb;
    public List<User> users = new LinkedList<User>();

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        myDb = new userDBHelper(this);
    }


    public void signUp (View Register)
    {

        EditText editText = (EditText) findViewById(R.id.editText5);
        String username = editText.getText().toString();

        EditText editTextP = (EditText) findViewById(R.id.editText6);
        String Password = editTextP.getText().toString();
        //need to make sure passowrds match
        EditText checkPass = (EditText) findViewById(R.id.editText8);
        String cPassword = checkPass.getText().toString();

        EditText getEmail = (EditText) findViewById(R.id.editText9);
        String email = getEmail.getText().toString();

        EditText check = (EditText) findViewById(R.id.editText10);
        String cEmail = getEmail.getText().toString();

        User user = new User(username, Password, cPassword, email, cEmail);

        users.add(user);

        String usernameChecker = myDb.searchCred(username);

        /*user.updateLevel(1200);
        user.returnLevel();

        boolean checker = myDb.updateUserStats(user);*/


        if(usernameChecker.equals("no such username"))
        {
            boolean isInstered = myDb.insertUser(user);


            if(isInstered == true)
            {
                Toast.makeText(RegistrationActivity.this,"Registerd!",Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(RegistrationActivity.this,"Unable to register!",Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(RegistrationActivity.this,"Username already exists, please choose another one!"
                    ,Toast.LENGTH_LONG).show();
        }


        //write username email and password to database
    }


}
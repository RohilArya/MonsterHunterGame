package com.example.rohilscomputer.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    userDBHelper myDb;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        myDb = new userDBHelper(this);
    }
    public void verify(View login) {
        EditText editText = (EditText) findViewById(R.id.editText);
        String user = editText.getText().toString();

        EditText editText3 = (EditText) findViewById(R.id.editText3);
        String pass = editText3.getText().toString();

        String passwordChecker = myDb.searchCred(user);

        if(pass.equals(passwordChecker))
        {
            Intent gameIntent = new Intent(this,GameActivity.class);
            startActivity(gameIntent);
        }
        else
        {
            Toast incorrect = Toast.makeText(LoginActivity.this,"Username or password is incorrect",Toast.LENGTH_LONG);
            incorrect.show();
        }


        //check database for usernames and password


    }

}

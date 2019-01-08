package com.example.rohilscomputer.finalproject;

import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static String url = "https://jsoneditoronline.org/#/123";
    final int VERIFY = 0;
    userDBHelper myDb;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new userDBHelper(this);

        mediaPlayer = MediaPlayer.create(this, R.raw.menu);
        mediaPlayer.start();
    }
    
    public void register(View login){

        Intent registerIntent = new Intent(this, RegistrationActivity.class);
        startActivity(registerIntent);

    }
    public void login(View login){
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
    }

    public void testGame(View login)
    {
        mediaPlayer.stop();
        Intent gameIntent = new Intent(this, GameActivity.class);
        startActivity(gameIntent);
    }
    public void onActivityResult(int requestCode,
                                 int responseCode,
                                 Intent result) {
        Log.i("IntentDemo", "onActivityResult()");

        super.onActivityResult(requestCode,
                responseCode,
                result);


        if (responseCode == 0) {
            Toast.makeText(getApplicationContext(), "Incorrect.",
                    Toast.LENGTH_SHORT).show();
        } else if (responseCode == 1) {
            Toast.makeText(getApplicationContext(), "Logged in.",
                    Toast.LENGTH_SHORT).show();
        }
    }
}


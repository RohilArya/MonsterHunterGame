package com.example.rohilscomputer.finalproject;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetData extends AsyncTask<String,Integer,String> {
    String data= "";

    String stringParsed = "";

    @Override
    protected String doInBackground(String... urls) {
        try {
            URL url = new URL(urls[0]);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            InputStream inputRaw = conn.getInputStream();
            BufferedReader input = new BufferedReader(new InputStreamReader(inputRaw));
            String line = "";
            while(line != null){
                line = input.readLine();
                data = data + line;
            }

            JSONArray array = new JSONArray(data);
            for (int i = 0; i < array.length(); i++)
            {
                JSONObject obj = (JSONObject) array.get(i);
                stringParsed = String.valueOf(obj.get("String"));


            }
            inputRaw.close();
            conn.disconnect();
            return data;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return"Not able to download";
    }
}

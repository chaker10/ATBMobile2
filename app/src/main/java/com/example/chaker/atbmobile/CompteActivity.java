package com.example.chaker.atbmobile;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CompteActivity extends AppCompatActivity {
    TextView num,id,type;
    String a;
    int id_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compte2);
        id=(TextView) findViewById(R.id.id);
        type=(TextView) findViewById(R.id.type);

        Bundle b =getIntent().getExtras();
        a =b.getString("cle");
        id_user =Integer.parseInt(a);
        num=(TextView)findViewById(R.id.num) ;

        String url="http://192.168.43.98/app/compte.php?id_user="+id_user;
        new MyAsyncTaskgetNews().execute(url);

    }
    public void buLogin(View view) {



    }
    public class MyAsyncTaskgetNews extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {

        }
        @Override
        protected String  doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                String NewsData;

                URL url = new URL(params[0]);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setConnectTimeout(7000);

                try {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    NewsData = ConvertInputToStringNoChange(in);
                    publishProgress(NewsData);
                } finally {

                    urlConnection.disconnect();
                }

            }catch (Exception ex){}
            return null;
        }
        protected void onProgressUpdate(String... progress) {

            try {

                JSONObject json = new JSONObject(progress[0]);
               id.setText(json.getInt("vir")+"");
                //num.setText(json.getInt("id")+"");
                type.setText(json.getString("pai")+"");
            } catch (Exception ex) {
            }


        }

        protected void onPostExecute(String  result2){


        }




    }

    public static String ConvertInputToStringNoChange(InputStream inputStream) {

        BufferedReader bureader=new BufferedReader( new InputStreamReader(inputStream));
        String line ;
        String linereultcal="";

        try{
            while((line=bureader.readLine())!=null) {

                linereultcal+=line;

            }
            inputStream.close();


        }catch (Exception ex){}

        return linereultcal;
    }



}


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
String a; int id_user;
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
     /*   type=(EditText)findViewById(R.id.edittypec);
        numcompte=(EditText)findViewById(R.id.editnumc);*/
        String url="http://192.168.8.101//app/compte.php?id_user="+id_user;
        new MyAsyncTaskgetNews().execute(url);

    }
    public void buLogin(View view) {


        //  new MyAsyncTaskgetNews().execute(url1);
    }
    public class MyAsyncTaskgetNews extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            //before works
        }
        @Override
        protected String  doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                String NewsData;
                //definir l'url avec lequel nous devons nous connecter

                URL url = new URL(params[0]);
                // faire la connexion  avec url et envoyer la demande

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                //attente de 7000ms pour reponse
                urlConnection.setConnectTimeout(7000);//regler le délai 5 secondes

                try {
                    //obtenir les donnnes de reponse
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    //convertir fe flux en chaine
                    NewsData = ConvertInputToStringNoChange(in);
                    //envoyer pour afficher les données
                    publishProgress(NewsData);
                } finally {
                    //connection
                    urlConnection.disconnect();
                }

            }catch (Exception ex){}
            return null;
        }
        protected void onProgressUpdate(String... progress) {

            try {

                JSONObject json = new JSONObject(progress[0]);
                id.setText(json.getString("id"));
                num.setText(json.getString("num"));
                type.setText(json.getString("type"));
            } catch (Exception ex) {
            }


        }

        protected void onPostExecute(String  result2){


        }




    }

    // cette methode convertit tous flux en chaine
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


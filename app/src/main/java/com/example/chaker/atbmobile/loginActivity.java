package com.example.chaker.atbmobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.AsyncTask;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
public class loginActivity extends AppCompatActivity {
    EditText etUserName;
    EditText etPassword,type,numcompte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUserName=(EditText)findViewById(R.id.etUserName);
        etPassword=(EditText)findViewById(R.id.etPassword);
     /*   type=(EditText)findViewById(R.id.edittypec);
        numcompte=(EditText)findViewById(R.id.editnumc);*/


    }
    public void buLogin(View view) {

        String url="http://chakerrahmani.esy.es/login.php?UserName="+  etUserName.getText().toString()+"&Password="+ etPassword.getText().toString();
     //   String url1="http://10.0.2.2/app/compte.php?UserName="+  etUserName.getText().toString()+"&Password="+ etPassword.getText().toString()+"type="+
           //     type.getText().toString();

        new MyAsyncTaskgetNews().execute(url);
    //    new MyAsyncTaskgetNews().execute(url1);
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
                JSONObject json= new JSONObject(progress[0]);
                //vous ne pouvez pas vous connecter
              //   Toast.makeText(getApplicationContext(),progress[0],Toast.LENGTH_LONG).show();

                if (json.getString("msg").equals("ok")){
                    Intent i=new Intent(loginActivity.this,Menu.class);
                    startActivity(i);


                    Toast.makeText(getApplicationContext(),"welecom",Toast.LENGTH_LONG).show();
                    etPassword.getText().clear();
                    Toast.makeText(getApplicationContext(),"type"+type+"et"+"numcompte"+numcompte+"",Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(getApplicationContext(),"verifier le login ou password",Toast.LENGTH_LONG).show();}


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


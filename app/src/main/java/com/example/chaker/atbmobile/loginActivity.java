package com.example.chaker.atbmobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.AsyncTask;

import android.view.View;
import android.widget.Button;
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
    EditText etPassword;
    Button buLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUserName=(EditText)findViewById(R.id.etUserName);
        etPassword=(EditText)findViewById(R.id.etPassword);


    }
    public void buLogin(View view) {

        String url="http://192.168.43.98/app/login.php?UserName="+  etUserName.getText().toString()+"&Password="+ etPassword.getText().toString();

        new MyAsyncTaskgetNews().execute(url);

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
                JSONObject json= new JSONObject(progress[0]);//recuper les resulita en json



                if((json.getString("msg").equals("ok"))){

                    Intent i=new Intent(loginActivity.this,Menu.class);
                    i.putExtra("1",json.getString("idlogin"));
                    startActivity(i);


                    Toast.makeText(getApplicationContext(),"Bienvenue",Toast.LENGTH_LONG).show();
                    etPassword.getText().clear();

                }
                else {
                    Toast.makeText(getApplicationContext(),"Veuillez sélectionner un nom d'utilisateur et mot de passe",Toast.LENGTH_LONG).show();}



            }
            catch (Exception ex) {
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


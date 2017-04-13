package com.example.chaker.atbmobile;

import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ChequeActivity extends AppCompatActivity {
    private  Button demandebtn;
    private RadioGroup radiogroup;
    private RadioButton rd0,rd1;
   // private EditText nbre_pageedit;
    String a ;
    int id_user;
    String type_cheque;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheque);
        demandebtn =(Button) findViewById(R.id.btncheque);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
     //   nbre_pageedit =(EditText) findViewById(R.id.nbre_page);
        radiogroup =(RadioGroup) findViewById(R.id.radiobtngroup);
      rd0 =(RadioButton) findViewById(R.id.btnr0);
        rd1 =(RadioButton) findViewById(R.id.btnr1);

        List<String> categories = new ArrayList<String>();
        categories.add("20");
        categories.add("50");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        /*
        recuperation id de utilisateur
         */
        Bundle b =getIntent().getExtras();
        a =b.getString("cle");
        id_user =Integer.parseInt(a);

/////////////////////////////////////////////

        demandebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*
* selection id de button check
 */
           int selectedId = radiogroup.getCheckedRadioButtonId();
                /*
                *recuperation de nombre saisu
                  en edit text
                 */
           String nbre_page = spinner.getSelectedItem().toString();

                if(selectedId == rd0.getId()){
                   String b = "true";
                    conexion(b,nbre_page,id_user);


                }
                else if(selectedId ==  rd1.getId()){
                    String b = "false";
                    conexion(b,nbre_page,id_user);

                }
            else
                    Toast.makeText(getApplicationContext(),"select btn else ",Toast.LENGTH_LONG).show();

            }

        });

    }

    private void conexion(String a, String nbre_page, int id_user) {
        String typecheque =a ;
        String nbrepage=nbre_page;
        int id_client=id_user;
        String url="http://192.168.43.98/app/demande.php?type_cheque="+typecheque+"&nbre_page="+nbrepage+"&id_user="+id_client;
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
                //define the url we have to connect with
                URL url = new URL(params[0]);
                //make connect with url and send request
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                //waiting for 7000ms for response
                urlConnection.setConnectTimeout(7000);//set timeout to 5 seconds

                try {
                    //getting the response data
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    //convert the stream to string
                    NewsData = ConvertInputToStringNoChange(in);
                    //send to display data
                    publishProgress(NewsData);
                } finally {
                    //end connection
                    urlConnection.disconnect();
                }

            }catch (Exception ex){}
            return null;
        }
        protected void onProgressUpdate(String... progress) {

            try {
                JSONObject json= new JSONObject(progress[0]);
                //display response data
                Toast.makeText(getApplicationContext(),json.getString("msg"),Toast.LENGTH_LONG).show();

            } catch (Exception ex) {
            }


        }

        protected void onPostExecute(String  result2){


        }




    }

    // this method convert any stream to string
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


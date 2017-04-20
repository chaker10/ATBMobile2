package com.example.chaker.atbmobile;


import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class Menu extends AppCompatActivity {

 String x;
    ArrayList<compte> listnewsData = new ArrayList<compte>();
    String idcompte[] = new String[2500];  String cmpte="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ArrayList<String> list=new ArrayList<String>();
        Bundle b =getIntent().getExtras();
        x =b.getString("1");

        String url="http://192.168.43.98/app/listcompte.php?id_user="+x;
        new MyAsyncTaskgetNews().execute(url);
    }
    public void  compteclik(View v)
    {
        Intent go=new Intent(Menu.this,listCompte.class);
        go.putExtra("cle",x);
        startActivity(go);

    }
    public  void carteclik(View v){
    Intent go=new Intent(Menu.this,Cates_Activity.class);
    go.putExtra("cle",x);
    startActivity(go);


    }
    public void chequeclik(View v)
    {    Intent go=new Intent(Menu.this,ChequeActivity.class);
        go.putExtra("cle",x);
        startActivity(go);

    }
    public void virementclik(View v)

    {
        for (int i=0;i<idcompte.length;i++){
           // Toast.makeText(getApplication(),idcompte[i]+"",Toast.LENGTH_SHORT).show();
        }


    }

    private void virement() {









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
                //JSONObject json= new JSONObject(progress[0]);
                JSONArray json =new JSONArray(progress[0]);
                // Toast.makeText(getApplicationContext(),progress[0],Toast.LENGTH_LONG).show();

                for (int i=0;i<json.length();i++){
                    JSONObject user= json.getJSONObject(i);

                   // listnewsData.add(new compte(user.getInt("id_compte"),user.getString("TYPE"),user.getString("Num_compte")));
                    idcompte[i]=user.getString("Num_compte");
                  //  Toast.makeText(getApplication(),idcompte[i]+"",Toast.LENGTH_SHORT).show();
                             }
                     Intent i =new Intent(Menu.this,VirementActivity.class);
                       // i.putExtra("")
                       for (int j=0;j<idcompte.length-1;j++){
                    Toast.makeText(getApplication(),idcompte[j]+"",Toast.LENGTH_SHORT).show();
                }

                //  Toast.makeText(getApplicationContext(),progress[0],Toast.LENGTH_LONG).show();

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
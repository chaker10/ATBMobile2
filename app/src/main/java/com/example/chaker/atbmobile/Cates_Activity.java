package com.example.chaker.atbmobile;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Cates_Activity extends AppCompatActivity {
    ArrayList<carte> listnewsData = new ArrayList<carte>();
    MyCustomAdapter myadapter;
    String a ;
    int id_user;
    int idcarte[] = new int[25];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cates_);


        ListView lsNews=(ListView) findViewById(R.id.LVNew);
        myadapter=new MyCustomAdapter(listnewsData);
        lsNews.setAdapter(myadapter);
        Bundle b =getIntent().getExtras();
        a =b.getString("cle");
        id_user =Integer.parseInt(a);
        String url="http://192.168.43.98/app/listcarte.php?id_user="+id_user;
        new  MyAsyncTaskgetNews().execute(url);
        lsNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int itemPosition     = i;
                // Toast.makeText(getApplicationContext(),idcompte[itemPosition]+"",Toast.LENGTH_SHORT).show();
                Intent go=new Intent(Cates_Activity.this,CarteActivity.class);
                go.putExtra("cle",idcarte[itemPosition]+"");
                startActivity(go);

            }
        });
    }
    private class MyCustomAdapter extends BaseAdapter {
        public ArrayList<carte> listnewsDataAdpater ;

        public MyCustomAdapter(ArrayList<carte> listnewsDataAdpater) {
            this.listnewsDataAdpater=listnewsDataAdpater;
        }


        @Override
        public int getCount() {
            return listnewsDataAdpater.size();
        }

        @Override
        public String getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater mInflater = getLayoutInflater();
            View myView = mInflater.inflate(R.layout.layoutcarte, null);

            final   carte s = listnewsDataAdpater.get(position);

            TextView typecarte=( TextView)myView.findViewById(R.id.idtyprecart);
            typecarte.setText(s.type_cartes);
            TextView numerocarte=( TextView)myView.findViewById(R.id.idnumerocarte);
            numerocarte.setText(s.num_carte);
            return myView;
        }

    }


    // get news from server
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

                    listnewsData.add(new carte(user.getString("type_cartes"),user.getString("num_carte")));
                    idcarte[i]=user.getInt("num_carte");
                }
                myadapter.notifyDataSetChanged();
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
    }}



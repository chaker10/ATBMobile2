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

public class listCompte extends AppCompatActivity {
    //adapter class
    ArrayList<compte>    listnewsData = new ArrayList<compte>();
    MyCustomAdapter myadapter;
    String a ;
     int id_user;
    int idcompte[] = new int[25];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listecompte);


        //add data and view it
        ListView lsNews=(ListView) findViewById(R.id.LVNews);
        myadapter=new MyCustomAdapter(listnewsData);
        lsNews.setAdapter(myadapter);//remplir liste view
    Bundle b =getIntent().getExtras();
        a =b.getString("cle");
        id_user =Integer.parseInt(a);


      String url="http://192.168.43.98/app/listcompte.php?id_user="+id_user;
       new  MyAsyncTaskgetNews().execute(url);
        lsNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int itemPosition     = i;
               // Toast.makeText(getApplicationContext(),idcompte[itemPosition]+"",Toast.LENGTH_SHORT).show();
               Intent go=new Intent(listCompte.this,CompteActivity.class);
                go.putExtra("cle",idcompte[itemPosition]+"");
                startActivity(go);

            }
        });
    }



    private class MyCustomAdapter extends BaseAdapter {
        public ArrayList<compte> listnewsDataAdpater ;

        public MyCustomAdapter(ArrayList<compte>  listnewsDataAdpater) {
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
            View myView = mInflater.inflate(R.layout.layoutcompte, null);

            final   compte s = listnewsDataAdpater.get(position);

            TextView etID=( TextView)myView.findViewById(R.id.compte);
            etID.setText( String.valueOf( s.Id_compte));
            TextView etUserName=( TextView)myView.findViewById(R.id.type);
            etUserName.setText(s.TYPE);
            TextView etPassword=( TextView)myView.findViewById(R.id.numero);
            etPassword.setText(s.Num_compte);
            return myView;
        }

    }


    // get news from server
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
                //JSONObject json= new JSONObject(progress[0]);
                JSONArray json =new JSONArray(progress[0]);
               // Toast.makeText(getApplicationContext(),progress[0],Toast.LENGTH_LONG).show();

             for (int i=0;i<json.length();i++){
                    JSONObject user= json.getJSONObject(i);

                  listnewsData.add(new compte(user.getInt("id_compte"),user.getString("TYPE"),user.getString("Num_compte")));
                 idcompte[i]=user.getInt("id_compte");
             }
                myadapter.notifyDataSetChanged();
                //display response data
              //  Toast.makeText(getApplicationContext(),progress[0],Toast.LENGTH_LONG).show();

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
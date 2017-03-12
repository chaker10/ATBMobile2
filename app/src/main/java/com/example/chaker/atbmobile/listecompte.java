package com.example.chaker.atbmobile;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
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

public class listecompte extends AppCompatActivity {
    //adapter class
    ArrayList<compte> listnewsData = new ArrayList<compte>();
    MyCustomAdapter myadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listecompte);


        //add data and view it
        // listnewsData.add(new AdapterItems(1,"developer"," develop apps"));
        myadapter=new MyCustomAdapter(listnewsData);
        ListView lsNews=(ListView) findViewById(R.id.LVNews);
        lsNews.setAdapter(myadapter);//intisal with data

        String url="http://chakerrahmani.esy.es/compte.php?id_user=1";

        new  MyAsyncTaskgetNews().execute(url);
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
            View myView = mInflater.inflate(R.layout.activity_compte, null);

            final compte s = listnewsDataAdpater.get(position);

            TextView etID=(TextView)myView.findViewById(R.id.etID);
            etID.setText( String.valueOf( s.id_compte));
            TextView etUserName=( TextView)myView.findViewById(R.id.etUserName);
            etUserName.setText(s.TYPE);
            TextView etPassword=( TextView)myView.findViewById(R.id.etPassword);
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
                Toast.makeText(getApplicationContext(),progress[0],Toast.LENGTH_LONG).show();

                for (int i=0;i<json.length();i++){
                    JSONObject user= json.getJSONObject(i);
                    listnewsData.add(new compte(user.getInt("id_compte"),user.getString("type"),user.getInt("Num_compte")));
                }
                myadapter.notifyDataSetChanged();
                //display response data
                Toast.makeText(getApplicationContext(),progress[0],Toast.LENGTH_LONG).show();

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


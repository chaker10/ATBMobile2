package com.example.chaker.atbmobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class AcceuilActivity extends AppCompatActivity {

    private Button btnloginacl;
   String x,d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceuil);

        btnloginacl = (Button) findViewById(R.id.btnloginacl);


    }
    public  void btnloginacl(View v)
    {
        Intent go=new Intent(AcceuilActivity.this,loginActivity.class);
        go.putExtra("cle",x);
        startActivity(go);
    }
    public void btn_localistion(View view)
    {

        Intent g =new Intent(AcceuilActivity.this,MapsActivity.class);
        g.putExtra("d",d);
        startActivity(g);
    }

    public void contact(View view) {
        Intent i =new Intent(AcceuilActivity.this,ContactActivity.class);
        startActivity(i);
    }
}

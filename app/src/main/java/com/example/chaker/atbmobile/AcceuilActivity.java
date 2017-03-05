package com.example.chaker.atbmobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class AcceuilActivity extends AppCompatActivity {
  public final static String message="android.polytech.monappli.message";
    float x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceuil);
    }


    public void btnloginacl(View v) {

        Intent go = new Intent(AcceuilActivity.this, loginActivity.class);
        go.putExtra("cle", x);
        startActivity(go);


    }
    public  void btn_localistion(View v)
    {

    }
}



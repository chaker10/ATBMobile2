package com.example.chaker.atbmobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class AcceuilActivity extends AppCompatActivity {

    private Button btnloginacl;
   float x;

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

}

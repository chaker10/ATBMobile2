package com.example.chaker.atbmobile;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class Menu extends AppCompatActivity {

float x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

    }
    public void  compteclik(View v)
    {
        Intent go=new Intent(Menu.this,listecompte.class);
        go.putExtra("cle",x);
        startActivity(go);

    }
    public  void carteclik(View v)
    {

    }
    public void chequeclik(View v)
    {

    }
    public void virementclik(View v)
    {


    }
}

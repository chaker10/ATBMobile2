package com.example.chaker.atbmobile;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


public class Menu extends AppCompatActivity {

 String x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Bundle b =getIntent().getExtras();
        x =b.getString("1");
    }
    public void  compteclik(View v)
    {
        Intent go=new Intent(Menu.this,listCompte.class);
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

package com.example.vibhu.moviesapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean check=isNetworkConnected();
        if(check==true){
            Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Not Connected", Toast.LENGTH_SHORT).show();
        }
    }
    public void EnterApp(View view){
        Intent intent=new Intent(this,SelectActivity.class);
        startActivity(intent);
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}


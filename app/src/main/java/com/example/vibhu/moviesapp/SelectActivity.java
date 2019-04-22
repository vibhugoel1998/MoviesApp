package com.example.vibhu.moviesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
    }

    public void ChooseMovies(View view){
        Intent intent=new Intent(this,OpeningActivity.class);
        startActivity(intent);

    }
    public void ChooseTV(View view){
        Intent intent=new Intent(this,TvStartActivity.class);
        startActivity(intent);
    }
}

package com.example.vibhu.moviesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CastInfo extends AppCompatActivity {
    ImageView imageofperson;
    TextView nameofperson;
    TextView ratingsofperson;
    TextView bdayofperson;
    TextView desc;
    ListView listView;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    LinearLayout linearLayout;
    TextView AlsoKnown;
    TextView birthplace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast_info);
        imageofperson=findViewById(R.id.personimage);
        nameofperson=findViewById(R.id.personname);
        ratingsofperson=findViewById(R.id.personratings);
        bdayofperson=findViewById(R.id.personBday);
        desc=findViewById(R.id.persondesc);
        linearLayout=findViewById(R.id.personbiolayout);
        listView=findViewById(R.id.listview);
        arrayList=new ArrayList<>();
        arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);
        AlsoKnown=findViewById(R.id.alsoknownas);
        birthplace=findViewById(R.id.personBirthPlace);
        Intent intent=getIntent();
        int id=intent.getIntExtra("castid1",-1);
        fetchInfo(id);
    }
    private void fetchInfo(int id) {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CustomApi customApi=retrofit.create(CustomApi.class);
        Call<PersonHeirarchy> call=customApi.getPersonInfo(id);
        call.enqueue(new Callback<PersonHeirarchy>() {
            @Override
            public void onResponse(Call<PersonHeirarchy> call, Response<PersonHeirarchy> response) {
                PersonHeirarchy newObj=response.body();
                if(response!=null)
                {
                    Picasso.get().load("http:image.tmdb.org/t/p/original"+newObj.getProfile_path()).into(imageofperson);
                    nameofperson.setText(newObj.getName());
                    ratingsofperson.setText("POPULARITY:"+newObj.getPopularity());
                    String deathday;
                    if(newObj.getDeathday()==(null))
                    {
                        deathday="Alive";
                    }
                    else
                    {
                        deathday=newObj.getDeathday();
                    }
                    bdayofperson.setText(newObj.getBirthday()+"----"+deathday);
                    if(newObj.getBiography()!=null)
                    {
                        desc.setText(newObj.getBiography());
                    }
                    List<String> list=newObj.getAlso_known_as();
                    if(list.size()==0)
                    {
                        linearLayout.removeView(AlsoKnown);
                    }
                    else
                    {
                        arrayList.addAll(list);
                        arrayAdapter.notifyDataSetChanged();
                    }
                    if(newObj.getPlace_of_birth()!=null)
                    {
                        birthplace.setText("BORN AT: "+newObj.getPlace_of_birth());
                    }
                }
            }

            @Override
            public void onFailure(Call<PersonHeirarchy> call, Throwable t) {
                Toast.makeText(CastInfo.this, "Try Again", Toast.LENGTH_SHORT).show();

            }
        });
    }

}

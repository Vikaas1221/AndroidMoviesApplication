package com.example.moviesdbapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.moviesdbapp.Fragments.exoPlayerFragment;
import com.example.moviesdbapp.Listners.BackPressed;
import com.example.moviesdbapp.Listners.RecyclerViewClickListner;
import com.example.moviesdbapp.R;

public class DetailsActivity extends AppCompatActivity implements BackPressed
{
    public static String type="AIzaSyAR-6Ej7276UArDyVQ9w-rafFfGPxr3Bqo";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        String id=getIntent().getExtras().getString("id");
        String img=getIntent().getExtras().getString("image");
        String name=getIntent().getExtras().getString("moviename");
        type=getIntent().getExtras().getString("type");
        exoPlayerFragment fragment=new exoPlayerFragment();
        fragment.setId(id);
        fragment.setImage(img);
        fragment.setType(type);
        fragment.setMovieNAme(name);
        getSupportFragmentManager().beginTransaction().replace(R.id.detailsContainer,fragment).addToBackStack(null).commit();

    }

    @Override
    public void onBackPressed()
    {
        finish();

    }
}


package com.example.moviesdbapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.moviesdbapp.Fragments.FavouritesFragement;
import com.example.moviesdbapp.Fragments.MoviesFragment;
import com.example.moviesdbapp.Fragments.TvShowsFragment;
import com.example.moviesdbapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeScreen extends AppCompatActivity
{
    public static Context context;
    private BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=HomeScreen.this;
        setContentView(R.layout.activity_home_screen);
    }

}

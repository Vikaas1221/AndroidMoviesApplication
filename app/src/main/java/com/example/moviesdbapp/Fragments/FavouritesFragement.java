package com.example.moviesdbapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesdbapp.Adapter.seeMoreScreenAdapter;
import com.example.moviesdbapp.Model.Movie;
import com.example.moviesdbapp.R;
import com.example.moviesdbapp.ViewModel.ViewModel;
import com.example.moviesdbapp.Listners.mainactivityInterface;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class FavouritesFragement extends Fragment
{
    private static FavouritesFragement instance;
    FirebaseAuth mauth;
    FirebaseUser currentuser;
    String user;
     RecyclerView SeeMoreRecycerView;
    public ArrayList<Movie> movieArrayList;
    seeMoreScreenAdapter adapter;
    ViewModel viewModel;
    Context mcontext;
    mainactivityInterface minterface;
    public static FavouritesFragement getInstance()
    {
        if (instance==null)
        {
            instance=new FavouritesFragement();
        }
        return instance;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View  view=inflater.inflate(R.layout.favourites_fragment,container,false);
        mauth=FirebaseAuth.getInstance();
        currentuser=mauth.getCurrentUser();
        SeeMoreRecycerView=view.findViewById(R.id.allMoviesRecyclerView);
        assert currentuser != null;
        minterface=(mainactivityInterface) getActivity();
        user=currentuser.getUid();
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        ArrayList<Movie> arrayList1=new ArrayList<>();
        SeeMoreRecycerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        SeeMoreRecycerView.setHasFixedSize(true);
        viewModel= ViewModelProviders.of(this).get(ViewModel.class);
        viewModel.setUser(user);
        viewModel.getAllFavourite().observe(this, new Observer<ArrayList<Movie>>()
        {

            @Override
            public void onChanged(ArrayList<Movie> arrayList)
            {
                arrayList1.clear();
                arrayList1.addAll(arrayList);
                Log.d("sizems",""+arrayList.size());
                adapter=new seeMoreScreenAdapter(arrayList1,minterface);
                SeeMoreRecycerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });

    }

}

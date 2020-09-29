package com.example.moviesdbapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesdbapp.Model.Movie;
import com.example.moviesdbapp.R;
import com.example.moviesdbapp.Listners.RecyclerViewClickListner;
import com.example.moviesdbapp.Listners.mainactivityInterface;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class seeMoreScreenAdapter extends RecyclerView.Adapter<seeMoreScreenAdapter.seeMoreViewHolder>
{

    private ArrayList<Movie> movieArrayList;
    private Context context;
    private RecyclerViewClickListner recyclerViewClickListner;
    private mainactivityInterface minterface;

    public static final String ImageBaseUrl="https://image.tmdb.org/t/p/w500";

    public seeMoreScreenAdapter(ArrayList<Movie> movieArrayList,Context context,RecyclerViewClickListner recyclerViewClickListner)
    {
        this.movieArrayList=movieArrayList;
        this.context=context;
        this.recyclerViewClickListner=recyclerViewClickListner;
    }
    public seeMoreScreenAdapter(ArrayList<Movie> movieArrayList,mainactivityInterface minterface)
    {
        this.movieArrayList=movieArrayList;
        this.minterface=minterface;
    }
    public seeMoreScreenAdapter(Context context,ArrayList<Movie> movieArrayList)
    {
        this.movieArrayList=movieArrayList;
        this.context=context;
    }



    @NonNull
    @Override
    public seeMoreScreenAdapter.seeMoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_layout_allmovies,parent,false);
        seeMoreViewHolder holder=new seeMoreViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull seeMoreScreenAdapter.seeMoreViewHolder holder, int position)
    {
        Movie movie=movieArrayList.get(position);
        Picasso.get().load(ImageBaseUrl+movie.getMovieImage()).into(holder.movieImage);
        holder.movieImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (recyclerViewClickListner==null)
                {
                    minterface.favouritesInterface(movieArrayList,position);
                }
                else if (minterface==null)
                {
                    recyclerViewClickListner.onItemClick(position);
                }

            }
        });
    }

    @Override
    public int getItemCount()
    {
        return movieArrayList.size();
    }

    public class seeMoreViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView movieImage;
        public seeMoreViewHolder(@NonNull View itemView)
        {
            super(itemView);
            movieImage=itemView.findViewById(R.id.movieImage);
        }
    }
}

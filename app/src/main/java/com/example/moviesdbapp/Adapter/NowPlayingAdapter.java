package com.example.moviesdbapp.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesdbapp.Model.Movie;
import com.example.moviesdbapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class NowPlayingAdapter extends RecyclerView.Adapter<NowPlayingAdapter.Viewholder>
{
    private ArrayList<Movie> movieArrayList;
    private Context context;
    public static final String ImageBaseUrl="https://image.tmdb.org/t/p/w500";
    public NowPlayingAdapter( ArrayList<Movie> movieArrayList,Context context)
    {
        this.movieArrayList=movieArrayList;
        this.context=context;
    }

    @NonNull
    @Override
    public NowPlayingAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.now_playing_layout,parent,false);
        Viewholder holder=new Viewholder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NowPlayingAdapter.Viewholder holder, int position)
    {
        Movie movie=movieArrayList.get(position);
        Picasso.get().load(ImageBaseUrl+movie.getMovieImage()).into(holder.imageView);
        holder.moviename.setText(movie.getOriginalTitle());
        Log.d("asdfgh",""+movie.getOriginalTitle());
        String date=movie.getRelaseDate();
        String year1="";
        char[] year=date.toCharArray();
        for (int i=0;i<=3;i++)
        {
            year1=year1+year[i];
        }

        holder.movieyear.setText(year1);
    }

    @Override
    public int getItemCount()
    {
        return movieArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder
    {
        CircleImageView imageView;
        TextView moviename,movieyear;
        public Viewholder(@NonNull View itemView)
        {
            super(itemView);
            imageView=itemView.findViewById(R.id.now_playing_movie_image);
            moviename=itemView.findViewById(R.id.movieName);
            movieyear=itemView.findViewById(R.id.movieYear);
        }
    }
}

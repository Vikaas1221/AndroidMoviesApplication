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

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder>
{
    private ArrayList<Movie> movieArrayList;
    private Context context;
    private RecyclerViewClickListner recyclerViewClickListner;
//    public id_interface idInterface;
    mainactivityInterface anInterface;

    public static final String ImageBaseUrl="https://image.tmdb.org/t/p/w500";

    public HomeAdapter( ArrayList<Movie> movieArrayList,Context context)
    {
        this.movieArrayList=movieArrayList;
        this.context=context;
    }
    public HomeAdapter(ArrayList<Movie> movieArrayList,mainactivityInterface anInterface,Context context)
    {
        this.movieArrayList=movieArrayList;
     //   this.idInterface=idInterface;
        this.context=context;
        this.anInterface=anInterface;
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_layout_allmovies,parent,false);
        ViewHolder holder=new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position)
    {
        Movie movie=movieArrayList.get(position);
        Picasso.get().load(ImageBaseUrl+movie.getMovieImage()).into(holder.movieImage);
        holder.movieImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
//                idInterface.item_id(movie.getId(),"movie",movie.getMovieImage());
                anInterface.item_id(movie.getId(),movie.getType(),movie.getMovieImage(),movie.getOriginalTitle());
            }
        });


    }

    @Override
    public int getItemCount()
    {
        return movieArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView movieImage;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            movieImage=itemView.findViewById(R.id.movieImage);
        }

    }
}

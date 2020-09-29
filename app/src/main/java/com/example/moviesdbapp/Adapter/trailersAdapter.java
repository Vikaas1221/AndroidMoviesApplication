package com.example.moviesdbapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesdbapp.Fragments.exoPlayerFragment;
import com.example.moviesdbapp.Model.Trailers;
import com.example.moviesdbapp.R;
import com.example.moviesdbapp.Listners.RecyclerViewClickListner;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class trailersAdapter extends RecyclerView.Adapter<trailersAdapter.trailerViewholder>
{
    public static ArrayList<Trailers> movieArrayList;
    private Context context;
    RecyclerViewClickListner clickListner;
    public static final String ImageBaseUrl="https://image.tmdb.org/t/p/w500";
    public trailersAdapter( ArrayList<Trailers> movieArrayList,Context context,RecyclerViewClickListner clickListner)
    {
        trailersAdapter.movieArrayList =movieArrayList;
        this.context=context;
        this.clickListner=clickListner;
    }



    @NonNull
    @Override
    public trailersAdapter.trailerViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trailer_single_layout,parent,false);
        trailerViewholder holder=new trailerViewholder(view);
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull trailersAdapter.trailerViewholder holder, int position)
    {

        Picasso.get().load(ImageBaseUrl+exoPlayerFragment.getImage()).into(holder.imageView);
        int pos=position+1;
        holder.moviename.setText("Trailer "+pos);
        holder.imageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                clickListner.onItemClick(position);
            }
        });
        //id.item_id(movieArrayList);


    }

    @Override
    public int getItemCount()
    {
        return movieArrayList.size();
    }

    public class trailerViewholder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView moviename;
        public trailerViewholder(@NonNull View itemView)
        {
            super(itemView);
            imageView=itemView.findViewById(R.id.now_playing_movie_image);
            moviename=itemView.findViewById(R.id.movieName);
        }
    }
}

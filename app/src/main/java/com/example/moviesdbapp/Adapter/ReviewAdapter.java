package com.example.moviesdbapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesdbapp.Model.ReviewsModel;
import com.example.moviesdbapp.R;
import com.example.moviesdbapp.ViewModel.ViewModel;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder>
{
    public Context context;
    public ArrayList<ReviewsModel> arrayList;
    public ReviewAdapter(Context context, ArrayList<ReviewsModel> arrayList)
    {
        this.context=context;
        this.arrayList=arrayList;
    }

    @NonNull
    @Override
    public ReviewAdapter.ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_login,parent,false);
        ReviewHolder holder=new ReviewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ReviewHolder holder, int position)
    {
        if (arrayList.size()==0)
        {
            holder.review.setText("No Reviews");
        }
        else
        {
            ReviewsModel model=arrayList.get(position);
            holder.author.setText(model.getAuthor());
            holder.review.setText(model.getContent());
        }

    }

    @Override
    public int getItemCount()
    {
        return arrayList.size();
    }

    public class ReviewHolder extends RecyclerView.ViewHolder
    {
        TextView author,review;
        public ReviewHolder(@NonNull View itemView)
        {
            super(itemView);
            author=itemView.findViewById(R.id.author);
            review=itemView.findViewById(R.id.review);
        }
    }
}

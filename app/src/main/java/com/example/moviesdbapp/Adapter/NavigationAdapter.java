package com.example.moviesdbapp.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesdbapp.Activity.MainActivity;
import com.example.moviesdbapp.Fragments.FavouritesFragement;
import com.example.moviesdbapp.Fragments.MoviesFragment;
import com.example.moviesdbapp.Fragments.TvShowsFragment;
import com.example.moviesdbapp.Model.NavModel;
import com.example.moviesdbapp.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.moviesdbapp.Activity.MainActivity.drawerLayout;
import static com.example.moviesdbapp.Activity.MainActivity.toolbar;

public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.NavViewHolder>
{
    ArrayList<NavModel> navModels;
    Context context;
    Context context1;
    public NavigationAdapter(ArrayList<NavModel> navModels,Context context)
    {
        this.navModels=navModels;
        this.context=context;
    }

    @NonNull
    @Override
    public NavigationAdapter.NavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.nav_item_layout,parent,false);
        NavViewHolder holder=new NavViewHolder(view);
        context1=parent.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NavigationAdapter.NavViewHolder holder, int position)
    {
        NavModel model=navModels.get(position);
        holder.nav_item_name.setText(model.getNav_item_name());
        holder.imageView.setImageResource(model.getImage());
        holder.nav_item_name.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Fragment selectedFragment=null;
                switch (position)
                {
                    case 0:
                    {
                            Log.d("fragmentObject","Fragment object is created ");
                            selectedFragment=MoviesFragment.getInstance();
                        ((MainActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.HomeScreenContainer,selectedFragment).commit();
                        drawerLayout.closeDrawers();
                            toolbar.setTitle("Movies");
                        break;
                    }
                    case 1:
                    {
                           selectedFragment=TvShowsFragment.getInstance();
                        ((MainActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.HomeScreenContainer,selectedFragment).commit();
                        drawerLayout.closeDrawers();
                            toolbar.setTitle("Tv Shows");
                        break;
                    }
                    case 2:
                    {
                            selectedFragment=FavouritesFragement.getInstance();
                        ((MainActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.HomeScreenContainer,selectedFragment).commit();
                        drawerLayout.closeDrawers();
                            toolbar.setTitle("Favourites");

                        break;
                    }
                    case 3:
                    {
                        drawerLayout.closeDrawers();
                        break;
                    }
                }
//                ((MainActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.HomeScreenContainer,selectedFragment).commit();
//                drawerLayout.closeDrawers();
            }
        });


    }

    @Override
    public int getItemCount()
    {
        return navModels.size();
    }

    public class NavViewHolder extends RecyclerView.ViewHolder
    {
        TextView nav_item_name;
        CircleImageView imageView;
        public NavViewHolder(@NonNull View itemView)
        {
            super(itemView);
            nav_item_name=itemView.findViewById(R.id.item_name);
            imageView=itemView.findViewById(R.id.now_playing_movie_image);
        }
    }
}

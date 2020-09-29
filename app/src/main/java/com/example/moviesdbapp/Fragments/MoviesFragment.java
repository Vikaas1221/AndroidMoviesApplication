package com.example.moviesdbapp.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesdbapp.Adapter.HomeAdapter;
import com.example.moviesdbapp.Adapter.NowPlayingAdapter;
import com.example.moviesdbapp.Model.Movie;
import com.example.moviesdbapp.R;
import com.example.moviesdbapp.ViewModel.ViewModel;
import com.example.moviesdbapp.Listners.communicator;
import com.example.moviesdbapp.Listners.id_interface;
import com.example.moviesdbapp.Listners.mainactivityInterface;

import java.util.ArrayList;

public class MoviesFragment extends Fragment
{
    Context context;
    private communicator comm;
    private id_interface idInterface;
    public RecyclerView NowPlayingrecyclerView,TopRatedRecyclerView,PopularRecycerView,UpcomingRecyclerView;
    private TextView seeMoreUpcoimg,seeMorePopular,seeMoreTopRated,seeMoreNowPlaying;
    private TextView NowPlaying,Upcoming,Top_Rated,Popular;

    private static final String POPULAR="Popular Movies";
    private static final String TOP_RATED="Top Rated";
    private static final String UPCOMING="Upcoming Movies";
    private static final String NOWPLAYING="Now Playing";
    private static final String TYPE="movie";

    ViewModel viewModel;
    RecyclerView.Adapter adapter;
    private static MoviesFragment instance;
    mainactivityInterface anInterface;

    public static MoviesFragment getInstance()
    {
        if (instance==null)
        {
            instance=new MoviesFragment();
        }
        return instance;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View  view=inflater.inflate(R.layout.movies_framgemt,container,false);
        NowPlayingrecyclerView=view.findViewById(R.id.nowPlaying);
        TopRatedRecyclerView=view.findViewById(R.id.topRated);
        PopularRecycerView=view.findViewById(R.id.popular);
        UpcomingRecyclerView=view.findViewById(R.id.upcoming);
        seeMoreNowPlaying=view.findViewById(R.id.seeMoreNowPlaying);
        seeMoreUpcoimg=view.findViewById(R.id.seeMoreUpcoming);
        seeMorePopular=view.findViewById(R.id.seeMorePopular);
        seeMoreTopRated=view.findViewById(R.id.seeMoreTopRated);
        NowPlaying=view.findViewById(R.id.NowPlaying);
        Upcoming=view.findViewById(R.id.Upcoming);
        Top_Rated=view.findViewById(R.id.TopRated);
        Popular=view.findViewById(R.id.Popular);
        context=getContext();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        anInterface=(mainactivityInterface)getActivity();
        fetchPopularDataFromViewModel(PopularRecycerView);
        fetchDataTopFromViewModel(TopRatedRecyclerView);
       fetchDataNowPlayingFromViewModel(NowPlayingrecyclerView);
        fetchDataUpcomingFromViewModel(UpcomingRecyclerView);


    }


    public void fetchPopularDataFromViewModel(RecyclerView popularRecycerView)
    {
        ArrayList<Movie> AllPopularMovies=new ArrayList<>();
        ArrayList<Movie> movieArrayList=new ArrayList<>();
        viewModel=ViewModelProviders.of(this).get(ViewModel.class);
        viewModel.setType(TYPE);
        popularRecycerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        popularRecycerView.setHasFixedSize(true);
        viewModel.getPopularMutableLiveData().observe(this, new Observer<ArrayList<Movie>>()
        {
            @Override
            public void onChanged(ArrayList<Movie> movies)
            {
                if (movies.size()>0)
                {
                    movieArrayList.clear();
                    AllPopularMovies.clear();
                    AllPopularMovies.addAll(movies);
                    for (int i=0;i<10;i++)
                    {
                        movieArrayList.add(movies.get(i));
                    }
                }
                adapter=new HomeAdapter(movieArrayList,anInterface,getContext());
               // adapter=new HomeAdapter(movieArrayList,getContext());
                showViews(seeMorePopular,Popular);
                popularRecycerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
        clickListnerFroSeeAll(seeMorePopular,AllPopularMovies,POPULAR);
    }
    public void fetchDataTopFromViewModel(RecyclerView topRatedRecyclerView)
    {
        ArrayList<Movie> AllTopRatedMovies=new ArrayList<>();
        ArrayList<Movie> movieArrayList=new ArrayList<>();
        viewModel=ViewModelProviders.of(this).get(ViewModel.class);
        viewModel.setType(TYPE);
        topRatedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        topRatedRecyclerView.setHasFixedSize(true);
        viewModel.getTopRatedMutableLiveData().observe(this, new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(ArrayList<Movie> movies)
            {

                if (movies.size()>0)
                {
                    AllTopRatedMovies.clear();
                    AllTopRatedMovies.addAll(movies);
                    Log.d("dashurlMove",""+AllTopRatedMovies.size());
                    movieArrayList.clear();
                    for (int i=0;i<10;i++)
                    {
                        movieArrayList.add(movies.get(i));
                    }
                }

                adapter=new HomeAdapter(movieArrayList,anInterface,getContext());
                showViews(seeMoreTopRated,Top_Rated);
                topRatedRecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
        clickListnerFroSeeAll(seeMoreTopRated,AllTopRatedMovies,TOP_RATED);
    }
    public void fetchDataUpcomingFromViewModel(RecyclerView upcomingRecyclerView)
    {
        ArrayList<Movie> AllUpcomingMovies=new ArrayList<>();
        ArrayList<Movie> movieArrayList=new ArrayList<>();
        viewModel=ViewModelProviders.of(this).get(ViewModel.class);
        viewModel.setType(TYPE);
        upcomingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        upcomingRecyclerView.setHasFixedSize(true);
        viewModel.getUpcomingMutableLiveData().observe(this, new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(ArrayList<Movie> movies)
            {

                if (movies.size()>0)
                {
                    AllUpcomingMovies.clear();
                    AllUpcomingMovies.addAll(movies);
                    movieArrayList.clear();
                    for (int i=0;i<10;i++)
                    {
                        movieArrayList.add(movies.get(i));
                    }
                }

                adapter=new HomeAdapter(movieArrayList,anInterface,getContext());
                showViews(seeMoreUpcoimg,Upcoming);
                upcomingRecyclerView.setBackgroundColor(android.R.color.white);
                upcomingRecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
        Log.d("dashurlMovie",""+AllUpcomingMovies.size());
        clickListnerFroSeeAll(seeMoreUpcoimg,AllUpcomingMovies,UPCOMING);
    }
    public void fetchDataNowPlayingFromViewModel(RecyclerView nowPlayingrecyclerView)
    {
        ArrayList<Movie> movieArrayList=new ArrayList<>();
        ArrayList<Movie> allNowPlayingMovies=new ArrayList<>();
        viewModel=ViewModelProviders.of(this).get(ViewModel.class);
        viewModel.setType(TYPE);
        nowPlayingrecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        nowPlayingrecyclerView.setHasFixedSize(true);
        viewModel.getNowPlayingMutableLiveData().observe(this, new Observer<ArrayList<Movie>>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onChanged(ArrayList<Movie> movies)
            {



                if (movies.size()>0)
                {
                    movieArrayList.clear();
                    allNowPlayingMovies.clear();
                    allNowPlayingMovies.addAll(movies);
                    for (int i=0;i<10;i++)
                    {
                        movieArrayList.add(movies.get(i));
                    }
                }
//                adapter=new HomeAdapter(movieArrayList,anInterface,getContext());
                adapter=new NowPlayingAdapter(movieArrayList,getContext());
                showViews(seeMoreNowPlaying,NowPlaying);
                nowPlayingrecyclerView.setBackgroundColor(android.R.color.white);
                nowPlayingrecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
        clickListnerFroSeeAll(seeMoreNowPlaying,allNowPlayingMovies,NOWPLAYING);

    }
    public void showViews(TextView seemore,TextView category)
    {
        seemore.setVisibility(View.VISIBLE);
        category.setVisibility(View.VISIBLE);
    }
    public void clickListnerFroSeeAll(TextView type,ArrayList<Movie> arrayList,String tag)
    {
        type.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                anInterface.dataArrayList(arrayList,tag,TYPE);
            }
        });
    }

}

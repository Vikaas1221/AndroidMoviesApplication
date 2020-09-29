package com.example.moviesdbapp.Fragments;

import android.content.Context;
import android.os.Bundle;
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
import com.example.moviesdbapp.Model.Movie;
import com.example.moviesdbapp.R;
import com.example.moviesdbapp.ViewModel.ViewModel;
import com.example.moviesdbapp.Listners.communicator;
import com.example.moviesdbapp.Listners.mainactivityInterface;

import java.util.ArrayList;

public class TvShowsFragment extends Fragment
{
    private static final String AIRINGTODAY ="Airing Today" ;
    private static final String ONTV ="On TV Shows" ;
    private static final String TOPRATED ="TopRated Shows" ;
    private static final String POPULAR ="Popular Shows" ;
    private static final String TYPE="tv";
    Context context;
    public RecyclerView NowPlayingrecyclerView,TopRatedRecyclerView,PopularRecycerView,UpcomingRecyclerView;
    private TextView seeMoreOnTV,seeMorePopular,seeMoreTopRated,seeMoreAiring;
    private TextView ONTv,AiringTv,Top_Rated,Popular;

    ViewModel viewModel;
    RecyclerView.Adapter adapter;
    private static TvShowsFragment instance;
    private communicator comm;
    mainactivityInterface anInterface;

    public static TvShowsFragment getInstance()
    {
        if (instance==null)
        {
            instance=new TvShowsFragment();
        }
        return instance;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View  view=inflater.inflate(R.layout.tvshows_fragment_layout,container,false);
        NowPlayingrecyclerView=view.findViewById(R.id.nowPlaying);
        TopRatedRecyclerView=view.findViewById(R.id.topRated);
        PopularRecycerView=view.findViewById(R.id.popular);
        UpcomingRecyclerView=view.findViewById(R.id.upcoming);

        seeMoreAiring=view.findViewById(R.id.seeMoreAiring);
        seeMoreOnTV=view.findViewById(R.id.seeMoreOntv);
        seeMorePopular=view.findViewById(R.id.seeMorePopular);
        seeMoreTopRated=view.findViewById(R.id.seeMoreTopRated);
        ONTv=view.findViewById(R.id.on_tv);
        AiringTv=view.findViewById(R.id.airing_today);
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
        fetchDataAllAiringFromViewModel((NowPlayingrecyclerView));
        fetchDataTvOnairFromViewModel(UpcomingRecyclerView);

    }
//    @Override
//    public void onAttach(@NonNull Context context)
//    {
//        super.onAttach(context);
//        try {
//           // comm=(communicator) context;
//            anInterface=(mainactivityInterface)context;
//
//        }catch (ClassCastException e)
//        {
//            throw new ClassCastException(context.toString()
//                    + " must implement communicator");
//        }
//    }
    public void fetchPopularDataFromViewModel(RecyclerView popularRecycerView)
    {
        ArrayList<Movie> PopularTvShows=new ArrayList<>();
        ArrayList<Movie> movieArrayList=new ArrayList<>();
        viewModel= ViewModelProviders.of(this).get(ViewModel.class);
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
                    PopularTvShows.addAll(movies);
                    for (int i=0;i<6;i++)
                    {
                        movieArrayList.add(movies.get(i));
                    }
                }
                adapter=new HomeAdapter(movieArrayList,anInterface,getContext());
                popularRecycerView.setAdapter(adapter);
                showViews(seeMorePopular,Popular);
                adapter.notifyDataSetChanged();
            }
        });
        clickListnerFroSeeAll(seeMorePopular,PopularTvShows,POPULAR);
    }
    public void fetchDataTopFromViewModel(RecyclerView topRatedRecyclerView)
    {
        ArrayList<Movie> TopRatedShows=new ArrayList<>();
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
                    movieArrayList.clear();
                    TopRatedShows.addAll(movies);
                    for (int i=0;i<6;i++)
                    {
                        movieArrayList.add(movies.get(i));
                    }
                }

                adapter=new HomeAdapter(movieArrayList,anInterface,getContext());
                topRatedRecyclerView.setAdapter(adapter);
                showViews(seeMoreTopRated,Top_Rated);
                adapter.notifyDataSetChanged();
            }
        });
        clickListnerFroSeeAll(seeMoreTopRated,TopRatedShows,TOPRATED);
    }
    public void fetchDataTvOnairFromViewModel(RecyclerView upcomingRecyclerView)
    {
        ArrayList<Movie> movieArrayList=new ArrayList<>();
        ArrayList<Movie> OnAirShows=new ArrayList<>();
        viewModel=ViewModelProviders.of(this).get(ViewModel.class);
        viewModel.setType(TYPE);
        upcomingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        upcomingRecyclerView.setHasFixedSize(true);
        viewModel.getAllOnTvMutableLiveData().observe(this, new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(ArrayList<Movie> movies)
            {

                if (movies.size()>0)
                {
                    movieArrayList.clear();
                    OnAirShows.addAll(movies);
                    for (int i=0;i<6;i++)
                    {
                        movieArrayList.add(movies.get(i));
                    }
                }

                adapter=new HomeAdapter(movieArrayList,anInterface,getContext());
                upcomingRecyclerView.setAdapter(adapter);
                showViews(seeMoreOnTV,ONTv);
                adapter.notifyDataSetChanged();
            }
        });
        clickListnerFroSeeAll(seeMoreOnTV,OnAirShows,ONTV);
    }
    public void fetchDataAllAiringFromViewModel(RecyclerView nowPlayingrecyclerView)
    {
        ArrayList<Movie> movieArrayList=new ArrayList<>();
        ArrayList<Movie> AllAiringShows=new ArrayList<>();
        viewModel=ViewModelProviders.of(this).get(ViewModel.class);
        viewModel.setType(TYPE);
        nowPlayingrecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        nowPlayingrecyclerView.setHasFixedSize(true);
        viewModel.getAllAiringMutableLiveData().observe(this, new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(ArrayList<Movie> movies)
            {

                if (movies.size()>0)
                {
                    movieArrayList.clear();
                    AllAiringShows.addAll(movies);
                    for (int i=0;i<6;i++)
                    {
                        movieArrayList.add(movies.get(i));
                    }
                }
                adapter=new HomeAdapter(movieArrayList,anInterface,getContext());
                nowPlayingrecyclerView.setAdapter(adapter);
                showViews(seeMoreAiring,AiringTv);
                adapter.notifyDataSetChanged();
            }
        });
        clickListnerFroSeeAll(seeMoreAiring,AllAiringShows,AIRINGTODAY);
    }
    public void showViews(TextView seemore, TextView category)
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
                //comm.dataArrayList(arrayList,tag,TYPE);
                anInterface.dataArrayList(arrayList,tag,TYPE);
            }
        });
    }
}

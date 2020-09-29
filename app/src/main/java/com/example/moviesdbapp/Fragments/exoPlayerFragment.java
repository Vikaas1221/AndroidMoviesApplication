package com.example.moviesdbapp.Fragments;
import android.os.Bundle;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesdbapp.Adapter.ReviewAdapter;
import com.example.moviesdbapp.Adapter.trailersAdapter;
import com.example.moviesdbapp.Listners.BackPressed;
import com.example.moviesdbapp.Model.ReviewsModel;
import com.example.moviesdbapp.Model.Trailers;
import com.example.moviesdbapp.R;
import com.example.moviesdbapp.Listners.RecyclerViewClickListner;
import com.example.moviesdbapp.ViewModel.ViewModel;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerSupportFragmentX;
import java.util.ArrayList;
public class exoPlayerFragment extends Fragment
{
    TextView movieName;
    RecyclerView trailerrecyler,reviewRecyclerView;
    RecyclerView.Adapter reviewadapter;
    RecyclerView.Adapter adapter;
    ArrayList<Trailers> arrayList;
    ViewModel viewModel;
    private static final String API_KEY="AIzaSyAR-6Ej7276UArDyVQ9w-rafFfGPxr3Bqo";
    private static String VIDEO_ID;
    String id;
    public static String image;
    RecyclerViewClickListner recyclerViewClickListner;
    BackPressed backPressed;


    public String movieNAme;
    public String type;
    ArrayList<Trailers> TrailersArrayList;
    ArrayList<Trailers> ReviewsArrayList;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.exoplayer_layout,container,false);
        trailerrecyler=view.findViewById(R.id.trailersRecycler);
        reviewRecyclerView=view.findViewById(R.id.reviewRecyclerView);
        movieName=view.findViewById(R.id.moviename);
        movieName.setText(getMovieNAme());

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        backPressed=(BackPressed)getActivity();
       fetchTrailersDataFromViewModel();
       fetchReviewsFromViewModel();
    }


    public void fetchTrailersDataFromViewModel()
    {
        TrailersArrayList=new ArrayList<>();
        viewModel= ViewModelProviders.of(this).get(ViewModel.class);
        viewModel.setType(getType());
        trailerrecyler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        trailerrecyler.setHasFixedSize(true);
        viewModel.getTrailerData(getid()).observe(getViewLifecycleOwner(), new Observer<ArrayList<Trailers>>()
        {
            @Override
            public void onChanged(ArrayList<Trailers> trailers)
            {
                if (trailers.size()>0)
                {
                    TrailersArrayList.clear();
                    Log.d("Arraylistsize", "" + TrailersArrayList.size());
                    getTrailerKey(trailers.get(0).getTrailerKey());
                    adapter = new trailersAdapter(trailers, getContext(), new RecyclerViewClickListner() {

                        @Override
                        public void onItemClick(int position)
                        {
                            getTrailerKey(trailers.get(position).getTrailerKey());
                        }
                    });
                    trailerrecyler.setAdapter(adapter);
                    TrailersArrayList.addAll(trailers);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
    public void fetchReviewsFromViewModel()
    {
        ReviewsArrayList=new ArrayList<>();
        viewModel= ViewModelProviders.of(this).get(ViewModel.class);
        viewModel.setType(getType());
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        reviewRecyclerView.setHasFixedSize(true);
        viewModel.getReviewsData(getid()).observe(this, new Observer<ArrayList<ReviewsModel>>()
        {
            @Override
            public void onChanged(ArrayList<ReviewsModel> arrayList)
            {
                    reviewadapter = new ReviewAdapter(getContext(), arrayList);
                    reviewRecyclerView.setAdapter(reviewadapter);
                    reviewadapter.notifyDataSetChanged();
            }
        });

    }
    public void getTrailerKey(String key)
    {
        if (key!=null||key.equals(""))
        {
            YouTubePlayerSupportFragmentX youTubePlayerFragment = YouTubePlayerSupportFragmentX.newInstance();
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.replace(R.id.youtube_layout, youTubePlayerFragment).commit();
            youTubePlayerFragment.initialize(API_KEY, new YouTubePlayer.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                    if (!b) {

                        youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                        youTubePlayer.loadVideo(key);
                        youTubePlayer.setShowFullscreenButton(false);
                        youTubePlayer.play();
                    }

                }

                @Override
                public void onInitializationFailure(Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                    String errorMessage = youTubeInitializationResult.toString();
                    Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
                    Log.d("errorMessage:", errorMessage);
                }
            });
        }
    }

    public String getid() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public  static String getImage() {
        return image;
    }

    public void setImage(String image) {
        exoPlayerFragment.image = image;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getMovieNAme()
    {
        return movieNAme;
    }

    public void setMovieNAme(String movieNAme)
    {
        this.movieNAme = movieNAme;
    }


}
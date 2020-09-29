package com.example.moviesdbapp.ViewModel;

import android.util.Log;
import android.widget.LinearLayout;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.moviesdbapp.Model.Movie;

import com.example.moviesdbapp.Model.ReviewsModel;
import com.example.moviesdbapp.Model.Trailers;
import com.example.moviesdbapp.Repositry.MovieRepositry;

import java.util.ArrayList;

public class ViewModel extends androidx.lifecycle.ViewModel
{
    String quer="";
    MovieRepositry movieRepo=null;
    MutableLiveData<Movie> queryMutable=new MutableLiveData<>();
    MutableLiveData<String> queryMutable2=new MutableLiveData<>();
    MutableLiveData<String> checkFroFav=new MutableLiveData<>();
    LiveData<ArrayList<Movie>> popularlistLiveData;
    LiveData<ArrayList<Movie>> topRatedlistLiveData;
    LiveData<ArrayList<Movie>> upcominglistLiveData;
    LiveData<ArrayList<Movie>> nowPlayinglistLiveData;
    LiveData<ArrayList<Movie>> allFavouritesLiveData;
    LiveData<ArrayList<Movie>> allAiringlistLiveData;
    LiveData<ArrayList<Movie>> allOnTvlistLiveData;

    LiveData<ArrayList<Trailers>> TrailermutableLiveData;
    LiveData<ArrayList<ReviewsModel>> ReviewsmutableLiveData;
    MutableLiveData<ArrayList<Movie>> ReviewsmutableLiveData1;
    LiveData<String> message;
    LiveData<String> message2;
    LiveData<Boolean> isFavMovie;


    private String user;
    private Movie movie;
    public ViewModel()
    {
        movieRepo=MovieRepositry.getInstance();
    }
    public void setType(String type)
    {
        movieRepo.getType(type);
    }
    public void setQuery(Movie movie)
    {
        queryMutable.setValue(movie);
    }
    public void setQueryMutable2(String movieId)
    {
        queryMutable2.setValue(movieId);
    }
    public void setId(String id)
    {
        movieRepo.getId(id);
    }

    public void setUser(String user) {
        this.user = user;
        movieRepo.getUser(user);
    }


    public LiveData<ArrayList<Movie>> getPopularMutableLiveData()
    {
        Log.d("uytr","int viewmodel");
        if (popularlistLiveData==null)
        {
            popularlistLiveData=movieRepo.getAllPopularMovies();
        }
        return popularlistLiveData;
    }
    public LiveData<ArrayList<Movie>> getTopRatedMutableLiveData()
    {
        Log.d("uytr","int viewmodel");
        if (topRatedlistLiveData==null)
        {
            topRatedlistLiveData=movieRepo.getAllTopRatedMovies();
        }
        return topRatedlistLiveData;
    }
    public LiveData<ArrayList<Movie>> getUpcomingMutableLiveData()
    {
        Log.d("uytr","int viewmodel");
        if (upcominglistLiveData==null)
        {
            upcominglistLiveData=movieRepo.getAllUpcomingMovies();
        }
        return upcominglistLiveData;
    }
    public LiveData<ArrayList<Movie>> getNowPlayingMutableLiveData()
    {
        Log.d("uytr","int viewmodel");
        if (nowPlayinglistLiveData==null)
        {
            nowPlayinglistLiveData=movieRepo.getAllNowPlayingMovies();
        }
        return nowPlayinglistLiveData;
    }
    public LiveData<ArrayList<Movie>> getAllAiringMutableLiveData()
    {
        Log.d("uytr","int viewmodel");
        if (allAiringlistLiveData==null)
        {
            allAiringlistLiveData=movieRepo.getAllairingTodayMovies();
        }
        return allAiringlistLiveData;
    }
    public LiveData<ArrayList<Movie>> getAllOnTvMutableLiveData()
    {
        Log.d("uytr","int viewmodel");
        if (allOnTvlistLiveData==null)
        {
            allOnTvlistLiveData=movieRepo.getAllOnTvTodayMovies();
        }
        return allOnTvlistLiveData;
    }
    public LiveData<ArrayList<Trailers>> getTrailerData(String id)
    {
        if (TrailermutableLiveData==null)
        {
            TrailermutableLiveData=movieRepo.getAllTrailers(id);
        }
        return TrailermutableLiveData;
    }
    public LiveData<ArrayList<ReviewsModel>> getReviewsData(String id)
    {
        if (ReviewsmutableLiveData==null)
        {
            ReviewsmutableLiveData=movieRepo.getAllReviews(id);
        }
        return ReviewsmutableLiveData;
    }
    public LiveData<String> getMessageOfAdd()
    {
        if (message==null)
        {
            message=Transformations.switchMap(queryMutable,input->movieRepo.addToFavourite(queryMutable.getValue()));
        }
        return message;
    }
    public LiveData<ArrayList<Movie>> getAllFavourite()
    {

        //MutableLiveData<ArrayList<Movie>> listMutableLiveData;
        if (allFavouritesLiveData==null)
        {
            allFavouritesLiveData=movieRepo.getAllFavouritesMovies();
        }
        return allFavouritesLiveData;
    }
    public LiveData<String> getMessageOfRemoved()
    {
        if (message2==null)
        {
            message2=Transformations.switchMap(queryMutable2,input->movieRepo.removeFromFavourites(queryMutable2.getValue()));
        }
        return message2;
    }

    public LiveData<Boolean> isFav()
    {
        if (isFavMovie==null)
        {
            isFavMovie=Transformations.switchMap(queryMutable2,input->movieRepo.searchMovieInDatabase(queryMutable2.getValue()));
        }
        return isFavMovie;
    }


}

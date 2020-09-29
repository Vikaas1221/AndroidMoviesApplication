package com.example.moviesdbapp.Repositry;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviesdbapp.Model.Movie;
import com.example.moviesdbapp.Model.ReviewsModel;
import com.example.moviesdbapp.Model.Trailers;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import static com.example.moviesdbapp.Activity.MainActivity.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class MovieRepositry
{
    private FirebaseFirestore firestore=FirebaseFirestore.getInstance();
    private CollectionReference reference=firestore.collection("Favourite");
    String API_KEY="";
    String type="";
    String id="";
    private static final String SUCESS="sucess";
    private static final String ERROR="failed";
    public static  int i=0;
    private static MovieRepositry instance;
    private String user;
    private static  String YOURAPIKEY="1f59ebe04c42625fc6c290dccb75e0d5";

    public static MovieRepositry getInstance()
    {
        if (instance==null)
        {
            instance=new MovieRepositry();
        }
        return instance;
    }
    public void getUser(String user)
    {
        this.user=user;
    }
    public void getType(String type)
    {
        this.type=type;
    }
    public void getId(String id)
    {
        this.id=id;
    }

    // HTTP request for popular movies from api
    public MutableLiveData<ArrayList<Movie>> getAllPopularMovies()
    {
        API_KEY="https://api.themoviedb.org/3/"+type+"/popular?api_key="+YOURAPIKEY+"&language=en-US&page=1";
        Log.d("qwert","In repo"+i++);
        final MutableLiveData<ArrayList<Movie>> data = new MutableLiveData<>();
        final ArrayList<Movie> arrayList=new ArrayList<>();
        JsonObjectRequest objectRequest=new JsonObjectRequest(Request.Method.GET, API_KEY, null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                Log.d("callingjsonapi","calling json api");
                JSONArray jsonArray= null;
                try {
                    jsonArray = response.getJSONArray("results");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                arrayList.clear();
                for (int i=0;i<jsonArray.length();i++)
                {
                    JSONObject object= null;
                    try {
                        object = (JSONObject) jsonArray.get(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String original_name= null;
                    if (type.equals("movie"))
                    {
                        try {
                            original_name = object.getString("original_title");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        try {
                            original_name = object.getString("original_name");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    String image_url= null;
                    try {
                        image_url = object.getString("poster_path");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String user_Rating= null;
                    try {
                        user_Rating = object.getString("vote_average");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String Relase_date= "";
                    try {
                        Relase_date = object.getString("release_date");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String overview= null;
                    try {
                        overview = object.getString("overview");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String id= null;
                    try {
                        id = object.getString("id");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Movie model=new Movie(original_name,image_url,user_Rating,Relase_date,overview,id);
                    model.setType(type);
                    arrayList.add(model);

                }
                data.setValue(arrayList);

            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(context,"Something wrong",Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(objectRequest);
        return data;

    }
    // HTTP request to get top rated movies from api
    public MutableLiveData<ArrayList<Movie>> getAllTopRatedMovies()
    {

        API_KEY="https://api.themoviedb.org/3/"+type+"/top_rated?api_key="+YOURAPIKEY+"&language=en-US&page=1";
        Log.d("qwert","In repo");
        final MutableLiveData<ArrayList<Movie>> data = new MutableLiveData<>();
        final ArrayList<Movie> arrayList=new ArrayList<>();
        JsonObjectRequest objectRequest=new JsonObjectRequest(Request.Method.GET, API_KEY, null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                JSONArray jsonArray= null;
                try {
                    jsonArray = response.getJSONArray("results");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                arrayList.clear();
                for (int i=0;i<jsonArray.length();i++)
                {
                    JSONObject object= null;
                    try {
                        object = (JSONObject) jsonArray.get(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String original_name= null;
                    if (type.equals("movie"))
                    {
                        try {
                            original_name = object.getString("original_title");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        try {
                            original_name = object.getString("original_name");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    String image_url= null;
                    try {
                        image_url = object.getString("poster_path");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String user_Rating= null;
                    try {
                        user_Rating = object.getString("vote_average");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String Relase_date= "";
                    try {
                        Relase_date = object.getString("release_date");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String overview= null;
                    try {
                        overview = object.getString("overview");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String id= null;
                    try {
                        id = object.getString("id");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Movie model=new Movie(original_name,image_url,user_Rating,Relase_date,overview,id);
                    model.setType(type);
                    arrayList.add(model);


                }
                data.setValue(arrayList);

            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(context,"Something wrong",Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(objectRequest);
        return data;

    }
    // HTTP request to get all upcoming movies from api
    public MutableLiveData<ArrayList<Movie>> getAllUpcomingMovies()
    {

        API_KEY="https://api.themoviedb.org/3/"+type+"/upcoming?api_key="+YOURAPIKEY+"&language=en-US&page=1";
        Log.d("qwert","In repo");
        final MutableLiveData<ArrayList<Movie>> data = new MutableLiveData<>();
        final ArrayList<Movie> arrayList=new ArrayList<>();
        JsonObjectRequest objectRequest=new JsonObjectRequest(Request.Method.GET, API_KEY, null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                JSONArray jsonArray= null;
                try {
                    jsonArray = response.getJSONArray("results");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                arrayList.clear();
                for (int i=0;i<jsonArray.length();i++)
                {
                    JSONObject object= null;
                    try {
                        object = (JSONObject) jsonArray.get(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String original_name= null;
                    if (type.equals("movie"))
                    {
                        try {
                            original_name = object.getString("original_title");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        try {
                            original_name = object.getString("original_name");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    String image_url= null;
                    try {
                        image_url = object.getString("poster_path");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String user_Rating= null;
                    try {
                        user_Rating = object.getString("vote_average");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String Relase_date= "";
                    try {
                        Relase_date = object.getString("release_date");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String overview= null;
                    try {
                        overview = object.getString("overview");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String id= null;
                    try {
                        id = object.getString("id");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Movie model=new Movie(original_name,image_url,user_Rating,Relase_date,overview,id);
                    model.setType(type);
                    arrayList.add(model);


                }
                data.setValue(arrayList);

            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(context,"Something wrong",Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(objectRequest);
        return data;

    }
    // HTTP request to get all  noeplaying movies from api
    public MutableLiveData<ArrayList<Movie>> getAllNowPlayingMovies()
    {

        API_KEY="https://api.themoviedb.org/3/"+type+"/now_playing?api_key="+YOURAPIKEY+"&language=en-US&page=1";
        Log.d("qwert","In repo");
        final MutableLiveData<ArrayList<Movie>> data = new MutableLiveData<>();
        final ArrayList<Movie> arrayList=new ArrayList<>();
        JsonObjectRequest objectRequest=new JsonObjectRequest(Request.Method.GET, API_KEY, null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                JSONArray jsonArray= null;
                try {
                    jsonArray = response.getJSONArray("results");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                arrayList.clear();
                for (int i=0;i<jsonArray.length();i++)
                {
                    JSONObject object= null;
                    try {
                        object = (JSONObject) jsonArray.get(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String original_name= null;
                    if (type.equals("movie"))
                    {
                        try {
                            original_name = object.getString("original_title");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        try {
                            original_name = object.getString("original_name");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    String image_url= null;
                    try {
                        image_url = object.getString("poster_path");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String user_Rating= null;
                    try {
                        user_Rating = object.getString("vote_average");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String Relase_date= "";
                    try {
                        Relase_date = object.getString("release_date");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String overview= null;
                    try {
                        overview = object.getString("overview");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String id= null;
                    try {
                        id = object.getString("id");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Movie model=new Movie(original_name,image_url,user_Rating,Relase_date,overview,id);
                    model.setType(type);
                    arrayList.add(model);


                }
                data.setValue(arrayList);

            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(context,"Something wrong",Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(objectRequest);
        return data;

    }
    // HTTP request to get all  tv shows from api
    public MutableLiveData<ArrayList<Movie>> getAllairingTodayMovies()
    {

        API_KEY="https://api.themoviedb.org/3/"+type+"/airing_today?api_key="+YOURAPIKEY+"&language=en-US&page=1";
        Log.d("qwert","In repo");
        final MutableLiveData<ArrayList<Movie>> data = new MutableLiveData<>();
        final ArrayList<Movie> arrayList=new ArrayList<>();
        JsonObjectRequest objectRequest=new JsonObjectRequest(Request.Method.GET, API_KEY, null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                JSONArray jsonArray= null;
                try {
                    jsonArray = response.getJSONArray("results");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                arrayList.clear();
                for (int i=0;i<jsonArray.length();i++)
                {
                    JSONObject object= null;
                    try {
                        object = (JSONObject) jsonArray.get(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String original_name= null;
                    if (type.equals("movie"))
                    {
                        try {
                            original_name = object.getString("original_title");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        try {
                            original_name = object.getString("original_name");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    String image_url= null;
                    try {
                        image_url = object.getString("poster_path");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String user_Rating= null;
                    try {
                        user_Rating = object.getString("vote_average");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String Relase_date= "";
                    String overview= null;
                    try {
                        overview = object.getString("overview");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String id= null;
                    try {
                        id = object.getString("id");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Movie model=new Movie(original_name,image_url,user_Rating,Relase_date,overview,id);
                    model.setType(type);
                    arrayList.add(model);


                }
                data.setValue(arrayList);

            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(context,"Something wrong",Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(objectRequest);
        return data;

    }
    // HTTP request to get tv shows from api
    public MutableLiveData<ArrayList<Movie>> getAllOnTvTodayMovies()
    {

        API_KEY="https://api.themoviedb.org/3/"+type+"/on_the_air?api_key="+YOURAPIKEY+"&language=en-US&page=1";
        Log.d("qwert","In repo");
        final MutableLiveData<ArrayList<Movie>> data = new MutableLiveData<>();
        final ArrayList<Movie> arrayList=new ArrayList<>();
        JsonObjectRequest objectRequest=new JsonObjectRequest(Request.Method.GET, API_KEY, null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                JSONArray jsonArray= null;
                try {
                    jsonArray = response.getJSONArray("results");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                arrayList.clear();
                for (int i=0;i<jsonArray.length();i++)
                {
                    JSONObject object= null;
                    try {
                        object = (JSONObject) jsonArray.get(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String original_name= null;
                    if (type.equals("movie"))
                    {
                        try {
                            original_name = object.getString("original_title");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        try {
                            original_name = object.getString("original_name");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    String image_url= null;
                    try {
                        image_url = object.getString("poster_path");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String user_Rating= null;
                    try {
                        user_Rating = object.getString("vote_average");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String Relase_date= "";
                    String overview= null;
                    try {
                        overview = object.getString("overview");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String id= null;
                    try {
                        id = object.getString("id");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Movie model=new Movie(original_name,image_url,user_Rating,Relase_date,overview,id);
                    model.setType(type);
                    arrayList.add(model);


                }
                data.setValue(arrayList);

            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(context,"Something wrong",Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(objectRequest);
        return data;

    }

    public MutableLiveData<ArrayList<Trailers>> getAllTrailers(String id)
    {
        final String TrailersAPIKEY="https://api.themoviedb.org/3/"+type+"/"+id+"/videos?api_key="+YOURAPIKEY+"&language=en-US";
        final MutableLiveData<ArrayList<Trailers>> data = new MutableLiveData<>();
        final ArrayList<Trailers> arrayList=new ArrayList<>();
        JsonObjectRequest objectRequest=new JsonObjectRequest(Request.Method.GET, TrailersAPIKEY, null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                JSONArray jsonArray= null;
                try {
                    jsonArray = response.getJSONArray("results");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                arrayList.clear();
                for (int i=0;i<jsonArray.length();i++)
                {
                    JSONObject object= null;
                    try {
                        object = (JSONObject) jsonArray.get(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String key= null;
                    try {
                        key = object.getString("key");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Trailers model=new Trailers(key);
                    arrayList.add(model);
                }
                data.setValue(arrayList);

            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(context,"Something wrong",Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(objectRequest);
        return data;

    }
    // HTTP request to get reviews of movies and tv shows according to there id from api
    public MutableLiveData<ArrayList<ReviewsModel>> getAllReviews(String id)
    {
        Log.d("revicess",""+id);
        final String TrailersAPIKEY="https://api.themoviedb.org/3/"+type+"/"+id+"/reviews?api_key="+YOURAPIKEY+"&language=en-US";
        final MutableLiveData<ArrayList<ReviewsModel>> data = new MutableLiveData<>();
        final ArrayList<ReviewsModel> arrayList=new ArrayList<>();
        JsonObjectRequest objectRequest=new JsonObjectRequest(Request.Method.GET, TrailersAPIKEY, null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                JSONArray jsonArray= null;
                try {
                    jsonArray = response.getJSONArray("results");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                arrayList.clear();
                for (int i=0;i<jsonArray.length();i++)
                {
                    JSONObject object= null;
                    try {
                        object = (JSONObject) jsonArray.get(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String Author= null;
                    try {
                        Author = object.getString("author");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String Content= null;
                    try {
                        Content = object.getString("content");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    ReviewsModel model=new ReviewsModel(Author,Content);
                    arrayList.add(model);
                }
                data.setValue(arrayList);

            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(context,"Something wrong",Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(objectRequest);
        return data;

    }
    // To add movie or tv shows to the firebase

    public MutableLiveData<String> addToFavourite(Movie movie )
    {
        Log.d("calledAddtofavo","called");
        String message1;
        MutableLiveData<String> message=new MutableLiveData<>();
        Map<String,String> moviemap=new HashMap<>();
        moviemap.put("MovieName",movie.getOriginalTitle());
        moviemap.put("MovieImage",movie.getMovieImage());
        moviemap.put("MovieRating",movie.getUserRating());
        moviemap.put("Type",movie.getType());
        moviemap.put("MovieOverview",movie.getOverView());
        moviemap.put("MovieDate",movie.getRelaseDate());
        moviemap.put("MovieId",movie.getId());
        moviemap.put("userid",user);
        reference.add(moviemap)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>()
                {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task)
                    {
                        if (task.isSuccessful())
                        {
                            message.setValue(SUCESS);
                        }
                        else
                        {
                            message.setValue(ERROR);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        message.setValue(ERROR);
                    }
                });

        return message;
    }
    // To retrive all the favourites from firebase
    public MutableLiveData<ArrayList<Movie>> getAllFavouritesMovies()
    {
        MutableLiveData<ArrayList<Movie>> mutableLiveData=new MutableLiveData<>();
        ArrayList<Movie> movies=new ArrayList<>();
        reference.whereEqualTo("userid",user)
                .addSnapshotListener(new EventListener<QuerySnapshot>()
                {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e)
                    {
                        if (!queryDocumentSnapshots.isEmpty())
                        {
                            for (QueryDocumentSnapshot queryDocumentSnapshot:queryDocumentSnapshots)
                            {
                                String movieName=queryDocumentSnapshot.getString("MovieName");
                                String movieOverview=queryDocumentSnapshot.getString("MovieOverview");
                                String movieRating=queryDocumentSnapshot.getString("MovieRating");
                                String movieRelease=queryDocumentSnapshot.getString("MovieDate");
                                String movieId=queryDocumentSnapshot.getString("MovieId");
                                String movieImage=queryDocumentSnapshot.getString("MovieImage");
                                String type=queryDocumentSnapshot.getString("Type");
                                Movie movie=new Movie(movieName,movieImage,movieRating,movieRelease,movieOverview,movieId);
                                movie.setType(type);
                                movies.add(movie);
                            }
                            mutableLiveData.setValue(movies);
                        }
                    }
                });

        return mutableLiveData;

    }

    public MutableLiveData<String> removeFromFavourites(String movieId)
    {
        MutableLiveData<String> deleteMessage=new MutableLiveData<>();
        reference.whereEqualTo("MovieId",movieId)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@androidx.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @androidx.annotation.Nullable FirebaseFirestoreException e)
                    {
                        for(QueryDocumentSnapshot queryDocumentSnapshot:queryDocumentSnapshots)
                        {
                            String id=queryDocumentSnapshot.getId();
                            DocumentReference documentReference=reference.document(id);
                            documentReference.delete()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            deleteMessage.setValue(SUCESS);
                                        }
                                    });
                        }
                    }
                });
//                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>()
//        {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots)
//            {
//                for (QueryDocumentSnapshot queryDocumentSnapshot:queryDocumentSnapshots)
//                {
//                    String id=queryDocumentSnapshot.getId();
//                    DocumentReference documentReference=reference.document(id);
//                    documentReference.delete()
//                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid)
//                                {
//                                    deleteMessage.setValue(SUCESS);
//                                }
//                            });
//                }
//            }
//        });

        return deleteMessage;
    }
    //  to search for for the id in firebase
    public MutableLiveData<Boolean> searchMovieInDatabase(String movieId)
    {
        MutableLiveData<Boolean> isFav=new MutableLiveData<>();

        reference.whereEqualTo("MovieId",movieId)
                .addSnapshotListener(new EventListener<QuerySnapshot>()
                {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e)
                    {
                        if (!queryDocumentSnapshots.isEmpty())
                        {
                            isFav.setValue(true);
                        }
                        else
                        {
                            isFav.setValue(false);
                        }
                    }
                });
        return isFav;
    }
    }

package com.example.moviesdbapp.Activity;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviesdbapp.Adapter.NavigationAdapter;
import com.example.moviesdbapp.Fragments.FavouritesFragement;
import com.example.moviesdbapp.Fragments.MoviesFragment;
import com.example.moviesdbapp.Model.Movie;
import com.example.moviesdbapp.Model.NavModel;
import com.example.moviesdbapp.Model.userapi;
import com.example.moviesdbapp.R;
import com.example.moviesdbapp.ViewModel.ViewModel;
import com.example.moviesdbapp.Listners.mainactivityInterface;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.moviesdbapp.Adapter.HomeAdapter.ImageBaseUrl;

public class MainActivity extends AppCompatActivity implements mainactivityInterface
{
    public static DrawerLayout drawerLayout;
    RecyclerView recyclerView;
    ArrayList<NavModel> arrayList;
    RecyclerView.Adapter adapter;
    public static Toolbar toolbar;
    public static Context context;
    private TextView username;
    Fragment fragment=null;
    Button logout;
    FirebaseAuth mauth;
    private Movie movie;
    private ImageView favourite;
    ViewModel viewModel;
    private Button trailer;
    FirebaseFirestore firestore=FirebaseFirestore.getInstance();
    CollectionReference reference=firestore.collection("Favourite");


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=MainActivity.this;
        mauth=FirebaseAuth.getInstance();
        recyclerView=findViewById(R.id.navigationRecyclerview);
        logout=findViewById(R.id.logout);
        username=findViewById(R.id.user_name);
        username.setText(userapi.getInstance().getUsername());
        toolbar=findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
        toolbar.setTitle("Movies");
        drawerLayout=findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle drawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(android.R.color.white));
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
        Fragment fragment=MoviesFragment.getInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.HomeScreenContainer,fragment).commit();

        setLayoutFroNavigation();

        // to logout the user
        logout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                logout();
            }
        });


    }
    public void setLayoutFroNavigation()
    {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        arrayList=new ArrayList<>();
        String[] naev_ar={"Movies","TvShows","Favourites","Share"};
        int[] nav_item_images={R.drawable.ic_action_video_library,R.drawable.ic_laptop_chromebook_black_24dp,R.drawable.ic_collections_bookmark_black_24dp,R.drawable.ic_share_black_24dp};
        for (int i=0;i<naev_ar.length;i++)
        {
            NavModel model=new NavModel(naev_ar[i],nav_item_images[i]);
            arrayList.add(model);
        }
        adapter=new NavigationAdapter(arrayList,this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void item_id(String id, String type, String image,String moviename)
    {
        Intent intent=new Intent(MainActivity.this,DetailsActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("image",image);
        intent.putExtra("type",type);
        intent.putExtra("moviename",moviename);
        startActivity(intent);
    }

    @Override
    public void dataArrayList(ArrayList<Movie> movies, String tag, String type)
    {
        Intent intent=new Intent(MainActivity.this,seeMoreActivity.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("movies", movies);
        intent.putExtra("TAG",tag);
        intent.putExtra("type",type);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void favouritesInterface(ArrayList<Movie> movies, int position)
    {
        Toast.makeText(getApplicationContext(),"posiiton"+position,Toast.LENGTH_LONG).show();
        showBottomsheet(movies,position);
        trailer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Movie movie=movies.get(position);
                Intent intent=new Intent(MainActivity.this,DetailsActivity.class);
                String id= movie.getId();
                String movie_image=movie.getMovieImage();
                if (!id.equals(""))
                {
                    intent.putExtra("id",id);
                    intent.putExtra("image",movie_image);
                    intent.putExtra("type",movie.getType());
                    intent.putExtra("moviename",movie.getOriginalTitle());
                    startActivity(intent);
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void showBottomsheet(ArrayList<Movie> movies, int position)
    {
        BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(MainActivity.this,R.style.BottomSheetDialogTheme);
        View bottomShetView= LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.bottom_sheet_layout, findViewById(R.id.bottomSheetContainer));
        ImageView movieImag=bottomShetView.findViewById(R.id.movieImage);

        TextView MovieName=bottomShetView.findViewById(R.id.MovieName);
        TextView MovieYear=bottomShetView.findViewById(R.id.ReleaseDate);
        TextView Movieoverview=bottomShetView.findViewById(R.id.overview);
        favourite=bottomShetView.findViewById(R.id.addtoFavourite);
        favourite.setImageResource(R.drawable.ic_favorite_black_24dp);
        trailer=bottomShetView.findViewById(R.id.trailer);
        RatingBar ratingBar=bottomShetView.findViewById(R.id.ratingbar);
        movie=movies.get(position);
        float rating=Float.parseFloat(movie.getUserRating());
        rating=rating/2;
        ratingBar.setRating(rating);
        favourite.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                removeFromFavourites(movie.getId());
                movies.remove(movie);
                bottomSheetDialog.dismiss();
                Fragment fragment=new FavouritesFragement();
                getSupportFragmentManager().beginTransaction().replace(R.id.HomeScreenContainer,fragment).commit();
            }
        });

        Picasso.get().load(ImageBaseUrl+movie.getMovieImage()).into(movieImag);
        MovieName.setText("Name: "+movie.getOriginalTitle());
        MovieYear.setText("Year: "+movie.getRelaseDate());
        Movieoverview.setText("OverView"+movie.getOverView());
        bottomSheetDialog.setContentView(bottomShetView);
        bottomSheetDialog.show();
    }
    public void removeFromFavourites(String movieId)
    {
        viewModel= ViewModelProviders.of(this).get(ViewModel.class);
        viewModel.setQueryMutable2(movieId);
        viewModel.getMessageOfRemoved().observe(this, new Observer<String>()
        {
            @Override
            public void onChanged(String s)
            {
                Toast.makeText(getApplicationContext(),""+s,Toast.LENGTH_LONG).show();
            }
        });
    }

    public void logout()
    {
        mauth.signOut();
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
        finish();
    }

}

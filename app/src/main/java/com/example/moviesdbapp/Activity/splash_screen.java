package com.example.moviesdbapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.moviesdbapp.Model.userapi;
import com.example.moviesdbapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;

public class splash_screen extends AppCompatActivity
{
    FirebaseAuth mauth;
    FirebaseUser currentuser;
    FirebaseFirestore firestore=FirebaseFirestore.getInstance();
    CollectionReference reference=firestore.collection("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mauth=FirebaseAuth.getInstance();
        if (mauth.getCurrentUser()!=null)
        {
            handler();
        }
        else
        {
            Handler handler=new Handler();
            handler.postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    startActivity(new Intent(splash_screen.this,LoginActivity.class));
                    finish();
                }
            },1000);
        }


    }
    public void handler()
    {
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run()
            {
                currentuser=mauth.getCurrentUser();
                assert currentuser != null;
                String userid=currentuser.getUid();
                reference.whereEqualTo("userid",userid)
                        .addSnapshotListener(new EventListener<QuerySnapshot>()
                        {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e)
                            {
                                if (!queryDocumentSnapshots.isEmpty())
                                {
                                    for (QueryDocumentSnapshot queryDocumentSnapshot:queryDocumentSnapshots)
                                    {
                                        String username=queryDocumentSnapshot.getString("username");
                                        userapi.getInstance().setUsername(username);
                                        startActivity(new Intent(splash_screen.this,MainActivity.class));
                                        finish();
                                    }
                                }
                            }
                        });


            }
        },1000);
    }
}

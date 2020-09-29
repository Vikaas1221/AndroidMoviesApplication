package com.example.moviesdbapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.moviesdbapp.Model.userapi;
import com.example.moviesdbapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText userEmail,userPassword;
    private Button userLogin,userSignin;
    ProgressBar progressBar;
    FirebaseAuth mauth;
    String emailAddress,password;
    FirebaseUser currentuser;
    FirebaseFirestore firestore=FirebaseFirestore.getInstance();
    CollectionReference reference=firestore.collection("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        init();
        mauth=FirebaseAuth.getInstance();
        userLogin.setOnClickListener(this);
        userSignin.setOnClickListener(this);
    }
    public void init()
    {
        userEmail=findViewById(R.id.email);
        userPassword=findViewById(R.id.password);
        userLogin=findViewById(R.id.login);
        userSignin=findViewById(R.id.signin);
        progressBar=findViewById(R.id.progressbar);
    }

    @Override
    public void onClick(View v)
    {
        int id=v.getId();
        switch (id)
        {
            case R.id.login:
            {
                loginUserToMtPlayer();
                break;
            }
            case R.id.signin:
            {
                startActivity(new Intent(LoginActivity.this,newUserActivity.class));
                break;
            }
        }
    }
    public void loginUserToMtPlayer()
    {

       emailAddress=userEmail.getText().toString().trim();
       password=userPassword.getText().toString().trim();
       if (emailAddress.isEmpty())
       {
           userEmail.setError("Empty field");
           userEmail.requestFocus();
           return;
       }
       if (password.isEmpty())
       {
           userPassword.setError("Empty field");
           userPassword.requestFocus();
           return;
       }
        progressBar.setVisibility(View.VISIBLE);
       mauth.signInWithEmailAndPassword(emailAddress,password)
               .addOnCompleteListener(new OnCompleteListener<AuthResult>()
               {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task)
                   {
                       if (task.isSuccessful())
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
                                                   String  username=queryDocumentSnapshot.getString("username");
                                                   userapi api=userapi.getInstance();
                                                   api.setUsername(username);
                                                   startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                                   finish();
                                                   progressBar.setVisibility(View.GONE);
                                               }
                                           }
                                       }
                                   });

                       }
                       else
                       {
                           Toast.makeText(LoginActivity.this,"Invalid details",Toast.LENGTH_LONG).show();
                           progressBar.setVisibility(View.GONE);
                       }

                   }
               })
               .addOnFailureListener(new OnFailureListener()
               {
                   @Override
                   public void onFailure(@NonNull Exception e)
                   {
                       progressBar.setVisibility(View.GONE);
                       Toast.makeText(LoginActivity.this,""+e.getMessage(),Toast.LENGTH_LONG).show();
                   }
               });

    }

}

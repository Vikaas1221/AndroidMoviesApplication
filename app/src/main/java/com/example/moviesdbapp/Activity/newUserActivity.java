package com.example.moviesdbapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviesdbapp.Model.userapi;
import com.example.moviesdbapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class newUserActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText userEmail,userPassword,userName;
    private Button userSignin;
    private TextView alreadyHaveAccount;
    ProgressBar progressBar;
    FirebaseAuth mauth;
    String emailAddress,password,username;
    FirebaseUser currentuser;
    FirebaseFirestore firestore=FirebaseFirestore.getInstance();
    CollectionReference reference=firestore.collection("Users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        init();
        mauth=FirebaseAuth.getInstance();
        userSignin.setOnClickListener(this);
        alreadyHaveAccount.setOnClickListener(this);
    }
    public void init()
    {
        userName=findViewById(R.id.username);
        userEmail=findViewById(R.id.email);
        userPassword=findViewById(R.id.password);
        userSignin=findViewById(R.id.signin);
        progressBar=findViewById(R.id.progressbar);
        alreadyHaveAccount=findViewById(R.id.alreadyHaveAccount);
    }

    @Override
    public void onClick(View v)
    {
        int id=v.getId();
        switch (id)
        {
            case R.id.signin:
            {
                createNewUser();
                break;
            }
            case R.id.alreadyHaveAccount:
            {
                startActivity(new Intent(newUserActivity.this,LoginActivity.class));
                finish();
                break;
            }
        }
    }
    public void createNewUser()
    {

        emailAddress=userEmail.getText().toString().trim();
        password=userPassword.getText().toString().trim();
        username=userName.getText().toString().trim();
        if (username.isEmpty())
        {
            userName.setError("Empty field");
            userName.requestFocus();
            return;
        }
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
        mauth.createUserWithEmailAndPassword(emailAddress,password)
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
                            Map<String,String> userObj=new HashMap<>();
                            userObj.put("userid",userid);
                            userObj.put("username",username);
                            reference.add(userObj)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>()
                                    {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference)
                                        {
                                            userapi api=userapi.getInstance();
                                            api.setUsername(username);
                                            Toast.makeText(newUserActivity.this,"SignIn sucessfully",Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(newUserActivity.this,MainActivity.class));
                                            finish();
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    });
                        }
                        else
                        {
                            Toast.makeText(newUserActivity.this,"Invalid details",Toast.LENGTH_LONG).show();
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
                        Toast.makeText(newUserActivity.this,""+e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
    }
}

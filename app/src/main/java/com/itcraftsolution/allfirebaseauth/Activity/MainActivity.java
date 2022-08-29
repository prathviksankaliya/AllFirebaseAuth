package com.itcraftsolution.allfirebaseauth.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.itcraftsolution.allfirebaseauth.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private GoogleSignInClient googleSignInClient;
    private GoogleSignInOptions googleSignInOptions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        if(user != null)
        {
            if(getIntent().getBooleanExtra("PhoneAuth", false))
            {
                binding.textView.setText(user.getPhoneNumber());
                binding.textView2.setText(user.getDisplayName());
            }else if (getIntent().getBooleanExtra("EmailAuth", false)){
                binding.textView.setText(user.getEmail());
                binding.textView2.setText(user.getDisplayName());
            }else {
                binding.textView.setText(user.getEmail());
                binding.textView2.setText(user.getDisplayName());
            }
        }

        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Sign in with Email password

                if(getIntent().getBooleanExtra("PhoneAuth", false) || getIntent().getBooleanExtra("EmailAuth", false))
                {
                    auth.signOut();
                    startActivity(new Intent(MainActivity.this, NextActivity.class));
                    finish();
                }else{

//                SignIn with Google Auth

                    googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

                    googleSignInClient = GoogleSignIn.getClient(MainActivity.this, googleSignInOptions);
                    googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                auth.signOut();
                                startActivity(new Intent(MainActivity.this, NextActivity.class));
                                finish();
                            }
                        }
                    });
                }
            }
        });


    }
}
package com.itcraftsolution.allfirebaseauth.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.itcraftsolution.allfirebaseauth.databinding.ActivityLoginEmailBinding;

import java.util.regex.Pattern;

public class LoginEmailActivity extends AppCompatActivity {

    private ActivityLoginEmailBinding binding;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginEmailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();

        binding.btnEmailLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validEmail(binding.edEmail.getText().toString()))
                {
                    Toast.makeText(LoginEmailActivity.this, "Plz Enter Valid Email!!", Toast.LENGTH_SHORT).show();
                }else if(binding.edPassword.getText().toString().length() < 7)
                {
                    Toast.makeText(LoginEmailActivity.this, "Plz Enter Valid Password!!", Toast.LENGTH_SHORT).show();
                }else{
                    String email = binding.edEmail.getText().toString();
                    String password = binding.edPassword.getText().toString();
                    loginEmailUserAccount(email, password);
                }
            }
        });

        binding.txMoveToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginEmailActivity.this, registerEmailActivity.class));
                finish();
            }
        });
    }

    private void loginEmailUserAccount(String email, String password)
    {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(LoginEmailActivity.this, "Register Successfully :)", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginEmailActivity.this, MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginEmailActivity.this, "Register Failed!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();
        if(user != null)
        {
            startActivity(new Intent(LoginEmailActivity.this, registerEmailActivity.class)
                    .putExtra("EmailAuth", true));
        }
    }

    private boolean validEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
}
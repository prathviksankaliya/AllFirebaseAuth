package com.itcraftsolution.allfirebaseauth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.itcraftsolution.allfirebaseauth.databinding.ActivityNextBinding;

public class NextActivity extends AppCompatActivity {

    private ActivityNextBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNextBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NextActivity.this, registerEmailActivity.class));
            }
        });

        binding.btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }
}

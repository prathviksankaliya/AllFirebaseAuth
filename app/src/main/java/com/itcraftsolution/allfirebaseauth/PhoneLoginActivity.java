package com.itcraftsolution.allfirebaseauth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.itcraftsolution.allfirebaseauth.databinding.ActivityMainBinding;
import com.itcraftsolution.allfirebaseauth.databinding.ActivityPhoneLoginBinding;

public class PhoneLoginActivity extends AppCompatActivity {

    private ActivityPhoneLoginBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPhoneLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnPhoneNumLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkPhoneNumber())
                {
                    startActivity(new Intent(PhoneLoginActivity.this, PhoneOtpActivity.class)
                            .putExtra("number", binding.edPhoneNumber.getText().toString()));
                }
            }
        });

    }
    private boolean checkPhoneNumber() {
        if (binding.edPhoneNumber.getText().toString().length() != 10) {
            binding.edPhoneNumber.setError("Phone number must be 10 digits");
            return false;
        }
        return true;
    }
}
package com.itcraftsolution.allfirebaseauth.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.itcraftsolution.allfirebaseauth.databinding.ActivityPhoneOtpBinding;

import java.util.concurrent.TimeUnit;

public class PhoneOtpActivity extends AppCompatActivity {

    private ActivityPhoneOtpBinding binding;
    private FirebaseAuth auth;
    private String phoneNumber,verifyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPhoneOtpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();

        phoneNumber = "+91" + getIntent().getStringExtra("number");
        binding.tvDisplayPhoneNumber.setText(phoneNumber);

        sendVerificationCode(phoneNumber);

        binding.btnContinuePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkOtp())
                {
                    verifyCode(binding.otpView.getOTP());
                }
            }
        });

    }

    private void sendVerificationCode(String phoneNumber)
    {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth)
                .setActivity(PhoneOtpActivity.this)
                .setPhoneNumber(phoneNumber)
                .setCallbacks(callbacks)
                .setTimeout(30L, TimeUnit.SECONDS)
                .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            final String code = phoneAuthCredential.getSmsCode();

            if(code != null)
            {
                binding.otpView.setOTP(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(PhoneOtpActivity.this, "Something Went Wrong!!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verifyId = s;
        }
    };

    private void verifyCode(String code)
    {
        PhoneAuthCredential credential =PhoneAuthProvider.getCredential(verifyId, code);
        signInWithCredential(credential);

    }

    private void signInWithCredential(PhoneAuthCredential credential)
    {
        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    startActivity(new Intent(PhoneOtpActivity.this, MainActivity.class)
                            .putExtra("PhoneAuth", true));
                }else{
                    Toast.makeText(PhoneOtpActivity.this, "Please Try Again!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean checkOtp() {
        if (binding.otpView.getOTP().length() != 6) {
            Toast.makeText(PhoneOtpActivity.this, "Fill The OTP", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
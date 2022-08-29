package com.itcraftsolution.allfirebaseauth.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.itcraftsolution.allfirebaseauth.Adapters.RvShowDetailsAdapter;
import com.itcraftsolution.allfirebaseauth.Model.UserModel;
import com.itcraftsolution.allfirebaseauth.R;
import com.itcraftsolution.allfirebaseauth.databinding.ActivityShowDeatilsBinding;

public class ShowDeatilsActivity extends AppCompatActivity {

    private ActivityShowDeatilsBinding binding;
    private RvShowDetailsAdapter adapter;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowDeatilsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dialog = new ProgressDialog(ShowDeatilsActivity.this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.show();

        FirebaseRecyclerOptions<UserModel> options = new FirebaseRecyclerOptions.Builder<UserModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Students"), UserModel.class)
                .build();

        adapter = new RvShowDetailsAdapter(options);
        binding.rvShowDetails.setAdapter(adapter);
        dialog.dismiss();
        binding.rvShowDetails.setLayoutManager(new LinearLayoutManager(ShowDeatilsActivity.this));

    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}
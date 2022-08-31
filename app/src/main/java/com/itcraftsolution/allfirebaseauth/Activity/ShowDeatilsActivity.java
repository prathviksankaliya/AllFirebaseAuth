package com.itcraftsolution.allfirebaseauth.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.itcraftsolution.allfirebaseauth.Adapters.RvShowDetailsAdapter;
import com.itcraftsolution.allfirebaseauth.Model.UserModel;
import com.itcraftsolution.allfirebaseauth.R;
import com.itcraftsolution.allfirebaseauth.databinding.ActivityShowDeatilsBinding;

import java.util.Locale;

public class ShowDeatilsActivity extends AppCompatActivity {

    private ActivityShowDeatilsBinding binding;
    private RvShowDetailsAdapter adapter;
    private ProgressDialog dialog;
    private SearchView searchView;

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
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.menuSearch);

        searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("Type here To Search...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                processSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                processSearch(newText);

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);

    }

    private void processSearch(String query) {
        FirebaseRecyclerOptions<UserModel> options = new FirebaseRecyclerOptions.Builder<UserModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Students").orderByChild("name").startAt(query).endAt(query + "\uf8ff"), UserModel.class)
                .build();

        adapter = new RvShowDetailsAdapter(options);
        adapter.startListening();
        binding.rvShowDetails.setAdapter(adapter);
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
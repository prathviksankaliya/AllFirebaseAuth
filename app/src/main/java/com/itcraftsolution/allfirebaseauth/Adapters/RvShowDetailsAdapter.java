package com.itcraftsolution.allfirebaseauth.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.itcraftsolution.allfirebaseauth.Model.UserModel;
import com.itcraftsolution.allfirebaseauth.R;
import com.itcraftsolution.allfirebaseauth.databinding.RvDetailsSampleBinding;

import java.util.ArrayList;

public class RvShowDetailsAdapter extends FirebaseRecyclerAdapter<UserModel, RvShowDetailsAdapter.viewHolder> {

    public RvShowDetailsAdapter(@NonNull FirebaseRecyclerOptions<UserModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull RvShowDetailsAdapter.viewHolder holder, int position, @NonNull UserModel model) {
        Glide.with(holder.binding.igCircleImage.getContext()).load(model.getpUri()).into(holder.binding.igCircleImage);
        holder.binding.txGraduation.setText(model.getGraduation());
        holder.binding.txName.setText(model.getName());
        holder.binding.txSem.setText(model.getSemester());
    }

    @NonNull
    @Override
    public RvShowDetailsAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_details_sample, parent, false);
        return new viewHolder(view);
    }

    static class viewHolder extends RecyclerView.ViewHolder {
        RvDetailsSampleBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RvDetailsSampleBinding.bind(itemView);
        }
    }
}

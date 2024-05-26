package com.example.my_dating_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dating_app.MessageActivity;
import com.example.dating_app.Model.User;
import com.example.dating_app.R;

import java.util.List;

public class LikedUsersAdapter extends RecyclerView.Adapter<LikedUsersAdapter.LikedUsersViewHolder> {

    private List<User> likedUsersList;
    private Context context;
    private String authToken;

    public LikedUsersAdapter(List<User> likedUsersList, Context context, String authToken) {
        this.likedUsersList = likedUsersList;
        this.context = context;
        this.authToken = authToken;
    }

    @NonNull
    @Override
    public LikedUsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_liked_user, parent, false);
        return new LikedUsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LikedUsersViewHolder holder, int position) {
        User user = likedUsersList.get(position);
        holder.textViewName.setText(user.getName());

        // Handle button click to go to MessageActivity
        holder.buttonChat.setOnClickListener(v -> {
            Intent intent = new Intent(context, MessageActivity.class);
            intent.putExtra("token", authToken);
            intent.putExtra("chatUserId", user.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return likedUsersList.size();
    }

    public static class LikedUsersViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        ImageView imageViewProfile;
        Button buttonChat;

        public LikedUsersViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            imageViewProfile = itemView.findViewById(R.id.imageViewProfile);
            buttonChat = itemView.findViewById(R.id.buttonChat);
        }
    }
}

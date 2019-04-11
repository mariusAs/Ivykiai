package com.example.ivykiai.adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ivykiai.R;
import com.example.ivykiai.models.User;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> {

    private Context mCtx;
    private List<User> userList;

    public UsersAdapter(Context mCtx, List<User> userList) {
        this.mCtx = mCtx;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_users, parent, false);
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
        User user = userList.get(position);

        holder.textViewName.setText(user.getName());
        holder.textViewEmail.setText(user.getEmail());
        holder.textViewLastName.setText(user.getLast_Name());
        holder.textViewPhoneNumber.setText(user.getPhone_number());
        holder.textViewPersonalCode.setText(user.getPersonal_code());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class UsersViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewEmail, textViewLastName, textViewPhoneNumber, textViewPersonalCode;

        public UsersViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textViewName);
            textViewEmail = itemView.findViewById(R.id.textViewEmail);
            textViewLastName = itemView.findViewById(R.id.textViewLastName);
            textViewPhoneNumber = itemView.findViewById(R.id.textViewPhoneNumber);
            textViewPersonalCode = itemView.findViewById(R.id.textViewPersonalCode);
        }
    }
}
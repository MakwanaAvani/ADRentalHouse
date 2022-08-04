package com.ad.adrentalhouse;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    ArrayList<UserInfo> userInfoArrayList;

    public UserAdapter(ArrayList<UserInfo> userInfoArrayList) {
        this.userInfoArrayList = userInfoArrayList;
    }
    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user_info, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        UserInfo userInfo = userInfoArrayList.get(position);
        try {

            Log.d("TagType--------------------------------------", "heelllo");
            holder.txtu_name.setText(userInfo.getFullName());
            holder.txtu_email.setText(userInfo.getUserEmail());
        } catch (Exception e) {
            Log.d("TagType111--------------------------------------", "" + e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return userInfoArrayList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        //here declare desgin
        TextView txtu_name, txtu_email;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtu_name = itemView.findViewById(R.id.txtu_name);
            txtu_email = itemView.findViewById(R.id.txtu_email);

        }
    }
}

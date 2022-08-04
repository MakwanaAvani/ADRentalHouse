package com.ad.adrentalhouse;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdminPropertyAdapter extends RecyclerView.Adapter<AdminPropertyAdapter.ViewHolder> {

    ArrayList<Property> propertyArrayList;


    public AdminPropertyAdapter(ArrayList<Property> propertyArrayList) {
        this.propertyArrayList = propertyArrayList;

    }
    //constractor now creating

    @NonNull
    @Override
    public AdminPropertyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_admin_propery_info, parent, false);
        return new AdminPropertyAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AdminPropertyAdapter.ViewHolder holder, int position) {
        //here we bind it
        Property property = propertyArrayList.get(position);
        try {

            Log.d("TagType--------------------------------------", "heelllo" + property.getProperty_Type());
            holder.txtad_type.setText("Type:" + property.getProperty_Type());
            holder.txtad_price.setText("Price:" + property.getRent_price());
            holder.txtad_ownername.setText("Name: " + property.getOwner_name());
            holder.txtad_email.setText("Email: " + property.getEmail());
            String imageUri = null;
            imageUri = property.getImage();
            Picasso.get().load(imageUri).into(holder.imgad_view);

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

        return propertyArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //here declare desgin
        ImageView imgad_view;
        TextView txtad_type, txtad_price, txtad_ownername, txtad_email;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgad_view = itemView.findViewById(R.id.imgad_view);
            txtad_type = itemView.findViewById(R.id.txtad_type);
            txtad_price = itemView.findViewById(R.id.txtad_price);
            txtad_ownername = itemView.findViewById(R.id.txtad_ownername);
            txtad_email = itemView.findViewById(R.id.txtad_email);

        }

    }

}

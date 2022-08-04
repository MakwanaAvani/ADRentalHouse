package com.ad.adrentalhouse;

import android.content.Intent;
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

public class PropertAdapter extends RecyclerView.Adapter<PropertAdapter.ViewHolder> {

    ArrayList<Property> propertyArrayList;


    public PropertAdapter(ArrayList<Property> propertyArrayList) {
        this.propertyArrayList = propertyArrayList;

    }
    //constractor now creating

    @NonNull
    @Override
    public PropertAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_house_and_apartment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PropertAdapter.ViewHolder holder, int position) {
        //here we bind it
        Property property = propertyArrayList.get(position);
        try {

            Log.d("TagType--------------------------------------", "heelllo" + property.getProperty_Type());
            holder.txt_type.setText("Type:" + property.getProperty_Type());
            holder.txt_room.setText("Room:" + property.getRoom());
            holder.txt_price.setText("Rent Price: " + property.getRent_price());
            String imageUri = null;
            imageUri = property.getImage();
            Picasso.get().load(imageUri).into(holder.imageView);

            holder.txt_type.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(holder.txt_type.getContext(), DisplayProperty.class);
                    i.putExtra("owner_name", property.getOwner_name());
                    i.putExtra("email", property.getEmail());
                    i.putExtra("phone", property.getPhone());
                    i.putExtra("addr1", property.getAddress1());
                    i.putExtra("addr2", property.getAddress2());
                    i.putExtra("floor", property.getFloor());
                    i.putExtra("rentprice", property.getRent_price());
                    i.putExtra("rentdetail", property.getRent_Other_Detail());
                    i.putExtra("rentout", property.getRent_Out_For());
                    i.putExtra("room", property.getRoom());
                    i.putExtra("ageprope", property.getAge_of_Property());
                    i.putExtra("type", property.getProperty_Type());
                    i.putExtra("image", property.getImage());
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    holder.txt_type.getContext().startActivity(i);
                }
            });


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
        ImageView imageView;
        TextView txt_price, txt_room, txt_type;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.img_view);
            txt_room = itemView.findViewById(R.id.txt_room);
            txt_price = itemView.findViewById(R.id.txt_price);
            txt_type = itemView.findViewById(R.id.txt_type);

        }

    }

}

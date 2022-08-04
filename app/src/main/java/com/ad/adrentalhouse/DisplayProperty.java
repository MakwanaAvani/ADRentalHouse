package com.ad.adrentalhouse;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class DisplayProperty extends AppCompatActivity {

    ImageView imav_view;
    TextView txtv_prop_Type, txtv_ownername, txtv_floor, txtv_age_property, txtv_room, txtv_rent_out, txtv_addr1, txtv_addr2, txtv_price, txtv_email, txtv_phone, txtv_rent_other_detail;
    Button btnv_back;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_property);
        txtv_prop_Type = findViewById(R.id.txtv_prop_Type);
        txtv_ownername = findViewById(R.id.txtv_ownername);
        txtv_floor = findViewById(R.id.txtv_floor);
        txtv_room = findViewById(R.id.txtv_room);
        txtv_rent_out = findViewById(R.id.txtv_rent_out);
        txtv_addr1 = findViewById(R.id.txtv_addr1);
        txtv_addr2 = findViewById(R.id.txtv_addr2);
        txtv_price = findViewById(R.id.txtv_price);
        txtv_email = findViewById(R.id.txtv_email);
        txtv_phone = findViewById(R.id.txtv_phone);
        txtv_age_property = findViewById(R.id.txtv_age_property);
        txtv_rent_other_detail = findViewById(R.id.txtv_rent_other_detail);
        imav_view = findViewById(R.id.imav_view);
        btnv_back = findViewById(R.id.btnv_back);
        String rtype = getIntent().getStringExtra("type").toString();
        txtv_ownername.setText("OWNER NAME:" + "" + getIntent().getStringExtra("owner_name").toString());
        txtv_prop_Type.setText("TYPE:" + "" + getIntent().getStringExtra("type").toString());
        txtv_floor.setText("FLOOR:" + "" + getIntent().getStringExtra("floor").toString());
        txtv_room.setText("TOTAL ROOM:" + "" + getIntent().getStringExtra("room").toString());
        txtv_rent_out.setText("RENT OUT FOR:" + "" + getIntent().getStringExtra("rentout").toString());
        txtv_addr1.setText("ADDRESS:" + "" + getIntent().getStringExtra("addr1").toString());
        txtv_addr2.setText(getIntent().getStringExtra("addr2").toString());
        txtv_price.setText("RENT EXPECTION:" + "" + getIntent().getStringExtra("rentprice").toString());
        txtv_email.setText("OWNER EMAIL ID:" + "" + getIntent().getStringExtra("email").toString());
        txtv_phone.setText("OWNER PHONE NO.:" + "" + getIntent().getStringExtra("phone").toString());
        txtv_rent_other_detail.setText("OTHER DETAIL:" + "" + getIntent().getStringExtra("rentdetail").toString());
        txtv_age_property.setText("AGE OF PROPERTY:" + "" + getIntent().getStringExtra("ageprope").toString());
        String imageUri = getIntent().getStringExtra("image").toString();
        Picasso.get().load(imageUri).into(imav_view);
        btnv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rtype.equals("Independent House")) {
                    startActivity(new Intent(getApplicationContext(), RentingAHome.class));
                    finish();
                } else if (rtype.equals("Apartment")) {
                    startActivity(new Intent(getApplicationContext(), RentingApartment.class));
                    finish();
                }
            }
        });
    }
}
package com.example.government;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import static com.example.government.R.color.dem;
import static com.example.government.R.color.noparty;
import static com.example.government.R.color.repub;

public class PhotoActivity extends AppCompatActivity {


    private TextView name;
    private TextView location;
    private ImageView photo;
    private ImageView party;
    private TextView office;
    private ConstraintLayout lastLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);


        name = findViewById(R.id.namezzz);
        location = findViewById(R.id.cityzz);
        photo = findViewById(R.id.person);
        party = findViewById(R.id.Plogo);
        office = findViewById(R.id.role);
        lastLayout = findViewById(R.id.lastLayout);

        flutter();
        party();
        fillPic();


    }

    private boolean doNetCheck() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            Toast.makeText(this, "Cannot access ConnectivityManager", Toast.LENGTH_SHORT).show();
            return false;
        }

        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnected()) {


            return true;

        } else {

            return false;
        }
    }


    public void flutter() {

        Intent jk = getIntent();

        name.setText(jk.getStringExtra("name"));
        location.setText(jk.getStringExtra("location"));
        office.setText(jk.getStringExtra("role"));

    }

    public void party() {

        Intent intent = getIntent();

        String gh = intent.getStringExtra("party").toLowerCase();

        if (gh != null) {


            switch (gh) {


                case "republican party":

                    lastLayout.setBackgroundColor(getResources().getColor(repub));
                    party.setImageResource(R.drawable.rep_logo);

                    break;

                case "democratic party":
                    lastLayout.setBackgroundColor(getResources().getColor(dem));
                    party.setImageResource(R.drawable.dem_logo);

                    break;

                case "nonpartisan":
                    lastLayout.setBackgroundColor(getResources().getColor(noparty));


                    break;

                default:

            }


        } else {
            lastLayout.setBackgroundColor(getResources().getColor(noparty));
        }


    }

    public void fillPic() {

        Intent jk = getIntent();


        String bn = jk.getStringExtra("image");

        if (doNetCheck() == true) {

            if (bn == null || bn.isEmpty()) {


                photo.setImageResource(R.drawable.missing);


            } else {

                Picasso picasso = new Picasso.Builder(this).build();

                picasso.load(bn)
                        .error(R.drawable.missing)
                        .placeholder(R.drawable.placeholder)
                        .into(photo);


            }
        } else {
            photo.setImageResource(R.drawable.brokenimage);

        }
    }
}

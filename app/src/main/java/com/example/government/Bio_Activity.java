package com.example.government;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.government.R.color.dem;
import static com.example.government.R.color.noparty;
import static com.example.government.R.color.repub;

public class Bio_Activity extends AppCompatActivity {

    private static final String TAG = "API_AsyncTask";

    private ImageView imageView;
    private TextView name;
    private TextView office;
    private TextView party;
    private ImageView partyLogo;
    private TextView address;
    private TextView phone;
    private TextView website;
    private ScrollView scroll;
    private TextView city;
    private TextView email;
    private TextView emailTitle;
    private TextView websitezz;
    private TextView addresszz;
    private TextView phonez;
    private ImageView facebook;
    private ImageView twitter;
    private ImageView youtube;
    private ImageView google;

    String face ;
    String tweat ;
    String tube ;
    String goog ;

    private HashMap<String, String> social;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio);

        imageView = findViewById(R.id.imageView);
        partyLogo = findViewById(R.id.partylogo);
        name = findViewById(R.id.namezzz);
        office = findViewById(R.id.office);
        party = findViewById(R.id.party);
        address = findViewById(R.id.address);
        phone = findViewById(R.id.phone);
        website = findViewById(R.id.website);
        scroll = findViewById(R.id.scrollview);
        city = findViewById(R.id.cityzz);
        email = findViewById(R.id.emailzzz);
        emailTitle = findViewById(R.id.email);
        websitezz = findViewById(R.id.websitezz);
        addresszz = findViewById(R.id.addresszz);
        phonez = findViewById(R.id.phonez);
        facebook = findViewById(R.id.facebook);
        twitter = findViewById(R.id.twitter);
        youtube = findViewById(R.id.youtube);
        google = findViewById(R.id.google);


        gator();

        fillin();
        fillPic();
        party();
        socialMedia();


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


    public void fillin() {


        Intent jk = getIntent();

        name.setText(jk.getStringExtra("name"));
        city.setText(jk.getStringExtra("location"));

        office.setText(jk.getStringExtra("office"));
        party.setText(String.format("(%s)", jk.getStringExtra("party")));
        //office.setText(jk.getStringExtra("office"));
        // address.setText(jk.getStringExtra("address"));
        // phone.setText(jk.getStringExtra("phone"));
        //website.setText(jk.getStringExtra("website"));

//        Linkify.addLinks(address, Linkify.ALL);
//        Linkify.addLinks(website, Linkify.ALL);
//        Linkify.addLinks(phone, Linkify.ALL);

    }

    public void fillPic() {



        Intent jk = getIntent();


        String bn = jk.getStringExtra("photo");
        //Picasso picasso = new Picasso.Builder(this).build();

        if(doNetCheck()==true) {

            if (bn == null || bn.isEmpty()) {


                // String text = jk.getStringExtra("photo");

                //Picasso picasso = new Picasso.Builder(this).build();

                imageView.setImageResource(R.drawable.missing);


//            picasso.load(R.drawable.missing)
//                    //.error(R.drawable.missing)
//                    .placeholder(R.drawable.placeholder)
//                    .into(imageView);


            } else {

                Picasso picasso = new Picasso.Builder(this).build();

                picasso.load(bn)
                        .error(R.drawable.missing)
                        .placeholder(R.drawable.placeholder)
                        .into(imageView);


            }


        }else{

            imageView.setImageResource(R.drawable.brokenimage);

        }
    }

    public void party() {

        Intent intent = getIntent();

        String gh = intent.getStringExtra("party").toLowerCase();

        if (gh != null) {


            switch (gh) {


                case "republican party":

                    scroll.setBackgroundColor(getResources().getColor(repub));
                    partyLogo.setImageResource(R.drawable.rep_logo);

                    break;

                case "democratic party":
                    scroll.setBackgroundColor(getResources().getColor(dem));
                    partyLogo.setImageResource(R.drawable.dem_logo);

                    break;

                case "nonpartisan":
                    scroll.setBackgroundColor(getResources().getColor(noparty));


                    break;

                default:

            }


        } else {
            scroll.setBackgroundColor(getResources().getColor(noparty));
        }


    }


    public void gator() {

        Intent intent = getIntent();

        String E = intent.getStringExtra("email");
        String W = intent.getStringExtra("website");
        String A = intent.getStringExtra("address");
        String P = intent.getStringExtra("phone");

        //HashMap<String,String> social = intent.get

        // String Face = intent.getStringExtra("social").

        if (E == null) {

            email.setVisibility(TextView.GONE);
            emailTitle.setVisibility(TextView.GONE);


        } else {

            email.setText(E);
            Linkify.addLinks(email, Linkify.EMAIL_ADDRESSES);
        }

        if (W == null) {
            website.setVisibility(TextView.GONE);
            websitezz.setVisibility(TextView.GONE);

        } else {

            website.setText(W);
            Linkify.addLinks(website, Linkify.WEB_URLS);
        }

        if (A == null) {

            address.setVisibility(TextView.GONE);
            addresszz.setVisibility(TextView.GONE);


        } else {

            address.setText(A);
            Linkify.addLinks(address, Linkify.MAP_ADDRESSES);
        }

        if (P == null) {

            phone.setVisibility(TextView.GONE);
            phonez.setVisibility(TextView.GONE);


        } else {

            phone.setText(P);
            Linkify.addLinks(phone, Linkify.PHONE_NUMBERS);
        }

    }

    public void socialMedia() {

        Intent jk = getIntent();


        social = (HashMap<String, String>) jk.getSerializableExtra("social");


         face = jk.getStringExtra("facebook");
         tweat = jk.getStringExtra("twitter");
         tube = jk.getStringExtra("youtube");
         goog = jk.getStringExtra("googleplus");
        Log.d(TAG, "facebook: " + face);
        Log.d(TAG, "you: " + tube);
        Log.d(TAG, "tw: " + tweat);
        Log.d(TAG, "goo: " + goog);

        if (face == null) {

            facebook.setVisibility(ImageView.GONE);

        } else {

            facebook.setVisibility(ImageView.VISIBLE);
        }

        if (goog == null) {

            google.setVisibility(ImageView.GONE);

        } else {

            google.setVisibility(ImageView.VISIBLE);
        }

        if (tweat == null) {

            twitter.setVisibility(ImageView.GONE);

        } else {

            twitter.setVisibility(ImageView.VISIBLE);
        }

        if (tube == null) {

            youtube.setVisibility(ImageView.GONE);

        } else {

            youtube.setVisibility(ImageView.VISIBLE);
        }

    }

    public void facebookClicked(View v) {
        String FACEBOOK_URL = "https://www.facebook.com/" +
        face;

        String urlToUse;

        PackageManager packageManager = getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app

            urlToUse = "fb://facewebmodal/f?href=" + FACEBOOK_URL;
        } else{ //older versions of fb app
            urlToUse = "fb://page/" + face;
        }
    } catch(PackageManager.NameNotFoundException e) {
        urlToUse = FACEBOOK_URL; //normal web url
    }

        Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
        facebookIntent.setData(Uri.parse(urlToUse));

        startActivity(facebookIntent);

}

    public void twitterClicked(View v) {
        Intent intent = null;
        String name = tweat; try {
            // get the Twitter app if possible
            getPackageManager().getPackageInfo("com.twitter.android", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + name));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
// no Twitter app, revert to browser
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/" + name));
        }
        startActivity(intent);
    }

    public void googlePlusClicked(View v) {
        String name = goog;
        Intent intent = null;
        try {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setClassName("com.google.android.apps.plus",
                    "com.google.android.apps.plus.phone.UrlGatewayActivity");
            intent.putExtra("customAppUri", name);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://plus.google.com/" + name)));
        }
    }

    public void youTubeClicked(View v) {
        String name = tube;
        Intent intent = null;
        try {
            intent = new Intent(Intent.ACTION_VIEW); intent.setPackage("com.google.android.youtube");
            intent.setData(Uri.parse("https://www.youtube.com/" + name)); startActivity(intent);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.youtube.com/" + name)));
        }
    }

    public void enLargePhoto(View v){

        Intent jk = getIntent();

        Intent intent = new Intent(this, PhotoActivity.class);

        intent.putExtra("image", jk.getStringExtra("photo"));
        intent.putExtra("party", jk.getStringExtra("party"));
        intent.putExtra("name", jk.getStringExtra("name"));
        intent.putExtra("location", jk.getStringExtra("location"));
        intent.putExtra("role",jk.getStringExtra("office"));


        startActivity(intent);



    }



}

//Brian Obmalay CSC 472 Fall 2019

package com.example.government;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private RecyclerView recyclerView;
    private GovAdapter mAdapter;
    private final ArrayList<Offical> GovList = new ArrayList<>();
    private TextView location;

    private static int MY_LOCATION_REQUEST_CODE_ID = 329;
    private LocationManager locationManager;
    private Criteria criteria;

    private double lat;
    private double lon;
    String postal;
    String st;
    String place;

    private static final String TAG = "API_AsyncTask";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        location = findViewById(R.id.Location);

        setTitle("Know Your Government");




        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        criteria = new Criteria();

        criteria.setPowerRequirement(Criteria.POWER_MEDIUM);
        //criteria.setPowerRequirement(Criteria.POWER_HIGH);

        criteria.setAccuracy(Criteria.ACCURACY_MEDIUM);
        //criteria.setAccuracy(Criteria.ACCURACY_FINE);

        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);



        go();

        recyclerView = findViewById(R.id.recycler);

        mAdapter= new GovAdapter(GovList, this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));


        //new AsyncOfficals(this).execute(postal);

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


    public void go(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    MY_LOCATION_REQUEST_CODE_ID);
        } else {
            setLocation();
        }


    }

    @Override
    public void onClick(View v) {

        int pos = recyclerView.getChildLayoutPosition(v);

        Offical yayo = GovList.get(pos);

        Log.d(TAG, "person: " + yayo);
        Intent intent2 = new Intent(this, Bio_Activity.class);

        intent2.putExtra("photo", yayo.getPhotoUrl());
        intent2.putExtra("party", yayo.getParty());
        intent2.putExtra("website", yayo.getUrl());
        intent2.putExtra("phone", yayo.getPhone());
        intent2.putExtra("address", yayo.getAddress());
        intent2.putExtra("office", yayo.getRole());
        intent2.putExtra("location", place);
        intent2.putExtra("name", yayo.getName());
        intent2.putExtra("email",yayo.getEmailz());
        intent2.putExtra("social",yayo.getSocial());
        intent2.putExtra("facebook", yayo.getFB());
        intent2.putExtra("twitter",yayo.getTW());
        intent2.putExtra("youtube",yayo.getYU());
        intent2.putExtra("googleplus",yayo.getGoo());

        startActivity(intent2);

        Log.d(TAG, "position: " + yayo.getParty());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.About:
                //Toast.makeText(this, "You want info", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(this, AboutActivitiy.class);
                startActivity(intent2);
                return true;
            case R.id.app_bar_search:
                AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.AlertDialog);


                final EditText et = new EditText(this);
                et.setInputType(InputType.TYPE_CLASS_TEXT);
                et.setGravity(Gravity.CENTER_HORIZONTAL);
                builder.setView(et);


                builder.setTitle("Enter a City, State or Zip Code");



                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                if(doNetCheck()) {


                                    postal = et.getText().toString();


                                    addNew();
                                }else{

                                    yoyo();
                                }



                            }



                        });


                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(MainActivity.this, "You changed your mind!", Toast.LENGTH_SHORT).show();


                        //tv1.setText(R.string.no_way);
                    }
                });


                AlertDialog dialog = builder.create();
                dialog.show();




                return true;

            default:
                return super.onOptionsItemSelected(item);
        }



    }

    public void yoyo(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("No NetWork Connection");
        builder.setMessage("Data cannont be accessed/loaded without an internet connection");
        AlertDialog hey = builder.create();
        hey.show();
        location.setText(R.string.error);

    }

    public void addNew(){

        GovList.clear();

        mAdapter.notifyDataSetChanged();

        new AsyncOfficals(this).execute(postal);


    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull
            String[] permissions, @NonNull
                    int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_LOCATION_REQUEST_CODE_ID) {
            if (permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) &&
                    grantResults[0] == PERMISSION_GRANTED) {
                setLocation();
                return;
            }
        }
        //((TextView) findViewById(R.id.locText)).setText(R.string.no_perm);

    }


    @SuppressLint("MissingPermission")
    private void setLocation() {

        if(doNetCheck()==true) {

            String bestProvider = locationManager.getBestProvider(criteria, true);
            // ((TextView) findViewById(R.id.bestText)).setText(bestProvider);

            Location currentLocation = locationManager.getLastKnownLocation(bestProvider);


            // locationManager.requestLocationUpdates(bestProvider,0,0,this);

            //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            if (currentLocation != null) {

                lat = currentLocation.getLatitude();
                lon = currentLocation.getLongitude();
                Log.d(TAG, "lat: " + lat + lon);

                //((TextView) findViewById(R.id.locText)).setText(
//                    String.format(Locale.getDefault(),
//                            "%.4f, %.4f", currentLocation.getLatitude(), currentLocation.getLongitude()));
            } else {
                //((TextView) findViewById(R.id.locText)).setText(R.string.no_locs);
            }

            geo(lat, lon);

        }else{

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("No NetWork Connection");
            builder.setMessage("Data cannont be accessed/loaded without an internet connection");
            AlertDialog dialog = builder.create();
            dialog.show();

            location.setText(R.string.error);
        }

    }

    public void geo(double lat, double lon){

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;
        try {

            addresses = geocoder.getFromLocation(lat, lon, 1);
            Log.d(TAG, "address: " + addresses);
            goZip(addresses);

        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    public void goZip(List<Address> crew){

        Log.d(TAG, "addList: " + crew);

        //String postal=null;

        for(Address k: crew){

           postal =  k.getPostalCode();

//            place = k.getLocality();
//
//            st = k.getAdminArea();
        }

        //location.setText(place + st + postal);
        new AsyncOfficals(this).execute(postal);

        //return postal;
    }

    public void createLocation(String city, String st, String zip){

        location.setText(String.format("%s, %s %s", city, st, zip));

        place = String.format("%s, %s %s", city, st, zip);

    }


    public void updateData(ArrayList<Offical> cList) {

        for(Offical yu : cList) {
            GovList.add(yu);

            mAdapter.notifyDataSetChanged();
        }



    }
}

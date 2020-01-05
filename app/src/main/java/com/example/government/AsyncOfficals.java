package com.example.government;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.webkit.JsResult;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class AsyncOfficals extends AsyncTask<String, Integer, String> {


    private static final String TAG = "API_AsyncTask";

    private MainActivity mainActivity;

    private String key = "use your key";

    private String web = "https://www.googleapis.com/civicinfo/v2/representatives";

    private String address;

    //private HashMap<String,String> social;

    AsyncOfficals(MainActivity mainActivity) {
        this.mainActivity = mainActivity;


        //this.address = "60120";
    }

    @Override
    protected void onPostExecute(String s) {
        //Log.d(TAG, "yo: " + s);

        ArrayList<Offical> grub = parseJSON(s);
        if (grub != null){
        Log.d(TAG, "onPostExecute: " + grub);

        mainActivity.updateData(grub);}

    }



    @Override
    protected String doInBackground(String... strings) {

        Uri.Builder buildURL = Uri.parse(web).buildUpon();


       // buildURL.appendPath(strings[0]);
        //buildURL.appendPath("quote");
        buildURL.appendQueryParameter("key", key);
        buildURL.appendQueryParameter("address", strings[0] );


        String urlToUse = buildURL.build().toString();
        Log.d(TAG, "doInBackground: " + urlToUse);

        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(urlToUse);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader((new InputStreamReader(is)));

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }



        } catch (Exception e) {

            return null;
        }
        return sb.toString();

        // parseJSON(sb.toString());


    }

    private ArrayList<Offical> parseJSON(String s) {


        String line1;
        String line2;
        String line3;
        String place;
        String state;
        String zi;
        String streetname = null;
        String phone = null;
        String thc= null; //URL
        String emailz=null;

        //HashMap<String,String> social =new HashMap<>() ;


        ArrayList<Offical> rawList = new ArrayList<>();
        try {
            //JSONArray jObjMain = new JSONArray(s);
            JSONObject jObjMain = new JSONObject(s);
            //for (int i = 0; i < jObjMain.length(); i++) {

                JSONObject city = jObjMain.getJSONObject("normalizedInput");
                JSONArray people = jObjMain.getJSONArray("officials");
                JSONArray offices = jObjMain.getJSONArray("offices");
//                //JSONObject jCountry = (JSONObject) jObjMain.get(i);
//                String name = jObjMain.getString("symbol");
//                String city = jObjMain.getString("normalizedInput");
//                String offices = jObjMain.getString("Offices");
//                String officials = jObjMain.getDouble("change");
//                Double ratio = jObjMain.getDouble("changePercent");
               // Log.d(TAG, "object: " + city);

//                rawList.add(
//                        new Offical(name, symbol, price, change, ratio));
                //for(int j =0; j < city.length(); j++){

                    String town = city.getString("city");//FOR MAIN VIEW LOCATION
                    String st = city.getString("state");//FOR MAIN VIEW LOCATION
                    String zip = city.getString("zip");//FOR MAIN VIEW LOCATION

                    mainActivity.createLocation(town, st, zip);

                    Log.d(TAG, "state: " + st);
               // }


                for (int k =0; k<offices.length();k++) {


                    JSONObject her = offices.getJSONObject(k);
                    String name = her.getString("name");     //position-
                    Log.d(TAG, "position: " + name);
                    JSONArray index = her.getJSONArray("officialIndices");

                    int[] oindex = new int[index.length()];
                    for (int gh = 0; gh < index.length(); gh++) {

                        oindex[gh] = index.getInt(gh);

                    }
                    //Log.d(TAG, "name: " + name);
                    for(int ui : oindex){

                    //for (int ui = 0; ui < oindex.length; ui++) {

                       // int t = oindex[ui];

                        HashMap<String,String> social =new HashMap<>() ;

                        JSONObject vc = people.getJSONObject(ui);

                        String sirname = vc.getString("name"); //NAME of person-
                        String party = vc.getString("party"); //PARTY-
                        String photourl = vc.optString("photoUrl");//-

                        JSONArray address = vc.optJSONArray("address");


                       // Log.d(TAG, "name: " + name);
                        //Log.d(TAG, "address: " + address);
                        if (address!=null) {

                            for (int n = 0; n < address.length(); n++) {

                                JSONObject loc = address.getJSONObject(n);

                                 line1 = loc.getString("line1");////concatenate LINES
                                 line2 = loc.getString("line2");
                                 line3 = loc.getString("line3");
                                 place = loc.getString("city");
                                 state = loc.getString("state");
                                 zi = loc.getString("zip");

                                 streetname = String.format("%s\n%s\n%s\n%s, %s %s",
                                         line1,line2,line3, place, state, zi );

                                 //streetname = line1 + line2 + line3 + place + state + zi;
                                // Log.d(TAG, "loc: " + loc);
                            }
                        }

                        JSONArray phones = vc.optJSONArray("phones");
                        if(phones!=null) {
                            for (int q = 0; q < phones.length(); q++) {

                                 phone = phones.getString(q);//PHONE NUMBERS
                            }
                        }

                        JSONArray web = vc.optJSONArray("urls");

                        if (web!=null) {

                            for (int w = 0; w < web.length(); w++) {

                                 thc = web.getString(w);//URL
                            }
                        }

                        JSONArray channels = vc.optJSONArray("channels");
                        Log.d(TAG, "channels: " + channels);

                        social.clear();///CLEARS HASHMAP BEFORE ITERATION


                        if (channels!=null) {


                            for (int ad = 0; ad < channels.length(); ad++) {



                                JSONObject loc = channels.getJSONObject(ad);

                                String type = loc.getString("type");
                                String id = loc.getString("id");///SOCIAL MEDIA


                                social.put(type,id);

                                Log.d(TAG, "soc: " + type + id);
                            }
                        }

                        Log.d(TAG, "hash: " + social);

                        JSONArray email = vc.optJSONArray("emails");
                        if (email!=null) {
                            for (int ad = 0; ad < email.length(); ad++) {


                                 emailz = email.getString(ad);


                            }
                        }

                        Log.d(TAG, "newTask: " + sirname + party + name);
                        rawList.add(new Offical(sirname,party, name,photourl,streetname,phone, thc,emailz,social));



                    }






                }

           // }



            Log.d(TAG, "list: " + rawList);
           // Log.d(TAG, "objects: " + jObjMain);
            return rawList;


        }catch (Exception e) {


            e.printStackTrace();
        }


        return null;
    }


}

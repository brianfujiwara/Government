package com.example.government;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.View;
import android.widget.TextView;

import java.util.regex.Pattern;

import androidx.appcompat.app.AppCompatActivity;

public class AboutActivitiy extends AppCompatActivity {

    private static final String googleURL = "https://developers.google.com/civic-information/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);



        TextView textView = findViewById(R.id.apiLink);



        //TextView link = (TextView) findViewById(R.id.link);
      // textView.setMovementMethod(LinkMovementMethod.getInstance());
        //textView.setText(R.string.your_string_here);


//        Pattern pattern = Pattern.compile("[a-zA-Z]+");
//        Linkify.addLinks(textView, pattern, "https://developers.google.com/civic-");
       //myTV.setPaintFlags(myTV.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
//
       textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
//        textView.setText(R.string.your_string_here);

    }

    public void goWeb(View view){


        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(googleURL));
        startActivity(i);




    }


}

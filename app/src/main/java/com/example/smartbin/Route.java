package com.example.smartbin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class Route extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        double latitude = 18.457240;
        double longitude = 73.850240;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("google.navigation:q=" +latitude + "," +longitude));
        startActivity(intent);
    }
}

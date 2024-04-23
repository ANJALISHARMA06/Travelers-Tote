package com.example.travelerstote;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AboutUs extends AppCompatActivity {

    ImageView imgYoutube, imgInstagram;
    TextView txtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about_us);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("About us");

        imgYoutube= findViewById(R.id.imgYoutube);
        txtEmail = findViewById(R.id.txtEmail);
        imgInstagram= findViewById(R.id.imgInstagram);

        imgYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToUrl("https://youtube.com/@Anjali.06?si=O6jo1jtIGRPbzzAA");
            }
        });

        txtEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "anjalikrs06@gmail.com"));
                    intent.putExtra(Intent.EXTRA_SUBJECT, "From Travelers Tote");
                    startActivity(intent);

                } catch (ActivityNotFoundException e) {
                    System.out.println(e);
                }
            }
        });

        imgInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToUrl("https://youtube.com/@Anjali.06?si=O6jo1jtIGRPbzzAA");
            }
        });

    }
    private void navigateToUrl(String url){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
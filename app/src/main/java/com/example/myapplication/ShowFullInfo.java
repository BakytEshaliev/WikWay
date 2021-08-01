package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ShowFullInfo extends AppCompatActivity {

    boolean isFromMain;
    String url;
    Bitmap image;
    boolean isFav;
    OfferForList offer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_company);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String mainInfo1Str = intent.getStringExtra("mainInfo1");
        String mainInfo2Str = intent.getStringExtra("mainInfo2");
        String mainInfo3Str = intent.getStringExtra("mainInfo3");
        String mainInfo4Str = intent.getStringExtra("mainInfo4");
        String mainInfo5Str = intent.getStringExtra("mainInfo5");
        String imageUrl = intent.getStringExtra("imageUrl");
        String compName = intent.getStringExtra("compName");
        String nameStr = intent.getStringExtra("name");
        isFav = intent.getBooleanExtra("fav",false);

        url = imageUrl;
        new GetLogo().execute();

        isFromMain = intent.getBooleanExtra("fromMain",false);
        final int index = intent.getIntExtra("index",-1);

        offer = new OfferForList();
        offer.setMainInfo1(mainInfo1Str);
        offer.setMainInfo2(mainInfo2Str);
        offer.setMainInfo3(mainInfo3Str);
        offer.setMainInfo4(mainInfo4Str);
        offer.setMainInfo5(mainInfo5Str);
        offer.setLogoUrl(imageUrl);
        offer.setCompName(compName);
        offer.setName(nameStr);
        offer.setFav(isFav);

        final FloatingActionButton starButton = findViewById(R.id.star_button);
        if(offer.isFav()){
            starButton.setImageResource(R.drawable.ic_star_black_24dp);
        }
        else
            starButton.setImageResource(R.drawable.ic_star_border_black_24dp);

        starButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!offer.isFav()) {
                    DbHelper.add(ShowFullInfo.this, offer);
                    starButton.setImageResource(R.drawable.ic_star_black_24dp);
                        if(isFromMain)
                            MainActivity.getInstance().setFav(index,true);
                        else
                            MainActivity.getInstance().addToFavList(index,offer);
                        offer.setFav(true);
                }
                else {
                    DbHelper.delete(ShowFullInfo.this,offer);
                    starButton.setImageResource(R.drawable.ic_star_border_black_24dp);
                    if(isFromMain)
                        MainActivity.getInstance().setFav(index,false);
                    else
                        MainActivity.getInstance().deleteFavMyList(index);
                    offer.setFav(false);
                }
            }
        });

        url = imageUrl;
        new GetLogo().execute();

        TextView comp = findViewById(R.id.comp_name);
        comp.setText(compName);

        TextView name = findViewById(R.id.name);
        name.setText(nameStr);

        TextView mainInfo1 = findViewById(R.id.mainInfo1);
        mainInfo1.setText(mainInfo1Str);

        TextView mainInfo2 = findViewById(R.id.mainInfo2);
        mainInfo2.setText(mainInfo2Str);

        TextView mainInfo3 = findViewById(R.id.mainInfo3);
        mainInfo3.setText(mainInfo3Str);

        TextView mainInfo4 = findViewById(R.id.mainInfo4);
        mainInfo4.setText(mainInfo4Str);

        TextView mainInfo5 = findViewById(R.id.mainInfo5);
        mainInfo5.setText(mainInfo5Str);
    }

    private class GetLogo extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... arg0) {
            Log.e(" ","Loand image");
            URL imageUrl = null;
            try {
                imageUrl = new URL(url);
                image = BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream());

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
             catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.e(" ","put image");
            ImageView logo = findViewById(R.id.comp_details);
            offer.setLogo(image);
            logo.setImageBitmap(image);
        }
    }
}

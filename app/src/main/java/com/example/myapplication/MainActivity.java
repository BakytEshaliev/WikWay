package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

//import androidx.annotation.NonNull;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity {

    ArrayList<OfferForList> offersList;
    ArrayList<OfferForList> offersListFav;
    private static MainActivity instance;
    int index;
    ListAdapter adapter;
    boolean canDownload;
    Filter filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        instance=this;
        filter = new Filter();

        FloatingActionButton filterBtn = findViewById(R.id.filter_button);
        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent filterIntent = new Intent(MainActivity.this,FilterActivity.class);
                MainActivity.this.startActivity(filterIntent);
            }
        });

        Intent intent = getIntent();
        if(intent.getBooleanExtra("hasFilter",false)){
            String[] art = intent.getStringArrayExtra("art");
            String[] bundesland = intent.getStringArrayExtra("bundesland");
            String[] berusfeld = intent.getStringArrayExtra("berusfeld");
            filter.setArt(art);
            filter.setBundesland(bundesland);
            filter.setBerusfeld(berusfeld);
        }

        ListView lv = findViewById(R.id.list);
        offersList = new ArrayList<>();
        adapter = new CustomAdapter(MainActivity.this, offersList);
        lv.setAdapter(adapter);
        new GetOffers().execute();
        lv.setOnScrollListener(new OnScroll());
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(MainActivity.this, ShowFullInfo.class);
                OfferForList off = offersList.get(position);
                myIntent.putExtra("name",off.getName());
                myIntent.putExtra("mainInfo1",off.getMainInfo1());
                myIntent.putExtra("mainInfo2",off.getMainInfo2());
                myIntent.putExtra("mainInfo3",off.getMainInfo3());
                myIntent.putExtra("mainInfo4",off.getMainInfo4());
                myIntent.putExtra("mainInfo5",off.getMainInfo5());
                myIntent.putExtra("imageUrl",off.getLogoUrl());
                myIntent.putExtra("compName",off.getCompName());
                myIntent.putExtra("fav",off.isFav());
                myIntent.putExtra("fromMain",true);
                myIntent.putExtra("index",position);
                MainActivity.this.startActivity(myIntent);
            }
        });
        BottomNavigationView bnv = findViewById(R.id.navigation);

        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                        case R.id.nav_home:
                            index = 0;
                            offersList = new ArrayList<>();
                            adapter = new CustomAdapter(MainActivity.this, offersList);
                            ListView lv = findViewById(R.id.list);
                            lv.setAdapter(adapter);
                            if(canDownload)
                                new GetOffers().execute();
                            lv.setOnScrollListener(new OnScroll());
                            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent myIntent = new Intent(MainActivity.this, ShowFullInfo.class);
                                    OfferForList off = offersList.get(position);
                                    myIntent.putExtra("name",off.getName());
                                    myIntent.putExtra("mainInfo1",off.getMainInfo1());
                                    myIntent.putExtra("mainInfo2",off.getMainInfo2());
                                    myIntent.putExtra("mainInfo3",off.getMainInfo3());
                                    myIntent.putExtra("mainInfo4",off.getMainInfo4());
                                    myIntent.putExtra("mainInfo5",off.getMainInfo5());
                                    myIntent.putExtra("imageUrl",off.getLogoUrl());
                                    myIntent.putExtra("compName",off.getCompName());
                                    myIntent.putExtra("fav",off.isFav());
                                    myIntent.putExtra("fromMain",true);
                                    myIntent.putExtra("index",position);
                                    MainActivity.this.startActivity(myIntent);
                                }
                            });
                            return true;
                        case R.id.nav_list:
                                offersListFav = DbHelper.select(MainActivity.this);
                                new GetImage().execute();
                                adapter = new CustomAdapter(MainActivity.this,offersListFav);
                                ListView lvFav = findViewById(R.id.list);
                                lvFav.setAdapter(adapter);
                                lvFav.setOnScrollListener(new OnScrollFav());
                                lvFav.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent myIntent = new Intent(MainActivity.this, ShowFullInfo.class);
                                    OfferForList off = offersListFav.get(position);
                                    myIntent.putExtra("name",off.getName());
                                    myIntent.putExtra("mainInfo1",off.getMainInfo1());
                                    myIntent.putExtra("mainInfo2",off.getMainInfo2());
                                    myIntent.putExtra("mainInfo3",off.getMainInfo3());
                                    myIntent.putExtra("mainInfo4",off.getMainInfo4());
                                    myIntent.putExtra("mainInfo5",off.getMainInfo5());
                                    myIntent.putExtra("imageUrl",off.getLogoUrl());
                                    myIntent.putExtra("compName",off.getCompName());
                                    myIntent.putExtra("fav",off.isFav());
                                    myIntent.putExtra("index",position);
                                    myIntent.putExtra("fromMain",false);
                                    MainActivity.this.startActivity(myIntent);
                                }
                            });
                                return true;
                        }
                        return true;
                    }
                });
    }

    public void setFav(int i,boolean condition) {
        offersList.get(i).setFav(condition);
        ((BaseAdapter)adapter).notifyDataSetChanged();
    }

    public void deleteFavMyList(int i) {
        offersListFav.remove(i);
        Log.e("i:",""+i);

        ((BaseAdapter)adapter).notifyDataSetChanged();
    }

    public void addToFavList(int i,OfferForList offer) {
        offersListFav.add(i,offer);
        ((BaseAdapter)adapter).notifyDataSetChanged();
    }


    private class OnScroll implements AbsListView.OnScrollListener {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (view.getLastVisiblePosition() >= view.getCount() - 1&&scrollState == SCROLL_STATE_IDLE&&canDownload) {
                new GetOffers().execute();
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        }
    }

    private class OnScrollFav implements AbsListView.OnScrollListener{

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        }
    }

    private class GetOffers extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this,"Download", LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            canDownload=false;
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = filter.createUrl();
            Log.e("url:",url);
            String jsonStr = sh.makeServiceCall(url);

            Log.e(" ", "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONArray offers = new JSONArray(jsonStr);

                    for(int i = index; i < index+10;i++) {
                        if (i>=offers.length()){
//
                            break;
                        }
                        else {
                            JSONObject offer = offers.getJSONObject(i);

                            String typeOfOffer = offer.getString("Art der Stelle");
                            String bundesland = offer.getString("Bundesland");
                            String city = offer.getString("Einsatzort");
                            String typeOfJob = offer.getString("Art der Anstellung");
                            String typeOfJob1 = offer.getString("Umfang");
                            String startTime = offer.getString("Einstiegstermin");
                            String nameStr = offer.getString("Bezeichnung der Stelle");
                            String imageUrlStr = offer.getString("Logo");
                            String partner = offer.getString("Ansprechpartner");
                            String adres = offer.getString("Straße");
                            String plz = offer.getString("PLZ");
                            String ort = offer.getString("Ort");
                            String email = offer.getString("E-Mail");

                            String mainInfo1="";
                            String mainInfo2="";
                            String mainInfo3="";
                            String mainInfo4="";
                            String mainInfo5="";

                            String befristung = offer.getString("Befristung");

                            String berusfeldStr="";
                            JSONArray berusfeld = offer.getJSONArray("Berufsfeld");
                            for (int j=0;j<berusfeld.length();j++) {
                                if (j!=0)
                                    berusfeldStr+=",";
                                berusfeldStr += berusfeld.getString(j);
                            }

                            String info = offer.getString("Anschreiben zur Stelle");
                            String obaz = offer.getString("Aufgabengebiet");
                            obaz = obaz.replaceAll("<li>", "\n");
                            obaz = obaz.replaceAll("\\<.*?\\>", "");

                            String compName = offer.getString("Bewerberkontakt Firma");

                            mainInfo3 = offer.getString("Fähigkeiten und Qualifikationen");
                            mainInfo3 = mainInfo3.replaceAll("<li>", "\n");
                            mainInfo3 = mainInfo3.replaceAll("\\<.*?\\>", "");

                            mainInfo4 = offer.getString("Einladung zur Kontaktaufnahme");
                            mainInfo4 = mainInfo4.replaceAll("<li>", "\n");
                            mainInfo4 = mainInfo4.replaceAll("\\<.*?\\>", "");

                            String infoStr = typeOfOffer + "\n" + "Einsatzolt:" + bundesland + "," + city + "\n";
                            if (!typeOfJob.equals("null"))
                                infoStr += typeOfJob;
                            if (!typeOfJob1.equals("null"))
                                infoStr += "," + typeOfJob1;
                            if (!startTime.equals(""))
                                infoStr += "\n" + "Einstiegstermin: ab " + startTime;

                            mainInfo1=infoStr;
                            if (!befristung.equals(""))
                                mainInfo1+="\n" + "Bewerbungsfrist:";
                            mainInfo1+="\n"+berusfeldStr;

                            if (!info.equals("null"))
                                mainInfo2+=info;
                            mainInfo2+="\n"+obaz;
                            mainInfo2 = mainInfo2.replaceAll("<li>", "\n");
                            mainInfo2 = mainInfo2.replaceAll("\\<.*?\\>", "");

                            mainInfo5+=partner+"\n"+adres+"\n"+plz+"\n"+ort+"\n"+email;


                            URL imageUrl = new URL(imageUrlStr);
                            Bitmap bmp = BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream());

                            Log.e(" ", "Anschreiben zur Stelle:" + infoStr);
                            Log.e(" ", "1:" + mainInfo1);
                            Log.e(" ", "2:" + mainInfo2);
                            Log.e(" ", "3:" + mainInfo3);
                            Log.e(" ", "4:" + mainInfo4);
                            Log.e(" ", "5:" + mainInfo5);

                            OfferForList of = new OfferForList();

                            of.setMainInfo1(mainInfo1);
                            of.setMainInfo2(mainInfo2);
                            of.setMainInfo3(mainInfo3);
                            of.setMainInfo4(mainInfo4);
                            of.setMainInfo5(mainInfo5);
                            of.setLogoUrl(imageUrlStr);
                            of.setDiscription(infoStr);
                            of.setName(nameStr);
                            of.setLogo(bmp);
                            of.setCompName(compName);

                            if (DbHelper.isFav(MainActivity.this,of))
                                of.setFav(true);
                            // Log.e(" ", "OF:" + of.get("info"));

                            offersList.add(of);
                        }
                    }
                } catch (final JSONException | MalformedURLException e) {
                    Log.e(" ", "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    LENGTH_LONG).show();
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e(" ", "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            ((BaseAdapter)adapter).notifyDataSetChanged();
            index+=10;
            canDownload=true;
        }
    }

    private class GetImage extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            for(int i=0;i<offersListFav.size();i++){
                String imageUrlStr = offersListFav.get(i).getLogoUrl();
                URL imageUrl = null;
                try {
                    imageUrl = new URL(imageUrlStr);
                    Bitmap bmp = BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream());
                    offersListFav.get(i).setLogo(bmp);
                    Log.e(" ","Set Logo");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            ((BaseAdapter)adapter).notifyDataSetChanged();
        }
    }

    public static MainActivity getInstance() {
        return instance;
    }
}

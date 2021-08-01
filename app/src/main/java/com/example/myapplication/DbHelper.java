package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class DbHelper {
    public static void add(Context context,OfferForList offer){
        SQLiteDatabase db = context.openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS offers (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,name TEXT, mainInfo1 TEXT, mainInfo2 TEXT,mainInfo3 TEXT,mainInfo4 TEXT,mainInfo5 TEXT,logoUrl TEXT,compName TEXT)");
        db.execSQL("INSERT INTO offers(name,mainInfo1,mainInfo2,mainInfo3,mainInfo4,mainInfo5,logoUrl,compName) VALUES (\""+offer.getName()+"\",\""+offer.getMainInfo1()+"\",\""+offer.getMainInfo2()+"\",\""+offer.getMainInfo3()+"\",\""+offer.getMainInfo4()+"\",\""+offer.getMainInfo5()+"\",\""+offer.getLogoUrl()+"\",\""+offer.getCompName()+"\");");
        db.close();
        Log.e(" ","Added to db");
    }

    public static void delete(Context context, OfferForList offer) {
        String name = offer.getName();
        String compName = offer.getCompName();
        SQLiteDatabase db = context.openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        db.execSQL("delete from offers where name=\""+name+"\" and compName=\""+compName+"\"");
        db.close();
        Log.e(" ","deteled from db");
    }

    public static ArrayList<OfferForList> select(Context context){
        SQLiteDatabase db = context.openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS offers (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,name TEXT, mainInfo1 TEXT, mainInfo2 TEXT,mainInfo3 TEXT,mainInfo4 TEXT,mainInfo5 TEXT,logoUrl TEXT,compName TEXT)");
        ArrayList<OfferForList> offers = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT * FROM offers", null);

        if (c.moveToFirst()) {
            while ( !c.isAfterLast() ) {
                OfferForList offer = new OfferForList();
                int id = c.getInt(0);
                String name = c.getString(1);
                String mainIfno1 = c.getString(2);
                String mainIfno2 = c.getString(3);
                String mainIfno3 = c.getString(4);
                String mainIfno4 = c.getString(5);
                String mainIfno5 = c.getString(6);
                String logoUrl = c.getString(7);
                String compName = c.getString(8);

                offer.setId(id);
                offer.setName(name);
                offer.setMainInfo1(mainIfno1);
                offer.setMainInfo2(mainIfno2);
                offer.setMainInfo3(mainIfno3);
                offer.setMainInfo4(mainIfno4);
                offer.setMainInfo5(mainIfno5);
                offer.setLogoUrl(logoUrl);
                offer.setCompName(compName);
                offer.setFav(true);

                offers.add(offer);
                c.moveToNext();
            }
        }
        Log.e(" ","selected from db");
        db.close();
        c.close();
        return offers;
        //db.execSQL("delete from offers where id="+offer.getId()+"");
    }

    public static boolean isFav(Context c,OfferForList off){
        String name=off.getName();
        String compName=off.getCompName();
        SQLiteDatabase db = c.openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS offers (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,name TEXT, mainInfo1 TEXT, mainInfo2 TEXT,mainInfo3 TEXT,mainInfo4 TEXT,mainInfo5 TEXT,logoUrl TEXT,compName TEXT)");

        String sql = "SELECT mainInfo1 FROM offers WHERE name='"+name+"' AND compName='"+compName+"'";
        Cursor mCursor = db.rawQuery(sql, null);
        if (mCursor.getCount() > 0) {
            return true;
        }
        mCursor.close();
        return false;
    }
}//
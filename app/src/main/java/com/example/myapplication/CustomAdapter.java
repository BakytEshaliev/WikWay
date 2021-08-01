package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<OfferForList> {
    Context mContext;
    public CustomAdapter(Context context, List<OfferForList> books) {
        super(context, 0, books);
        mContext=context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if(row == null) {
            row = LayoutInflater.from(mContext).inflate(R.layout.home_view,null);
        }
        final OfferForList offer = getItem(position);
        final ImageView im = row.findViewById(R.id.comp_photo);
        im.setImageBitmap(offer.getLogo());
        final TextView name = row.findViewById(R.id.company);
        name.setText(offer.getName());
        final TextView discription = row.findViewById(R.id.berufsfeld);
        discription.setText(offer.getDiscription());

        final FloatingActionButton starButton = row.findViewById(R.id.star_button);
        if(offer.isFav()){
            starButton.setImageResource(R.drawable.ic_star_black_24dp);
        }
        else
            starButton.setImageResource(R.drawable.ic_star_border_black_24dp);

        starButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!offer.isFav()) {
                    offer.setFav(true);
                    DbHelper.add(mContext, offer);
                    starButton.setImageResource(R.drawable.ic_star_black_24dp);
                }
                else {
                    offer.setFav(false);
                    DbHelper.delete(mContext,offer);
                    starButton.setImageResource(R.drawable.ic_star_border_black_24dp);
                }
            }
        });
        return row;
    }
}

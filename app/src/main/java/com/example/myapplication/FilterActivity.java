package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FilterActivity extends AppCompatActivity {
    String[] art;
    String[] bundesland;
    String[] berusfeld;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter);
        FloatingActionButton bt = findViewById(R.id.floatingActionButton2);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CheckBox cb1 = (CheckBox) findViewById(R.id.stelle);
                final CheckBox cb2 = (CheckBox) findViewById(R.id.praktikum);
                final CheckBox cb3 = (CheckBox) findViewById(R.id.studienplatz);
                final CheckBox cb4 = (CheckBox) findViewById(R.id.abschlussarbeit);
                final CheckBox cb5 = (CheckBox) findViewById(R.id.traineestelle);
                final CheckBox cb6 = (CheckBox) findViewById(R.id.Sachsen);
                final CheckBox cb7 = (CheckBox) findViewById(R.id.Bayern);
                final CheckBox cb8 = (CheckBox) findViewById(R.id.Deutschland);
                final CheckBox cb9 = (CheckBox) findViewById(R.id.Thüringen);
                final CheckBox cb10 = (CheckBox) findViewById(R.id.Baden);
                final CheckBox cb11 = (CheckBox) findViewById(R.id.SachsenA);
                final CheckBox cb12 = (CheckBox) findViewById(R.id.hessen);
                final CheckBox cb13 = (CheckBox) findViewById(R.id.ingenieurswissenschaften);
                final CheckBox cb14 = (CheckBox) findViewById(R.id.it);
                final CheckBox cb15 = (CheckBox) findViewById(R.id.elektronik);
                final CheckBox cb16 = (CheckBox) findViewById(R.id.informatik);
                final CheckBox cb17 = (CheckBox) findViewById(R.id.metal);
                final CheckBox cb18 = (CheckBox) findViewById(R.id.wirtschaftswissenschaften);
                final CheckBox cb19 = (CheckBox) findViewById(R.id.beratung);

                art = new String[5];
                bundesland = new String[7];
                berusfeld = new String[7];

                if(cb1.isChecked())
                    art[0]="Stelle";
                if(cb2.isChecked())
                    art[1]="Praktikum/+Referendariat";
                if(cb3.isChecked())
                    art[2]="Studienplatz/+Werkstudent";
                if(cb4.isChecked())
                    art[3]="Abschlussarbeit";
                if(cb5.isChecked())
                    art[4]="Traineestelle";
                if(cb6.isChecked())
                    bundesland[0]="Sachsen";
                if(cb7.isChecked())
                    bundesland[1]="Bayern";
                if(cb8.isChecked())
                    bundesland[2]="Deutschland";
                if(cb9.isChecked())
                    bundesland[3]="Thüringen";
                if(cb10.isChecked())
                    bundesland[4]="Baden-Württemberg";
                if(cb11.isChecked())
                    bundesland[5]="Sachsen-Anhalt";
                if(cb12.isChecked())
                    bundesland[6]="Hessen";
                if(cb13.isChecked())
                    berusfeld[0]="ingenieurswissenschaften";
                if(cb14.isChecked())
                    berusfeld[1]="IT+/+Telekommunikation";
                if(cb15.isChecked())
                    berusfeld[2]="Elektronik,+Elektrotechnik";
                if(cb16.isChecked())
                    berusfeld[3]="Informatik";
                if(cb17.isChecked())
                    berusfeld[4]="Metall,+Maschinenbau";
                if(cb18.isChecked())
                    berusfeld[5]="Wirtschaftswissenschaften";
                if(cb19.isChecked())
                    berusfeld[6]="Beratung+/+Consulting";




                Intent intent = new Intent(FilterActivity.this,MainActivity.class);
                intent.putExtra("art",art);
                intent.putExtra("bundesland",bundesland);
                intent.putExtra("berusfeld",berusfeld);
                intent.putExtra("hasFilter",true);
                FilterActivity.this.startActivity(intent);
            }
        });
        FloatingActionButton btCancel = findViewById(R.id.floatingActionButton4);
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FilterActivity.this,MainActivity.class);
                intent.putExtra("hasFilter",false);
                FilterActivity.this.startActivity(intent);
            }
        });
    }
}

package com.context.kroket.escapeapp;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Game_C_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game__c_);
    }

    public void startC(View view) {
        View v = findViewById(R.id.layout_rec);
        LayerDrawable bgDrawable = (LayerDrawable)v.getBackground();
        final GradientDrawable shape = (GradientDrawable)   bgDrawable.findDrawableByLayerId(R.id.shape_rec);
        shape.setColor(Color.BLACK);
        shape.clearColorFilter();
        shape.setColor(Color.WHITE);

    }

}

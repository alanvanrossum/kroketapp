package com.context.kroket.escapeapp.minigames;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.context.kroket.escapeapp.R;

public class F_Lock extends AppCompatActivity {
    ImageButton left,right;
    ImageView turnlock;
    int rotation=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.f__lock);
        addListenersToArrows();
        turnlock = (ImageView) findViewById(R.id.turnlock);
        turnlock.setRotation(232);
        rotation=232;
    }
    private void addListenersToArrows() {
        left =(ImageButton) findViewById(R.id.arrowleft);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0){
                rotateLeft();
            }

        });
        right =(ImageButton) findViewById(R.id.arrowright);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0){
                rotateRight();
            }

        });
    }

    private void rotateRight() {
        rotation+=9;
        turnlock.setRotation(rotation);
    }


    private void rotateLeft() {
        rotation-=9;
        turnlock.setRotation(rotation);
    }

}

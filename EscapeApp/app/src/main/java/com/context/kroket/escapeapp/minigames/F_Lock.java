package com.context.kroket.escapeapp.minigames;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.context.kroket.escapeapp.R;

import java.util.ArrayList;

public class F_Lock extends AppCompatActivity {
    ImageButton left,right;
    ImageView turnlock;
    boolean rotatingRight;
    ArrayList<Integer> enteredSequence;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.f__lock);
        addListenersToArrows();
        turnlock = (ImageView) findViewById(R.id.turnlock);
        enteredSequence = new ArrayList<Integer>();
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
        if(!rotatingRight){
            rotatingRight=true;
            addToSequence();
            System.out.println(enteredSequence.toString());
        }
        rotate(9);
    }


    private void rotateLeft() {
        if(rotatingRight){
            rotatingRight=false;
            addToSequence();
            System.out.println(enteredSequence.toString());

        }
        rotate(-9);
    }

    private void addToSequence() {
        if(turnlock.getRotation()>0)
            enteredSequence.add(Math.round(40-(turnlock.getRotation()/9)));
        else
            enteredSequence.add(Math.round(turnlock.getRotation()/-9));
    }

    private void rotate(int alpha) {
        turnlock.setRotation((turnlock.getRotation()+alpha)%360);
    }

}

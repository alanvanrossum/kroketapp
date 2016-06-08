package com.context.kroket.escapeapp.minigames;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.context.kroket.escapeapp.application.ActivityManager;
import com.context.kroket.escapeapp.R;

/**
 * This activity is part of the minigame A.
 * Responsible for showing the picture.
 */
public class A_Code_Cracker_Pictureview extends AppCompatActivity {

    /**
     * Initializes the layout.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_code_cracker_pictureview);
    }

    /**
     * When the code button is clicked, the A_CodeCrackerCodeview is started.
     *
     * @param view the view that was clicked.
     */
    public void codeButton(View view) {
        Intent intent = new Intent(this, A_CodeCrackerCodeview.class);
        startActivity(intent);
    }

    /**
     * Start the activity. Change current activity to this.
     */
    @Override
    protected void onStart() {
        super.onStart();

        //Change the current activity.
        ((ActivityManager) this.getApplicationContext()).setCurrentActivity(this);
    }

}

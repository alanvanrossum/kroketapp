package com.context.kroket.escapeapp;

import android.app.Activity;
import android.app.Application;

/**
 * Created by Irene on 21-5-2016.
 */
public class App extends Application {

    //The current active activity
    private Activity current_activity = null;

    /**
     * Get the current active activity.
     *
     * @return the activity that is currently active.
     */
    public Activity getCurrentActivity() {
        return current_activity;
    }

    /**
     * Set the current activity.
     *
     * @param current_activity the activity to be set as current.
     */
    public void setCurrentActivity(Activity current_activity) {
        this.current_activity = current_activity;
    }

}

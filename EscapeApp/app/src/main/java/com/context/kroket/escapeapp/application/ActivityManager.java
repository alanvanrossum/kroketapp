package com.context.kroket.escapeapp.application;

import android.app.Activity;
import android.app.Application;

/**
 * Created by Irene on 21-5-2016.
 */
public class ActivityManager extends Application {

  // The current active activity.
  private Activity currentActivity = null;

  /**
   * Get the current active activity.
   *
   * @return the activity that is currently active.
   */
  public Activity getCurrentActivity() {
    return currentActivity;
  }

  /**
   * Set the current activity.
   *
   * @param current_activity
   *          the activity to be set as current.
   */
  public void setCurrentActivity(Activity current_activity) {
    this.currentActivity = current_activity;
  }

}

package com.context.kroket.escapeapp;

import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Harvey van Veltom on 18/05/2016.
 */
public class Game_AA_ActivityTest {

    @Test
    public void clickPicButton() {
        onView(withId(R.id.picButton)).perform(click());

        intended(hasComponent(hasShortClassName(".Game_A_Activity")));

    }
}

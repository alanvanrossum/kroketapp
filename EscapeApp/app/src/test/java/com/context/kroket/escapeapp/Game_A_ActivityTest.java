package com.context.kroket.escapeapp;

/**
 * Created by Harvey van Veltom on 18/05/2016.
 */


import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;

public class Game_A_ActivityTest {

    @Test
    public void clickPicButton() {
        onView(withId(R.id.codeButton)).perform(click());

        intended(hasComponent(hasShortClassName(".Game_AA_Activity")));

    }
}

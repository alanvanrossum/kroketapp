package com.context.kroket.escapeapp.helpclasses;

import android.widget.ImageView;

/**
 * Created by Team Kroket on 5-6-2016.
 */
public class GoldCoin extends Coin{

    /**
     * This method created a new Coin Object, which contains a count and an imageView.
     * The ImageView corresponds to the viewID of the coin, and contains information such as location and rotation.
     * count contains the amount of coins of this type collected in total by the player.
     *
     * @param newImageView
     */
    public GoldCoin(ImageView newImageView) {
        super(newImageView);
    }

    /**
     * Increase the score.
     */
    public void score() {
        count = count + 3;
    }

}

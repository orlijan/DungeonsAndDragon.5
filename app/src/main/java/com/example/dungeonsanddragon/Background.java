package com.example.dungeonsanddragon;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.content.res.Resources ;


public class Background {
    int x=0, y=0;
    Bitmap background;



    Background(int screenX, int screenY, Resources res)
    {
       background = BitmapFactory.decodeResource(res, R.drawable.background);
       background = Bitmap.createScaledBitmap(background, screenX, screenY, false);

    }
}

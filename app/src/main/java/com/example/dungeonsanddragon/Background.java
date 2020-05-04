package com.example.dungeonsanddragon;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.content.res.Resources ;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.widget.ImageView;

public class Background {
    int x=0, y=0;
    Bitmap background;
    Bitmap blankBitmap;
    Canvas canvas;
    ImageView gameView;
    Paint paint;


    Background(int screenX, int screenY, Resources res)
    {
       background = BitmapFactory.decodeResource(res, R.drawable.background);
       background = Bitmap.createScaledBitmap(background, screenX, screenY, false);

    }
}

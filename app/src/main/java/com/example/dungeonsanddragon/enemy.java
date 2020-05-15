package com.example.dungeonsanddragon;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.dungeonsanddragon.GameView.screenRatioX;
import static com.example.dungeonsanddragon.GameView.screenRatioY;


public class enemy {

    public int speed = 20;
    public boolean wasShot = true;
    public int losePoints ;
    int x = 0, y, width, height, enemyCounter = 1;
    Bitmap enemy1, enemy2;
            //enemy3, enemy4;

    enemy (Resources res) {

        enemy1 = BitmapFactory.decodeResource(res, R.drawable.enemy1);
        enemy2 = BitmapFactory.decodeResource(res, R.drawable.enemy2);
        width = enemy1.getWidth();
        height = enemy1.getHeight();
        width /= 6;
        height /= 6;
        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);
        enemy1 = Bitmap.createScaledBitmap(enemy1, width, height, false);
        enemy2 = Bitmap.createScaledBitmap(enemy2, width, height, false);
        y = -height;
    }


    Bitmap getenemy () {

        if (enemyCounter == 1) {
            enemyCounter++;
            return enemy1;
        }
       enemyCounter = 1;
        return enemy2;

    }
    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }

}
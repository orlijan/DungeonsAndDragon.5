package com.example.dungeonsanddragon;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.dungeonsanddragon.GameView.screenRatioX;
import static com.example.dungeonsanddragon.GameView.screenRatioY;

public class tower {

    int toShoot = 0;
    int x, y, width, height,  shootCounter = 1;
    Bitmap tower1, tower2, shoot1, shoot2, shoot3, shoot4, shoot5, dead;
    private GameView gameView;

    tower (GameView gameView, int screenY, Resources res) {

        this.gameView = gameView;

        tower1 = BitmapFactory.decodeResource(res, R.drawable.tower1);
        tower2 = BitmapFactory.decodeResource(res, R.drawable.tower2);

        width = tower1.getWidth();
        height = tower1.getHeight();

        width /= 4;
        height /= 4;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        tower1 = Bitmap.createScaledBitmap(tower1, width, height, false);
        tower2 = Bitmap.createScaledBitmap(tower2, width, height, false);

        shoot1 = BitmapFactory.decodeResource(res, R.drawable.shoot1);
        shoot2 = BitmapFactory.decodeResource(res, R.drawable.shoot2);
        shoot3 = BitmapFactory.decodeResource(res, R.drawable.shoot3);
        shoot4 = BitmapFactory.decodeResource(res, R.drawable.shoot4);
        shoot5 = BitmapFactory.decodeResource(res, R.drawable.shoot5);

        shoot1 = Bitmap.createScaledBitmap(shoot1, width, height, false);
        shoot2 = Bitmap.createScaledBitmap(shoot2, width, height, false);
        shoot3 = Bitmap.createScaledBitmap(shoot3, width, height, false);
        shoot4 = Bitmap.createScaledBitmap(shoot4, width, height, false);
        shoot5 = Bitmap.createScaledBitmap(shoot5, width, height, false);

        dead = BitmapFactory.decodeResource(res, R.drawable.dead);
        dead = Bitmap.createScaledBitmap(dead, width, height, false);

        y = screenY / 2;
        x = (int) (64 * screenRatioX);

    }

    Bitmap gettower () {

        if (toShoot != 0) {

            if (shootCounter == 1) {
                shootCounter++;
                return shoot1;
            }

            if (shootCounter == 2) {
                shootCounter++;
                return shoot2;
            }

            if (shootCounter == 3) {
                shootCounter++;
                return shoot3;
            }

            if (shootCounter == 4) {
                shootCounter++;
                return shoot4;
            }

            shootCounter = 1;
            toShoot--;
            gameView.newBullet();

            return shoot5;
        }
        return tower2;
    }

    Rect getCollisionShape () {
        return new Rect(x-300, y-300, x + width+300, y + height+300);
    }

    Bitmap getDead () {
        return dead;
    }

}
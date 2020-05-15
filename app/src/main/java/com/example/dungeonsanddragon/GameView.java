package com.example.dungeonsanddragon;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceView;
import java.math.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements Runnable {

    private Thread thread;
    private boolean isPlaying, isGameOver = false;
    private int screenX, screenY, score = 0;
    public static float screenRatioX, screenRatioY;
    private Paint paint;
    private enemy[] enemys;
    private SharedPreferences prefs;
    private Random random;
    private SoundPool soundPool;
    private List<Bullet> bullets;
    private int sound;
    private tower tower;
    private GameActivity activity;
    private Background background1;

    public GameView(GameActivity activity, int screenX, int screenY) {
        super(activity);

        this.activity = activity;

        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .build();

        } else
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);

        sound = soundPool.load(activity, R.raw.shoot, 1);

        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f / screenY;

        background1 = new Background(screenX, screenY, getResources());

        tower = new tower(this, screenY, getResources());

        bullets = new ArrayList<>();



        paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.WHITE);

        enemys = new enemy[3];

        for (int i = 0;i < 3;i++) {

            enemy enemy = new enemy(getResources());
            enemys[i] = enemy;

        }

        random = new Random();

    }

    @Override
    public void run() {

        while (isPlaying) {

            update ();
            draw ();
            sleep ();

        }

    }

    private void update () {


        List<Bullet> trash = new ArrayList<>();

        for (enemy enemy : enemys) {

            enemy.x -= enemy.speed;

            if (enemy.x + enemy.width < 0) {


                if (!enemy.wasShot) {
                    score--;
                    isGameOver = true;
                    return;
                }

                int bound = (int) (10 * screenRatioX);
                enemy.speed = random.nextInt(bound);

                if (enemy.speed < 10 * screenRatioX)
                    enemy.speed = (int) (10 * screenRatioX);

                enemy.x = screenX;
                enemy.y = (screenY / 2) - enemy.height;
                enemy.wasShot = false;

            }


            if (Rect.intersects(enemy.getCollisionShape(), tower.getCollisionShape())) {
                newBullet();
                for (Bullet bullet : bullets) {

                    if (bullet.x < screenX)
                        trash.add(bullet);

                    bullet.y =  ( (bullet.y+enemy.y/10)  );
                    bullet.x = ( (bullet.x+enemy.y/10) );

                        if (Rect.intersects(enemy.getCollisionShape(),
                                bullet.getCollisionShape())) {

                            score++;
                            enemy.x = -500;
                            bullet.x = screenX + 500;


                            enemy.wasShot = true;
                            score++;
                            trash.add(bullet);
                        }



                }

                return;
            }


        }
        for (Bullet bullet : trash)
        {
            bullets.remove(bullet);
        }

    }



    private void draw () {

        if (getHolder().getSurface().isValid()) {

            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            //canvas.drawBitmap(background2.background, background2.x, background2.y, paint);

            for (enemy enemy : enemys)
                canvas.drawBitmap(enemy.getenemy(), enemy.x, enemy.y, paint);



            canvas.drawText(score + "", screenX / 2f, 164, paint);

            if (isGameOver) {
                isPlaying = false;
                canvas.drawBitmap(tower.getDead(), tower.x, tower.y, paint);
                getHolder().unlockCanvasAndPost(canvas);
                saveIfHighScore();
                waitBeforeExiting ();
                return;
            }

                canvas.drawBitmap(tower.gettower(), tower.x, tower.y, paint);


            for (Bullet bullet : bullets) {
                canvas.drawBitmap(bullet.bullet, bullet.x, bullet.y, paint);
            }
            getHolder().unlockCanvasAndPost(canvas);

        }

    }

    private void waitBeforeExiting() {
        try {
            Thread.sleep(2000);
            activity.startActivity(new Intent(activity, MainActivity.class));
            activity.finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void saveIfHighScore() {

        if (prefs.getInt("highscore", 0) < score) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("highscore", score);
            editor.apply();
        }

    }

    private void sleep () {
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume () {

        isPlaying = true;
        thread = new Thread(this);
        thread.start();

    }

    public void pause () {

        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

@Override
public boolean onTouchEvent(MotionEvent event) {

    if((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP)
    {
        tower.x= (int)event.getX();
        tower.y = (int)event.getY();
    }

    return true;
}
    public void newBullet() {

        if (!prefs.getBoolean("isMute", false))
            soundPool.play(sound, 1, 1, 0, 0, 1);

        Bullet bullet = new Bullet(getResources());
        bullet.x = tower.x + (tower.width/2);
        bullet.y = tower.y + (tower.height / 2);
        bullets.add(bullet);

    }
}
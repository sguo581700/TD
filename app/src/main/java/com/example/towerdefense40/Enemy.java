package com.example.towerdefense40;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;

import java.util.Random;


class Enemy extends GameObject {
    private Bitmap bitmapObject;
    private Bitmap bitmapObjectUp;
    private Bitmap bitmapObjectR;
    private Bitmap bitmapObjectD;
    private int objectWidth;
    private int objectHeight;
    private int screenWidth;
    private int screenHeight;
    private Point location;
    private int speed;
    private int enemiesRemain;
    private static double hitPoint;
    private boolean isDead;
    private Rect rect;
    Enemy(Context context, Point size) {
        super(context);
        objectWidth = CONSTANT.SQUARE_SIZE;
        objectHeight = CONSTANT.SQUARE_SIZE;
        screenWidth = size.x;
        screenHeight = size.y;
        bitmapObject = setBitmapObject(context, objectWidth, objectHeight, R.drawable.basic_enemy);
        bitmapObjectUp = rotateBitmap(CONSTANT.LEFT, objectWidth, objectHeight);
        bitmapObjectR = setBitmapObject(context, objectWidth, objectHeight, R.drawable.basic_enemy);
        bitmapObjectD = rotateBitmap(CONSTANT.RIGHT, objectWidth, objectHeight);
        speed = 1;
        enemiesRemain = CONSTANT.WAVE1_ENEMY;
        location = new Point();
        hitPoint =10;
        isDead = false;
        rect = new Rect(location.x, location.y, objectWidth, objectHeight);

    }


    void setEnemiesRemain() {
        enemiesRemain -= 1;

    }
    int getEnemiesRemain(){ return enemiesRemain; }

    private void turnUp(){bitmapObject = bitmapObjectUp;}
    private void turnDown(){bitmapObject =bitmapObjectD;}
    private void recover(){bitmapObject = bitmapObjectR;}
    void hitPointLoss(){
        double temp = hitPoint;
        hitPoint= temp-5.0;

        if (hitPoint <= 0) setEnemiesRemain();
    }
    void setLocation(int x, int y){
        location.x = x;
        location.y = y;
    }
    void move(){

        if(location.x< 25 * CONSTANT.SQUARE_SIZE && location.y == 12 * CONSTANT.SQUARE_SIZE ||
                location.x< 24 * CONSTANT.SQUARE_SIZE && location.y == 13 * CONSTANT.SQUARE_SIZE ){
            location.x+=speed;
        }else{
            turnDown();
            location.y+=speed;
        }

        if(location.y<screenHeight/2-6*objectHeight){
            this.recover();
            location.y=screenHeight/2-6*objectHeight;
            location.x+=speed;
        }

    }

    void pause(){
        speed=0;
    }
    void resume(){
        speed=1;
    }
    BitmapDrawable beTransparent(BitmapDrawable drawable){
        drawable.setAlpha(100);
        return drawable;
    }
    void draw(Canvas canvas, Paint paint){
        final Random myRandom = new Random();

        if(location.x == CONSTANT.SQUARE_SIZE) {
            int spawnY = myRandom.nextInt(2) + 12;
            location.y = spawnY * CONSTANT.SQUARE_SIZE;
        }

        if(location.y < objectHeight * 17) {

            canvas.drawBitmap(bitmapObject, location.x, location.y, paint);
        }
    }
    Bitmap getBitmapObject(){return this.bitmapObject;}
    int getLocationX(){return location.x;}
    int getLocationY(){return location.y;}
    int getScreenWidth(){return screenWidth;}
    int getScreenHeight(){return screenHeight;}
    int getObjectWidth(){return objectWidth;}
    int getObjectHeight(){return objectHeight;}
    int getSquareSize(){return CONSTANT.SQUARE_SIZE;}
    double getHitPoint(){return hitPoint;}
}

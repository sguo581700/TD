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


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;

class Enemy extends GameObject {
    private Bitmap bitmapObject;
    private Bitmap bitmapObjectUp;
    private Bitmap bitmapObjectR;
    private Bitmap bitmapObjectD;
    private Bitmap bitmapObject_Dead;
    private int objectWidth;
    private int objectHeight;
    private int screenWidth;
    private int screenHeight;
    private Point location;
    private int speed;
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
        bitmapObject_Dead = setBitmapObject(context, objectWidth, objectHeight, R.drawable.uibarsquare);
        bitmapObjectD = rotateBitmap(CONSTANT.RIGHT, objectWidth, objectHeight);
        speed = 1;
        location = new Point(getSquareSize(), getSquareSize()*13);
        hitPoint =10;
        isDead = false;
        rect = new Rect(location.x, location.y, objectWidth, objectHeight);
    }
    void dead(){bitmapObject=bitmapObject_Dead;}
    private void turnUp(){bitmapObject = bitmapObjectUp;}
    private void turnDown(){bitmapObject =bitmapObjectD;}
    private void recover(){bitmapObject = bitmapObjectR;}
    void hitPointLoss(){
        double temp = hitPoint;
        hitPoint= temp-5.0;}
    void setLocation(int x, int y){
        location.x = x;
        location.y = y;
    }
    boolean isDead(){return isDead;}
    void setDead(){isDead=true;}
    void move(){
        if(location.x<objectWidth*25){
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
        canvas.drawBitmap(bitmapObject, location.x, location.y, paint);
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
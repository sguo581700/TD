package com.example.towerdefense40;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import java.util.ArrayList;

class Tower extends GameObject {
    private Point location;
    private Bitmap bitmapObject;
    private Bitmap bitmapObjectR;
    private Bitmap bitmapObjectD;
    private Bitmap bitmapObjectL;
    private Bitmap test;
    private int S;
    private double distance;
    private boolean withInRange;
    private Projectile projectile;
    Tower(Context context) {
        super(context);
        S = CONSTANT.SQUARE_SIZE;
        Resources res = Resources.getSystem();
        bitmapObject = setBitmapObject(context,2*S, 2*S, R.drawable.tower);
        bitmapObjectD = rotate(180);
        bitmapObjectL=rotate(-90);
        RoundedBitmapDrawable roundedBitmapDrawable= RoundedBitmapDrawableFactory.create(context.getResources(), bitmapObject);
        roundedBitmapDrawable.setCircular(true);
        bitmapObjectR =setBitmapObject(context,2*S, 2*S, R.drawable.tower);
        test=setBitmapObject(context, 2*S, 2*S, R.drawable.buildsquare);
        location = new Point();
        projectile = new Projectile(context);
        withInRange = false;
    }
    void inRange(){withInRange=true;}
    void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(bitmapObject, location.x, location.y, paint);
    }
    void setLocation(int x, int y){
        location.x = x - CONSTANT.SQUARE_SIZE; //set location x to be center of the tower
        location.y = y - CONSTANT.SQUARE_SIZE; //set location y to be center of the tower
    }
      double distance(Enemy enemy){
         return Math.sqrt(Math.pow(Math.abs(enemy.getLocationX()+S/2-this.getLocationX()+S), 2)+Math.pow(Math.abs(enemy.getLocationY()+S/2-this.getLocationY()+S/2),2));
     }
     private double degree(Enemy enemy){
        return Math.asin(Math.abs(enemy.getLocationY()+S/2-this.getLocationY()-S/2)/distance(enemy));
     }
     void rotateDown(){bitmapObject=bitmapObjectD;}
     void rotateLeft(){bitmapObject= bitmapObjectL;}
     void recover(){bitmapObject=bitmapObjectR;}
     private Bitmap rotate(int degree){
         Matrix matrix = new Matrix();
         matrix.setRotate(degree,S, S);
         return Bitmap.createBitmap(bitmapObject,0,0, 2*S, 2*S, matrix, true);
     }
    int getLocationX(){return location.x;}
    int getLocationY(){return location.y;}
    Projectile getProjectile(){return projectile;}
    int countEnemyLoss(ArrayList<Enemy>enemies){
        int counter=0;
        for(Enemy enemy: enemies){
                counter++;
        }
        return CONSTANT.WAVE1_ENEMY-counter;
    }
}

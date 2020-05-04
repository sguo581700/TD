package com.example.towerdefense40;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.ArrayList;

class Tower extends GameObject {
    private Point location;
    private Bitmap bitmapObject;
    private Bitmap bitmapObjectR;
    private Bitmap test;
    private int S;
    private boolean withInRange;
    private Projectile projectile;
    Tower(Context context) {
        super(context);
        S = CONSTANT.SQUARE_SIZE;
        bitmapObject = setBitmapObject(context,2*S, 2*S, R.drawable.tower);
        bitmapObjectR = setBitmapObject(context,2*S, 2*S, R.drawable.tower);
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
         return Math.sqrt(Math.pow(Math.abs(enemy.getLocationX()-this.getLocationX()), 2)+Math.pow(Math.abs(enemy.getLocationY()-this.getLocationY()),2));
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

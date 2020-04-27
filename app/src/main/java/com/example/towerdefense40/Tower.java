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
        location.x = x;
        location.y = y;
    }
      double distance(Enemy enemy){
         return Math.sqrt(Math.pow(Math.abs(enemy.getLocationX()-this.getLocationX()), 2)+Math.pow(Math.abs(enemy.getLocationY()-this.getLocationY()),2));
     }
     void openFile(Canvas canvas, Paint paint, ArrayList<Enemy> enemies, GameState gameState){
            if(gameState.getPaused()){
                projectile.pause();
            }else{
                if(distance(enemies.get(0))<CONSTANT.FIRING_RANGE&&!enemies.get(0).Dead()){
                    inRange();
                    if(withInRange){
                        projectile.draw(canvas,paint);
                    }
                }else {
                    withInRange = false;
                }
            }
     }
    int getLocationX(){return location.x;}
    int getLocationY(){return location.y;}
    Projectile getProjectile(){return projectile;}
}

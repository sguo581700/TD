package com.example.towerdefense40;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

class Projectile extends GameObject {
    private Bitmap bitmapObject;
    private int objectWidth;
    private int objectHeight;
    private Point location;
    private int speed;
    Projectile(Context context) {
        super(context);
        objectWidth = CONSTANT.SQUARE_SIZE/2;
        objectHeight = CONSTANT.SQUARE_SIZE/2;
        location = new Point();
        bitmapObject = setBitmapObject(context, objectWidth, objectHeight, R.drawable.projectile_arrow);
        speed = 1;
    }
    void setLocation(Tower tower){
        location.x = tower.getLocationX();
        location.y = tower.getLocationY();
    }
    void draw(Canvas canvas, Paint paint){
        canvas.drawBitmap(bitmapObject, location.x, location.y, paint);
    }
    void pause(){
        speed=0;
    }
    void reset(){speed=10;}
    int getLocationX(){
        return location.x;
    }
    int getLocationY(){
        return location.y;
    }
    void move(Tower tower, Enemy enemy){
        if(location.x > 0 && location.y < 22*CONSTANT.SQUARE_SIZE) {
            if (tower.getLocationY() < enemy.getLocationY()) {   //area1 tower to enemy
                if (enemy.getLocationX() < tower.getLocationX()) {
                    location.x -= speed;
                    location.y += speed;
                } else {
                    location.x += speed;
                    location.y += speed;
                }
                //if(location.y>=enemy.getLocationY()) setLocation(tower);
            }
            if (tower.getLocationY() > enemy.getLocationY()) {  //area2 tower to enemy
                if (enemy.getLocationX() < tower.getLocationX()) {
                    location.x -= speed;
                    location.y -= speed;
                } else {
                    location.x += speed;
                    location.y -= speed;
                }
               // if(location.y<enemy.getLocationY()+enemy.getObjectHeight())setLocation(tower);
            }
        }
        if(location.x<=0||location.y>=22*CONSTANT.SQUARE_SIZE){
            setLocation(tower);
            location.x=getLocationX();
            location.y=getLocationY();
        }
    }
}

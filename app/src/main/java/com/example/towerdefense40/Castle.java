package com.example.towerdefense40;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.ArrayList;

class Castle extends GameObject{
    private Point location;
    private Bitmap bitmapObject;
    private int S=CONSTANT.SQUARE_SIZE;
    private int objectWidth;
    private int objectHeight;
    Castle(Context context){
        super(context);
        objectWidth = S * 4;
        objectHeight = S * 4;
        location = new Point(S * 20, S * 17);
        bitmapObject = this.setBitmapObject(context, objectWidth, objectHeight, R.drawable.castle);
    }
    //draw the image onto Canvas
    void draw(Canvas canvas, Paint paint){
        canvas.drawBitmap(bitmapObject, location.x, location.y, paint);
    }
    void setLocation(int x, int y){
        location.x = x;
        location.y = y;
    }
    private Bitmap rotateBitmap(Bitmap bitmapObject, int degree){
        Matrix matrix = new Matrix();
        matrix.preScale(1, 1);
        matrix.preRotate(degree);
        this.bitmapObject = Bitmap.createBitmap(bitmapObject,0,0, objectWidth, objectHeight, matrix, true);
        return this.bitmapObject;
    }

    private int getLocationX(){
        return location.x;
    }

    private int getLocationY(){
        return location.y;
    }

    int countIntruders(ArrayList<Enemy> enemies){
        int counter=0;
        for(Enemy enemy: enemies){
            if(enemy.getLocationX()>=(this.getLocationX()+ CONSTANT.SQUARE_SIZE)&&enemy.getLocationY()>this.getLocationY()){
                counter++;
            }
        }
        return counter;
    }
}

package com.example.towerdefense40;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public class Map extends GameObject {
    private Point location;
    private Bitmap bitmapObject;
    private Bitmap bitmapPath;
    private Bitmap UIBar;
    private int horSize;
    private int verSize;
    private int S;
    private Castle castle;
    Map(Context context, Point size) {
        super(context);
        this.verSize = size.y;
        this.horSize = size.x;
        S = CONSTANT.SQUARE_SIZE;
        this.UIBar = this.setBitmapObject(context,S,S, R.drawable.uibarsquare);
        this.bitmapPath = this.setBitmapObject(context,S,S, R.drawable.pathsquare);
        this.bitmapObject = this.setBitmapObject(context,S,S, R.drawable.mapsquare);
        this.location = new Point();
        castle = new Castle(context);
    }
    //draw the map
    void draw(Canvas canvas, Paint paint){
        setLocation(0, 0);
        int w = (horSize / S) * S;
        int h = (verSize / S) * S;

        //draw the green area and UI bar
        for(int j = 0; j < verSize; ) {
            for (int i = 0; i < horSize; ) {
                if(j == 0 || j == S){
                    canvas.drawBitmap(this.UIBar, i, j, paint);
                }
                else{
                    canvas.drawBitmap(this.bitmapObject, i, j, paint);
                }
                i += S;
            }
            j += S;
        }
        //draw the brown horizontal path
        for(int i = 0; i <= S * 25;){
            canvas.drawBitmap(this.bitmapPath, i, S * 12, paint);
            canvas.drawBitmap(this.bitmapPath, i, S * 13, paint);
            i += S;
        }
        //draw the brown vertical path
        for(int j = S * 12; j <= verSize;){
            canvas.drawBitmap(this.bitmapPath,S * 24, j, paint);
            canvas.drawBitmap(this.bitmapPath,S * 25, j, paint);
            j += S;
        }
        castle.draw(canvas,paint);
    }
    private void setLocation(int x, int y){
        location.x = x;
        location.y = y;
    }
    Castle getCastle(){return castle;}
}

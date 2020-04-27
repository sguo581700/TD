package com.example.towerdefense40;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;

public class HUD extends GameObject {
    private int S;
    private Point location = new Point();
    private ArrayList<Bitmap>controls;
    private ArrayList<Rect>controlsR;
    private ArrayList<Bitmap>areas;
    private ArrayList<Rect>areasR;
    private ArrayList<Integer> label;
    static int PLAY = 0;
    static int PAUSE = 1;
    static int CONSTRUCT1 = 2;
    static int CONSTRUCT2 = 3;
    static int CONSTRUCT3 = 4;
    static int RECYCLE = 5;
    static int AREA1 = 0;
    static int AREA2 = 1;
    private int textFormatting;

    HUD(Context context) {
        super(context);
        this.S = CONSTANT.SQUARE_SIZE;
        label = new ArrayList<>();
        label.add(0,R.drawable.play);
        label.add(1,R.drawable.pause);
        label.add(2,R.drawable.construct1);
        label.add(3,R.drawable.construct2);
        label.add(4,R.drawable.construct3);
        label.add(5,R.drawable.recycle);


        //set list of bitmap objs
        controls = new ArrayList<>();
        for(int i=0; i<CONSTANT.BUTTONS;i++) {
            controls.add(i, this.setBitmapObject(context,2*S,2*S, label.get(i)));
        }
        createControlsR();
        areas = new ArrayList<>();
        for(int i= 0; i<CONSTANT.NUM_AREAS;i++){
            areas.add(i, this.setBitmapObject(context,2*S,2*S, R.drawable.buildsquare));
        }
        createAreasR();
        textFormatting = S;
    }
    private void createControlsR(){
        //set the touch area of each button
        Rect playR = new Rect( 0, 0, S * 2, S * 2);
        Rect pauseR = new Rect( 0, 0, S * 2, S * 2);
        Rect construct1 = new Rect(S * 2, 0, S * 4, S * 2);
        Rect construct2 = new Rect(S * 4, 0, S * 6, S * 2);
        Rect construct3 = new Rect(S * 6, 0, S * 8, S * 2);
        Rect recycleR = new Rect (S * 8, 0, S * 10, S * 2);

        controlsR = new ArrayList<>();
        controlsR.add(PLAY, playR);
        controlsR.add(PAUSE, pauseR);
        controlsR.add(CONSTRUCT1, construct1);
        controlsR.add(CONSTRUCT2, construct2);
        controlsR.add(CONSTRUCT3, construct3);
        controlsR.add(RECYCLE, recycleR);
    }
    private void createAreasR(){
        Rect area1 = new Rect(0, S*10, S*26, S*12); //areas built towers
        Rect area2 = new Rect (0, S*14, S*22, S*16); //areasbuilt towers
        areasR = new ArrayList<>();
        areasR.add(AREA1, area1);
        areasR.add(AREA2, area2);
    }
    ArrayList<Rect>getControlsR(){
        return controlsR;
    }
    ArrayList<Rect>getAreasR(){return areasR;}

    private void drawControls(Canvas canvas, Paint paint){
        paint.setColor(Color.argb(0x00, 0xff, 0xff, 0xff));
        for(Rect r: controlsR){
            canvas.drawRect(r.left, r.top, r.right, r.bottom, paint);
        }
        paint.setColor(Color.argb(255, 255, 255, 255));
    }
    private void setLocation(){
        location.x = 0; location.y=0;
    }

    Point getLocation(){
        return location;
    }
    void draw(Canvas canvas, Paint paint, GameState gameState){
        paint.setColor(Color.argb(255, 255, 255, 255));
        paint.setTextSize(S);
        drawHP(canvas, paint, gameState);
        drawWarFund(canvas, paint, gameState);
        drawTimer(canvas, paint, gameState);
        gameState.startTimer();

        if(gameState.getGameOver()){
            paint.setTextSize(textFormatting*5);
            canvas.drawText("PRESS PLAY" , S*4, S*12, paint);
            gameState.resetTimer();
        }
        if(gameState.getPaused()&&!gameState.getGameOver()){
            paint.setTextSize(S * 5);
            canvas.drawText("PAUSED", S*4, S*12, paint);
            gameState.pauseTimer();
        }

        drawControls(canvas, paint);
        setLocation();
        for(int i = 0; i<CONSTANT.BUTTONS;i++) {
            //if the game is not pause (or running), skip play button and display pause instead
            if(!gameState.getPaused() && i == 0){
                i ++;
            }
            //if the game is pause (not running), display the play button, skipp the pause button
            if(gameState.getPaused() && i == 1){
                i ++;
            }
            canvas.drawBitmap(this.controls.get(i), location.x, 0, paint);
            location.x += 2 * S;
        }
    }
    private void drawHP(Canvas canvas, Paint paint, GameState gameState){
        canvas.drawText("HP: "+gameState.getHitPoint(), S * 12, S, paint);
    }
    private void drawWarFund(Canvas canvas, Paint paint, GameState gameState){
        canvas.drawText("War Fund: "+gameState.getWarFund(), S * 12, S * 2 - 2, paint);
    }
    private void drawTimer(Canvas canvas, Paint paint, GameState gameState){
        canvas.drawText("Timer: "+(int)gameState.getTime(), S * 20, S , paint);
    }

}

package com.example.towerdefense40;

import android.content.Context;
import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.ArrayList;

public class UIController implements InputObserver{
    private Tower t;
    private Tower t2;
    UIController(GameEngineBroadcaster gb, Context context){
        gb.addObserver(this);
         t = new Tower(context);
         t2= new Tower(context);
    }
    Tower getT(){return t;}
    Tower getT2(){return t2;}
    @Override
    public void handleInput(MotionEvent event, GameState gameState, ArrayList<Rect>buttons, ArrayList<Rect>areas){
        int i = event.getActionIndex();
        int x = (int)event.getX(i);
        int y = (int)event.getY(i);
        int eventType = event.getAction() & MotionEvent.ACTION_MASK;
        if(eventType == MotionEvent.ACTION_UP || eventType == MotionEvent.ACTION_POINTER_UP) {
            if (buttons.get(HUD.PAUSE).contains(x, y) || buttons.get(HUD.PLAY).contains(x,y)) {
                if (!gameState.getPaused()) {
                    // Pause the game
                    gameState.pause();
                } else if (gameState.getGameOver()) {
                    //start new game
                    gameState.startNewGame();
                } else if (gameState.getPaused() && !gameState.getGameOver()) {
                    //resume game
                    gameState.resume();
                }
            }
            if(buttons.get(HUD.CONSTRUCT1).contains(x,y)){
                if(!gameState.getPaused()){
                    gameState.pause();
                    gameState.setBuild();
                }
            }
            if(buttons.get(HUD.CONSTRUCT2).contains(x,y)){
                if(!gameState.getPaused()){
                    gameState.pause();
                }
            }
            if(buttons.get(HUD.CONSTRUCT3).contains(x,y)){
                if(!gameState.getPaused()){
                    gameState.pause();
                }
            }
            if(buttons.get(HUD.RECYCLE).contains(x,y)){
                if(!gameState.getPaused()){
                    gameState.pause();
                }
            }
            if (areas.get(HUD.AREA1).contains(x, y)) {
                    if(!gameState.getPaused()) {
                        gameState.pause();
                    }
                    gameState.setConstruct();
                    t.setLocation(x,y);
                    t.getProjectile().setLocation(t);
            }
            if (areas.get(HUD.AREA2).contains(x, y)) {
                if(!gameState.getPaused()) {
                    gameState.pause();
                }
                gameState.setConstruct();
                t2.setLocation(x,y);
                t2.getProjectile().setLocation(t2);
            }
        }
    }
}

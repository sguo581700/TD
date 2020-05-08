package com.example.towerdefense40;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

@SuppressLint("ViewConstructor")
class GameEngine extends SurfaceView implements Runnable, GameEngineBroadcaster{
    private Thread thread = null;
    private GameState gameState;
    private Renderer renderer;
    private HUD hud;
    private ArrayList<InputObserver> inputObservers = new ArrayList();
    private UIController uiController;
    @RequiresApi(api = Build.VERSION_CODES.N)
    public GameEngine(Context context, Point size) {
        super(context);
        gameState = new GameState();
        renderer = new Renderer(this, size);
        hud = new HUD(context);
        uiController = new UIController(this, context);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void run() {
        while(gameState.getThreadRunning()){
              renderer.draw(hud, gameState, uiController);
            }
    }
    void startThread(){
       gameState.startThread();
        thread = new Thread(this);
        thread.start();
    }
    void stopThread(){
        gameState.stopEverything();
        try{
            thread.join();
        }catch(InterruptedException e){
            Log.e("Exception", "stopThread()"+e.getMessage());
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        for(InputObserver o: inputObservers)
                uiController.handleInput(motionEvent, gameState, hud.getControlsR(), hud.getAreasR());
        return true;
    }
    @Override
    public void addObserver(InputObserver o) {
        inputObservers.add(o);
    }
}

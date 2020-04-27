package com.example.towerdefense40;

import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.ArrayList;

public interface InputObserver {
    void handleInput(MotionEvent event, GameState gameState, ArrayList<Rect> controls, ArrayList<Rect> Areas);
}

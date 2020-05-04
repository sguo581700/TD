package com.example.towerdefense40;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.function.Predicate;

class Renderer {
    private int S;
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private Map map;
    private ArrayList<Enemy>enemies;
    //private int enemyLeft;
    @RequiresApi(api = Build.VERSION_CODES.N)
    Renderer(SurfaceView sv, Point size){
        paint = new Paint();
        surfaceHolder = sv.getHolder();
        S = CONSTANT.SQUARE_SIZE;
        map = new Map(sv.getContext(), size);
        enemies = new ArrayList<>();
        for(int i=0; i < CONSTANT.WAVE1_ENEMY;i++){
            Enemy g = new Enemy(sv.getContext(), size);
            enemies.add(g);
            //enemyLeft++;
        }
        enemies.forEach(enemy -> enemy.setLocation(enemy.getSquareSize(), enemy.getSquareSize()*13));
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    void draw(HUD hud, GameState gameState, UIController uiController){
        if(surfaceHolder.getSurface().isValid()){
            this.canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.argb(255,0,0,0));
            gameState.startDrawing();
            if(gameState.getDrawing()) {
                map.draw(canvas, paint);
                uiController.getT().draw(canvas,paint);
                uiController.getT2().draw(canvas,paint);
                if(gameState.getTime()>=gameState.getTimeToSpawn()) {
                    gameObjectSpawn(gameState);
                    arrowSpawn(uiController, gameState);
                }
            }
            gameState.increaseWarFund(uiController.getT().countEnemyLoss(enemies));
            gameState.loseLife(map.getCastle().countIntruders(enemies));
            hud.draw(canvas, paint, gameState);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void arrowSpawn(UIController uiController, GameState gameState){
        if(enemies.size() > 0) {
            if (uiController.getT().distance(enemies.get(enemies.size() - 1)) <= CONSTANT.FIRING_RANGE) {
                uiController.getT().getProjectile().draw(canvas, paint);
                if (gameState.getPaused()) {
                    uiController.getT().getProjectile().pause();
                } else {
                    uiController.getT().getProjectile().reset();
                    ProjectileMove(uiController);
                    EnemiesTakenHit(uiController);
                    EnemyRemoval(uiController);
                }
            }
        }

        if(enemies.size() > 0) {
            if (uiController.getT2().distance(enemies.get(enemies.size() - 1)) <= CONSTANT.FIRING_RANGE) {
                uiController.getT2().getProjectile().draw(canvas, paint);
                if (gameState.getPaused()) {
                    uiController.getT2().getProjectile().pause();
                } else {
                    uiController.getT().getProjectile().reset();
                    ProjectileMove(uiController);
                    EnemiesTakenHit(uiController);
                    EnemyRemoval(uiController);
                }
            }
        }

        if (enemies.size() == 0){
            paint.setTextSize(S * 5);
            canvas.drawText("COMPLETED", S*4, S*12, paint);
            gameState.setCompleted();
            gameState.pauseTimer();


        }
    }
    private void enemyTakenHit(Enemy enemy, UIController uiController){
        if(enemy.getLocationX()==uiController.getT().getProjectile().getLocationX()){
            enemy.hitPointLoss();
        }
        if(enemy.getLocationX()==uiController.getT2().getProjectile().getLocationX()){
            enemy.hitPointLoss();

        }

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void EnemiesTakenHit(UIController uiController){
        enemies.forEach(enemy -> enemyTakenHit(enemy, uiController));
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void ProjectileMove(UIController uiController){
        enemies.forEach(enemy -> uiController.getT().getProjectile().move(uiController.getT(),enemy));
        enemies.forEach(enemy -> uiController.getT2().getProjectile().move(uiController.getT2(),enemy));
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void EnemyRemoval(UIController uiController){
        Predicate<Enemy> enemyPredicate = enemy -> (enemy.getLocationX()==uiController.getT().getProjectile().getLocationX());
        Predicate<Enemy> enemyPredicate2 = enemy -> (enemy.getLocationX()==uiController.getT2().getProjectile().getLocationX());
        enemies.removeIf(enemyPredicate);//change module setting source and language to level 8
        enemies.removeIf(enemyPredicate2);//change module setting source and language to level 8
    }
    private void gameObjectSpawn(GameState gameState) {
        if(enemies.size() > 0) {
            int i = enemies.size() - 1;
            enemies.get(i).draw(canvas, paint);
            if (gameState.getPaused()) {
                enemies.get(i).pause();
            } else {
                enemies.get(i).resume();
                enemies.get(i).move();
            }
            while ((enemies.get(i).getLocationX() >= 2 * CONSTANT.SQUARE_SIZE) && i >= 1) {
                i = i - 1;
                enemies.get(i).draw(canvas, paint);
                if (gameState.getPaused()) {
                    enemies.get(i).pause();
                } else {
                    enemies.get(i).resume();
                    enemies.get(i).move();
                }

                if (i <= 0) {
                    return;
                }
            }
        }
    }
}

package com.example.towerdefense40;

public class GameState {
    private static volatile boolean threadRunning = false;
    private static volatile boolean paused = true;
    private static volatile boolean gameOver = true;
    private static volatile boolean drawing = false;
    private static volatile boolean buildState = false;
    private static volatile boolean construct = false;

    private static int hitPoint;
    private static float timeIncrement=0.1f;
    private int warFund;
    private float timer; //timing system;
    private final float timeToSpawn = CONSTANT.SPAWN_TIME;
    GameState(){
        timer=0;
    }
    private void endGame(){
        gameOver = true;
        paused = true;
    }
    void startNewGame(){
        hitPoint = CONSTANT.HP;
        warFund = CONSTANT.WF_INIT;
        stopDrawing();
        resume();
        startDrawing();
    }
    void loseLife(int intruders){
        int temp = CONSTANT.HP;
        hitPoint = temp - intruders;
        //don't forget the sound
        if(hitPoint<=0){
            pauseTimer();
            pause();
            endGame();
        }
    }
    int getHitPoint(){
        return hitPoint;
    }

    void increaseWarFund(int loss){
        int temp = CONSTANT.WF_INIT;

        warFund =temp + loss*10;
    }

    int getWarFund(){
        return warFund;
    }

    void pause(){
        paused = true;
    }
    void resume(){
        gameOver = false;
        paused = false;
        startTimer();
    }

    void stopEverything(){
        paused = true;
        gameOver = true;
        threadRunning = false;
        pauseTimer();
    }
    void startTimer(){
        timer += timeIncrement;
    }
    void pauseTimer(){

        timer-=timeIncrement;
    }
    void resetTimer(){
        timer=0;
    }

    boolean getThreadRunning(){
        return threadRunning;
    }
    void startThread(){
        threadRunning = true;
    }
    private void stopDrawing(){
        drawing = false;
    }

    void startDrawing(){
        drawing = true;
    }

    boolean getDrawing(){
        return drawing;
    }
    boolean getPaused(){
        return paused;
    }
    boolean getGameOver(){
        return gameOver;
    }

    float getTime(){
        return timer;
    }
    float getTimeToSpawn(){
        return timeToSpawn;
    }
    boolean getBuild(){
        return buildState;
    }
    void setBuild(){
        buildState = true;
    }
    void setConstruct(){construct=true;}
}

package com.example.towerdefense40;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public abstract class GameObject {
    private Bitmap bitmapObject;
    GameObject(Context context){
    }
    Bitmap setBitmapObject(Context context, int objectWidth, int objectHeight, int id) {
        this.bitmapObject = BitmapFactory.decodeResource(context.getResources(), id);
        this.bitmapObject= Bitmap.createScaledBitmap(this.bitmapObject, objectWidth, objectHeight, false);
        return bitmapObject;
    }
    Bitmap rotateBitmap(int degree, int objectWidth, int objectHeight){
        Matrix matrix = new Matrix();
        matrix.preScale(1, 1);
        matrix.preRotate(degree);
        bitmapObject = Bitmap.createBitmap(bitmapObject,0,0, objectWidth, objectHeight, matrix, true);
        return bitmapObject;
    }
}

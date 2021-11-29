package com.sdm.mgp2021;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

import java.util.Random;

public class Ship implements EntityBase
{
    private boolean isDone = false;

    private Bitmap bmp = null;
    private Bitmap scaledbmp = null;

    int ScreenWidth, ScreenHeight;
    private float xStart, xPos, yPos;
    private float speed = 1;


    @Override
    public boolean IsDone()
    {
        return isDone;
    }

    @Override
    public void SetIsDone(boolean _isDone)
    {
        isDone = _isDone;
    }

    @Override
    // For us to intialize or load resource eg: images
    public void Init(SurfaceView _view)
    {
        bmp = BitmapFactory.decodeResource(_view.getResources(), R.mipmap.ic_launcher);

        // Find the surfaceview size or the screen size.
        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        ScreenWidth = metrics.widthPixels;
        ScreenHeight = metrics.heightPixels;

        scaledbmp = Bitmap.createScaledBitmap(bmp, ScreenWidth, ScreenHeight, true);

    }

    @Override
    public void Update(float _dt)
    {
    }

    @Override
    public void Render(Canvas _canvas)
    {
    }

    @Override
    public boolean IsInit()
    {
        return false;
    }

    @Override
    public int GetRenderLayer()
    {
        return LayerConstants.RENDERTEXT_LAYER;
    }

    @Override
    public void SetRenderLayer(int _newLayer)
    {
        return;
    }

    @Override
    public EntityBase.ENTITY_TYPE GetEntityType(){
        return EntityBase.ENTITY_TYPE.ENT_DEFAULT;
    }

    public static Ship Create(){
        Ship result = new Ship();
        EntityManager.Instance.AddEntity(result, EntityBase.ENTITY_TYPE.ENT_DEFAULT);
        return result;
    }

    public float GetPosX()
    {
        return xPos;
    }

    public float GetPosY()
    {
        return yPos;
    }

    // Optional
    public float getRadius()
    {
        return bmp.getHeight() * 0.5f;
    }

    public void OnHit(Collidable _other)
    {
        if (_other.GetType() == "SampleEntity")
        {
            //SetIsDone(true);
        }
    }
}

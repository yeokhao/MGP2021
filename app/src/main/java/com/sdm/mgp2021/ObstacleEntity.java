package com.sdm.mgp2021;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

import java.util.Random;

public class ObstacleEntity implements EntityBase
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

        // Logic alog here!!
        // Option 1
        // Make this entity deadable/ destroy - respawn all the time.

        // Option 2
        // Make this object IMMORTAL. Loop it all again and again.

        // Randomize a location to spawn on screen
        Random ranGen = new Random();
        xStart = xPos = _view.getWidth();
        ScreenHeight = _view.getHeight();
        yPos = ranGen.nextInt() % ScreenHeight;

        // Set a speed to cross the screen
        speed = _view.getWidth() * 0.5f;
    }

    @Override
    public void Update(float _dt)
    {
        // Check if current state is where
        //if (StateManager.Instance.GetCurrentState() != "MainGame")
        //{
        //return;
        //}

        xPos -= speed * _dt;
        // Check if we are out of the screen
        if (xPos <= -bmp.getHeight() * 0.5f)
        {
            // Move it to another location
            xPos = xStart;
            Random ranGen = new Random();
            yPos = ranGen.nextInt() % ScreenHeight;
        }

        // NEED TO FIX
        // Check collision with player or another object
        //if (Collision.SphereToSphere(xPos, yPos, bmp.getWidth() * 0.5f, GameSystem.Instance.Ship.xPos, GameSystem.Instance.Ship.yPos, GameSystem.Instance.Ship.bmp.getWidth()))
        {

        }
    }

    @Override
    public void Render(Canvas _canvas)
    {
        _canvas.drawBitmap(scaledbmp, xPos, yPos, null);
        _canvas.drawBitmap(scaledbmp, xPos + ScreenWidth, yPos, null);
        //_canvas.drawBitmap(scaledbmp, tfx, null);
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

    public static ObstacleEntity Create(){
        ObstacleEntity result = new ObstacleEntity();
        EntityManager.Instance.AddEntity(result, EntityBase.ENTITY_TYPE.ENT_DEFAULT);
        return result;
    }
}

package com.sdm.mgp2021;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceView;

import java.util.Random;

public class ObstacleEntity implements EntityBase, Collidable
{
    private boolean isDone = false;

    private Bitmap bmp = null;
    //private Bitmap scaledbmp = null;

    int ScreenWidth, ScreenHeight;
    private float xStart, xPos, yPos;
    private int path;
    private float speed = 1;
    private float obstacleSpeed = 1;
    //private SurfaceView view = null;
    //Matrix tfx = new Matrix();
    DisplayMetrics metrics;

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
        bmp = BitmapFactory.decodeResource(_view.getResources(), R.drawable.obstacle);

        // Find the surfaceview size or the screen size.
        metrics = _view.getResources().getDisplayMetrics();
        ScreenWidth = metrics.widthPixels;
        ScreenHeight = metrics.heightPixels;

        //scaledbmp = Bitmap.createScaledBitmap(bmp, ScreenWidth, ScreenHeight, true);

        // Logic alog here!!
        // Option 1
        // Make this entity deadable/ destroy - respawn all the time.

        // Option 2
        // Make this object IMMORTAL. Loop it all again and again.

        // Randomize a location to spawn on screen
        Random ranGen = new Random();
        xStart = xPos = _view.getWidth() + 50;
        ScreenHeight = _view.getHeight();
        yPos = ranGen.nextInt((int)(0.8 * ScreenHeight) + (int)(0.1 * ScreenHeight)); // Returns random value ranging from 1-3
        //yPos = ScreenHeight * (path / 10);

        // Set a speed to cross the screen
        speed = _view.getWidth() * 0.5f;
        //speed = 100.f;
    }

    @Override
    public void Update(float _dt)
    {
        obstacleSpeed += 0.00001* _dt;
        if (obstacleSpeed >=5 )
        {
            obstacleSpeed =5;
        }
        if (GameSystem.Instance.GetIsPaused())
            return;

        // Check if current state is where
        //if (StateManager.Instance.GetCurrentState() != "MainGame")
        //{
        //return;
        //}

        xPos -= speed * _dt * obstacleSpeed;

        // Check if we are out of the screen
        if (xPos <= -bmp.getWidth() * 0.5f)
        {
            //Log.d("offscreen", "Obstacle went offscreen");
            // Move it to another location
            xPos = xStart;
            Random ranGen = new Random();
            yPos = ranGen.nextInt((int)(0.8 * ScreenHeight) + (int)(0.1 * ScreenHeight)); // Returns random value ranging from 1-3
            //yPos = ScreenHeight * (path / 10);
        }
    }

    @Override
    public void Render(Canvas _canvas)
    {
        _canvas.drawBitmap(bmp, xPos, yPos, null);
        //_canvas.drawBitmap(scaledbmp, xPos + ScreenWidth, yPos, null);
        //_canvas.drawBitmap(scaledbmp, tfx, null);
    }

    @Override
    public boolean IsInit()
    {
        return bmp != null;
    }

    @Override
    public int GetRenderLayer()
    {
        return LayerConstants.GAMEOBJECTS_LAYER;
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

    public String GetType()
    {
        return "Obstacle";
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
    public float GetRadius()
    {
        return bmp.getHeight() * 0.5f;
    }

    public void OnHit(Collidable _other)
    {
        if (_other.GetType() == "Ship")
        {
            xPos= xStart;
            Random ranGen = new Random();
            yPos = ranGen.nextInt((int)(0.8 * ScreenHeight) + (int)(0.1 * ScreenHeight)); // Returns random value ranging from 1-3
            //SetIsDone(true);
        }
    }
}

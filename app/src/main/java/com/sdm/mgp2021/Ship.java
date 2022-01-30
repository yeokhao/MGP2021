package com.sdm.mgp2021;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Debug;
import android.text.method.Touch;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceView;

//import android.hardware.Sensor;
//import android.hardware.SensorEvent;
//import android.hardware.SensorEventListener;
//import android.hardware.SensorManager;

import java.util.Random;

public class Ship implements EntityBase, Collidable
{
    private boolean isDone = false;
    Paint paint = new Paint();
    private Bitmap bmp = null;
    private  int red = 0, green = 255, blue = 0;
    private int highestScore,tempScore;
    Typeface myfont;
    //SensorManager sensor;
    public final static Ship Instance = new Ship();

    int ScreenWidth, ScreenHeight;
    private float xStart, xPos, yPos, upButtonXpos,upButtonYpos,downButtonXpos,downButtonYpos;
    private float speed = 1;

    private SurfaceView view = null;
    Matrix tfx = new Matrix();
    DisplayMetrics metrics;

    private Sprite spriteSmurf = null;

    private boolean hasTouched = false;

    private Bitmap upButton,downButton, scaledUpButton,scaledDownButton = null;

    public float score;

    private float buttonDelay = 0;
    private boolean moveUp, moveDown;

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
        //sensor = (SensorManager) _view.getContext().getSystemService(Context.SENSOR_SERVICE);
        //sensor.registerListener(this, sensor.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0), SensorManager.SENSOR_DELAY_NORMAL);

        myfont = Typeface.createFromAsset(_view.getContext().getAssets(), "fonts/Gemcut.otf");
        bmp = BitmapFactory.decodeResource(_view.getResources(), R.drawable.gameship);

        spriteSmurf = new Sprite(ResourceManager.Instance.GetBitmap(R.drawable.smurf_sprite), 4, 4, 16);

        // Find the surfaceview size or the screen size.
        metrics = _view.getResources().getDisplayMetrics();
        ScreenWidth = metrics.widthPixels;
        ScreenHeight = metrics.heightPixels;

        upButton =  ResourceManager.Instance.GetBitmap(R.drawable.upbutton);
        downButton =  ResourceManager.Instance.GetBitmap(R.drawable.downbutton);

        scaledUpButton = Bitmap.createScaledBitmap(upButton, (int)(ScreenWidth)/12, (int)(ScreenWidth)/12, true);
        scaledDownButton = Bitmap.createScaledBitmap(downButton, (int)(ScreenWidth)/12, (int)(ScreenWidth)/12, true);


//        Random ranGen = new Random();
//        xPos = ranGen.nextFloat() * _view.getWidth();
//        yPos = ranGen.nextFloat() * _view.getHeight();

        xPos = 500;
        yPos = 500;

        //left button position
        upButtonXpos = 180;
        upButtonYpos = (ScreenHeight/10) * 9;

        //right button position
        downButtonXpos = 380;
        downButtonYpos = (ScreenHeight/10) * 9;

        moveUp = false;
        moveDown = false;
    }

    @Override
    public void Update(float _dt)
    {
        if (GameSystem.Instance.GetIsPaused())
            return;

        spriteSmurf.Update(_dt);
//        tfx.preRotate(20 * _dt,metrics.widthPixels / 10,metrics.heightPixels / 10);
//        tfx.postTranslate(10*_dt,10*_dt);

//        lifetime -= _dt;
//        if (lifetime < 0.0f)
//        {
//            SetIsDone(true); // When time is up, kill entity
//        }

        /*
        if (TouchManager.Instance.IsDown())
        {
            float imgRadius = bmp.getHeight() * 0.5f;
            if (Collision.SphereToSphere(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(), 0.0f, xPos, yPos, imgRadius))
            {
                SetIsDone(true);
            }
        }
        */

       //if (TouchManager.Instance.HasTouch()) // Touch and drag
       //{
       //    // Check collision with the smurf sprite
       //    //float imgRadius1 = spriteSmurf.GetWidth() * 0.5f;
       //    //if (Collision.SphereToSphere(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(), 0.0f, xPos, yPos, imgRadius1) || hasTouched)
       //    //{
       //    //    hasTouched = true;
       //    //    xPos = TouchManager.Instance.GetPosX();
       //    //    yPos = TouchManager.Instance.GetPosY();
       //    //}
       //}

        //Log.d("xPos", String.valueOf(xPos) + " " + String.valueOf(yPos));

        // Player Movement
        if (moveUp == true)
        {
            if (yPos > bmp.getHeight())
            {
                yPos -= 10;
            }
        }
        if (moveDown == true)
        {
            if (yPos < ScreenHeight - bmp.getHeight())
            {
                yPos += 10;
            }
        }
        //Log.d("yPos", String.valueOf(yPos));

        buttonDelay += _dt;

        if (TouchManager.Instance.HasTouch())
        {
            if (TouchManager.Instance.IsDown())
            {
                float imgRadius = scaledUpButton.getHeight() * 0.5f;
                if (Collision.SphereToSphere(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(), 0.0f, upButtonXpos, upButtonYpos, imgRadius) && buttonDelay >= 0)
                {
                    moveUp = true;

                }
                else if (Collision.SphereToSphere(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(), 0.0f, downButtonXpos, downButtonYpos, imgRadius) && buttonDelay >= 0)
                {
                    moveDown = true;
                }
                buttonDelay = 0;
            }
            else
            {
                if (TouchManager.Instance.IsUp())
                {
                    moveUp = false;
                    moveDown = false;
                }
            }
        }
        else
        {
            moveUp = false;
            moveDown = false;
        }

        if (score >= 5)
        {
            //SetIsDone(true);

            int currScore = GameSystem.Instance.GetIntFromSave("Score");
            ++currScore;
            GameSystem.Instance.SaveEditBegin();
            GameSystem.Instance.SetIntInSave("Score", currScore);
            GameSystem.Instance.SaveEditEnd();
            GamePage.Instance.SetToEnd();
            //StateManager.Instance.ChangeState("Endscreen");
        }
    }

    @Override
    public void Render(Canvas _canvas)
    {
        //spriteSmurf.Render(_canvas, (int)xPos, (int)yPos);


        paint.setARGB(255, red, green, blue);
        paint.setStrokeWidth(200);
        paint.setTypeface(myfont);
        paint.setTextSize(70);
        _canvas.drawText("Score:" + score, 30, 120, paint);
        _canvas.drawBitmap(bmp, xPos, yPos, null);
        _canvas.drawBitmap(scaledUpButton, upButtonXpos - scaledUpButton.getWidth() * 0.5f, upButtonYpos - scaledUpButton.getHeight() * 0.5f, null);
        _canvas.drawBitmap(scaledDownButton, downButtonXpos - scaledDownButton.getWidth() * 0.5f, downButtonYpos - scaledDownButton.getHeight() * 0.5f, null);

//        Matrix transform = new Matrix();
//        transform.postScale((0.5f + Math.abs((float)Math.sin(lifetime))), (0.5f + Math.abs((float)Math.sin(lifetime))));
//        _canvas.drawBitmap(bmp, transform, null);
    }

    @Override
    public boolean IsInit()
    {
        return bmp != null;
    }

    @Override
    public int GetRenderLayer()
    {
        return LayerConstants.SHIP_LAYER;
    }

    @Override
    public void SetRenderLayer(int _newLayer)
    {
        return;
    }

    @Override
    public EntityBase.ENTITY_TYPE GetEntityType(){
        return EntityBase.ENTITY_TYPE.ENT_PLAYER;
    }


    public static Ship Create(){
        Ship result = new Ship();
        EntityManager.Instance.AddEntity(result, EntityBase.ENTITY_TYPE.ENT_PLAYER);
        return result;
    }

    public String GetType()
    {
        return "Ship";
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

    public void SetPosX(float newXPos)
    {
        xPos = newXPos;
    }

    public void SetPosY(float newYPos)
    {
        yPos = newYPos;
    }

    public void OnHit(Collidable _other)
    {
        if (_other.GetType() == "Obstacle")
        {
            if (true)
            {
                score++;
            }
            else
            {
                score--;
            }
            //SetIsDone(true);
        }
    }
}

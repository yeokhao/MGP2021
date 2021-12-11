package com.sdm.mgp2021;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.text.method.Touch;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

public class PauseButtonEntity implements EntityBase{

    private Bitmap bmpP = null;
    private Bitmap bmpUP = null;

    private Bitmap ScaledbmpP = null;
    private Bitmap ScaledbmpUP = null;

    private float xPos = 0;
    private float yPos = 0;
    private float screenHeight = 0;
    private boolean isDone = false;
    private boolean isInit = false;
    private boolean Paused = false;
    int ScreenWidth, ScreenHeight;

    private float buttonDelay = 0;
    @Override
    public boolean IsDone() {
        return isDone;
    }

    @Override
    public void SetIsDone(boolean _isDone) {
        isDone = _isDone;
    }

    @Override
    public void Init(SurfaceView _view) {

        // New method using our own resource manager : Returns pre-loaded one if exists
        //bmp = ResourceManager.Instance.GetBitmap(R.drawable.star);
        bmpP = ResourceManager.Instance.GetBitmap(R.drawable.pause);
        bmpUP = ResourceManager.Instance.GetBitmap(R.drawable.pause1);

        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        ScreenWidth = metrics.widthPixels;
        ScreenHeight = metrics.heightPixels;

        ScaledbmpP = Bitmap.createScaledBitmap(bmpP,(int)(ScreenWidth)/12,(int)(ScreenWidth)/7,true);
        ScaledbmpUP = Bitmap.createScaledBitmap(bmpUP,(int)(ScreenWidth)/12,(int)(ScreenWidth)/7,true);

        xPos = ScreenWidth - 150;
        yPos = 150;
        isInit = true;
    }

    @Override
    public void Update(float _dt) {
    buttonDelay += _dt;

        if(TouchManager.Instance.HasTouch())
        {
            if(TouchManager.Instance.IsDown() && !Paused)
            {
                float imgRadius = ScaledbmpP.getHeight() * 0.5f;
                //check COllision of button
                if (Collision.SphereToSphere(TouchManager.Instance.GetPosX(),TouchManager.Instance.GetPosY(),0.0f,xPos,yPos,imgRadius) && buttonDelay >= 0.25f)
                {
                    Paused = true;

                }
                buttonDelay = 0;
                GameSystem.Instance.SetIsPaused(!GameSystem.Instance.GetIsPaused());
            }
        }
        else
            Paused = false;

    }

    @Override
    public void Render(Canvas _canvas) {
        if (Paused == false)
         _canvas.drawBitmap(ScaledbmpP,xPos = ScaledbmpP.getWidth(),yPos-ScaledbmpUP.getHeight() *0.5f,null);
        else
            _canvas.drawBitmap(ScaledbmpP,xPos = ScaledbmpP.getWidth(),yPos-ScaledbmpUP.getHeight() *0.5f,null);

    }

    @Override
    public boolean IsInit() {

        return isInit;
    }

    @Override
    public int GetRenderLayer() {
        return LayerConstants.PAUSEBUTTON_LAYER;
    }

    @Override
    public void SetRenderLayer(int _newLayer) {
        return;
    }

    @Override
    public ENTITY_TYPE GetEntityType(){ return ENTITY_TYPE.ENT_DEFAULT;}

    public static PauseButtonEntity Create()
    {
        PauseButtonEntity result = new PauseButtonEntity();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_DEFAULT);
        return result;
    }


}

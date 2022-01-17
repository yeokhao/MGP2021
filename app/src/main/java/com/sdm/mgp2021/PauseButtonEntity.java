package com.sdm.mgp2021;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.icu.number.Scale;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

public class PauseButtonEntity implements EntityBase
{
    private Bitmap bmpP = null;
    private Bitmap bmpUP = null;

    private Bitmap ScaledbmpP = null;
    private Bitmap ScaledbmpUP = null;

    private Bitmap bmpExit = null;
    private Bitmap ScaledbmpExit = null;

    private float PausexPos = 0;
    private float PauseyPos = 0;
    private float ExitxPos = 0;
    private float ExityPos = 0;

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
    public void Init(SurfaceView _view)
    {
        bmpP = ResourceManager.Instance.GetBitmap(R.drawable.pause1);
        bmpUP = ResourceManager.Instance.GetBitmap(R.drawable.pause);
        bmpExit = ResourceManager.Instance.GetBitmap(R.drawable.exit);

        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        ScreenWidth = metrics.widthPixels;
        ScreenHeight = metrics.heightPixels;

        ScaledbmpP = Bitmap.createScaledBitmap(bmpP, (int) (ScreenWidth) / 12, (int) (ScreenHeight) / 7, true);
        ScaledbmpUP = Bitmap.createScaledBitmap(bmpUP, (int) (ScreenWidth) / 12, (int) (ScreenHeight) / 7, true);
        ScaledbmpExit = Bitmap.createScaledBitmap(bmpExit, (int) (ScreenWidth) / 12, (int) (ScreenHeight) / 7, true);

        PausexPos = ScreenWidth - 150;
        PauseyPos = 150;

        ExitxPos = ScreenWidth - 300;
        ExityPos = 150;

        isInit = true;
    }

    @Override
    public void Update(float _dt)
    {
        buttonDelay += _dt;

        if (TouchManager.Instance.HasTouch())
        {
            if (TouchManager.Instance.IsDown() && !Paused)
            {
                // Check collision of button here
                float imgRadius = ScaledbmpP.getHeight() * 0.5f;
                if (Collision.SphereToSphere(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(), 0.0f, PausexPos, PauseyPos, imgRadius) && buttonDelay >= 0.25)
                {
                    AudioManager.Instance.PlayAudio(R.raw.select, 1.0f);

                    Paused = true;

                    if (PauseConfirmDialogFragment.isShown)
                    {
                        return;
                    }

                    PauseConfirmDialogFragment newPauseConfirm = new PauseConfirmDialogFragment();
                    newPauseConfirm.show(GamePage.Instance.getSupportFragmentManager(), "PauseConfirm");

                    //GameSystem.Instance.SetIsPaused(!GameSystem.Instance.GetIsPaused());
                }
                else if (Collision.SphereToSphere(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(), 0.0f, ExitxPos, ExityPos, imgRadius) && buttonDelay >= 0.25)
                {
                    StateManager.Instance.ChangeState("Mainmenu");
                }
                buttonDelay = 0;
            }
        }
        else
        {
            Paused = false;
        }
    }

    @Override
    public void Render(Canvas _canvas)
    {
        float imgRadius = ScaledbmpP.getHeight() * 0.5f;
        if (TouchManager.Instance.HasTouch() && Collision.SphereToSphere(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(), 0.0f, PausexPos, PauseyPos, imgRadius))
        {
            _canvas.drawBitmap(ScaledbmpP, PausexPos - ScaledbmpP.getWidth() * 0.5f, PauseyPos - ScaledbmpP.getHeight() * 0.5f, null);
        }
        else
        {
            _canvas.drawBitmap(ScaledbmpUP, PausexPos - ScaledbmpUP.getWidth() * 0.5f, PauseyPos - ScaledbmpUP.getHeight() * 0.5f, null);
        }

        _canvas.drawBitmap(ScaledbmpExit, ExitxPos - ScaledbmpExit.getWidth() * 0.5f, ExityPos - ScaledbmpExit.getHeight() * 0.5f, null);

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
    public EntityBase.ENTITY_TYPE GetEntityType(){ return EntityBase.ENTITY_TYPE.ENT_PAUSE;}

    public static PauseButtonEntity Create()
    {
        PauseButtonEntity result = new PauseButtonEntity();
        EntityManager.Instance.AddEntity(result, EntityBase.ENTITY_TYPE.ENT_PAUSE);
        return result;
    }
}

/*
Under a new entity,
 Using paint to draw rectangle for health bar or progress bar:

 Under Render(Canvas _canvas),
 paint.setColor (Color.BLUE);
 paint.setStyle (Paint.Style.FILL);

 _canvas.drawRect(ScreenWidth/20 + 8, ScreenHeight/25, ScreenWidth/20 + points, 2 * ScreenHeight/25 -5, paint);  )

_canvas.drawRect()....

Fill and Fill and stroke (Paint.Style.STROKE)

-------------------------

Under each Entity

  @Override
    public void Update(float _dt) {

        if (GameSystem.Instance.GetIsPaused())
            return; <--------------------------------------------- Add this
        // Week 8
        spritesmurf.Update(_dt);
*/
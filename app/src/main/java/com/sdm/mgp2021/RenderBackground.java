package com.sdm.mgp2021;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.text.method.Touch;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceView;

import androidx.core.content.ContextCompat;

public class RenderBackground implements EntityBase
{
    private boolean isDone = false;
    private Bitmap bmp = null;

    private Bitmap Ship = null;

    // For use to scale the background w respect to the screen size
    private Bitmap scaledbmp = null;
    int ScreenWidth, ScreenHeight;

    private float xPos, yPos, offset;
    private SurfaceView view = null;

    int[] bg_images = new int[]{R.drawable.scrollbg, R.drawable.scrollbg2, R.drawable.scrollbg3};
    int bg_img_index = 0;

    float zoneTime = 10.0f;

    // Check if anything that we want to do with the entity is done. (use for pause)
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
    public void Init(SurfaceView _view)
    {
        view = _view;

        bmp = BitmapFactory.decodeResource(_view.getResources(), bg_images[bg_img_index]);

        // Find the surfaceview size or the screen size.
        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        ScreenWidth = metrics.widthPixels;
        ScreenHeight = metrics.heightPixels;

        scaledbmp = Bitmap.createScaledBitmap(bmp, ScreenWidth, ScreenHeight, true);

        Ship = BitmapFactory.decodeResource(_view.getResources(), R.drawable.ship2_1);
        Ship = ResourceManager.Instance.GetBitmap(R.drawable.ship2_1);
    }

    @Override
    public void Update(float _dt)
    {
        if (GameSystem.Instance.GetIsPaused())
            return;

        xPos -= _dt * 500; // deals w the speed of moving the screen

        if (xPos < -ScreenWidth)
        {
            xPos = 0;
        }

        zoneTime -= _dt;
        if (zoneTime <= 0.0f)
        {
            bg_img_index++;

            if (bg_img_index >= bg_images.length)
            {
                bg_img_index = 0;
            }

            bmp = BitmapFactory.decodeResource(view.getResources(), bg_images[bg_img_index]);
            scaledbmp = Bitmap.createScaledBitmap(bmp, ScreenWidth, ScreenHeight, true);

            zoneTime = 10.0f;
        }

        //Log.d("tag", Integer.toString(GetBackgroundIndex()));
    }

    @Override
    public void Render(Canvas _canvas)
    {

        _canvas.drawBitmap(scaledbmp, xPos, yPos, null);  // 1st image
        _canvas.drawBitmap(scaledbmp, xPos + ScreenWidth, yPos, null); // 2nd image

        Matrix transform = new Matrix();

        //_canvas.drawBitmap(Ship, transform, null);

        transform.postTranslate (400, 300);
        transform.postRotate(30.f);
        //transform.postScale (1, 1);

        //_canvas.drawBitmap(Ship, transform, null);


    }

    @Override
    public boolean IsInit()
    {
        return bmp != null;
    }

    @Override
    public int GetRenderLayer()
    {
        return LayerConstants.BACKGROUND_LAYER;
    }

    @Override
    public void SetRenderLayer(int _newLayer)
    {
        return;
    }

    public static RenderBackground Create()
    {
        RenderBackground result = new RenderBackground();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_DEFAULT);
        return result;
    }

    public int GetBackgroundIndex()
    {
        return bg_img_index;
    }

    @Override
    public ENTITY_TYPE GetEntityType()
    {
        return ENTITY_TYPE.ENT_DEFAULT;
    }
}
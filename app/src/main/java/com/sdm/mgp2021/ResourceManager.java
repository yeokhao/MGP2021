package com.sdm.mgp2021;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.SurfaceView;

import java.util.HashMap;

public class ResourceManager
{
    public final static ResourceManager Instance = new ResourceManager();

    private Resources res = null;
    // bmp = BitmapFactory.decodeResource(_view.getResources(), R.drawable.gamescene)
    // Means we need to retrieve the information from the _view, which is our surfaceview, and our image to be loaded on the view.

    // We will use a Hash Map
    private HashMap<Integer, Bitmap> resMap = new HashMap<Integer, Bitmap>();

    private ResourceManager()
    {

    }

    public void Init(SurfaceView _view)
    {
        // _view.getResources() gets all textures within the current screen
        res = _view.getResources();
    }

    public Bitmap GetBitmap(int _id)
    {
        if (resMap.containsKey(_id)) // Use by Key
        {
            return resMap.get(_id);
        }

        // This allow the images to be loaded.
        // === Bitmap bmp
        // Every image used there is always an ID tied to it.
        // If image is null --> program wil crash
        // Image size to big will also crash the program
        Bitmap results = BitmapFactory.decodeResource(res, _id);
        resMap.put(_id, results);
        return results;
    }
}

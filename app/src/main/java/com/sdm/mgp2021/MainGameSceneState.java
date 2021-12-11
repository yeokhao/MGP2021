package com.sdm.mgp2021;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceView;

// Created by TanSiewLan2021

public class MainGameSceneState implements StateBase {
    private float timer = 0.0f;

    @Override
    public String GetName()
    {
        return "MainGame";
    }

    @Override
    public void OnEnter(SurfaceView _view)
    {
        RenderBackground.Create(); // Background is an entity
        // Example to include another Renderview for Pause Button

        RenderTextEntity.Create();

        Ship.Create();
        ObstacleEntity.Create();

        PauseButtonEntity.Create();
    }

    @Override
    public void Update(float _dt) {

        if (GameSystem.Instance.GetIsPaused())
        {
            return;
        }

        EntityManager.Instance.Update(_dt);

        if (TouchManager.Instance.IsDown()) {
			
            //Example of touch on screen in the main game to trigger back to Main menu
            //StateManager.Instance.ChangeState("Mainmenu");
        }
    }

    @Override
    public void Render(Canvas _canvas)
    {
        EntityManager.Instance.Render(_canvas);

    }

    @Override
    public void OnExit() {
        EntityManager.Instance.Clean();
        GamePage.Instance.finish();
    }
}




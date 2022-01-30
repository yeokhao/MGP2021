package com.sdm.mgp2021;

// Created by TanSiewLan2021
// Create a GamePage is an activity class used to hold the GameView which will have a surfaceview

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class GamePage extends Activity
{

    public static GamePage Instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //To make fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE); // Hide titlebar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);  // Hide topbar

        //getSupportActionBar().hide();

        Instance = this;

        setContentView(new GameView(this)); // Surfaceview = GameView
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        // WE are hijacking the touch event into our own system
        int x = (int) event.getX();
        int y = (int) event.getY();

        TouchManager.Instance.Update(x, y, event.getAction());

        return true;
    }
    public void setToPause()
    {
        Intent intent = new Intent();
        intent.setClass(this, PauseMenuState.class);
        StateManager.Instance.ChangeState("PauseMenuState"); // Default is like a loading page
    }

    public void setToGame()
    {
        Intent intent = new Intent();
        intent.setClass(this, MainGameSceneState.class);
        StateManager.Instance.ChangeState("MainGameSceneState"); // Default is like a loading page
    }
    public void SetToEnd() {
        //String fuck = null;
        //Log.d(fuck, "fuck");
        StateManager.Instance.ChangeState("Endscreen");
        Intent intent = new Intent();
        intent.setClass(this, Endscreen.class);
        GamePage.Instance.startActivity(intent);
    }
}


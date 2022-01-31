package com.sdm.mgp2021;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class Gamemode extends Activity implements View.OnClickListener, StateBase // Using StateBase class
{
    // Define buttons. We have 2 buttons (Start, Back)
    private Button btn_endless;
    private Button btn_ending;
    private Button btn_return;
    public static boolean endless;

    @Override
    protected void onCreate (Bundle saveInstanceState){
        super.onCreate(saveInstanceState);

        //Hide Title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Hide Top Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.gamemode);

        btn_endless = (Button)findViewById(R.id.btn_endless); // noice
        btn_endless.setOnClickListener(this);  // Set Listener to this button --> endless mode

        btn_return = (Button)findViewById(R.id.btn_return);
        btn_return.setOnClickListener(this);  // Set Listener to this button --> Back Button

        btn_ending = (Button)findViewById(R.id.btn_Ending);
        btn_ending.setOnClickListener(this);  // Set Listener to this button --> 3 lives mode

        StateManager.Instance.AddState(new Gamemode());
    }

    @Override
    // Invoke a callback event in the view
    public void onClick(View v)
    {
        //Intent = action to be performed
        //Intent is an object provides runtime binding
        //You have to new an instance of this object to use it
        //
        //Because we need to check if start or back button is clicked/ touched on the screen, then after
        //what do we want to do.
        //If start button is clicked, we go to Splash page.
        //If back button is clicked, we go to main menu.

        //AudioManager.Instance.PlayAudio(R.raw.select, 0.9f);

        Intent intent = new Intent();

        if (v == btn_endless)
        {
            //intent -> to set to another class which is another page or screen to be launch.
            //Equal to change screen
            endless = true;
            intent.setClass(this, GamePage.class);
            StateManager.Instance.ChangeState("MainGame"); // Default is like a loading page
        }
        else if (v == btn_return)
        {
            intent.setClass(this, Mainmenu.class);
        }
        else if (v == btn_ending)
        {
            endless = false;
            intent.setClass(this, GamePage.class);
            StateManager.Instance.ChangeState("MainGame");
        }

        startActivity(intent);
    }

    @Override
    public void Render(Canvas _canvas)
    {
    }

    @Override
    public void OnEnter(SurfaceView _view)
    {
    }

    @Override
    public void OnExit()
    {
    }

    @Override
    public void Update(float _dt)
    {
    }

    @Override
    public String GetName()
    {
        return "Gamemode";
    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }
}


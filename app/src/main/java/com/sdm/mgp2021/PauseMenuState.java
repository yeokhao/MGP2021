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

public class PauseMenuState extends Activity implements View.OnClickListener, StateBase{

    // Define buttons. We have 2 buttons (Start, main menu)
    private Button btn_start;
    private Button btn_mainMenu;
    private Button btn_options;

    @Override
    protected void onCreate (Bundle saveInstanceState){
        super.onCreate(saveInstanceState);

        //Hide Title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Hide Top Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.pause);

        btn_start = (Button)findViewById(R.id.btn_start); // noice
        btn_start.setOnClickListener(this);  // Set Listener to this button --> Start Button

        btn_mainMenu = (Button)findViewById(R.id.btn_mainMenu);
        btn_mainMenu.setOnClickListener(this);  // Set Listener to this button --> Back Button

        StateManager.Instance.AddState(new PauseMenuState());
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

        Intent intent = new Intent();

        if (v == btn_start)
        {
            //intent -> to set to another class which is another page or screen to be launch.
            //Equal to change screen
            intent.setClass(this, GamePage.class);
            StateManager.Instance.ChangeState("MainGame"); // Default is like a loading page
        }
        else if (v == btn_mainMenu)
        {
            intent.setClass(this, Mainmenu.class );
        }
        //else if (v == btn_options)
        //{
        //    intent.setClass(this, Options.class);
        //    StateManager.Instance.ChangeState("Options");
        //}

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
        return "PauseMenu";
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

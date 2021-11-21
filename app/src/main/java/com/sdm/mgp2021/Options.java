package com.sdm.mgp2021;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class Options extends Activity implements OnClickListener, StateBase
{
    private Button btn_options_back;

    @Override
    protected void onCreate (Bundle saveInstanceState){
        super.onCreate(saveInstanceState);

        //Hide Title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Hide Top Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.options);

        btn_options_back = (Button)findViewById(R.id.btn_options_back);
        btn_options_back.setOnClickListener(this);  // Set Listener to this button --> Back Button
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

        if (v == btn_options_back)
        {
            intent.setClass(this, Mainmenu.class);
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
        return "Options";
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

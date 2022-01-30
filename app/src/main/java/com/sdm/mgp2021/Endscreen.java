package com.sdm.mgp2021;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class Endscreen extends Activity implements OnClickListener, StateBase
{
    private Button btn_retry;
    private Button btn_exit;

    @Override
    protected void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.endscreen);

        btn_retry = (Button)findViewById(R.id.btn_retry);
        btn_retry.setOnClickListener(this);

        btn_exit = (Button)findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        Intent intent = new Intent();

        if (v == btn_retry)
        {
            intent.setClass(this, GamePage.class);
            StateManager.Instance.ChangeState("MainGame");
        }
        else if (v == btn_exit)
        {
            intent.setClass(this, Mainmenu.class);
            StateManager.Instance.ChangeState("Mainmenu");
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
        return "Endscreen";
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

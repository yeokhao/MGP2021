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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.Arrays;

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

        int Leaderboard[] = new int[5];
        Leaderboard[0] = GameSystem.Instance.GetIntFromSave("Leaderboard1");
        Leaderboard[1] = GameSystem.Instance.GetIntFromSave("Leaderboard2");
        Leaderboard[2] = GameSystem.Instance.GetIntFromSave("Leaderboard3");
        Leaderboard[3] = GameSystem.Instance.GetIntFromSave("Leaderboard4");
        Leaderboard[4] = GameSystem.Instance.GetIntFromSave("Leaderboard5");
        Integer[] Scoreboard= Arrays.stream(Leaderboard).boxed().toArray(Integer[]::new);
        ListView leaderboardList = findViewById(R.id.leaderboard);
        ArrayAdapter<Integer> arr = new ArrayAdapter<Integer>(this,R.layout.support_simple_spinner_dropdown_item,Scoreboard);

        leaderboardList.setAdapter(arr);
    }

    @Override
    public void onClick(View v)
    {
        Intent intent = new Intent();

        if (v == btn_retry)
        {
            intent.setClass(this, Gamemode.class);
            StateManager.Instance.ChangeState("Gamemode");
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

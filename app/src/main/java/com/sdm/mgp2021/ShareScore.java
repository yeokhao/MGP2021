package com.sdm.mgp2021;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

import com.facebook.share.Share;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;

import org.w3c.dom.Text;

import java.util.Arrays;

public class ShareScore extends Activity implements OnClickListener, StateBase
{
    private Button btn_sharescore;

    private CallbackManager callbackManager;
    private LoginManager loginManager;

    private static final String EMAIL = "email";

    private LoginButton btn_fb_login;

    private ShareDialog shareDialog;
    private int PICK_IMAGE_REQUEST = 1;

    // ShareDialog shareDialog
    ProfilePictureView profile_pic;

    private Button btn_score_back;

    int highscore;

    @Override
    protected void onCreate (Bundle saveInstanceState){
        super.onCreate(saveInstanceState);

        //Hide Title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Hide Top Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        FacebookSdk.setApplicationId("471296094664281");

        if (!FacebookSdk.isInitialized())
        {
            FacebookSdk.sdkInitialize(getApplicationContext());
        }

        if (BuildConfig.DEBUG)
        {
            FacebookSdk.setIsDebugEnabled(true);
            FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        }

        setContentView(R.layout.sharescore);

        TextView scoreText;
        scoreText = (TextView) findViewById(R.id.scoreText);

        Typeface myfont;
        myfont = Typeface.createFromAsset(getAssets(), "fonts/Gemcut.otf");

        highscore = 10;

        scoreText.setTypeface(myfont);
        scoreText.setText(String.format("Your score is " + highscore));

        btn_score_back = (Button)findViewById(R.id.btn_options_back);
        btn_score_back.setOnClickListener(this);  // Set Listener to this button --> Back Button

        btn_sharescore = (Button) findViewById(R.id.btn_sharescore);
        btn_sharescore.setOnClickListener(this);

        btn_fb_login = (LoginButton) findViewById(R.id.fb_login_button);
        btn_fb_login.setReadPermissions(Arrays.asList(EMAIL));
        //LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));

        callbackManager = CallbackManager.Factory.create();

        loginManager = LoginManager.getInstance();

        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>()
        {
            @Override
            public void onSuccess(LoginResult loginResult)
            {
                profile_pic.setProfileId(Profile.getCurrentProfile().getId());

                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                loginResult.getAccessToken().getUserId();
            }

            @Override
            public void onCancel()
            {
                System.out.println("Login attempt cancelled.");
            }

            @Override
            public void onError(FacebookException error)
            {
                System.out.println("Login attempt failed.");
            }
        });
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

        if (v == btn_sharescore)
        {
            ShareScore();
        }
        else if (v == btn_score_back)
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

    public void ShareScore()
    {
        Bitmap image = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        if (ShareDialog.canShow(SharePhotoContent.class))
        {
            System.out.println("photoShown");
            SharePhoto photo = new SharePhoto.Builder()
                    .setBitmap(image)
                    .setCaption("Thank you for playing MGP2021. Your final score is " + highscore)
                    .build();

            SharePhotoContent content = new SharePhotoContent.Builder()
                    .addPhoto(photo)
                    .build();

            shareDialog.show(content);
        }
    }

    @Override
    public String GetName()
    {
        return "ShareScore";
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
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

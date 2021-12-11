package com.sdm.mgp2021;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.text.method.Touch;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

public class JumpButton implements EntityBase{
        private Bitmap bmpJ = null;

        private Bitmap ScaledbmpJ = null;

        private float xPos = 0;
        private float yPos = 0;
        private float screenHeight = 0;
        private boolean isDone = false;
        private boolean isInit = false;
        private boolean Paused = false;
        int ScreenWidth, ScreenHeight;

        private float buttonDelay = 0;
        @Override
        public boolean IsDone() {
            return isDone;
        }

        @Override
        public void SetIsDone(boolean _isDone) {
            isDone = _isDone;
        }

        @Override
        public void Init(SurfaceView _view) {

            // New method using our own resource manager : Returns pre-loaded one if exists
            //bmp = ResourceManager.Instance.GetBitmap(R.drawable.star);
            bmpJ = ResourceManager.Instance.GetBitmap(R.drawable.obstacle);

            DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
            ScreenWidth = metrics.widthPixels;
            ScreenHeight = metrics.heightPixels;

            ScaledbmpJ = Bitmap.createScaledBitmap(bmpJ,(int)(ScreenWidth)/12,(int)(ScreenWidth)/7,true);

            xPos = 20;
            yPos = 800;
            isInit = true;
        }

        @Override
        public void Update(float _dt)
        {
            buttonDelay += _dt;

            if (TouchManager.Instance.HasTouch())
            {
                if (TouchManager.Instance.IsDown() && !Paused)
                {
                    // Check collision of button here
                    float imgRadius = ScaledbmpJ.getHeight() * 0.5f;
                    if (Collision.SphereToSphere(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(), 0.0f, xPos,yPos, imgRadius) && buttonDelay >= 0.25)
                    {
                        Ship.Instance.SetPosX(20);
                        //GameSystem.Instance.SetIsPaused(!GameSystem.Instance.GetIsPaused());
                    }
                    buttonDelay = 0;
                }
            }
            else
            {
                Paused = false;
            }
        }

        @Override
        public void Render(Canvas _canvas) {
            if (Paused == false)
                _canvas.drawBitmap(ScaledbmpJ,xPos = ScaledbmpJ.getWidth(),yPos-ScaledbmpJ.getHeight() *0.5f,null);
            else
                _canvas.drawBitmap(ScaledbmpJ,xPos = ScaledbmpJ.getWidth(),yPos-ScaledbmpJ.getHeight() *0.5f,null);

        }

        @Override
        public boolean IsInit() {

            return isInit;
        }

        @Override
        public int GetRenderLayer() {
            return LayerConstants.JUMPBUTTON_LAYER;
        }

        @Override
        public void SetRenderLayer(int _newLayer) {
            return;
        }

        @Override
        public ENTITY_TYPE GetEntityType(){ return ENTITY_TYPE.ENT_DEFAULT;}

        public static JumpButton Create()
        {
            JumpButton result = new JumpButton();
            EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_DEFAULT);
            return result;
        }

}

package com.TeamT3N.fukiosk.ui.Profile.ProfileOptions;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

import com.TeamT3N.fukiosk.R;

public class Option_printouts extends AppCompatActivity {

    private ScaleGestureDetector mScaleGestureDEtector;
    private float mScaleFactor = 1.0f;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_printouts);
        mImageView=(ImageView)findViewById(R.id.assesment);
        mScaleGestureDEtector = new ScaleGestureDetector(this, new ScaleListener());

        }
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        mScaleGestureDEtector.onTouchEvent(motionEvent);
        return true;

    }
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener{
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector){
            mScaleFactor *= scaleGestureDetector.getScaleFactor();
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));
            mImageView.setScaleX(mScaleFactor);
            mImageView.setScaleY(mScaleFactor);

            return true;

        }
    }

}

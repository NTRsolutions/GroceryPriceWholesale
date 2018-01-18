package com.example.admin.grocerypricewholesale;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    Button btn;
    Animation fromleft,fromright,scale,alpha;
    ActionBar actionBar;
    TextView tvname,tvdes;
    ImageView ivlogo;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        actionBar=getSupportActionBar();
        actionBar.hide();

        Window window=getWindow();
        window.setStatusBarColor(Color.parseColor(String.valueOf("#FFFFFF")));

        tvname=(TextView)findViewById(R.id.tvname);
        ivlogo=(ImageView)findViewById(R.id.ivlogo);
        tvdes=(TextView)findViewById(R.id.tvdes);

        scale=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.scale);
        fromright=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.frombottom);
        fromleft=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.lefttoright);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ivlogo.setImageResource(R.drawable.mylogo);
                ivlogo.setAnimation(fromright);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ivlogo.setAnimation(fromleft);
                    }
                },500);
            }
        },800);


        alpha=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.alpha);
        tvname.setAnimation(alpha);
        tvdes.setAnimation(alpha);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        },1500);

    }
}

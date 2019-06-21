package com.mohammadasad.flyingfakhta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Time for Splash Activity
        Thread thread = new Thread(){
            @Override
            public void run() {
                //Time alotted
                try
                {
                    sleep(5000);
                }
                catch (Exception e)
                {
                 e.printStackTrace();
                }
                // After Splash where it went
                finally
                {
                    Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                }
            }
        };
        thread.start();


    }

    @Override
    protected void onPause() {
        super.onPause();

        finish();
    }
}

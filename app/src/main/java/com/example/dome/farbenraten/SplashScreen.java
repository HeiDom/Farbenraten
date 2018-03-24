package com.example.dome.farbenraten;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Dome on 21.05.2016.
 */
public class SplashScreen extends Activity
{
    public static boolean appStartedCorrectly = false;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        appStartedCorrectly = true;
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    protected void onResume()
    {
        super.onResume();
        finish();
    }
}

package com.example.dome.farbenraten.timer;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;

public class Timer extends Service{

    public IBinder onBind(Intent intent)
    {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId)
    {
        /*
        public void countDownGame(int gameTimer)
        {
            timer = new CountDownTimer(gameTimer, 1000)
            {
                public void onTick(long millisUntilFinished)
                {
                    gameIsRunning = true;
                    counterStillRuning = true;
                    countDown = millisUntilFinished / 1000;

                    if(countDown <= 60)
                    {
                        //txtNewTimer.setText("Timer: " + countDown + "sek");
                    }
                    txtGeneratedColor.setText("Timer: " + countDown + "sek");
                }

                public void onFinish()
                {
                    gameIsRunning = false;
                    counterStillRuning = true;
                    //txtNewTimer.setText("Timer: ");

                    btnColor1.setText("Spiel");
                    btnColor0.setText("vorbei");
                    btnColor2.setText("Starte");
                    btnColor3.setText("Spiel");

                    txtGeneratedColor.setText("Sie Haben " + counterRightAnswer + " Richtig und " + counterWrongAnswer + " Falsch");
                }
            }.start();
        }
        */
        return 1;
    }
}

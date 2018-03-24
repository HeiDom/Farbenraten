package com.example.dome.farbenraten;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.graphics.Color;
import java.util.Random;

public class MainActivity extends Activity
{
    //Konstanten für die wiederherstellung der App, falls sie abgestürzt ist
    private final String BUTTON_VISIBILITY_STATE = "visibilityState";
    private final String BUTTON_STARTGAME_Y = "startGameY";
    private final String BUTTON_STARTGAME_X = "startGameX";
    private final String LAYOUT_MOVEBUTTONS_Y = "moveButonsY";
    private final String LAYOUT_MOVEBUTTONS_X = "moveButonsX";
    private final String BUTTON_STARTGAME_WIDTH = "startGameWidth";
    private final String BUTTON_STARTGAME = "startGame";
    private final String BUTTON_0_TEXT = "button0Text";
    private final String BUTTON_1_TEXT = "button1Text";
    private final String BUTTON_2_TEXT = "button2Text";
    private final String BUTTON_3_TEXT = "button3Text";
    private final String BUTTON_0_COLOR = "button0Color";
    private final String BUTTON_1_COLOR = "button1Color";
    private final String BUTTON_2_COLOR = "button2Color";
    private final String BUTTON_3_COLOR = "button3Color";
    private final String TXT_VIEW_TEXT = "txtViewText";
    private final String TXT_VIEW_COLOR = "txtViewColor";
    private final String RIGHT_BUTTON = "richtigerButton";
    private final String COUNTDOWN = "countDownTimer";

    //Globale Objekte
    private Button btnack;
    private Button btnStartGame;
    private Button btnColor0;
    private Button btnColor1;
    private Button btnColor2;
    private Button btnColor3;
    private TextView txtGeneratedColor, txtFarbenraten, txtRichtigeAntworten, txtFalscheAntworten, txtNewTimer;
    private TextView txtCountDownTimer;
    private CountDownTimer timer;
    private CountDownTimer countDownTImer;

    //Layouts
    private LinearLayout layoutButtonStartGame;
    private LinearLayout layoutMoveButtons;
    private LinearLayout layoutTxtCountDown;

    //Globale Variablen
    private long countDown;
    private int counterWrongAnswer = 0;
    private int counterRightAnswer = 0;
    private int rightButton;
    private boolean gameIsRunning = true;
    private boolean counterStillRuning;
    private boolean counterTillStart;
    private boolean notAllowedToPressButton = true;

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);

        outState.putLong(COUNTDOWN, countDown);
        outState.putInt(RIGHT_BUTTON, rightButton);

        outState.putInt(BUTTON_STARTGAME_WIDTH, btnStartGame.getWidth());
        outState.putBoolean(BUTTON_VISIBILITY_STATE, btnColor0.isShown());
        outState.putFloat(BUTTON_STARTGAME_X, layoutButtonStartGame.getX());
        outState.putFloat(LAYOUT_MOVEBUTTONS_Y, layoutMoveButtons.getY());
        outState.putFloat(LAYOUT_MOVEBUTTONS_X, layoutMoveButtons.getX());

        outState.putCharSequence(BUTTON_STARTGAME, btnStartGame.getText());
        outState.putCharSequence(BUTTON_0_TEXT, btnColor0.getText());
        outState.putCharSequence(BUTTON_1_TEXT, btnColor1.getText());
        outState.putCharSequence(BUTTON_2_TEXT, btnColor2.getText());
        outState.putCharSequence(BUTTON_3_TEXT, btnColor3.getText());
        outState.putCharSequence(TXT_VIEW_TEXT, txtGeneratedColor.getText());

        outState.putInt(BUTTON_0_COLOR, btnColor0.getCurrentTextColor());
        outState.putInt(BUTTON_1_COLOR, btnColor1.getCurrentTextColor());
        outState.putInt(BUTTON_2_COLOR, btnColor2.getCurrentTextColor());
        outState.putInt(BUTTON_3_COLOR, btnColor3.getCurrentTextColor());
        outState.putInt(TXT_VIEW_COLOR, txtGeneratedColor.getCurrentTextColor());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int intCountDown;

        if(savedInstanceState.getBoolean(BUTTON_VISIBILITY_STATE) == true)
        {
            rightButton = savedInstanceState.getInt(RIGHT_BUTTON);
            countDown = savedInstanceState.getInt(COUNTDOWN);
            intCountDown = (int)countDown;

            btnColor0.isClickable();
            btnColor1.isClickable();
            btnColor2.isClickable();
            btnColor3.isClickable();
            btnColor0.setVisibility(View.VISIBLE);
            btnColor1.setVisibility(View.VISIBLE);
            btnColor2.setVisibility(View.VISIBLE);
            btnColor3.setVisibility(View.VISIBLE);

            notAllowedToPressButton = false;
        }

        btnStartGame.setText(savedInstanceState.getCharSequence(BUTTON_STARTGAME));
        btnColor0.setText(savedInstanceState.getCharSequence(BUTTON_0_TEXT));
        btnColor1.setText(savedInstanceState.getCharSequence(BUTTON_1_TEXT));
        btnColor2.setText(savedInstanceState.getCharSequence(BUTTON_2_TEXT));
        btnColor3.setText(savedInstanceState.getCharSequence(BUTTON_3_TEXT));
        txtGeneratedColor.setText(savedInstanceState.getCharSequence(TXT_VIEW_TEXT));

        btnColor0.setTextColor(savedInstanceState.getInt(BUTTON_0_COLOR));
        btnColor1.setTextColor(savedInstanceState.getInt(BUTTON_1_COLOR));
        btnColor2.setTextColor(savedInstanceState.getInt(BUTTON_2_COLOR));
        btnColor3.setTextColor(savedInstanceState.getInt(BUTTON_3_COLOR));
        txtGeneratedColor.setTextColor(savedInstanceState.getInt(TXT_VIEW_COLOR));
    }

    //region Super.Class

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Setzte das Layout für die Activity
        setContentView(R.layout.activity_main);

        //Initialisieren der ganzen Widgets
        btnColor0 = (Button) findViewById(R.id.btnColor1);
        btnColor1 = (Button) findViewById(R.id.Button01);
        btnColor2 = (Button) findViewById(R.id.Button02);
        btnColor3 = (Button) findViewById(R.id.Button03);
        btnStartGame = (Button) findViewById(R.id.btnStartGame);
        txtGeneratedColor = (TextView) findViewById(R.id.txtGeneratedColor);
        txtCountDownTimer = (TextView) findViewById(R.id.txtCountDownTimer);
        layoutMoveButtons = (LinearLayout) findViewById(R.id.layoutMoveButtons);
        layoutTxtCountDown = (LinearLayout) findViewById(R.id.layoutTxtCountDown);
        layoutButtonStartGame = (LinearLayout) findViewById(R.id.linearLayoutStartGame);

        //OnClickListener setzen
        btnStartGame.setOnClickListener(clickHandler);
        btnColor0.setOnClickListener(clickHandler);
        btnColor1.setOnClickListener(clickHandler);
        btnColor2.setOnClickListener(clickHandler);
        btnColor3.setOnClickListener(clickHandler);

        if(!SplashScreen.appStartedCorrectly)
        {
            getAnimation();
        }
    }

    protected void onResume()
    {
        super.onResume();

        //getAnimation();
    }

    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
    }

    //endregion

    View.OnClickListener clickHandler = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            // TODO Auto-generated method stub
            switch(v.getId())
            {
                case R.id.btnStartGame:
                    startGame();
                    break;
                case R.id.btnColor1:
                    if(!notAllowedToPressButton)
                    {
                        evaluateAnswer(0);
                    }
                    break;
                case R.id.Button01:
                    if(!notAllowedToPressButton)
                    {
                        evaluateAnswer(1);
                    }
                    break;
                case R.id.Button02:
                    if(!notAllowedToPressButton)
                    {
                        evaluateAnswer(2);
                    }
                    break;
                case R.id.Button03:
                    if(!notAllowedToPressButton)
                    {
                        evaluateAnswer(3);
                    }
                    break;
            }
        }
    };

    private static class ButtonAnimatorHelper {

        final Button mButton;

        public ButtonAnimatorHelper(final Button button) {
            mButton = button;
        }

        public void setMarginLeft(final int margin) {
            final ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mButton.getLayoutParams();

            params.leftMargin = margin;
            params.rightMargin = margin;

            mButton.setLayoutParams(params);
        }
    }

    private void getAnimation()
    {
        int orientation = this.getResources().getConfiguration().orientation;
        double getMoveHeight = layoutMoveButtons.getHeight() / 2;
        float moveButtonsHeigt = ((float) getMoveHeight);

        int getButtonWidth = btnStartGame.getWidth();
        int newButtonWidth = btnStartGame.getWidth() / 2;

        final ObjectAnimator horizontalAnimator = ObjectAnimator.ofInt(new ButtonAnimatorHelper(btnStartGame), "marginLeft", newButtonWidth, newButtonWidth);

        if(orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            getMoveHeight = layoutMoveButtons.getHeight() / 1.2;
            moveButtonsHeigt = ((float) getMoveHeight);
            double getMoveHeightButtonStartGame = layoutButtonStartGame.getHeight() * 1.6;
            float moveStartGameButton = ((float) getMoveHeightButtonStartGame);

            layoutButtonStartGame.animate()
                .translationY(moveStartGameButton)
                .setDuration(300);

            layoutMoveButtons.animate()
                    .translationY(-moveButtonsHeigt)
                    .setDuration(0);

            horizontalAnimator
                    .setDuration(300)
                    .start();
        }

        if(orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            double getMoveHeightButtonStartGame = layoutButtonStartGame.getHeight() * 2.2;
            float moveStartGameButton = ((float) getMoveHeightButtonStartGame);

            layoutButtonStartGame.animate()
                    .translationY(moveStartGameButton)
                    .setDuration(300);

            layoutMoveButtons.animate()
                    .translationY(-moveButtonsHeigt)
                    .setDuration(0);

            horizontalAnimator
                    .setDuration(5000)
                    .start();
        }
    }

    public void setButtonVisibility(int visibility)
    {
        btnColor0.setVisibility(visibility);
        btnColor1.setVisibility(visibility);
        btnColor2.setVisibility(visibility);
        btnColor3.setVisibility(visibility);
    }

    public void startGame()
    {
        String play = "Spielen"; //neu starten
        String btnText = btnStartGame.getText().toString();
        counterWrongAnswer = 0;
        counterRightAnswer = 0;

        if(counterStillRuning == true)
        {
            timer.cancel();
        }

        if(counterTillStart == true)
        {
            countDownTImer.cancel();
        }

        if(btnText != play)
        {
            getAnimation();
            countDown(4000);
        }
        else
        {
            countDown(4000);
        }

        btnColor0.setVisibility(View.INVISIBLE);
        btnColor1.setVisibility(View.INVISIBLE);
        btnColor2.setVisibility(View.INVISIBLE);
        btnColor3.setVisibility(View.INVISIBLE);

        btnStartGame.setText(play);
        txtGeneratedColor.setText("");
        txtCountDownTimer.setText("");
    }

    public void countDown(int timer)
    {
        countDownTImer = new CountDownTimer(timer, 1000)
        {
            public void onTick(long millisUntilFinished)
            {
                long countDown = millisUntilFinished / 1000;

                counterTillStart = true;
                notAllowedToPressButton = true;

                if(countDown < 4)
                {
                    txtCountDownTimer.setText("" + countDown);
                }
            }

            public void onFinish()
            {
                txtGeneratedColor.setText("");
                txtCountDownTimer.setText("");
                notAllowedToPressButton = false;
                counterTillStart = false;

                generateRandomColor();
                setButtonVisibility(0);
                countDownGame(61000);
            }
        }.start();
    }

    public void countDownGame(int gameTimer)
    {
        timer = new CountDownTimer(gameTimer, 1000)
        {
            public void onTick(long millisUntilFinished)
            {
                gameIsRunning = true;
                counterStillRuning = true;
                countDown = millisUntilFinished / 1000;
            }

            public void onFinish()
            {
                gameIsRunning = false;
                counterStillRuning = true;

                btnColor1.setText("Spiel");
                btnColor0.setText("vorbei");
                btnColor2.setText("Starte");
                btnColor3.setText("Spiel");

                txtGeneratedColor.setText("Sie Haben " + counterRightAnswer + " Richtig und " + counterWrongAnswer + " Falsch");
            }
        }.start();
    }

    public int randomInt(int randomIndex)
    {
        Random r = new Random();
        int randomTextInt = r.nextInt(randomIndex);

        return randomTextInt;
    }

    public void generateRandomColor()
    {
        String[] arrColorsText = new String[]{"Gr�n", "Rot", "Gelb", "Blau", "Schwarz", "Braun", "Grau", "Orange", "Pink", "Lila"};
        String[] arrColors = new String[]{"green", "red", "yellow", "blue", "black", "#795548", "#9e9e9e",  "#ff9800", "#e91e63", "#9c27b0"};
        String[] arrButtonColors = new String[] {"btn_black", "btn_red"};

        int flag = 5, i = 0, randomButton, intRandomColor1 = 0, intRandomColor2 = 0, intRandomColor3 = 0, intRandomColor4 = 0, intRandomColor5 = 0;
        int intRandomCorrectCollor, intRandomText1 = 0, intRandomText2 = 0, intRandomText3 = 0, intRandomText4 = 0, intRandomText5 = 0;
        int randomButtonColor1 = 0, randomButtonColor2 = 0,  randomButtonColor3 = 0, randomButtonColor4 = 0, randomButtonColor5 = 0;
        int intRandomButton1 = 0, intRandomButton2 = 0, intRandomButton3 = 0, intRandomButton4 = 0, intRandomButton5 = 0;
        int randomColorCounter = 0, randomTextCounter = 0, randomButtonCounter = 0;
        boolean generateTextColor = true;


        intRandomText1 = randomInt(10);
        randomButton = randomInt(4);
        txtGeneratedColor.setText(arrColorsText[intRandomText1]);

        while(generateTextColor)
        {
            intRandomColor1  = randomInt(10);

            if(intRandomColor1 != intRandomText1)
            {
                txtGeneratedColor.setTextColor(Color.parseColor(arrColors[intRandomColor1]));
                generateTextColor = false;
            }
        }

        switch(randomButton)
        {
            case 0:
                btnColor0.setTextColor(Color.parseColor(arrColors[intRandomText1]));
                flag = 0;
                break;
            case 1:
                btnColor1.setTextColor(Color.parseColor(arrColors[intRandomText1]));
                flag = 1;
                break;
            case 2:
                btnColor2.setTextColor(Color.parseColor(arrColors[intRandomText1]));
                flag = 2;
                break;
            case 3:
                btnColor3.setTextColor(Color.parseColor(arrColors[intRandomText1]));
                flag = 3;
                break;
        }

        do
        {
            switch(i)
            {
                case 0:
                    if (randomTextCounter == 0)
                    {
                        intRandomText2 = randomInt(8);
                    }

                    if(randomColorCounter == 0)
                    {
                        intRandomColor2 = randomInt(8);
                    }

                    if (randomButtonCounter == 0)
                    {
                        //randomButtonColor2 = randomInt(5);
                    }

                    //Generiert die Niedergeschriebene Farbe
                    if(intRandomColor2 != intRandomText2 && intRandomText1 != intRandomText2 && randomTextCounter == 0)
                    {
                        btnColor0.setText(arrColorsText[intRandomText2]);
                        randomTextCounter++;
                    }

                    //Generiert die Schriftfarbe
                    if(intRandomColor2 != intRandomText2 && intRandomText1 != intRandomColor2 && flag != 0 && randomColorCounter == 0)
                    {
                        btnColor0.setTextColor(Color.parseColor(arrColors[intRandomColor2]));
                        randomColorCounter++;
                    }

                    if(intRandomText1 != intRandomColor2 && flag == 0 && randomColorCounter == 0)
                    {
                        randomColorCounter++;
                        rightButton = 0;
                    }
                    if(randomTextCounter == 1 && randomColorCounter == 1) // && randomButtonCounter == 1
                    {
                        i++;
                    }
                    break;
                case 1:
                    if (randomTextCounter == 1)
                    {
                        intRandomText3 = randomInt(8);
                    }

                    if(randomColorCounter == 1)
                    {
                        intRandomColor3 = randomInt(8);
                    }

                    if(intRandomColor3 != intRandomText3 && intRandomText1 != intRandomText3 && intRandomText2 != intRandomText3 && randomTextCounter == 1)
                    {
                        btnColor1.setText(arrColorsText[intRandomText3]);
                        randomTextCounter++;
                    }

                    if(intRandomColor3 != intRandomText3 && intRandomText1 != intRandomColor3 && intRandomColor2 != intRandomColor3 && flag != 1 && randomColorCounter == 1)
                    {
                        btnColor1.setTextColor(Color.parseColor(arrColors[intRandomColor3]));
                        randomColorCounter++;
                    }

                    if(intRandomText1 != intRandomColor3 && intRandomColor2 != intRandomColor3 && flag == 1 && randomColorCounter == 1)
                    {
                        randomColorCounter++;
                        rightButton = 1;
                    }

                    if(randomTextCounter == 2 && randomColorCounter == 2) //&& randomButtonCounter == 2
                    {
                        i++;
                    }
                    break;
                case 2:
                    if (randomTextCounter == 2)
                    {
                        intRandomText4 = randomInt(8);
                    }

                    if(randomColorCounter == 2)
                    {
                        intRandomColor4 = randomInt(8);
                    }

                    if(intRandomColor4 != intRandomText4 && intRandomText1 != intRandomText4 && intRandomText2 != intRandomText4 && intRandomText3 != intRandomText4 && randomTextCounter == 2)
                    {
                        btnColor2.setText(arrColorsText[intRandomText4]);
                        randomTextCounter++;
                    }

                    if(intRandomColor4 != intRandomText4 && intRandomText1 != intRandomColor4 && intRandomColor2 != intRandomColor4 && intRandomColor3 != intRandomColor4 && flag != 2 && randomColorCounter == 2)
                    {
                        btnColor2.setTextColor(Color.parseColor(arrColors[intRandomColor4]));
                        randomColorCounter++;
                    }

                    if(intRandomText1 != intRandomColor4 && intRandomColor2 != intRandomColor4 && intRandomColor3 != intRandomColor4 && flag == 2 && randomColorCounter == 2)
                    {
                        randomColorCounter++;
                        rightButton = 2;
                    }

                    if(randomTextCounter == 3 && randomColorCounter == 3 ) // && randomButtonCounter == 3
                    {
                        i++;
                    }
                    break;
                case 3:
                    if (randomTextCounter == 3)
                    {
                        intRandomText5 = randomInt(8);
                    }

                    if(randomColorCounter == 3)
                    {
                        intRandomColor5 = randomInt(8);
                    }

                    if(intRandomColor5 != intRandomText5 && intRandomText1 != intRandomText5 && intRandomText2 != intRandomText5 && intRandomText3 != intRandomText5 && intRandomText4 != intRandomText5 && randomTextCounter == 3)
                    {
                        btnColor3.setText(arrColorsText[intRandomText5]);
                        randomTextCounter++;
                    }

                    if(intRandomColor5 != intRandomText5 && intRandomText1 != intRandomColor5 && intRandomColor2 != intRandomColor5 && intRandomColor3 != intRandomColor5 && intRandomColor4 != intRandomColor5 && flag != 3 && randomColorCounter == 3)
                    {
                        btnColor3.setTextColor(Color.parseColor(arrColors[intRandomColor5]));
                        randomColorCounter++;
                    }

                    if(intRandomText1 != intRandomColor5 && intRandomColor2 != intRandomColor5 && intRandomColor3 != intRandomColor5 && intRandomColor4 != intRandomColor5 && flag == 3 && randomColorCounter == 3)
                    {
                        randomColorCounter++;
                        rightButton = 3;
                    }
                    if(randomTextCounter == 4 && randomColorCounter == 4) // && randomButtonCounter == 4
                    {
                        i++;
                    }
                    break;
            }
        }
        while(i <= 3);
    }

    public void evaluateAnswer(int pressedButton) {
        if (gameIsRunning == true) {
            //pr�ft ob die Eeingabe richtig ist
            if (pressedButton == 0 && rightButton == 0) {
                counterRightAnswer++;
            }
            if (pressedButton == 1 && rightButton == 1) {
                counterRightAnswer++;
            }

            if (pressedButton == 2 && rightButton == 2) {
                counterRightAnswer++;
            }

            if (pressedButton == 3 && rightButton == 3) {
                counterRightAnswer++;
            }

            //Pr�ft ob Eingabe falsch ist
            if (pressedButton == 0 && rightButton != 0) {
                counterWrongAnswer++;
            }

            if (pressedButton == 1 && rightButton != 1) {
                counterWrongAnswer++;
            }

            if (pressedButton == 2 && rightButton != 2) {
                counterWrongAnswer++;
            }

            if (pressedButton == 3 && rightButton != 3) {
                counterWrongAnswer++;
            }

            generateRandomColor();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

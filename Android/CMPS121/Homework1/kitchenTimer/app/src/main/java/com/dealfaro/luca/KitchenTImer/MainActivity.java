package com.dealfaro.luca.KitchenTImer;
 /*
 this app acts as a basic Kitchen timer.
 Scoure code: git 'clone https://bitbucket.org/luca_de_alfaro/kitchen_timer.git'
    by: Luca de Alfaro

 app was modified for the purpose of homework 1
 changes made by Michael James Schmidt
  */

 /*
 Homework note:

 "What you have to do"
   -Below the START and STOP buttons, place three buttons in a row, The buttons should be labeled
    with the three most recent times the user has set.

  */
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static final private String LOG_TAG = "test2017app1";
    // variables
    View view;  // the current View
    private int seconds = 0; // Counter for the number of seconds.
    private int save1 = 0;  // variables to store the most used times
    private int save2 = 0;
    private int save3 = 0;
    private boolean buttonWraper1 = false;  // keeps track of witch button we save the a used time.
    private boolean buttonWraper2 = false;
    // Countdown timer.
    private CountDownTimer timer = null; // this cool thing that's used as are timer
    // One second.  We use Mickey Mouse time.
    private static final int ONE_SECOND_IN_MILLIS = 100;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) { // on start up, do this code
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = this.getWindow().getDecorView();     // set view to be this window
        view.setBackgroundResource(R.color.blue);    // change the background color
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onResume() {
        //Log.i(LOG_TAG, "OnResume");
        super.onResume();
        displayTime();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void onClickPlus(View v) {
        seconds += 60;
        displayTime();
    };

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void onClickMinus(View v) {
        //Log.i(LOG_TAG, "OnClickMinus");
        seconds = Math.max(0, seconds - 60);
        displayTime();
    };

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void onReset(View v) {
        //Log.i(LOG_TAG, "onReset");
        seconds = 0;
        cancelTimer();
        displayTime();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void onClickStart(View v) {
        startTimer();   // call to start timer
    } // end of onclickStart

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void onClickStop(View v) {
        cancelTimer();
        displayTime();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private void cancelTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // method used to start time
    public void startTimer(){
        // this code starts the timer
        ///////////////////////////////////////////////////
        // this is where we get the most used time
        // this code needs some work, not the best way to do this.
        // buttonWraper1 deals with wraping the save button back around from button 3 to 1
        // buttonWraper2 deals with moving the save button from 1 to 2
        if(save1 == 0 || buttonWraper1 == true){
            // this is to check to see if the seconds is already a stored value.
            if(seconds != save1 && seconds != save2 && seconds != save3 ) {
                buttonWraper1 = false; // set it to false cause where done wraping back
                buttonWraper2 = true; // where now going to move the next save to button 2
                save1 = seconds; // most pressed time 1
            }
        }else if(save2 == 0 || buttonWraper2 == true ) {
            if(seconds != save1 && seconds != save2 && seconds != save3 ) {
                buttonWraper2 = false;
                save2 = seconds; // most pressed time 2
            }
        }else{
            if(seconds != save1 && seconds != save2 && seconds != save3 ) {
                buttonWraper1 = true;   // wrap around from button 3 to button 1
                save3 = seconds;    // most pressed time 3
            }
        }
        ///////////////////////////////////////////////////
        // if no secounds left on the time, then kill it.
        if (seconds == 0) {
            cancelTimer();
        }
        // create a new timer.
        // if the timer exists and not null, then don't disturb it, go around this code.
        if (timer == null) { // if timer is null create a new timer
            // create a new timer object, using the CountDownTime constructor from CountDownTime class
            timer = new CountDownTimer(seconds * ONE_SECOND_IN_MILLIS, ONE_SECOND_IN_MILLIS) {
                //some local methods for the timer
                @Override
                public void onTick(long millisUntilFinished) {
                    Log.d(LOG_TAG, "Tick at " + millisUntilFinished); // log thing
                    // take the larger of the two numbers 0 and the second thats counting down
                    seconds = Math.max(0, seconds - 1);
                    displayTime();
                }
                @Override
                public void onFinish() {
                    seconds = 0;
                    timer = null; // timer is done, back to null, we can now start the timer again
                    displayTime();
                }
            };
            // start the dam thing
            timer.start(); //Start the countdown, Public method, part of CountDownTime class
        } // end of timer count down running
    }   // end of startTimer method

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Updates the time display.
    private void displayTime() {
        Log.d(LOG_TAG, "Displaying time " + seconds);
        TextView v = (TextView) findViewById(R.id.display); // the display text on the screen
        int m = seconds / 60;       // variables used for the dispaly
        int s = seconds % 60;
        Log.d(LOG_TAG, "s " + m);
        v.setText(String.format("%d:%02d", m, s));  // set the text to the display

        ////////////////////////////////////////////////////////////////////////////////////////////////
        // Manages the buttons.
        Button stopButton = (Button) findViewById(R.id.button_stop);
        Button startButton = (Button) findViewById(R.id.button_start);

        // changes the button color when time is no or off
        if(timer != null) {
            startButton.setBackgroundResource(R.color.LightGray);   // when the timer is on
        }else{
            startButton.setBackgroundResource(R.color.red); //when the timer is off
        }

        startButton.setEnabled(timer == null && seconds > 0);
        stopButton.setEnabled(timer != null && seconds > 0);
        /* homework note
        what should these buttons look like, and how should they act?

            "Initially, the three buttons should be blank.
                Then, they should be labeled with times, as the user sets the times."

        When a button containing a pre-defined time is pressed, you should:
            -Set the timer value to the time on the button.
            -Start the timer immediately, unless it is already started.
         */
        // are other 3 new buttons will go here
        final Button save1Button = (Button) findViewById(R.id.button7);
        final Button save2Button = (Button) findViewById(R.id.button2);
        final Button save3Button = (Button) findViewById(R.id.button8);

        if(save1 != 0) {                // if there is a number in the save1, then display that time.
            int min = save1 / 60;       // variables used for the display.
            int sec = save1 % 60;
            save1Button.setText(String.format("%d:%02d", min, sec));
        }

        if(save2 != 0 ) {
            int min2 = save2 / 60;
            int sec2 = save2 % 60;
            save2Button.setText(String.format("%d:%02d", min2, sec2));
        }

        if(save3 != 0 ) {
            int min3 = save3 / 60;
            int sec3 = save3 % 60;
            save3Button.setText(String.format("%d:%02d", min3, sec3));
        }

       // Listeners for the  3 lower buttons that store the most used times
        save1Button.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {  // call back method
                        if (save1 != 0 ) {
                                save1Button.setText(String.format("%d:%02d", getMin(seconds), getSec(seconds)));
                                // - HW_note - Set the timer value to the time on the button.
                                seconds = save1; // this is where the time gets set for this button
                                displayTime();
                                startTimer();
                        }
                    }
                }
        );
        save2Button.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {  // call back method
                        if (save2 != 0 ) {
                            save1Button.setText(String.format("%d:%02d", getMin(seconds), getSec(seconds)));
                            seconds = save2;
                            displayTime();
                            startTimer();
                        }
                    }
                }
        );
        save3Button.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {  // call back method
                        if (save3 != 0 ) {
                            save1Button.setText(String.format("%d:%02d",getMin(seconds), getSec(seconds)));
                            seconds = save3;
                            displayTime();
                            startTimer();
                        }
                    }
                }
        );
    }

    // get methods, effort to save some lines of code by making these calculations.
    public int getMin(int num){
        return num / 60;
    }

    public int getSec(int num ){
        return num % 60;
    }

}
package org.nicolasta.sleeptime;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;

import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;
import com.google.android.glass.view.WindowUtils;
import com.google.android.glass.widget.CardBuilder;
import com.google.android.glass.widget.CardScrollView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import adapters.alarmAdapter;

/**
 * Created by Nicolas on 27/04/2016.
 */
public class SleepNowActivity extends Activity{

    private CardScrollView mCardScroller;
    private List<CardBuilder> mCards;
    private GestureDetector mGestureDetector;


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().requestFeature(WindowUtils.FEATURE_VOICE_COMMANDS);
        mCardScroller = new CardScrollView(this);
        mCards = new ArrayList<CardBuilder>();


System.out.println("Launch FindAlarm()");
        findAlarm();
        mCardScroller.setAdapter(new alarmAdapter(mCards));

        // Handle the TAP event.
        mCardScroller.setOnItemClickListener(new
                                                     AdapterView.OnItemClickListener() {

                                                         @Override
                                                         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                             openOptionsMenu();
                                                         }
                                                     });

        mGestureDetector = createGestureDetector(this);
        setContentView(mCardScroller);
    }

    private GestureDetector createGestureDetector(Context context) {
        GestureDetector gestureDetector = new GestureDetector(context);

        //Create a base listener for generic gestures
        gestureDetector.setBaseListener( new GestureDetector.BaseListener() {
            @Override
            public boolean onGesture(Gesture gesture) {
                if (gesture == Gesture.TAP) {
                    openOptionsMenu();
                    return true;
                } else if (gesture == Gesture.TWO_TAP) {
                    // do something on two finger tap
                    return true;
                } else if (gesture == Gesture.SWIPE_RIGHT) {
                    // do something on right (forward) swipe
                    return true;
                } else if (gesture == Gesture.SWIPE_LEFT) {
                    // do something on left (backwards) swipe
                    return true;
                } else if (gesture == Gesture.SWIPE_DOWN){
                    finish();
                }
                return false;
            }
        });

        gestureDetector.setFingerListener(new com.google.android.glass.touchpad.GestureDetector.FingerListener() {
            @Override
            public void onFingerCountChanged(int previousCount, int currentCount) {
                // do something on finger count changes
            }
        });

        gestureDetector.setScrollListener(new com.google.android.glass.touchpad.GestureDetector.ScrollListener() {
            @Override
            public boolean onScroll(float displacement, float delta, float velocity) {
                // do something on scrolling
                return true;
            }
        });

        return gestureDetector;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCardScroller.activate();
    }

    @Override
    protected void onPause() {
        mCardScroller.deactivate();
        super.onPause();
    }
    private void findAlarm(){
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        int displayedHour;
        int displayedMinute;
        int addedHour=0;
        String dayTime= "AM";
        minute+= 14;
        for (int i=1; i<=6; i++){
            hour++;
            minute += 30;

            displayedHour= hour;
            displayedMinute = minute;

            if(minute >= 60){

                addedHour = minute/60;
                displayedMinute=  minute%60;
                displayedHour += addedHour;
                addedHour=0;

            }
                if(displayedHour >12 && displayedHour < 24){


                    displayedHour= displayedHour % 12;
                    dayTime = "PM";

                }
                if(displayedHour>= 24){

                    displayedHour = displayedHour% 12;
                    dayTime = "AM";

                }





            System.out.println("If you sleep now, you should set up the alarm at "+ displayedHour +":"+displayedMinute+" "+dayTime);
            CardBuilder card = new CardBuilder(this, CardBuilder.Layout.TEXT)
                    .setText("If you head to bed right now, try to wake up at:\n      "+ displayedHour +":"+displayedMinute+" "+dayTime+ "\n      Sleep's cycle: "+ i)

                    .setFootnote("A good night: 5-6 cycles     swipe →: cycle++");

            mCards.add(card);
        }
        mCardScroller.setSelection(5);
    }

}
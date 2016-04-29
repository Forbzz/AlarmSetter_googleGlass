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
import java.util.List;

import adapters.alarmAdapter;

/**
 * Created by Nicolas TA on 29/04/2016.
 */
public class WhenToSleepResultActivity extends Activity {




    private CardScrollView mCardScroller;
    private List<CardBuilder> mCards;
    private GestureDetector mGestureDetector;


    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().requestFeature(WindowUtils.FEATURE_VOICE_COMMANDS);
        mCardScroller = new CardScrollView(this);
        mCards = new ArrayList<CardBuilder>();



        resultView();
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

    private void resultView() {
    System.out.println("Rentré dans resultView");
        int hour = Integer.parseInt(WhenToSleepActivity.hourWake);
        int minute = Integer.parseInt(MinuteActivity.minuteWake);
        String am_pm = AmOrPmActivity.am_PmWake;

        int wakeHour =hour;
        int wakeMinute = minute;
        String wakeDayTime= am_pm;
        int displayedHour;
        int displayedMinute;
        int addedHour;

        hour -= 9;

        if(am_pm == "PM"){
            hour += 12;
        }

        if (minute < 14) {
            minute = 60 - (14 - minute);
            hour--;

        } else {
            minute -= 14;
        }

        for (int i = 0; i <= 4; i++) {
            System.out.println("Rentré dans resultView");

            displayedHour = hour;
            displayedMinute = minute;

            if (minute >= 60) {

                addedHour = minute / 60;
                displayedMinute = minute % 60;
                displayedHour += addedHour;
                addedHour = 0;

            }
            if (displayedHour > 12 && displayedHour < 24) {


                displayedHour = displayedHour % 12;
                am_pm = "PM";

            }
            if (displayedHour >= 24) {

                displayedHour = displayedHour % 12;
                am_pm = "AM";

            }

            if (displayedHour <= 0 && displayedHour <= 12) {

                am_pm = "AM";

            }

            if (displayedHour <0){
System.out.println("Displayed hour < 0 =" + displayedHour + am_pm);
                displayedHour = displayedHour % 12;
                displayedHour = 12 + displayedHour;
                am_pm ="PM";
                System.out.println("after transformation  =" + displayedHour + am_pm);
            }


            System.out.println("If you wake up at" + wakeHour+":"+wakeMinute+" "+wakeDayTime+", you should go to bed at:"+ displayedHour + ":" + displayedMinute + " " + am_pm+ "\n      Sleep's cycle: " + (6-i));
            CardBuilder card = new CardBuilder(this, CardBuilder.Layout.TEXT)
                    .setText("If you wake up at" + wakeHour+":"+wakeMinute+" "+wakeDayTime+", you should go to bed at:\n      " + displayedHour + ":" + displayedMinute + " " + am_pm + "\n      Sleep's cycle: " + (6-i))

                    .setFootnote("A good night: 5-6 cycles     swipe →: cycle--");

            hour++;
            minute += 30;

            mCards.add(card);
        }
        mCardScroller.setSelection(6);
    }

}

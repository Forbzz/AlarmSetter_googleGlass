package org.nicolasta.sleeptime;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;

import com.google.android.glass.media.Sounds;
import com.google.android.glass.view.WindowUtils;
import com.google.android.glass.widget.CardBuilder;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

/**
 * Created by Nicolas on 27/04/2016.
 */
public class MinuteActivity extends Activity {



    public static String minuteWake="00";

    private CardScrollView mCardScroller;

    private View mView;
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().requestFeature(WindowUtils.FEATURE_VOICE_COMMANDS);
        mView = whichHour();

        mCardScroller = new CardScrollView(this);
        mCardScroller.setAdapter(new CardScrollAdapter() {
            @Override
            public int getCount() {
                return 1;
            }

            @Override
            public Object getItem(int position) {
                return mView;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                return mView;
            }

            @Override
            public int getPosition(Object item) {
                if (mView.equals(item)) {
                    return 0;
                }
                return AdapterView.INVALID_POSITION;
            }
        });


        // Handle the TAP event.
        mCardScroller.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Plays disallowed sound to indicate that TAP actions are not supported.
                AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                am.playSoundEffect(Sounds.DISALLOWED);
            }
        });

        // Handle the TAP event.
        mCardScroller.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openOptionsMenu();
            }
        });


        setContentView(mCardScroller);



    }


    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS || featureId == Window.FEATURE_OPTIONS_PANEL) {
            getMenuInflater().inflate(R.menu.minutechoice, menu);
            return true;
        }
        return super.onCreatePanelMenu(featureId, menu);
    }


    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS || featureId == Window.FEATURE_OPTIONS_PANEL) {
            switch (item.getItemId()) {


                case R.id.minute0:
                    minuteWake="00";
                    nextActivity();
                    break;


                case R.id.minute15:
                    minuteWake="15";
                    nextActivity();
                    break;


                case R.id.minute30:
                    minuteWake="30";
                    nextActivity();
                    break;

                case R.id.minute45:
                    minuteWake="45";
                    nextActivity();
                    break;


                case R.id.minuteback:
                    previousActivity();
                    break;



            }
            return true;
        }
        return super.onMenuItemSelected(featureId, item);
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




    private View whichHour() {



        CardBuilder card = new CardBuilder(this, CardBuilder.Layout.TEXT)
                .setText(WhenToSleepActivity.hourWake+":??\nHow many minutes?");


        return card.getView();


    }

    private void nextActivity(){


        Intent am_pmAct = new Intent(this, AmOrPmActivity.class);
        startActivity(am_pmAct);
    }

    private void previousActivity(){


        Intent hourAct = new Intent(this, WhenToSleepActivity.class);
        startActivity(hourAct);
    }
}
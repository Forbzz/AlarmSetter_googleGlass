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

import java.util.List;

/**
 * Created by Nicolas on 27/04/2016.
 */
public class WhenToSleepActivity extends Activity {

    public static String hourWake="00";
    private CardScrollView mCardScroller;
    private List<CardBuilder> mCards;
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
            getMenuInflater().inflate(R.menu.hourchoice, menu);
            return true;
        }
        return super.onCreatePanelMenu(featureId, menu);
    }


    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS || featureId == Window.FEATURE_OPTIONS_PANEL) {
            switch (item.getItemId()) {


                case R.id.hour1:
                    hourWake="1";
                    nextActivity();
                    break;


                case R.id.hour2:
                    hourWake="2";
                    nextActivity();
                    break;


                case R.id.hour3:
                    hourWake="3";
                    nextActivity();
                    break;


                case R.id.hour4:
                    hourWake="4";
                    nextActivity();
                    break;


                case R.id.hour5:
                    hourWake="5";
                    nextActivity();
                    break;


                case R.id.hour6:
                    hourWake="6";
                    nextActivity();
                    break;


                case R.id.hour7:
                    hourWake="7";
                    nextActivity();
                    break;


                case R.id.hour8:
                    hourWake="8";
                    nextActivity();
                    break;


                case R.id.hour9:
                    hourWake="9";
                    nextActivity();
                    break;


                case R.id.hour10:
                    hourWake="10";
                    nextActivity();
                    break;


                case R.id.hour11:
                    hourWake="11";
                    nextActivity();
                    break;


                case R.id.hour12:
                    hourWake="12";
                    nextActivity();
                    break;

                case R.id.hourback:
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
                .setText(" What hour shall you wake up?");


        return card.getView();


    }


    private void nextActivity(){


        Intent minuteAct = new Intent(this, MinuteActivity.class);
        startActivity(minuteAct);
    }

    private void previousActivity(){


        Intent mainAct = new Intent(this, MainActivity.class);
        startActivity(mainAct);
    }
}